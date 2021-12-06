/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package web;

import java.io.InputStream;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author pantelispanka
 */
@Named("fileViewController")
@RequestScoped
public class FileViewController {

    private StreamedContent file;

    public FileViewController() {
        
        InputStream stream = this.getClass().
                getResourceAsStream("/resources/demo/images/AgglomersPreProcessingOnly.ijm");

        file = new DefaultStreamedContent(stream,
                "application/octet-stream", "AgglomersPreProcessingOnly.ijm");
    }

    public StreamedContent getFile() {
        return file;
    }

}
