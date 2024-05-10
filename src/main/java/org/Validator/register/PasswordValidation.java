package org.Validator.register;

import org.Validator.ParametrosDTO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation {

    static boolean esPasswordProhibido(String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(ParametrosDTO.getFilePathProhibitedPasswords()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (password.equalsIgnoreCase(line.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de contraseñas prohibidas: " + e.getMessage());
        }
        return false;
    }
    public static boolean validarPassword(String username, String password) {
        return esPasswordSintacticamenteValido(password) && !esPasswordProhibido(password) && !esPasswordIgualAUsuario(username, password);
    }
    private static boolean esPasswordSintacticamenteValido(String password) {
        // Aplicar normalización de caracteres Unicode
        String normalizedPassword = Normalizer.normalize(password, Normalizer.Form.NFKC);
        Pattern pattern = Pattern.compile("[\\p{Graph} ]*"); // Utilizamos \\p{Graph} para permitir todos los caracteres imprimibles y el espacio
        Matcher matcher = pattern.matcher(normalizedPassword);
        return password.length() >= 8 && password.length() <= 64 && matcher.matches();
    }
    public static boolean esPasswordIgualAUsuario(String username, String password) {
        return username.equals(password);
    }

}
