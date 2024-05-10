package org.Validator;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class Login {
    public static void login() {
        Scanner scanner = new Scanner(System.in);
        Integer intentosMax = ParametrosDTO.getIntentosMaxLogIn();
        Integer intentos = 0;

        //TO DO
        //Resolver pq no puedo iniciar sesion si ya me registre. Creo q el problema esta cuando comparo las pass
        do{
            intentos++;
            System.out.print(Mensajes.MSG_INGRESO_USERNAME);
            String username = scanner.nextLine();
            System.out.print(Mensajes.MSG_INGRESO_PASSWORD);
            String password = scanner.nextLine();

            if(ManejoArchivo.checkUser(username)){
                List<String> passwords = ManejoArchivo.getPasswords();
            }else System.out.println(Mensajes.MSG_USUARIO_NO_ENCONTRADO);

        } while (intentos < intentosMax);
    }
}
