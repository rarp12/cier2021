/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.fachada;

import co.stackpointer.cier.modelo.entidad.acceso.Usuario;
import co.stackpointer.cier.modelo.entidad.digitado.Adjunto;
import co.stackpointer.cier.modelo.entidad.digitado.InstrumentoDig;
import co.stackpointer.cier.modelo.entidad.digitado.Papelera;
import co.stackpointer.cier.modelo.entidad.dpa.ConfiguracionNivel;
import co.stackpointer.cier.modelo.entidad.dpa.Nivel;
import co.stackpointer.cier.modelo.entidad.sistema.Parametro;
import co.stackpointer.cier.modelo.excepcion.ErrorGeneral;
import co.stackpointer.cier.modelo.tipo.ParamSistema;
import co.stackpointer.cier.modelo.utilidad.UtilCadena;
import co.stackpointer.cier.modelo.utilidad.UtilConsulta;
import co.stackpointer.cier.modelo.utilidad.UtilProperties;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 *
 * @author Usuario
 */
@Stateless
public class ArchivoFacade implements ArchivoFacadeLocal{
    
    @Inject
    private TenantPersistenceManager tpm;
    
    @EJB
    private ParametrosFacadeLocal parametrosFacade;
    
    @EJB
    private InstrumentoFacadeLocal instrumentoFacade;

    @Override
    public boolean subirArchivo(File file, String prefix, InputStream inputStream) {
        File fileToDirectory;
        boolean sw;
        InputStream is =null;
        OutputStream out =  null;
        try {
            fileToDirectory = new File(file, prefix);
            if (fileToDirectory.exists()) {
                sw = false;
            } else {
                is = inputStream;
                out = new FileOutputStream(fileToDirectory);
                byte buf[] = new byte[1024];
                int len;
                while ((len = is.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }                
                sw = true;
            }
            return sw;
        } catch (Exception e) {
            throw new ErrorGeneral("Error al subir el archivo", e.getMessage());
        }finally{
            try {
                is.close();
            } catch (IOException ex) {
            }
            try {
                out.close();
            } catch (IOException ex) {
            }
        }
    }
    
    @Override
    public String getPathname(Long idInstrumentoDig) {
        try {
            Parametro parametro = parametrosFacade.consultarParametro(ParamSistema.RUTA_ADJUNTOS.name());
            String pathname = parametro.getValor();
            String separador = System.getProperty("file.separator");

            StringBuilder subdirectorios = new StringBuilder();

            InstrumentoDig instrumento = instrumentoFacade.consultarInstrumentoPorCodigo(idInstrumentoDig);
            if (!UtilCadena.isNullOVacio(instrumento)) {
                if (!UtilCadena.isNullOVacio(instrumento.getNivel0())) {
                    subdirectorios.append(instrumento.getNivel0().getId().toString());
                    subdirectorios.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getNivel1())) {
                    subdirectorios.append(instrumento.getNivel1().getId().toString());
                    subdirectorios.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getNivel2())) {
                    subdirectorios.append(instrumento.getNivel2().getId().toString());
                    subdirectorios.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getNivel3())) {
                    subdirectorios.append(instrumento.getNivel3().getId().toString());
                    subdirectorios.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getNivel4())) {
                    subdirectorios.append(instrumento.getNivel4().getId().toString());
                    subdirectorios.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getNivel5())) {
                    subdirectorios.append(instrumento.getNivel5().getId().toString());
                    subdirectorios.append(separador);
                }

                pathname = pathname + separador + subdirectorios.toString();
                crearDirectorio(pathname);

                return pathname;
            } else {
                throw new ErrorGeneral("Instrumento no encontrado", "revisar el parametro idInstrumentoDig de la funcion getPathname");
            }
        } catch (Exception exp) {
            throw new ErrorGeneral("Error al obtener directorio de archivo", exp.getMessage());
        }
    }
    
    @Override
    public String getPrefix(String fileName, Long idInstrumentoDig) {
        try {
            StringBuilder prefix = new StringBuilder();
            String separador = "_";
            String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());

            InstrumentoDig instrumento = instrumentoFacade.consultarInstrumentoPorCodigo(idInstrumentoDig);
            if (!UtilCadena.isNullOVacio(instrumento)) {
                if (!UtilCadena.isNullOVacio(instrumento.getNivel0())) {
                    prefix.append(instrumento.getNivel0().getId().toString());
                    prefix.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getNivel1())) {
                    prefix.append(instrumento.getNivel1().getId().toString());
                    prefix.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getNivel2())) {
                    prefix.append(instrumento.getNivel2().getId().toString());
                    prefix.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getNivel3())) {
                    prefix.append(instrumento.getNivel3().getId().toString());
                    prefix.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getNivel4())) {
                    prefix.append(instrumento.getNivel4().getId().toString());
                    prefix.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getNivel5())) {
                    prefix.append(instrumento.getNivel5().getId().toString());
                    prefix.append(separador);
                }
                if (!UtilCadena.isNullOVacio(instrumento.getPredio())) {
                    prefix.append(instrumento.getBoletaCensal());
                    prefix.append("-");
                    prefix.append(instrumento.getVersion());
                    prefix.append(separador);
                }
                String consecutivo = obtenerSecuenciaAdjunto();
                prefix.append(consecutivo);
                prefix.append(extension);
            } else {
                throw new ErrorGeneral("Instrumento no encontrado", "revisar el parametro idInstrumentoDig de la funcion getPrefix");
            }

            return prefix.toString();
        } catch (Exception exp) {
            throw new ErrorGeneral("Error al obtener nombre de archivo", exp.getMessage());
        }
    }
    
    @Override
    public boolean eliminarArchivo(Adjunto adjunto, Usuario usuario) {
        boolean sw;
        try {
            //Realizamos el registro en la tabla papelera de adjuntos
            Papelera papelera = new Papelera();
            papelera.setRuta(adjunto.getRuta());
            papelera.setTipo(adjunto.getTipo());
            papelera.setNombre(adjunto.getNombre());
            papelera.setRespuestaDigitada(adjunto.getRespuestaDigitada());
            papelera.setUsuario(usuario);            

            //creamos el directorio de la papelera
            Parametro parametro = parametrosFacade.consultarParametro(ParamSistema.RUTA_ADJUNTOS.name());
            String pathname = parametro.getValor();
            String separador = System.getProperty("file.separator");
            String rutaPapelera = pathname + separador + "papelera" + separador;
            crearDirectorio(rutaPapelera);

            //verifica la ruta relativa de origen
            String rutaOrigen = adjunto.getRuta();
            if (!adjunto.getRuta().contains("ADJUNTOS_CIER")) {
                rutaOrigen = pathname + separador + adjunto.getRuta();
            }
            //verificamos que exista en el servidor
            File f = new File(rutaOrigen);
            if (!f.exists()) {
                return false;
            }
            //movemos el archivo
            Path origenPath = FileSystems.getDefault().getPath(rutaOrigen);
            Path destinoPath = FileSystems.getDefault().getPath(rutaPapelera + adjunto.getNombre());
            Files.move(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);         
            
            //commit: persistimos el registro en la papelera
            tpm.getEm().persist(papelera);
            tpm.getEm().flush();
            
            sw = true;
        } catch (Exception e) {
            //e.printStackTrace();
            throw new ErrorGeneral("Error al eliminar el archivo", e.getMessage());
        }
        return sw;
    }
    
    @Override
    public void crearDirectorioPais(Long tenant) {
        try {
            ConfiguracionNivel nivelMaximo = consultarNivelConfMaximo();
            if (!nivelMaximo.getNiveles().isEmpty()) {
                Parametro parametro = parametrosFacade.consultarParametro(ParamSistema.RUTA_ADJUNTOS.name());
                String raiz = parametro.getValor();
                String separador = System.getProperty("file.separator");
                for (Nivel n : nivelMaximo.getNiveles()) {
                    String pathname = raiz + directorioNivel(n, separador);
                    crearDirectorio(pathname);
                }
            }
        } catch (Exception e) {
            String msjError = "error en funcion crearDirectorioPais. " + e.getMessage();
            throw new ErrorGeneral("Error al crear arbol de directorio", msjError);
        }
    }
    
    @Override
    public String ubicacionArchivo(String ruta) {
        if (!ruta.contains("ADJUNTOS_CIER")) {
            Parametro parametro = parametrosFacade.consultarParametro(ParamSistema.RUTA_ADJUNTOS.name());
            String pathname = parametro.getValor();
            String separador = System.getProperty("file.separator");
            ruta = pathname + separador + ruta;
        }
        return ruta;
    }
    
    private void crearDirectorio(String pathname) {
        File directorio = new File(pathname);
        directorio.mkdirs();
    }
    
    private String obtenerSecuenciaAdjunto() {
        try {
            Object valor = tpm.getEm().createNativeQuery(UtilProperties.getProperties().getProperty("obtenerSecAdjunto")).getSingleResult();
            int largo = 8;
            String secuencia = UtilConsulta.stringValueOf(valor);
            String ceros = "";
            int cantidad = largo - secuencia.length();
            if (cantidad >= 1) {
                for (int i = 0; i < cantidad; i++) {
                    ceros += "0";
                }
                return (ceros + secuencia);
            } else {
                return secuencia;
            }
        } catch (Exception exp) {
            throw new ErrorGeneral("Error obtener secuencia de adjunto", exp.getMessage());
        }
    }    
    
    private ConfiguracionNivel consultarNivelConfMaximo() {
        TypedQuery<ConfiguracionNivel> query = tpm.getEm().createNamedQuery("ConfiguracionNivel.consultarNivelMaximo", ConfiguracionNivel.class);
        return query.getSingleResult();
    }
    
    private String directorioNivel(Nivel nivel, String separador) {
        try {
            String subdirectorios = "";
            while (!UtilCadena.isNullOVacio(nivel)) {
                subdirectorios = separador + nivel.getId().toString() + subdirectorios;
                nivel = nivel.getPadre();
            }
            return subdirectorios;
        } catch (Exception exp) {
            String msjError = "Error al obtener directorio de la funcion 'pathnameNivel'. " + exp.getMessage();
            throw new ErrorGeneral("Error al obtener directorio del nivel", msjError);
        }
    }
        
}
