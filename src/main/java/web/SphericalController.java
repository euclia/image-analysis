package web;

import ij.ImagePlus;
import image.ApplicationMain;
import image.helpers.FileMinion;
import image.models.spherical.SphericalOptions;
import image.models.spherical.ParticleResult;
import image.models.spherical.SphericalReport;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Named("indexController")
@SessionScoped
public class SphericalController implements Serializable {

    //constants
    private final String FORM_SUBMITTED = "The form submitted successfully!";
    private final String IMAGEANALYSIS = "imageAnalysis";
    private final String DIR_PATH2 = "/src/main/webapp/WEB-INF/files/";
    private final String DIR_PATH = "src/main/webapp/WEB-INF/files/";
    private final String BLANK_IMAGE_PATH = "/resources/blank.png";
    private final String SPHERICAL_IMAGE_PATH = "/resources/spherical.jpg";

    private final int BUFFER_SIZE = 6124;

    //variables
    private String thresholdType;
    private FileMinion fileMinion;
    private List<String> measurements;
    private String[] selectedMeasurements;
    private BufferedImage bufferedImage;
    private String function;
    private SphericalReport result;
    private List<ParticleResult> resultMap;
    private Double scaleFactor = 0.3;
    @Inject
    private ServletContext context;
    
    @PostConstruct
    public void init() {
        SphericalOptions sphericalOptionsModel = new SphericalOptions();
        this.measurements = sphericalOptionsModel.getMeasurementList();
        this.fileMinion = new FileMinion();
    }
    
    public String submitForm() {
        try {
            String msg = FORM_SUBMITTED;
            ImagePlus imagePlus = new ImagePlus("theTitle", bufferedImage);
            ApplicationMain applicationMain = new ApplicationMain(this.selectedMeasurements, this.thresholdType, imagePlus);
            this.resultMap = new ArrayList<>();
            if (this.function.equals(IMAGEANALYSIS)) {
                this.result = applicationMain.analyseImage();
            } else {
                this.result = applicationMain.countParticles();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg));
            FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fileMinion.deleteDirectoryAndFiles(DIR_PATH + getSessionID());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("result", this.result);
        return "spherical_result?faces-redirect=true";
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
    
    public void selectAllMeasurements() {
        this.setSelectedMeasurements(measurements.toArray(new String[measurements.size()]));
    }
    
    public void deselectAllMeasurements() {
        this.setSelectedMeasurements(new String[measurements.size()]);
    }
    
    public String getThresholdType() {
        return thresholdType;
    }
    
    public void setThresholdType(String thresholdType) {
        this.thresholdType = thresholdType;
    }
    
    public String[] getSelectedMeasurements() {
        return selectedMeasurements;
    }
    
    public void setSelectedMeasurements(String[] selectedMeasurements) {
        this.selectedMeasurements = selectedMeasurements;
    }
    
    public List<String> getMeasurements() {
        return measurements;
    }
    
    public void setMeasurements(List<String> measurements) {
        this.measurements = measurements;
    }
    
    public String getFunction() {
        return function;
    }
    
    public void setFunction(String function) {
        this.function = function;
    }

    public Double getScaleFactor() {
        return scaleFactor;
    }

    public void setScaleFactor(Double scaleFactor) {
        this.scaleFactor=scaleFactor;
    }

    public DefaultStreamedContent getImgPreview() {
        DefaultStreamedContent imgPreview;
        if (bufferedImage != null) {
            ImagePlus imagePlus = new ImagePlus("theTitle", bufferedImage);
            ApplicationMain applicationMain = new ApplicationMain(imagePlus);
            BufferedImage temp = applicationMain.applyThreshold(this.thresholdType);

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

    public void useExample(){
        try {
            bufferedImage = ImageIO.read(context.getResourceAsStream(SPHERICAL_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateThresholdType(ValueChangeEvent event) {
        event.getNewValue();
    }

}
