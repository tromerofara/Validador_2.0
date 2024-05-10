package org.Validator;

public class ParametrosDTO {
    private static final Integer intentosMaxLogIn = 3;
    private static final String filePathDB = "src/main/java/org/Validator/Utils/BddUsuarios.txt";
    private static final String filePathProhibitedPasswords = "src/main/java/org/Validator/Utils/forbidden_passwords.txt";
    private static final String ALLOWED_CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*()-_=+";
    private static final Integer SALT_LENGTH = 16; // Longitud de la sal en bytes
    private static final Integer ITERATIONS = 10000; // Número de iteraciones para stretching
    private static final Integer cantIntentosFallidos = 3;


    public static Integer getIntentosMaxLogIn() {
        return intentosMaxLogIn;
    }

    public static String getFilePathDB() {
        return filePathDB;
    }
    public static String getFilePathProhibitedPasswords(){
        return filePathProhibitedPasswords;
    }

    public static String getAllowedCharacters() {
        return ALLOWED_CHARACTERS;
    }

    public static Integer getSaltLength() {
        return SALT_LENGTH;
    }

    public static Integer getITERATIONS() {
        return ITERATIONS;
    }
    public static Integer getCantIntentosFallidos(){
        return cantIntentosFallidos;
    }
}
