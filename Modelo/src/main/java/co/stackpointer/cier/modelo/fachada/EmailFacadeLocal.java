/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.utilidad.UtilFileItem;
import java.util.List;
import java.util.Properties;
import javax.ejb.Local;
import javax.mail.MessagingException;

/**
 *
 * @author rjay
 */
@Local
public interface EmailFacadeLocal {
    
    public List<Object[]> getEmailByDPALevel(String hierarchy, long level);
    
    public void sendEmail(String to, String subject, String message) throws MessagingException;
   
    public void sendEmail(String to, String subject, String message, List<UtilFileItem> attachments) throws MessagingException;

    public void sendEmail(Properties prop, String to, String subject, String message) throws MessagingException;

    public void sendEmail(Properties prop, String to, String subject, String message, List<UtilFileItem> attachments) throws MessagingException;

    public void sendEmail(String hierarchy, long level, String subject, String message, List<UtilFileItem> attachments);

}