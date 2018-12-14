package web;

import ij.ImagePlus;
import image.helpers.NanotubesHelper;
import image.models.nanotubes.NanoParameters;
import image.models.nanotubes.NanoResult;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class NanotubesController {
    @Inject
    private ServletContext context;

    private BufferedImage bufferedImage;
    private final String BLANK_IMAGE_PATH = "/resources/blank.png";
    private final String NANOTUBE_IMAGE_PATH = "/resources/nanotube.jpg";

    private String thresholdType;
    private NanoParameters nanoParameters;
    private NanoResult nanoResult;
    private final String DIR_PATH = "src/main/webapp/WEB-INF/files/";

    private double sigma = 8.58D;
    private double lowt = 0.00D;
    private double uppt= 0.17D;
    private double minl =0.0D;
    private double maxl = 0.0D;
    private double scaleFactor = 0.3;

    @PostConstruct
    public void init() {
        this.nanoParameters = new NanoParameters();
    }

    public String submitForm() {
        try {
            this.nanoResult = new NanoResult();
            final ImagePlus imp = new ImagePlus("theTitle", bufferedImage);

            //Run ridge detection
            NanotubesHelper nanotubesHelper = new NanotubesHelper(imp);
            nanoResult = nanotubesHelper.runRidgeDetection(sigma,uppt,lowt,minl,maxl,scaleFactor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ridgeresult", this.nanoResult);
        return "nanotubes_result?faces-redirect=true";
    }

    public void useExample(){
        try {
            bufferedImage = ImageIO.read(context.getResourceAsStream(NANOTUBE_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            InputStream inputStream = event.getFile().getInputstream();
            bufferedImage = ImageIO.read(inputStream);
            FacesMessage msg
                    = new FacesMessage("File Description", "file name: "
                    + event.getFile().getFileName() + "file size: "
                    + event.getFile().getSize() / 1024 + " Kb content type: "
                    + event.getFile().getContentType() + "The file was uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (IOException e) {
            e.printStackTrace();
            FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "The files were not uploaded!", "");
            FacesContext.getCurrentInstance().addMessage(null, error);
        }
    }

    private String getSessionID() {
        FacesContext fCtx = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) fCtx.getExternalContext().getSession(false);
        return session.getId();
    }

    private DefaultStreamedContent getBlankImage() {
        InputStream is = context.getResourceAsStream(BLANK_IMAGE_PATH);
        return new DefaultStreamedContent(is);
    }


    public DefaultStreamedContent getImgPreview() {
        DefaultStreamedContent imgPreview;
        if (bufferedImage != null) {
            ImagePlus imagePlus = new ImagePlus("theTitle", bufferedImage);
            BufferedImage temp = imagePlus.getBufferedImage();
            int newWidth = new Double(temp.getWidth() * 0.5).intValue();
            int newHeight = new Double(temp.getHeight() * 0.5).intValue();
            BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(temp, 0, 0, newWidth, newHeight, null);
            g.dispose();
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

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public NanoParameters getNanoParameters() {
        return nanoParameters;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double getLowt() {
        return lowt;
    }

    public void setLowt(double lowt) {
        this.lowt = lowt;
    }

    public double getUppt() {
        return uppt;
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(double scaleFactor){
        this.scaleFactor=scaleFactor;
    }

    public void setUppt(double uppt) {
        this.uppt = uppt;
    }

    public double getMinl() {
        return minl;
    }

    public void setMinl(double minl) {
        this.minl = minl;
    }

    public double getMaxl() {
        return maxl;
    }

    public void setMaxl(double maxl) {
        this.maxl = maxl;
    }
}