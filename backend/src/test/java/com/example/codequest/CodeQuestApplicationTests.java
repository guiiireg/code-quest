package com.example.codequest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Test de chargement du contexte de l'application CodeQuest.
 */
@SpringBootTest
class CodeQuestApplicationTests {

    /**
     * Vérifie que le contexte de l'application Spring se charge correctement.
     */
    @Test
    void contextLoads() {
        org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder enc = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        System.out.println(">>> BCRYPT_HASH_FOR_PASSWORD: " + enc.encode("password"));
    }

}
