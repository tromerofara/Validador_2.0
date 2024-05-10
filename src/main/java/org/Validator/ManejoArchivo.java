package org.Validator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ManejoArchivo {

    public static void checkUserAndPassword(String userNew, String passNew, Integer intentos) {
        File file = new File(ParametrosDTO.getFilePathDB());
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            boolean usuarioEncontrado = buscarUsuario(br, userNew, passNew, intentos);
            if (!usuarioEncontrado) {
                System.out.println(Mensajes.MSG_USUARIO_NO_ENCONTRADO);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean buscarUsuario(BufferedReader br, String userNew, String passNew, Integer intentos) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] parts = line.split(";");
            String usernameGuardado = parts[0]; // Obtener el nombre de usuario
            String storedHashedPassword = parts[1]; // Obtener la contraseña hasheada
            if (usernameGuardado.equals(userNew)) {
                return validarContraseña(passNew, storedHashedPassword, intentos);
            }
        }
        return false;
    }

    private static boolean validarContraseña(String passNew, String storedHashedPassword, Integer intentos) {
        if (storedHashedPassword.equals(passNew)) {
            System.out.println(Mensajes.MSG_INICIO_EXITOSO);
            return true;
        } else {
            System.out.println(Mensajes.MSG_PASSWORD_INCORRECTA + "( Intentos restantes: " + ((ParametrosDTO.getIntentosMaxLogIn()) - intentos) + "/" + ParametrosDTO.getIntentosMaxLogIn() + ")");
            return false;
        }
    }

    public static boolean checkUser(String userSended) {
        Boolean alreadyExist = false;
        File file = new File(ParametrosDTO.getFilePathDB());
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            boolean usuarioEncontrado = false;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String usernameGuardado = parts[0]; // Obtener el nombre de usuario
                if (usernameGuardado.equals(userSended)) {
                    alreadyExist=true;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return alreadyExist;
    }


    public static void saveUserAndPass(String username, String passHashed) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ParametrosDTO.getFilePathDB(), true))) {
            writer.write(username + ";" + passHashed + "\n");
        } catch (IOException e) {
            System.err.println(Mensajes.MSG_ERROR_GUARDADO + ": " + e.getMessage());
        }
    }

    public static List<String> getPasswords(String filePath) {
        List<String> passwords = new ArrayList<>();

        try {
            // Leer todas las líneas del archivo
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            // Iterar sobre cada línea para obtener usuario y contraseña
            for (String line : lines) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    passwords.add(parts[1]); // Agregar la contraseña a la lista
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return passwords;
    }
}
