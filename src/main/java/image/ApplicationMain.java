package image;

import ij.ImagePlus;
import image.helpers.AgglomeratesHelper;
import image.helpers.SphericalHelper;
import image.models.spherical.SphericalOptions;
import image.models.spherical.SphericalReport;

import java.awt.image.BufferedImage;


public class ApplicationMain{

	private String[] selectedMeasurements;
	private String selectedThreshold;
	private SphericalOptions sphericalOptions;
	private ImagePlus imagePlus;
	private Double scale;

	public ApplicationMain(String[] selectedMeasurements, String selectedThreshold, ImagePlus imagePlus, Double scale){
		this.selectedMeasurements = selectedMeasurements;
		this.selectedThreshold = selectedThreshold;
		this.sphericalOptions = new SphericalOptions();
		this.imagePlus = imagePlus;
		this.scale = scale;
	}

	public ApplicationMain(ImagePlus imagePlus){
		this.imagePlus = imagePlus;
	}

	public SphericalReport analyseImage(){
		SphericalReport theSphericalReport;
		SphericalHelper sphericalHelper = new SphericalHelper(this.imagePlus,scale);
		int measurements = this.sphericalOptions.convertMeasurementListToInt(this.selectedMeasurements);
		theSphericalReport = sphericalHelper.analyseImage(measurements, this.selectedThreshold);
		theSphericalReport.setSelectedMeasurements(this.sphericalOptions.selectedMeasurementsMap);
		return theSphericalReport;
	}

	public SphericalReport countParticles(){
		SphericalReport theSphericalReport;
		SphericalHelper sphericalHelper = new SphericalHelper(this.imagePlus,scale);
		int measurements = this.sphericalOptions.convertMeasurementListToInt(this.selectedMeasurements);
		theSphericalReport =  sphericalHelper.countParticles(this.selectedThreshold, measurements);
		theSphericalReport.setSelectedMeasurements(this.sphericalOptions.selectedMeasurementsMap);
		return theSphericalReport;
	}

	public BufferedImage applyThreshold(String selectedThreshold){
		if (selectedThreshold != null){
			SphericalHelper sphericalHelper = new SphericalHelper(this.imagePlus,1.0D);
			return sphericalHelper.applyThreshold(selectedThreshold);
		} else {
			SphericalHelper sphericalHelper = new SphericalHelper(this.imagePlus, 1.0D);
			return sphericalHelper.applyThreshold("Default");
		}
	}
        
	public SphericalReport analyseAgglomeratedImage(){
		SphericalReport theSphericalReport;
		AgglomeratesHelper agglomeratesHelper = new AgglomeratesHelper(this.imagePlus,scale);
		int measurements = this.sphericalOptions.convertMeasurementListToInt(this.selectedMeasurements);
		theSphericalReport = agglomeratesHelper.analyseImage(measurements, this.selectedThreshold);
		theSphericalReport.setSelectedMeasurements(this.sphericalOptions.selectedMeasurementsMap);
		return theSphericalReport;
	}

	public SphericalReport countAgglomeratedParticles(){
		SphericalReport theSphericalReport;
		AgglomeratesHelper agglomeratesHelper = new AgglomeratesHelper(this.imagePlus,scale);
		int measurements = this.sphericalOptions.convertMeasurementListToInt(this.selectedMeasurements);
		theSphericalReport =  agglomeratesHelper.countParticles(this.selectedThreshold, measurements);
		theSphericalReport.setSelectedMeasurements(this.sphericalOptions.selectedMeasurementsMap);
		return theSphericalReport;
	}

	public BufferedImage applyAgglomeratedThreshold(String selectedThreshold){
		if (selectedThreshold != null && !"Default".equals(selectedThreshold) && !"Watershed".equals(selectedThreshold)){
			AgglomeratesHelper agglomeratesHelper = new AgglomeratesHelper(this.imagePlus,1.0D);
			return agglomeratesHelper.applyThreshold(selectedThreshold);
		} else if ("Watershed".equals(selectedThreshold)) {
                        AgglomeratesHelper agglomeratesHelper = new AgglomeratesHelper(this.imagePlus,1.0D);
			return agglomeratesHelper.getWatershed();
                } else {
			AgglomeratesHelper agglomeratesHelper = new AgglomeratesHelper(this.imagePlus, 1.0D);
			return agglomeratesHelper.applyNoThreshold("Default");
		}
	}
        
        
}
