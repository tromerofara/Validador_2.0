package org.Validator.register;

import org.Validator.Hasheo;
import org.Validator.ManejoArchivo;
import org.Validator.Mensajes;
import org.Validator.ParametrosDTO;

import java.util.Scanner;

public class Register {

    private static Integer intentosFallidos = 0;

    public static void register() {

        Scanner scanner = new Scanner(System.in);
        String username =getUsername(scanner);
        String password = getPassword(scanner,username);

        if (password !=null){

            String passHashed = Hasheo.hashearPass(password);
            ManejoArchivo.saveUserAndPass(username,passHashed);
        }

    }

    private static String getUsername(Scanner scanner) {
        String username;
        do {
            System.out.print(Mensajes.MSG_INGRESO_USERNAME);
            username = scanner.nextLine().toLowerCase();
            if (ManejoArchivo.checkUser(username)) {
                System.out.println(Mensajes.MSG_USUARIO_EXISTENTE);
            }
        } while (ManejoArchivo.checkUser(username));
        return username;
    }
    private static String getPassword(Scanner scanner, String username) {
        int intentosFallidos = 0;
        String password = null;

        while (intentosFallidos < ParametrosDTO.getCantIntentosFallidos()) {
            String respuesta = getPasswordOption(scanner);
            if (respuesta.equalsIgnoreCase("S")) {
                password = generateRandomPassword(scanner);
                break;
            } else if (respuesta.equalsIgnoreCase("N")) {
                password = getManualPassword(scanner, username, intentosFallidos);
                if (password != null) {
                    break;
                }
                intentosFallidos++;
            } else {
                System.out.println(Mensajes.MSG_OPCION_INVALIDA);
            }
        }

        if (intentosFallidos >= 3) {
            System.out.println(Mensajes.MSG_INTENTOS_FALLIDOS);
        }

        return password;
    }

    private static String getPasswordOption(Scanner scanner) {
        System.out.println(Mensajes.MSG_GENERAR_PASSWORD);
        return scanner.nextLine();
    }

    private static String generateRandomPassword(Scanner scanner) {
        System.out.println(Mensajes.MSG_LARGO_PASSWORD);
        int length = scanner.nextInt();
        scanner.nextLine();
        while (length < 6 || length > 64) {
            System.out.println(Mensajes.MSG_PASSWORD_LARGO_INVALIDO);
            System.out.println(Mensajes.MSG_LARGO_PASSWORD);
            length = scanner.nextInt();
            scanner.nextLine();
        }
        System.out.println(Mensajes.MSG_GENERANDO_PASSWORD);
        String password = PasswordGenerator.generateRandomPassword(length);
        System.out.println(Mensajes.MSG_PASSWORD_GENERADA + password);
        return password;
    }

    private static String getManualPassword(Scanner scanner, String username, int intentosFallidos) {
        System.out.print(Mensajes.MSG_INGRESO_PASSWORD);
        String password = scanner.nextLine();
        if (!PasswordValidation.validarPassword(username, password)) {
            if (PasswordValidation.esPasswordIgualAUsuario(username, password)) {
                System.out.println(Mensajes.MSG_USERNAME_PASSWORD_IGUALES + "(Intento " + (intentosFallidos + 1) + "/3)");
            } else if (PasswordValidation.esPasswordProhibido(password)) {
                System.out.println(Mensajes.MSG_PASSWORD_PROHIBIDA + "(Intento " + (intentosFallidos + 1) + "/3)");
            } else {
                System.out.println(Mensajes.MSG_PASSWORD_SINTAXIS_INVALIDA + "(Intento " + (intentosFallidos + 1) + "/3)");
            }
            return null;
        } else {
            return password;
        }
    }


}
