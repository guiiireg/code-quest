package com.example.codequest.security.services;

import com.example.codequest.models.User;
import com.example.codequest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service pour charger les détails de l'utilisateur.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    /**
     * Charge les détails d'un utilisateur à partir de son nom d'utilisateur.
     * Utilisé par Spring Security pour vérifier les informations d'identification.
     * 
     * @param username Le nom d'utilisateur à charger
     * @return Les détails de l'utilisateur correspondants sous la forme d'un objet UserDetails
     * @throws UsernameNotFoundException Si l'utilisateur n'est pas trouvé dans la base de données
     */
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

        return UserDetailsImpl.build(user);
    }
}
