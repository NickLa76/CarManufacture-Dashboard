/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaeee.CarManifacture.webservice;

/**
 *
 * @author LAMPERN
 */

import dhbwka.wwi.vertsys.javaeee.CarManifacture.webservice.CarService;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
/**
 *
 * @author LAMPERN
 */
        @ApplicationPath("rest")
public class ApplicationConfig extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();

        // Hier für jede Webservice-Klasse eine Zeile hinzufügen
        resources.add(CarService.class);
        
        return resources;
    }

}
