package web;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import image.helpers.Constants;
import image.models.spherical.ParticleResult;
import image.models.spherical.SphericalReport;
import org.primefaces.model.DefaultStreamedContent;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;

@Named("resultController")
@SessionScoped
public class SphericalResultController implements Serializable {


    private final String BLANK_IMAGE_PATH = "/resources/blank.png";
    private SphericalReport result;
    private List<ParticleResult> particleResults;
    private HashMap<String, Boolean> selectedMeasurements;
    private Boolean isAreaSelected = false;
    private Boolean isVolumeSelected = false;
    private Boolean isSphericitySelected = false;
    private Boolean isVolumeToSurfaceSelected = false;
    private Boolean isShapeDescriptorsSelected = false;
    private Boolean isStandardDeviationSelected = false;
    private Boolean isMinMaxSelected = false;
    private Boolean isCenterOfMassSelected = false;
    private Boolean isBoundingPrefsSelected = false;
    private Boolean isIntegratedDensitySelected = false;
    private Boolean isSkewenessSelected = false;
    private Boolean isPorositySelected = false;
    private Boolean isMeanGrayValueSelected = false;
    private Boolean isModalGrayValueSelected = false;
    private Boolean isCentroidSelected = false;
    private Boolean isPerimeterSelected = false;
    private Boolean isFitEllipseSelected = false;
    private Boolean isFeretSelected = false;
    private Boolean isMedianSelected = false;
    private Boolean isKurtosisSelected = false;
    private Boolean isSurfaceDiameterSelected =false;

    @Inject
    private ServletContext context;

    public void initialize() {
        this.result = (SphericalReport) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("result");
        if (result.getParticleResults()!=null)
        this.particleResults = result.getParticleResults();
        if (result.getSelectedMeasurements()!=null) {
            this.selectedMeasurements = result.getSelectedMeasurements();
            this.initializeRenderedColumns(result.getSelectedMeasurements());
        }
    }

    public List<ParticleResult> getParticleResults() {
        return particleResults;
    }

    public void setParticleResults(List<ParticleResult> particleResults) {
        this.particleResults = particleResults;
    }

    public DefaultStreamedContent getImgPreview() {
        DefaultStreamedContent imgPreview;
        if (this.result.getProcessedImage() != null) {
            BufferedImage temp = this.result.getProcessedImage();

            int newWidth = 800;
            int newHeight = new Double(newWidth/((double)temp.getWidth()/(double)temp.getHeight())).intValue();
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

    private DefaultStreamedContent getBlankImage() {

        InputStream is = context.getResourceAsStream(BLANK_IMAGE_PATH);
        return new DefaultStreamedContent(is);

    }

    private void initializeRenderedColumns(HashMap<String, Boolean> theMap) {
        this.isAreaSelected = theMap.get(Constants.AREA);
        this.isPorositySelected = theMap.get(Constants.AREA_FRACTION);
        this.isStandardDeviationSelected = theMap.get(Constants.STD_DEV);
        this.isBoundingPrefsSelected = theMap.get(Constants.BOUNDING_PREFERENCES);
        this.isShapeDescriptorsSelected = theMap.get(Constants.SHAPE_DESCRIPTORS);
        this.isIntegratedDensitySelected = theMap.get(Constants.INT_DEN);
        this.isSkewenessSelected = theMap.get(Constants.SKEWNESS);
        this.isMeanGrayValueSelected = theMap.get(Constants.MEAN);
        this.isMinMaxSelected = theMap.get(Constants.MIN_MAX);
        this.isKurtosisSelected = theMap.get(Constants.KURTOSIS);
        this.isMedianSelected = theMap.get(Constants.MEDIAN);
        this.isSphericitySelected = theMap.get(Constants.SPHERICITY);
        this.isPerimeterSelected = theMap.get(Constants.PERIMETER);
        this.isCenterOfMassSelected = theMap.get(Constants.CENTER_OF_MASS);
        this.isFeretSelected = theMap.get(Constants.FERET);
        this.isVolumeSelected = theMap.get(Constants.VOLUME);
        this.isModalGrayValueSelected = theMap.get(Constants.MODAL);
        this.isFitEllipseSelected = theMap.get(Constants.FIT_ELLIPSE);
        this.isBoundingPrefsSelected = theMap.get(Constants.BOUNDING_PREFERENCES);
        this.isCentroidSelected = theMap.get(Constants.CENTROID);
        this.isVolumeToSurfaceSelected = theMap.get(Constants.VOLUME_TO_SURFACE);
        this.isSurfaceDiameterSelected = theMap.get(Constants.SURFACE_DIAMETER);
    }

    public void setSelectedMeasurements(HashMap<String, Boolean> selectedMeasurements) {
        this.selectedMeasurements = selectedMeasurements;
    }

    public Boolean getIsAreaSelected() {
        return isAreaSelected;
    }

    public Boolean getIsVolumeSelected() {
        return isVolumeSelected;
    }

    public Boolean getIsSphericitySelected() {
        return isSphericitySelected;
    }

    public Boolean getIsVolumeToSurfaceSelected() {
        return isVolumeToSurfaceSelected;
    }

    public Boolean getIsShapeDescriptorsSelected() {
        return isShapeDescriptorsSelected;
    }

    public Boolean getIsStandardDeviationSelected() {
        return isStandardDeviationSelected;
    }

    public Boolean getIsMinMaxSelected() {
        return isMinMaxSelected;
    }

    public Boolean getIsCenterOfMassSelected() {
        return isCenterOfMassSelected;
    }

    public Boolean getIsBoundingPrefsSelected() {
        return isBoundingPrefsSelected;
    }

    public Boolean getIsIntegratedDensitySelected() {
        return isIntegratedDensitySelected;
    }

    public Boolean getIsSkewenessSelected() {
        return isSkewenessSelected;
    }

    public Boolean getIsPorositySelected() {
        return isPorositySelected;
    }

    public Boolean getIsMeanGrayValueSelected() {
        return isMeanGrayValueSelected;
    }

    public Boolean getIsModalGrayValueSelected() {
        return isModalGrayValueSelected;
    }

    public Boolean getIsCentroidSelected() {
        return isCentroidSelected;
    }

    public Boolean getIsPerimeterSelected() {
        return isPerimeterSelected;
    }

    public Boolean getIsFitEllipseSelected() {
        return isFitEllipseSelected;
    }

    public Boolean getIsFeretSelected() {
        return isFeretSelected;
    }

    public Boolean getIsMedianSelected() {
        return isMedianSelected;
    }

    public Boolean getIsKurtosisSelected() {
        return isKurtosisSelected;
    }

    public Boolean getIsSurfaceDiameterSelected() {
        return isSurfaceDiameterSelected;
    }

    public void preProcessPDF(Object document){
        Document pdf = (Document) document;
        pdf.setMargins(0.2f,0.2f,0.2f,0.2f);
        pdf.setPageSize(PageSize.A3.rotate());
    }
}
