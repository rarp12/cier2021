/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.utilidad;

import co.stackpointer.cier.modelo.entidad.sistema.Idioma;
import co.stackpointer.cier.modelo.tipo.ParamBaseDatos;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

/**
 *
 * @author eviloria
 */
public class UtilProperties {
 
    private static Properties properties; 
    private static long dbMotor; 
    private static Idioma idiomaPais;
   
    public Properties loadQueries(long dbMotor, Idioma idIdiomaPais) {
        try {
            if (properties == null || properties.isEmpty()) {
                properties = new Properties();
                UtilProperties.dbMotor = dbMotor;
                String bd = ParamBaseDatos.ORACLE.equals(UtilProperties.dbMotor) ? "QueriesOracle.xml" : "QueriesPostgres.xml";
                FacesContext contexto = FacesContext.getCurrentInstance();
                String directorioBase = ((ServletContext) contexto.getExternalContext().getContext()).getRealPath("/resources/xmlBD/" + bd);
                InputStream is = new FileInputStream(directorioBase);
                properties.loadFromXML(is);
            }
            UtilProperties.idiomaPais = idIdiomaPais;
        } catch (Exception exception) {
        }
        return properties;
    }

    public static Properties  getProperties() {
        return properties;
    }      

    public static long getDbMotor() {
        return dbMotor;
    }

    public static Idioma getIdiomaPais() {
        return idiomaPais;
    }
    
}
