package image.models.spherical;

import ij.measure.Measurements;
import image.helpers.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SphericalOptions {

	private List<String> measurementList;
	public HashMap<String, Integer> measurementMap;
	public HashMap<String, Boolean> selectedMeasurementsMap = new HashMap<>();


	public SphericalOptions(){
		this.populateMeasurementList();
		//this.matchingMeasurementsWithName();
	}

	private void populateMeasurementList(){
		this.measurementList = new ArrayList<>();
		this.measurementList.add(Constants.ANGLE);
		this.measurementList.add(Constants.AREA);
		this.measurementList.add(Constants.ASPECT_RATIO);
		this.measurementList.add(Constants.CIRCULARITY);
		this.measurementList.add(Constants.FERET_ANGLE);
		this.measurementList.add(Constants.STD_DEV);
		this.measurementList.add(Constants.INT_DEN);
		this.measurementList.add(Constants.KURTOSIS);
		this.measurementList.add(Constants.MAJOR);
		this.measurementList.add(Constants.MAX_GREY_VALUE);
		this.measurementList.add(Constants.MEAN);
		this.measurementList.add(Constants.MIN_GREY_VALUE);
		this.measurementList.add(Constants.MIN_FERET);
		this.measurementList.add(Constants.MINOR);
		this.measurementList.add(Constants.MODAL_GREY_VALUE);
		this.measurementList.add(Constants.PERIMETER);
		this.measurementList.add(Constants.POROSITY);
		this.measurementList.add(Constants.ROUNDNESS);
		this.measurementList.add(Constants.SKEWNESS);
		this.measurementList.add(Constants.SOLIDITY);

		this.measurementList.add(Constants.SPHERICITY);
		this.measurementList.add(Constants.SURFACE_DIAMETER);
		this.measurementList.add(Constants.VOLUME);
		this.measurementList.add(Constants.VOLUME_TO_SURFACE);
	}

/*	private void matchingMeasurementsWithName(){
		this.measurementMap = new HashMap<>();
		this.measurementMap.put(Constants.ANGLE, Measurements.ELLIPSE);
		this.measurementMap.put(Constants.AREA, Measurements.AREA);
		this.measurementMap.put(Constants.ASPECT_RATIO, Measurements.SHAPE_DESCRIPTORS);
		this.measurementMap.put(Constants.CIRCULARITY, Measurements.CIRCULARITY);
		this.measurementMap.put(Constants.FERET_ANGLE, Measurements.FERET);
		this.measurementMap.put(Constants.STD_DEV, Measurements.STD_DEV);
		this.measurementMap.put(Constants.INT_DEN, Measurements.INTEGRATED_DENSITY);
		this.measurementMap.put(Constants.KURTOSIS, Measurements.KURTOSIS);
		this.measurementMap.put(Constants.MAJOR, Measurements.ELLIPSE);
		this.measurementMap.put(Constants.MAX_GREY_VALUE, Measurements.MIN_MAX);
		this.measurementMap.put(Constants.MEAN, Measurements.MEAN);
		this.measurementMap.put(Constants.MIN_GREY_VALUE, Measurements.MIN_MAX);
		this.measurementMap.put(Constants.MINOR, Measurements.ELLIPSE);
		this.measurementMap.put(Constants.MODAL_GREY_VALUE, Measurements.MODE);
		this.measurementMap.put(Constants.PERIMETER, Measurements.PERIMETER);
		this.measurementMap.put(Constants.POROSITY, Measurements.AREA_FRACTION);
		this.measurementMap.put(Constants.ROUNDNESS, Measurements.SHAPE_DESCRIPTORS);
		this.measurementMap.put(Constants.SKEWNESS, Measurements.SKEWNESS);
		this.measurementMap.put(Constants.SOLIDITY, Measurements.SHAPE_DESCRIPTORS);
	}*/

	public int convertMeasurementListToInt(String[] selectedMeasurements){
		int result = Measurements.ALL_STATS;
		for (String s: selectedMeasurements) {
			this.selectedMeasurementsMap.put(s,true);
		}
		return result;
	}

	private Boolean isMeasureVolumeRelated(String measure) {
		Boolean isRelated = false;
		if (measure.equals(Constants.VOLUME)) {
			isRelated = true;
		} else if (measure.equals(Constants.SPHERICITY)) {
			isRelated = true;
		} else if (measure.equals(Constants.VOLUME_TO_SURFACE)) {
			isRelated = true;
		} else if (measure.equals(Constants.SURFACE_DIAMETER)) {
			isRelated = true;
		}
		return isRelated;
	}

	public List<String> getMeasurementList() {
		return measurementList;
	}
}
