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
		this.matchingMeasurementsWithName();
	}

	private void populateMeasurementList(){
		this.measurementList = new ArrayList<>();
		this.measurementList.add(Constants.AREA);
		this.measurementList.add(Constants.AREA_FRACTION);
		//this.measurementList.add(Constants.BOUNDING_PREFERENCES);
		//this.measurementList.add(Constants.CENTER_OF_MASS);
		//this.measurementList.add(Constants.CENTROID);
		this.measurementList.add(Constants.FERET);
		this.measurementList.add(Constants.FIT_ELLIPSE);
		this.measurementList.add(Constants.INT_DEN);
		this.measurementList.add(Constants.KURTOSIS);
		this.measurementList.add(Constants.MEAN);
		this.measurementList.add(Constants.MEDIAN);
		this.measurementList.add(Constants.MIN_MAX);
		this.measurementList.add(Constants.MODAL);
		this.measurementList.add(Constants.PERIMETER);
		this.measurementList.add(Constants.SHAPE_DESCRIPTORS);
		this.measurementList.add(Constants.SKEWNESS);
		this.measurementList.add(Constants.STD_DEV);
		//Volume related measurements
		this.measurementList.add(Constants.SPHERICITY);
		this.measurementList.add(Constants.VOLUME);
		this.measurementList.add(Constants.SURFACE_DIAMETER);
		this.measurementList.add(Constants.VOLUME_TO_SURFACE);
		this.measurementList.add(Constants.VOLUME_DIAMETER);
	}

	private void matchingMeasurementsWithName(){
		this.measurementMap = new HashMap<>();
		this.measurementMap.put(Constants.AREA, Measurements.AREA);
		this.measurementMap.put(Constants.AREA_FRACTION, Measurements.AREA_FRACTION);
		//this.measurementMap.put(Constants.BOUNDING_PREFERENCES, Measurements.LIMIT);
		//this.measurementMap.put(Constants.CENTER_OF_MASS, Measurements.CENTER_OF_MASS);
		//this.measurementMap.put(Constants.CENTROID, Measurements.CENTROID);
		this.measurementMap.put(Constants.FERET, Measurements.FERET);
		this.measurementMap.put(Constants.FIT_ELLIPSE, Measurements.ELLIPSE);
		this.measurementMap.put(Constants.INT_DEN, Measurements.INTEGRATED_DENSITY);
		this.measurementMap.put(Constants.KURTOSIS, Measurements.KURTOSIS);
		this.measurementMap.put(Constants.MEAN, Measurements.MEAN);
		this.measurementMap.put(Constants.MEDIAN, Measurements.MEDIAN);
		this.measurementMap.put(Constants.MIN_MAX, Measurements.MIN_MAX);
		this.measurementMap.put(Constants.MODAL, Measurements.MODE);
		this.measurementMap.put(Constants.PERIMETER, Measurements.PERIMETER);
		this.measurementMap.put(Constants.SHAPE_DESCRIPTORS, Measurements.SHAPE_DESCRIPTORS);
		this.measurementMap.put(Constants.SKEWNESS, Measurements.SKEWNESS);
		this.measurementMap.put(Constants.STD_DEV, Measurements.STD_DEV);
	}


	public int convertMeasurementListToInt(String[] selectedMeasurements){
		int result = 0;
		for (int i = 0; i < selectedMeasurements.length; i++) {
			this.selectedMeasurementsMap.put(selectedMeasurements[i], true);
			if (!this.isMeasureVolumeRelated(selectedMeasurements[i])) {
				Integer value = measurementMap.get(selectedMeasurements[i]);
				result += value.intValue();
			}
		}
		if ((selectedMeasurementsMap.get(Constants.VOLUME) != null)
				|| (selectedMeasurementsMap.get(Constants.SPHERICITY) != null)
				|| (selectedMeasurementsMap.get(Constants.VOLUME_DIAMETER) != null)
				|| (selectedMeasurementsMap.get(Constants.VOLUME_TO_SURFACE) != null)
				|| (selectedMeasurementsMap.get(Constants.SURFACE_DIAMETER) != null)){
			if (selectedMeasurementsMap.get(Constants.SHAPE_DESCRIPTORS) == null) {
//				selectedMeasurementsMap.put(Constants.SHAPE_DESCRIPTORS, true);
				result = result + Measurements.SHAPE_DESCRIPTORS;
			}

			if (selectedMeasurementsMap.get(Constants.AREA) == null) {
//				selectedMeasurementsMap.put(Constants.AREA, true);
				result = result + Measurements.AREA;
			}
			if (selectedMeasurementsMap.get(Constants.FERET) == null) {
//				selectedMeasurementsMap.put(Constants.FERET, true);
				result = result + Measurements.FERET;
			}
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
		} else if (measure.equals(Constants.VOLUME_DIAMETER)) {
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
