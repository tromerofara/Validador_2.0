package org.Validator;

import java.util.Scanner;

public class Main {
    public static void paginaInicio() {
        System.out.println(Mensajes.MSG_OPCIONES);

        Scanner scanner = new Scanner(System.in);
        int op = scanner.nextInt();
        scanner.nextLine();

        switch (op) {
            case 1:
                Session.login();
                break;
            case 2:
                Session.register();
                break;
            default:
                System.out.print(Mensajes.MSG_OPCION_INVALIDA);
                paginaInicio();
        }
    }

    public static void main(String[] args) {
        paginaInicio();
    }
}
