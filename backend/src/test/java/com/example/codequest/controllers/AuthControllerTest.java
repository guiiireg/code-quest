package com.example.codequest.controllers;

import com.example.codequest.Role;
import com.example.codequest.RoleRepository;
import com.example.codequest.User;
import com.example.codequest.UserRepository;
import com.example.codequest.payload.request.LoginRequest;
import com.example.codequest.payload.request.SignupRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;

/**
 * Tests d'intégration et de sécurité pour le contrôleur d'authentification AuthController.
 */
@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    /**
     * Initialise les mocks requis avant l'exécution de chaque test.
     */
    @BeforeEach
    void setup() {
        Role userRole = new Role("role-user", "ROLE_USER");
        Mockito.when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(userRole));
        
        Role adminRole = new Role("role-admin", "ROLE_ADMIN");
        Mockito.when(roleRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.of(adminRole));
    }

    /**
     * Vérifie que l'inscription d'un nouvel utilisateur réussit lorsque
     * le nom d'utilisateur et l'email ne sont pas encore pris.
     * 
     * @throws Exception En cas d'erreur lors de l'exécution de la requête MockMvc
     */
    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        SignupRequest signupRequest = new SignupRequest("testuser", "test@test.com", "password", null);
        
        Mockito.when(userRepository.existsByUsername("testuser")).thenReturn(false);
        Mockito.when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        Mockito.when(userRepository.save(any(User.class))).thenAnswer(i -> i.getArguments()[0]);

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    /**
     * Vérifie que l'inscription échoue avec un code de retour HTTP 400 (Bad Request)
     * si le nom d'utilisateur demandé est déjà pris.
     * 
     * @throws Exception En cas d'erreur lors de l'exécution de la requête MockMvc
     */
    @Test
    void shouldFailRegistrationIfUsernameTaken() throws Exception {
        SignupRequest signupRequest = new SignupRequest("testuser", "test2@test.com", "password", null);
        
        Mockito.when(userRepository.existsByUsername("testuser")).thenReturn(true);

        mockMvc.perform(post("/api/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Username is already taken!"));
    }

    /**
     * Vérifie qu'un utilisateur existant peut s'authentifier avec succès et obtenir un token JWT.
     * 
     * @throws Exception En cas d'erreur lors de l'exécution de la requête MockMvc
     */
    @Test
    void shouldAuthenticateUserSuccessfully() throws Exception {
        User user = new User("123", "testuser", "test@test.com", encoder.encode("password"));
        Role userRole = new Role("role-user", "ROLE_USER");
        user.getRoles().add(userRole);
        
        Mockito.when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        LoginRequest loginRequest = new LoginRequest("testuser", "password");

        mockMvc.perform(post("/api/auth/signin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }
}
