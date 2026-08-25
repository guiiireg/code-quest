package com.example.codequest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Spring application context loading integration test.
 */
@SpringBootTest
class CodeQuestApplicationTests {

    /**
     * Verifies that the Spring Boot application context loads properly.
     */
    @Test
    void contextLoads() {
        org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder enc = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        System.out.println(">>> BCRYPT_HASH_FOR_PASSWORD: " + enc.encode("password"));
    }

}

