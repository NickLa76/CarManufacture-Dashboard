/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaeee.CarManifacture.webservice;






import dhbwka.wwi.vertsys.javaee.CarManufacture.tasks.ejb.TaskBean;
import dhbwka.wwi.vertsys.javaee.CarManufacture.tasks.jpa.TaskStatus;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author lampern
 */
public class CarService {

    @EJB
    TaskBean taskBean;

    @Path("tasks")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String getAll() {
       return taskBean.findAll().get(0).getStatus().toString();
        
    }
}
    

