package org.Validator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Hasheo {
    private static final Logger logger = Logger.getLogger(Hasheo.class.getName());
    public static String hashearPass(String password) {
        try {
            byte[] salt = generateSalt();
            byte[] saltedPassword = doSalt(password, salt);
            saltedPassword = hashAndStreching(saltedPassword);

            // Convertir el resultado a una representación en base64
            String hashedPassword = Base64.getEncoder().encodeToString(saltedPassword);

            // Preparar el resultado con la sal para poder verificar la contraseña posteriormente
            return Base64.getEncoder().encodeToString(salt) + "$" + hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Error al calcular el hash de la contraseña: " + e.getMessage(), e);
            return null;
        }

    }
    public static Boolean compareHashedPass(String storedPassword,String password) {
        try {
            byte[] salt = giveMeSaltOfPass(storedPassword);
            byte[] saltedPassword = doSalt(password, salt);
            String storedHash = giveMeHash(storedPassword);
            saltedPassword = hashAndStreching(saltedPassword);

            // Convertir el resultado a una representación en base64
            String hashedPassword = Base64.getEncoder().encodeToString(saltedPassword);

            return hashedPassword.equals(storedHash);
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.SEVERE, "Error al calcular el hash de la contraseña: " + e.getMessage(), e);
            return null;
        }

    }
    private static byte[] giveMeSaltOfPass(String password){
        String[] parts = password.split("\\$");
        byte[] salt = Base64.getDecoder().decode(parts[0]);
        return  salt;
    }
    private static String giveMeHash(String password){
        String[] parts = password.split("\\$");
        String hash = parts[1];
        return  hash;
    }

    private static byte[] hashAndStreching(byte[] saltedPassword) throws NoSuchAlgorithmException {
        // Aplicar hashing con stretching
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        for (int i = 0; i < ParametrosDTO.getITERATIONS(); i++) {
            saltedPassword = digest.digest(saltedPassword);
        }
        return saltedPassword;
    }

    private static byte[] doSalt(String password, byte[] salt) {
        // Aplicar salting al password
        byte[] passwordBytes = password.getBytes();
        byte[] saltedPassword = new byte[passwordBytes.length + salt.length];
        System.arraycopy(passwordBytes, 0, saltedPassword, 0, passwordBytes.length);
        System.arraycopy(salt, 0, saltedPassword, passwordBytes.length, salt.length);
        return saltedPassword;
    }

    private static byte[] generateSalt() {
        // Generar una sal aleatoria
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[ParametrosDTO.getSaltLength()];
        random.nextBytes(salt);
        return salt;
    }
}
