package com.example.codequest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principale de l'application CodeQuest.
 * Point d'entrée de l'application Spring Boot.
 */
@SpringBootApplication
public class CodeQuestApplication {

	/**
	 * Méthode principale qui lance l'application.
	 * 
	 * @param args Arguments de la ligne de commande
	 */
	public static void main(String[] args) {
		SpringApplication.run(CodeQuestApplication.class, args);
	}

}
