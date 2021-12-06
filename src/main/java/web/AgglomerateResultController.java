package web;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import image.helpers.Constants;
import image.models.spherical.ParticleResult;
import image.models.spherical.SphericalReport;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;

/**
 *
 * @author pantelispanka
 */
@Named("agglomerateResult")
@SessionScoped
public class AgglomerateResultController implements Serializable {
    
    private final String BLANK_IMAGE_PATH = "/resources/blank.png";
    private SphericalReport result;
    private List<ParticleResult> particleResults;
    private HashMap<String, Boolean> selectedMeasurements;

    private Boolean isAngleSelected = false;
    private Boolean isAreaSelected = false;
    private Boolean isAspectRationSelected = false;
    private Boolean isCircularitySelected = false;
    private Boolean isFeretAngleSelected = false;
    private Boolean isStandardDeviationSelected = false;
    private Boolean isIntegratedDensitySelected = false;
    private Boolean isKurtosisSelected = false;
    private Boolean isMajorSelected = false;
    private Boolean isMaxGreyValueSelected = false;
    private Boolean isMeanSelected = false;
    private Boolean isMinGreyValueSelected = false;
    private Boolean isMinFeretSelected = false;
    private Boolean isMinorSelected = false;
    private Boolean isModalGrayValueSelected = false;
    private Boolean isPerimeterSelected = false;
    private Boolean isPorositySelected = false;
    private Boolean isRoundnessSelected = false;
    private Boolean isSkewenessSelected = false;
    private Boolean isSoliditySelected = false;
    private Boolean isSphericitySelected =false;
    private Boolean isSurfaceDiameterSelected =false;
    private Boolean isVolumeSelected =false;
    private Boolean isVolumeToSurfaceSelected=false;
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
        this.isAngleSelected = theMap.get(Constants.ANGLE);
        this.isAspectRationSelected = theMap.get(Constants.ASPECT_RATIO);
        this.isCircularitySelected = theMap.get(Constants.CIRCULARITY);
        this.isFeretAngleSelected = theMap.get(Constants.FERET_ANGLE);
        this.isStandardDeviationSelected = theMap.get(Constants.STD_DEV);
        this.isIntegratedDensitySelected = theMap.get(Constants.INT_DEN);
        this.isKurtosisSelected = theMap.get(Constants.KURTOSIS);
        this.isMajorSelected = theMap.get(Constants.MAJOR);
        this.isMaxGreyValueSelected = theMap.get(Constants.MAX_GREY_VALUE);
        this.isMeanSelected = theMap.get(Constants.MEAN);
        this.isMinGreyValueSelected = theMap.get(Constants.MIN_GREY_VALUE);
        this.isMinFeretSelected = theMap.get(Constants.MIN_FERET);
        this.isMinorSelected = theMap.get(Constants.MINOR);
        this.isModalGrayValueSelected = theMap.get(Constants.MODAL_GREY_VALUE);
        this.isPerimeterSelected = theMap.get(Constants.PERIMETER);
        this.isPorositySelected = theMap.get(Constants.POROSITY);
        this.isRoundnessSelected = theMap.get(Constants.ROUNDNESS);
        this.isSkewenessSelected = theMap.get(Constants.SKEWNESS);
        this.isSoliditySelected = theMap.get(Constants.SOLIDITY);
        this.isSphericitySelected = theMap.get(Constants.SPHERICITY);
        this.isSurfaceDiameterSelected = theMap.get(Constants.SURFACE_DIAMETER);
        this.isVolumeSelected = theMap.get(Constants.VOLUME);
        this.isVolumeToSurfaceSelected = theMap.get(Constants.VOLUME_TO_SURFACE);
    }

    public void setSelectedMeasurements(HashMap<String, Boolean> selectedMeasurements) {
        this.selectedMeasurements = selectedMeasurements;
    }

    public Boolean getIsAngleSelected() {
        return isAngleSelected;
    }

    public Boolean getIsAreaSelected() {
        return isAreaSelected;
    }

    public Boolean getIsAspectRationSelected() {
        return isAspectRationSelected;
    }

    public Boolean getIsCircularitySelected() {
        return isCircularitySelected;
    }

    public Boolean getIsFeretAngleSelected() {
        return isFeretAngleSelected;
    }

    public Boolean getIsStandardDeviationSelected() {
        return isStandardDeviationSelected;
    }

    public Boolean getIsIntegratedDensitySelected() {
        return isIntegratedDensitySelected;
    }

    public Boolean getIsKurtosisSelected() {
        return isKurtosisSelected;
    }

    public Boolean getIsMajorSelected() {
        return isMajorSelected;
    }

    public Boolean getIsMaxGreyValueSelected() {
        return isMaxGreyValueSelected;
    }

    public Boolean getIsMeanSelected() {
        return isMeanSelected;
    }

    public Boolean getIsMinGreyValueSelected() {
        return isMinGreyValueSelected;
    }

    public Boolean getIsMinFeretSelected() {
        return isMinFeretSelected;
    }

    public Boolean getIsMinorSelected() {
        return isMinorSelected;
    }

    public Boolean getIsModalGrayValueSelected() {
        return isModalGrayValueSelected;
    }

    public Boolean getIsPerimeterSelected() {
        return isPerimeterSelected;
    }

    public Boolean getIsPorositySelected() {
        return isPorositySelected;
    }

    public Boolean getIsRoundnessSelected() {
        return isRoundnessSelected;
    }

    public Boolean getIsSkewenessSelected() {
        return isSkewenessSelected;
    }

    public Boolean getIsSoliditySelected() {
        return isSoliditySelected;
    }

    public Boolean getIsSphericitySelected() {
        return isSphericitySelected;
    }

    public Boolean getIsSurfaceDiameterSelected() {
        return isSurfaceDiameterSelected;
    }

    public Boolean getIsVolumeSelected() {
        return isVolumeSelected;
    }

    public Boolean getIsVolumeToSurfaceSelected() {
        return isVolumeToSurfaceSelected;
    }

    public ServletContext getContext() {
        return context;
    }

    public void preProcessPDF(Object document){
        Document pdf = (Document) document;
        pdf.setMargins(0.2f,0.2f,0.2f,0.2f);
        pdf.setPageSize(PageSize.A3.rotate());
    }
    
}