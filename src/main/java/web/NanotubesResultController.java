package web;

import ij.ImagePlus;
import image.models.nanotubes.NanoFullReport;
import image.models.nanotubes.NanoSummaryReports;
import image.models.nanotubes.NanoResult;
import org.primefaces.model.DefaultStreamedContent;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Named("result2Controller")
@SessionScoped
public class NanotubesResultController implements Serializable {
    private final String BLANK_IMAGE_PATH = "/resources/blank.png";

    private NanoResult nanoResult;

    @Inject private ServletContext context;

    public void initialize() {
        this.nanoResult = (NanoResult) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ridgeresult");
    }

    public DefaultStreamedContent getImgPreview(ImagePlus imagePlus, boolean halfResize) {
        DefaultStreamedContent imgPreview;
        if (imagePlus != null) {
            BufferedImage temp = imagePlus.getBufferedImage();
            BufferedImage newImage;
            if(halfResize) {
                int newWidth = new Double(temp.getWidth() * 0.5).intValue();
                int newHeight = new Double(temp.getHeight() * 0.5).intValue();
                newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = newImage.createGraphics();
                g.drawImage(temp, 0, 0, newWidth, newHeight, null);
                g.dispose();
            }
            else {
                newImage = new BufferedImage(temp.getWidth(), temp.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g = newImage.createGraphics();
                g.drawImage(temp, 0, 0, temp.getWidth(), temp.getHeight(), null);
                g.dispose();
            }
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            try {
                ImageIO.write(newImage, "png", bas);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data = bas.toByteArray();
            InputStream is = new ByteArrayInputStream(data);
            imgPreview = new DefaultStreamedContent(is);
        } else {
            imgPreview = this.getBlankImage();
        }
        return imgPreview;
    }

    private DefaultStreamedContent getBlankImage() {
        InputStream is = context.getResourceAsStream(BLANK_IMAGE_PATH);
        return new DefaultStreamedContent(is);
    }
    public java.util.List<NanoSummaryReports> getRidgeLinesReport() {
        return  nanoResult.getNanoSummaryReports();
    }
    public java.util.List<NanoFullReport> getRidgeLineReport() {
        return  nanoResult.getNanoFullReport();
    }

    public ImagePlus getInitialImage (){
        return nanoResult.getInitialImage();
    }

    public ImagePlus getResultImage(){
        return  nanoResult.getResultImage();
    }

    public ImagePlus getHistogram(){
        return nanoResult.getHistogram();
    }
}
