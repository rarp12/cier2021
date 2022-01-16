/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.stackpointer.cier.modelo.utilidad;

import java.io.InputStream;

/**
 *
 * @author rjay1
 */
public class UtilFileItem {

    private String fileName;
    private InputStream inputstream;
    private String contentType;

    public UtilFileItem(String fileName, InputStream inputstream, String contentType) {
        this.fileName = fileName;
        this.inputstream = inputstream;
        this.contentType = contentType;
    }

    public String getFileName() {
        return fileName;
    }

    public InputStream getInputstream() {
        return inputstream;
    }

    public String getContentType() {
        return contentType;
    }
}
