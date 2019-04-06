/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaeee.CarManifacture.webservice;






import dhbwka.wwi.vertsys.javaee.CarManufacture.bookings.ejb.BookingBean;
import dhbwka.wwi.vertsys.javaee.CarManufacture.bookings.jpa.BookingStatus;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Yannick Ulmrich
 */
public class CarService {

    @EJB
    BookingBean bookingBean;

    @Path("bookings")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public String getAll() {
       return bookingBean.findAll().get(0).getStatus().toString();
        
    }
}
    

