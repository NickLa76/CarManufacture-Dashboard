/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import org.my.ns.AccessRestrictedException_Exception;
import org.my.ns.CarManufactureSOAP;
import org.my.ns.CarManufactureSOAPService;
import org.my.ns.InvalidCredentialsException_Exception;
import org.my.ns.User;

/**
 *
 * @author justi
 */
public class main {
    
 public static void main(String[] args) throws IOException, AccessRestrictedException_Exception, InvalidCredentialsException_Exception {
        // Stub-Objekt zum entfernten Aufruf erstellen
        CarManufactureSOAPService service = new CarManufactureSOAPService();
        CarManufactureSOAP validateUserWebservice = service.getCarManufactureSOAPPort();
        CarManufactureSOAP findeAlle=service.getCarManufactureSOAPPort();
        
        

        // Abgerufenes Ergebnis anzeigen
        System.out.println("===============================================");
        System.out.println("    Benutzeranalyse für Systemadministrator    ");
        System.out.println("===============================================");
        System.out.println();
        
        BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Benutzername: ");
        String username = fromKeyboard.readLine();
        System.out.println("Password: ");
        String password = fromKeyboard.readLine();
    
        validateUserWebservice.validateUser(username, password);

            System.out.println("");
            System.out.println("");
            System.out.println("Ausgabe aller registrierten User: ");
            System.out.println("");

            List<User> Allusers = findeAlle.findeAlle();  // Entfernter Aufruf

            for (User user : Allusers) {
                System.out.println("Mitarbeiternummer: " + user.getMitarbeiternummer());
                System.out.println("Benutzername:      " + user.getUsername());
                System.out.println("Vorname:           " + user.getFirstName());
                System.out.println("Nachname:          " + user.getLastName());
                
                System.out.println();

            }
}
}
