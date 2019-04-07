/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dhbwka.wwi.vertsys.javaee.CarManufacture.soap;

import dhbwka.wwi.vertsys.javaee.CarManufacture.common.ejb.UserBean;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 *
 * @author justi
 */

@Stateless
@WebService(targetNamespace = "http://my.org/ns/")
public class CarManufactureSOAP {
    
    
    @EJB
    private UserBean userBean;
    
    
    @WebMethod
    @WebResult (name="status")
    public String validateUser(
            @WebParam(name="username") String username,
            @WebParam(name="password") String password
    )
            throws UserBean.InvalidCredentialsException, UserBean.AccessRestrictedException{
        this.userBean.validateUser(username, password);
    
    return "OK";
}
}