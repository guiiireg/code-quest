package com.codequest.backend.repository;

import com.codequest.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserByUsername() {
        // Given
        User user = User.builder()
                .username("testuser")
                .email("test@example.com")
                .password("hashed_password")
                .build();
        userRepository.save(user);

        // When
        Optional<User> found = userRepository.findByUsername("testuser");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@example.com");
    }

    @Test
    void shouldFindUserByEmail() {
        // Given
        User user = User.builder()
                .username("jane_doe")
                .email("jane@example.com")
                .password("hashed_password")
                .build();
        userRepository.save(user);

        // When
        Optional<User> found = userRepository.findByEmail("jane@example.com");

        // Then
        assertThat(found).isPresent();
        assertThat(found.get().getUsername()).isEqualTo("jane_doe");
    }

    @Test
    void shouldCheckIfUsernameExists() {
        // Given
        User user = User.builder()
                .username("existing_user")
                .email("user@example.com")
                .password("pwd")
                .build();
        userRepository.save(user);

        // When
        boolean exists = userRepository.existsByUsername("existing_user");
        boolean doesNotExist = userRepository.existsByUsername("non_existing");

        // Then
        assertThat(exists).isTrue();
        assertThat(doesNotExist).isFalse();
    }

    @Test
    void shouldCheckIfEmailExists() {
        // Given
        User user = User.builder()
                .username("another_user")
                .email("another@example.com")
                .password("pwd")
                .build();
        userRepository.save(user);

        // When
        boolean exists = userRepository.existsByEmail("another@example.com");
        boolean doesNotExist = userRepository.existsByEmail("missing@example.com");

        // Then
        assertThat(exists).isTrue();
        assertThat(doesNotExist).isFalse();
    }
}
