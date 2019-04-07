/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.soap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.my.ns.CarManufactureSOAP;
import org.my.ns.CarManufactureSOAPService;

/**
 *
 * @author justi
 */
public class main {
    
 public static void main(String[] args) throws IOException {
        // Stub-Objekt zum entfernten Aufruf erstellen
        CarManufactureSOAPService service = new CarManufactureSOAPService();
        CarManufactureSOAP validateUserWebservice = service.getCarManufactureSOAPPort();
        
        

        // Abgerufenes Ergebnis anzeigen
        System.out.println("========================");
        System.out.println("Die große Autoproduktion");
        System.out.println("========================");
        System.out.println();
        
        BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Benutzername: ");
        String username = fromKeyboard.readLine();
        System.out.println("Password: ");
        String password = fromKeyboard.readLine();
    }
}

