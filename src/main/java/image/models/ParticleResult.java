package image.models;

import image.helpers.AnalysisHelper;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class ParticleResult {

    private String id;
    private String angle;
    private String area;
    private String area_fraction;
    private String aspect_ratio;
    private String circularity;
    private String feret;
    private String feret_angle;
    private String feret_x;
    private String feret_y;
    private String integrated_density;
    private String kurtosis;
    private String major;
    private String max_grey_value;
    private String mean_grey_value;
    private String median;
    private String min_grey_value;
    private String min_feret;
    private String minor;
    private String perimeter;
    private String raw_integrated_density;
    private String roundness;
    private String skewness;
    private String solidity;
    private String std_dev;
    private String bounding_rectangle_origin_x;
    private String bounding_rectangle_origin_y;
    private String bounding_rectangle_width;
    private String bounding_rectangle_height;
    private String x_center_of_mass;
    private String x_centroid;
    private String y_center_of_mass;
    private String y_centroid;
    private String volume = "";
    private String sphericity = "";
    private String surfaceDiameter = "";
    private String volumeDiameter = "";
    private String volumeToSurface = "";

    public ParticleResult(HashMap<String, String> map) {
        this.id = map.get("id");
        this.angle = replaceNull(map.get("Angle"));
        this.area = replaceNull(map.get("Area"));
        this.std_dev = replaceNull(map.get("StdDev"));
        this.circularity = replaceNull(map.get("Circ."));
        this.area_fraction = replaceNull(map.get("%Area"));
        this.solidity = replaceNull(map.get("Solidity"));
        this.x_center_of_mass = replaceNull(map.get("XM"));
        this.y_center_of_mass = replaceNull(map.get("YM"));
        this.min_grey_value = replaceNull(map.get("Min"));
        this.max_grey_value = replaceNull(map.get("Max"));
        this.bounding_rectangle_origin_x = replaceNull(map.get("BX"));
        this.bounding_rectangle_origin_y = replaceNull(map.get("BY"));
        this.bounding_rectangle_width = replaceNull(map.get("Width"));
        this.bounding_rectangle_height = replaceNull(map.get("Height"));
        this.aspect_ratio = replaceNull(map.get("AR"));
        this.circularity = replaceNull(map.get("Circ."));
        this.roundness = replaceNull(map.get("Round"));
        this.solidity = replaceNull(map.get("Solidity"));
        this.integrated_density = replaceNull(map.get("IntDen"));
        this.raw_integrated_density = replaceNull(map.get("RawIntDen"));
        this.skewness = replaceNull(map.get("Skew"));
        this.kurtosis = replaceNull(map.get("Kurt"));
        this.feret = replaceNull(map.get("Feret"));
        this.feret_angle = replaceNull(map.get("FeretAngle"));
        this.feret_x = replaceNull(map.get("FeretX"));
        this.feret_y = replaceNull(map.get("FeretY"));
        this.min_feret = replaceNull(map.get("MinFeret"));
        this.x_centroid = replaceNull(map.get("X"));
        this.y_centroid = replaceNull(map.get("Y"));
        this.major = replaceNull(map.get("Major"));
        this.minor = replaceNull(map.get("Minor"));
        this.perimeter = replaceNull(map.get("Perim."));
        this.mean_grey_value = replaceNull(map.get("Mean"));
        this.median = replaceNull(map.get("median"));
        this.calculateExtraParameters();
    }

    private void calculateExtraParameters() {
        if (!this.area.isEmpty() && !this.circularity.isEmpty() && !this.feret.isEmpty()) {
            AnalysisHelper analysisHelper = new AnalysisHelper();
            double volume = analysisHelper.findApproximateVolume(Double.parseDouble(this.circularity),
                    Double.parseDouble(this.feret_x),
                    Double.parseDouble(this.feret_y));
            double surfaceDiameter = analysisHelper.getSurfaceDiameter(Double.parseDouble(this.area));
            double sphericity = analysisHelper.getSphericity(volume, Double.parseDouble(this.area));
            double volumeDiameter = analysisHelper.getVolumeDiameter(volume);
            double volumeToSurface = analysisHelper.getVolumeToSurface(volume, Double.parseDouble(this.area));
            this.volume = Double.toString(volume);
            this.surfaceDiameter = Double.toString(surfaceDiameter);
            this.sphericity = Double.toString(sphericity);
            this.volumeDiameter = Double.toString(volumeDiameter);
            this.volumeToSurface = Double.toString(volumeToSurface);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String replaceNull(String input) {
        return input == null ? "" : input;
    }

    private String getID(HashMap<String, String> theMap) {
        return theMap.get("");
    }

    // getters and setters
    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea_fraction() {
        return area_fraction;
    }

    public void setArea_fraction(String area_fraction) {
        this.area_fraction = area_fraction;
    }

    public String getAspect_ratio() {
        return aspect_ratio;
    }

    public void setAspect_ratio(String aspect_ratio) {
        this.aspect_ratio = aspect_ratio;
    }

    public String getCircularity() {
        return circularity;
    }

    public void setCircularity(String circularity) {
        this.circularity = circularity;
    }

    public String getFeret() {
        return feret;
    }

    public void setFeret(String feret) {
        this.feret = feret;
    }

    public String getFeret_angle() {
        return feret_angle;
    }

    public void setFeret_angle(String feret_angle) {
        this.feret_angle = feret_angle;
    }

    public String getFeret_x() {
        return feret_x;
    }

    public void setFeret_x(String feret_x) {
        this.feret_x = feret_x;
    }

    public String getFeret_y() {
        return feret_y;
    }

    public void setFeret_y(String feret_y) {
        this.feret_y = feret_y;
    }

    public String getIntegrated_density() {
        return integrated_density;
    }

    public void setIntegrated_density(String integrated_density) {
        this.integrated_density = integrated_density;
    }

    public String getKurtosis() {
        return kurtosis;
    }

    public void setKurtosis(String kurtosis) {
        this.kurtosis = kurtosis;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMax_grey_value() {
        return max_grey_value;
    }

    public void setMax_grey_value(String max_grey_value) {
        this.max_grey_value = max_grey_value;
    }

    public String getMean_grey_value() {
        return mean_grey_value;
    }

    public void setMean_grey_value(String mean_grey_value) {
        this.mean_grey_value = mean_grey_value;
    }

    public String getMedian() {
        return median;
    }

    public void setMedian(String median) {
        this.median = median;
    }

    public String getMin_grey_value() {
        return min_grey_value;
    }

    public void setMin_grey_value(String min_grey_value) {
        this.min_grey_value = min_grey_value;
    }

    public String getMin_feret() {
        return min_feret;
    }

    public void setMin_feret(String min_feret) {
        this.min_feret = min_feret;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(String perimeter) {
        this.perimeter = perimeter;
    }

    public String getRaw_integrated_density() {
        return raw_integrated_density;
    }

    public void setRaw_integrated_density(String raw_integrated_density) {
        this.raw_integrated_density = raw_integrated_density;
    }

    public String getRoundness() {
        return roundness;
    }

    public void setRoundness(String roundness) {
        this.roundness = roundness;
    }

    public String getSkewness() {
        return skewness;
    }

    public void setSkewness(String skewness) {
        this.skewness = skewness;
    }

    public String getSolidity() {
        return solidity;
    }

    public void setSolidity(String solidity) {
        this.solidity = solidity;
    }

    public String getStd_dev() {
        return std_dev;
    }

    public void setStd_dev(String std_dev) {
        this.std_dev = std_dev;
    }

    public String getBounding_rectangle_origin_x() {
        return bounding_rectangle_origin_x;
    }

    public void setBounding_rectangle_origin_x(String bounding_rectangle_origin_x) {
        this.bounding_rectangle_origin_x = bounding_rectangle_origin_x;
    }

    public String getBounding_rectangle_origin_y() {
        return bounding_rectangle_origin_y;
    }

    public void setBounding_rectangle_origin_y(String bounding_rectangle_origin_y) {
        this.bounding_rectangle_origin_y = bounding_rectangle_origin_y;
    }

    public String getBounding_rectangle_width() {
        return bounding_rectangle_width;
    }

    public void setBounding_rectangle_width(String bounding_rectangle_width) {
        this.bounding_rectangle_width = bounding_rectangle_width;
    }

    public String getBounding_rectangle_height() {
        return bounding_rectangle_height;
    }

    public void setBounding_rectangle_height(String bounding_rectangle_height) {
        this.bounding_rectangle_height = bounding_rectangle_height;
    }

    public String getX_center_of_mass() {
        return x_center_of_mass;
    }

    public void setX_center_of_mass(String x_center_of_mass) {
        this.x_center_of_mass = x_center_of_mass;
    }

    public String getX_centroid() {
        return x_centroid;
    }

    public void setX_centroid(String x_centroid) {
        this.x_centroid = x_centroid;
    }

    public String getY_center_of_mass() {
        return y_center_of_mass;
    }

    public void setY_center_of_mass(String y_center_of_mass) {
        this.y_center_of_mass = y_center_of_mass;
    }

    public String getY_centroid() {
        return y_centroid;
    }

    public void setY_centroid(String y_centroid) {
        this.y_centroid = y_centroid;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getSphericity() {
        return sphericity;
    }

    public void setSphericity(String sphericity) {
        this.sphericity = sphericity;
    }

    public String getSurfaceDiameter() {
        return surfaceDiameter;
    }

    public void setSurfaceDiameter(String surfaceDiameter) {
        this.surfaceDiameter = surfaceDiameter;
    }

    public String getVolumeDiameter() {
        return volumeDiameter;
    }

    public void setVolumeDiameter(String volumeDiameter) {
        this.volumeDiameter = volumeDiameter;
    }

    public String getVolumeToSurface() {
        return volumeToSurface;
    }

    public void setVolumeToSurface(String volumeToSurface) {
        this.volumeToSurface = volumeToSurface;
    }
}
