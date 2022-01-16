/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.sistema.Parametro;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import co.stackpointer.cier.modelo.utilidad.UtilFileItem;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import com.sun.mail.smtp.SMTPTransport;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.persistence.Query;

/**
 *
 * @author rjay
 */
@Stateless
public class EmailFacade implements EmailFacadeLocal {

    @Inject
    private TenantPersistenceManager tpm;
    @EJB
    private ParametrosFacadeLocal paramLocal;
    private String emailConfigUser;
    private String emailConfigPswd;
    private String emailSmtpHost;
    private String emailSmtpPort;

    private String getParametroValueByCode(String paramCode) {
        try {
            Parametro param = paramLocal.consultarParametro(paramCode);
            return param.getValor();
        } catch (Exception err) {
            return "";
        }
    }

    @Override
    public void sendEmail(String to, String subject, String message) throws MessagingException {
        sendEmailBase(to, subject, message, null);
    }

    private void sendEmailBase(String to, String subject, String message, List<UtilFileItem> emailAttachments) throws MessagingException {
        try {
            emailConfigUser = getParametroValueByCode("EMAIL_CONFIG_USER");
            emailConfigPswd = getParametroValueByCode("EMAIL_CONFIG_PSWD");
            emailSmtpHost = getParametroValueByCode("EMAIL_SMTP_HOST");
            emailSmtpPort = getParametroValueByCode("EMAIL_SMTP_PORT");
            Properties prop = new Properties();
            prop.put("mail.smtp.host", emailSmtpHost);
            prop.put("mail.smtp.port", emailSmtpPort);
            prop.put("mail.smtp.ssl.trust", emailSmtpHost);
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.from", emailConfigUser);
            prop.put("mail.user", emailConfigUser);
            prop.put("mail.password", emailConfigPswd);
            sendEmailBase(prop, to, subject, message, emailAttachments);
        } catch (IOException ex) {
            Logger.getLogger(EmailFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendEmail(Properties prop, String to, String subject, String message) throws MessagingException {
        try {
            sendEmailBase(prop, to, subject, message, null);
        } catch (IOException ex) {
            Logger.getLogger(EmailFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendEmail(Properties prop, String to, String subject, String message, List<UtilFileItem> attachments) throws MessagingException {
        try {
            sendEmailBase(prop, to, subject, message, attachments);
        } catch (IOException ex) {
            Logger.getLogger(EmailFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sendEmailBase(Properties properties, String emailTo, String emailSubject, String emailMessage, List<UtilFileItem> emailAttachments) throws MessagingException, IOException {
        try {
            // Se agrega encabezado de certificado
            properties.put("mail.smtp.ssl.trust", properties.getProperty("mail.smtp.host"));
        } catch (Exception err) {
        }
        //creamos una sesión
        Session session = Session.getInstance(properties, null);
        //creamos el mensaje a enviar
        Message mensaje = new MimeMessage(session);
        //agregamos la dirección que envía el email
        mensaje.setFrom(new InternetAddress(properties.getProperty("mail.from")));
        StringTokenizer emailsSt = new StringTokenizer(emailTo, ";,");
        while (emailsSt.hasMoreTokens()) {
            String email = emailsSt.nextToken();
            try {
                //agregamos las direcciones de email que reciben el email, en el primer parametro envíamos el tipo de receptor
                mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                //Message.RecipientType.TO;  para
                //Message.RecipientType.CC;  con copia
                //Message.RecipientType.BCC; con copia oculta
            } catch (Exception ex) {
                //en caso que el email esté mal formado lanzará una exception y la ignoramos
            }
        }
        mensaje.setSubject(emailSubject);
        //agregamos una fecha al email
        mensaje.setSentDate(new Date());
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(emailMessage, "text/html; charset=utf-8");
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
        if (emailAttachments != null) {
            for (UtilFileItem attachment : emailAttachments) {
                //agregamos los adjuntos
                messageBodyPart = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(attachment.getInputstream(), attachment.getContentType());
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(attachment.getFileName());
                multipart.addBodyPart(messageBodyPart);
            }
        }
        mensaje.setContent(multipart);
        SMTPTransport transport = (SMTPTransport) session.getTransport("smtp");
        try {
            //conectar al servidor
            transport.connect(properties.getProperty("mail.user"), properties.getProperty("mail.password"));
            //enviar el mensaje
            transport.sendMessage(mensaje, mensaje.getAllRecipients());
        } finally {
            //cerrar la conexión
            transport.close();
        }
    }

    @Override
    public List getEmailByDPALevel(String hierarchy, long level) {
        StringBuilder sql = new StringBuilder();
        if (ParamBaseDatos.ORACLE.equals(UtilProperties.getDbMotor())) {
            sql.append("with tree_view (id_tnt, id, codigo, descripcion, nivel_configuracion, padre, id_hierarchy) as ");
            sql.append("(select id_tnt, id, codigo, descripcion, nivel_configuracion, padre, cast(id as varchar(255)) ");
            sql.append("   from dpa_niveles ");
            sql.append("  where nivel_configuracion = 0 ");
            sql.append(" union all ");
            sql.append(" select h.id_tnt, h.id, h.codigo, h.descripcion, h.nivel_configuracion, h.padre, concat(p.id_hierarchy, cast(h.id as varchar (255))) ");
            sql.append("   from dpa_niveles h, tree_view p ");
            sql.append("  where h.id_tnt = p.id_tnt and p.id = h.padre and h.nivel_configuracion <> 0) ");
            sql.append("select distinct au.correo ");
            sql.append("  from aya_usuarios au ");
            sql.append(" where exists (select 0 ");
            sql.append("                 from tree_view tv ");
            sql.append("                where tv.id_tnt = ?id_tnt ");
            sql.append("                  and tv.id_hierarchy like ?id_hierarchy ");
            sql.append("                  and nvl(au.id_nivel_dpa, 0) = tv.nivel_configuracion ");
            sql.append("                  and au.id_tnt = tv.id_tnt ");
            sql.append("                  and nvl(au.id_nivel_especifico, ?id_level) = tv.id ");
            sql.append("                  and au.estado = 'A') ");
            tpm.getEm().clear();
            Query query = tpm.getEm().createNativeQuery(sql.toString());
            query.setParameter("id_tnt", tpm.getCurrentTenant());
            query.setParameter("id_hierarchy", hierarchy + "%");
            query.setParameter("id_level", level);
            List r = query.getResultList();
            return r;
        } else {
            sql.append("with recursive tree_view (id_tnt, id, codigo, descripcion, nivel_configuracion, padre, id_hierarchy) as ");
            sql.append("(select id_tnt, id, codigo, descripcion, nivel_configuracion, padre, id::text as id_hierarchy ");
            sql.append("   from cier.dpa_niveles ");
            sql.append("  where nivel_configuracion = 0 ");
            sql.append("  union all ");
            sql.append(" select h.id_tnt, h.id, h.codigo, h.descripcion, h.nivel_configuracion, h.padre, concat(p.id_hierarchy, h.id::text) as id_hierarchy ");
            sql.append("   from cier.dpa_niveles h, tree_view p ");
            sql.append("  where h.id_tnt = p.id_tnt and p.id = h.padre and h.nivel_configuracion <> 0) ");
            sql.append(" select distinct au.correo ");
            sql.append("   from cier.aya_usuarios au ");
            sql.append("  where exists (select 0 ");
            sql.append("                  from tree_view tv ");
            sql.append("                 where tv.id_tnt = ?id_tnt ");
            sql.append("                   and tv.id_hierarchy like ?id_hierarchy ");
            sql.append("                   and coalesce(au.id_nivel_dpa, 0) = tv.nivel_configuracion ");
            sql.append("                   and au.id_tnt = tv.id_tnt ");
            sql.append("                   and cast(coalesce(au.id_nivel_especifico, ?id_level) as int) = tv.id ");
            sql.append("                   and au.estado = 'A') ");
            tpm.getEm().clear();
            Query query = tpm.getEm().createNativeQuery(sql.toString());
            query.setParameter("id_tnt", tpm.getCurrentTenant());
            query.setParameter("id_hierarchy", hierarchy + "%");
            query.setParameter("id_level", level + "");
            List r = query.getResultList();
            return r;
        }
    }

    @Override
    public void sendEmail(String hierarchy, long level, String subject, String message, List<UtilFileItem> attachments) {
        emailConfigUser = getParametroValueByCode("EMAIL_CONFIG_USER");
        emailConfigPswd = getParametroValueByCode("EMAIL_CONFIG_PSWD");
        emailSmtpHost = getParametroValueByCode("EMAIL_SMTP_HOST");
        emailSmtpPort = getParametroValueByCode("EMAIL_SMTP_PORT");
        Properties prop = new Properties();
        prop.put("mail.smtp.host", emailSmtpHost);
        prop.put("mail.smtp.port", emailSmtpPort);
        prop.put("mail.smtp.ssl.trust", emailSmtpHost);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.from", emailConfigUser);
        prop.put("mail.user", emailConfigUser);
        prop.put("mail.password", emailConfigPswd);
        List list = this.getEmailByDPALevel(hierarchy, level);
        for (Object obj1 : list) {
            try {
                sendEmailBase(prop, obj1.toString(), subject, message, attachments);
            } catch (Exception ex) {
                Logger.getLogger(EmailFacade.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void sendEmail(String to, String subject, String message, List<UtilFileItem> attachments) throws MessagingException {
        emailConfigUser = getParametroValueByCode("EMAIL_CONFIG_USER");
        emailConfigPswd = getParametroValueByCode("EMAIL_CONFIG_PSWD");
        emailSmtpHost = getParametroValueByCode("EMAIL_SMTP_HOST");
        emailSmtpPort = getParametroValueByCode("EMAIL_SMTP_PORT");
        Properties prop = new Properties();
        prop.put("mail.smtp.host", emailSmtpHost);
        prop.put("mail.smtp.port", emailSmtpPort);
        prop.put("mail.smtp.ssl.trust", emailSmtpHost);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.from", emailConfigUser);
        prop.put("mail.user", emailConfigUser);
        prop.put("mail.password", emailConfigPswd);
        try {
            sendEmailBase(prop, to, subject, message, attachments);
        } catch (Exception ex) {
            Logger.getLogger(EmailFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}