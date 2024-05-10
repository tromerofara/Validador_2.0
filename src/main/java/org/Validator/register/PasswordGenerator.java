package org.Validator.register;

import org.Validator.ParametrosDTO;

import java.security.SecureRandom;

public class PasswordGenerator {
    public static String generateRandomPassword(int length) {

        String ALLOWED_CHARACTERS = ParametrosDTO.getAllowedCharacters();
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ParametrosDTO.getAllowedCharacters().length());
            password.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }

        // Verificamos si la contraseña generada de manera random es prohibida, es decir, si está en la lista de contraseñas prohibidas (casi imposible)
        while (PasswordValidation.esPasswordProhibido(password.toString())) {
            password = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
                password.append(ALLOWED_CHARACTERS.charAt(randomIndex));
            }
        }

        return password.toString();
    }
}
