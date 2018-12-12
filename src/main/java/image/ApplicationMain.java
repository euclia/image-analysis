package image;

import ij.ImagePlus;
import image.helpers.ProcessHelper;
import image.models.spherical.SphericalOptions;
import image.models.spherical.SphericalReport;

import java.awt.image.BufferedImage;


public class ApplicationMain{

	private String[] selectedMeasurements;
	private String selectedThreshold;
	private SphericalOptions sphericalOptions;
	private ImagePlus imagePlus;

	public ApplicationMain(String[] selectedMeasurements, String selectedThreshold, ImagePlus imagePlus){
		this.selectedMeasurements = selectedMeasurements;
		this.selectedThreshold = selectedThreshold;
		this.sphericalOptions = new SphericalOptions();
		this.imagePlus = imagePlus;
	}

	public ApplicationMain(ImagePlus imagePlus){
		this.imagePlus = imagePlus;
	}

	public SphericalReport analyseImage(){
		SphericalReport theSphericalReport;
		ProcessHelper processHelper = new ProcessHelper(this.imagePlus);
		int measurements = this.sphericalOptions.convertMeasurementListToInt(this.selectedMeasurements);
		theSphericalReport = processHelper.analyseImage(measurements, this.selectedThreshold);
		theSphericalReport.setSelectedMeasurements(this.sphericalOptions.selectedMeasurementsMap);
		return theSphericalReport;
	}

	public SphericalReport countParticles(){
		SphericalReport theSphericalReport;
		ProcessHelper processHelper = new ProcessHelper(this.imagePlus);
		int measurements = this.sphericalOptions.convertMeasurementListToInt(this.selectedMeasurements);
		theSphericalReport =  processHelper.countParticles(this.selectedThreshold, measurements);
		theSphericalReport.setSelectedMeasurements(this.sphericalOptions.selectedMeasurementsMap);
		return theSphericalReport;
	}

	public BufferedImage applyThreshold(String selectedThreshold){
		if (selectedThreshold != null){
			ProcessHelper processHelper = new ProcessHelper(this.imagePlus);
			return processHelper.applyThreshold(selectedThreshold);
		} else {
			ProcessHelper processHelper = new ProcessHelper(this.imagePlus);
			return processHelper.applyThreshold("Default");
		}
	}
}
