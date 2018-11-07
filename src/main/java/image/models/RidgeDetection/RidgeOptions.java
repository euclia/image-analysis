package image.models.RidgeDetection;

/**
 * Created by root on 2/11/2018.
 */
public class RidgeOptions {
    double lineWidth = 3.5D;
    double contrastHigh = 230.0D;
    double contrastLow = 87.0D;
    double sigma = 1.51D;
    double lowerThresh = 3.06D;
    double upperThresh = 7.99D;
    double minLength = 0.0D;
    double maxLength = 0.0D;
    boolean isDarkLine = false;

    static final double lineWidthDefault = 3.5D;
    static final double contrastHighDefault = 230.0D;
    static final double contrastLowDefault = 87.0D;
    static final double sigmaDefault = 1.51D;
    static final double lowerThreshDefault = 3.06D;
    static final double upperThreshDefault = 7.99D;
    static final double minLengthDefault = 0.0D;
    static final double maxLengthDefault = 0.0D;
    static final boolean isDarkLineDefault = false;

    public RidgeOptions(double lineWidth, double contrastHigh, double contrastLow, double sigma, double lowerThresh, double upperThresh, double minLength, double maxLength, boolean isDarkLine) {
        this.lineWidth = lineWidth;
        this.contrastHigh = contrastHigh;
        this.contrastLow = contrastLow;
        this.sigma = sigma;
        this.lowerThresh = lowerThresh;
        this.upperThresh = upperThresh;
        this.minLength = minLength;
        this.maxLength = maxLength;
        this.isDarkLine = isDarkLine;
    }

    public RidgeOptions() {
    }

    public double getLineWidthDefault() {
        return lineWidthDefault;
    }

    public double getContrastHighDefault() {
        return contrastHighDefault;
    }

    public double getContrastLowDefault() {
        return contrastLowDefault;
    }

    public double getSigmaDefault() {
        return sigmaDefault;
    }

    public double getLowerThreshDefault() {
        return lowerThreshDefault;
    }

    public double getUpperThreshDefault() {
        return upperThreshDefault;
    }

    public double getMinLengthDefault() {
        return minLengthDefault;
    }

    public double getMaxLengthDefault() {
        return maxLengthDefault;
    }

    public boolean isDarkLineDefault() {
        return isDarkLineDefault;
    }

    public double getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(double lineWidth) {
        this.lineWidth = lineWidth;
    }

    public double getContrastHigh() {
        return contrastHigh;
    }

    public void setContrastHigh(double contrastHigh) {
        this.contrastHigh = contrastHigh;
    }

    public double getContrastLow() {
        return contrastLow;
    }

    public void setContrastLow(double contrastLow) {
        this.contrastLow = contrastLow;
    }

    public double getSigma() {
        return sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public double getLowerThresh() {
        return lowerThresh;
    }

    public void setLowerThresh(double lowerThresh) {
        this.lowerThresh = lowerThresh;
    }

    public double getUpperThresh() {
        return upperThresh;
    }

    public void setUpperThresh(double upperThresh) {
        this.upperThresh = upperThresh;
    }

    public double getMinLength() {
        return minLength;
    }

    public void setMinLength(double minLength) {
        this.minLength = minLength;
    }

    public double getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(double maxLength) {
        this.maxLength = maxLength;
    }

    public boolean isDarkLine() {
        return isDarkLine;
    }

    public void setDarkLine(boolean darkLine) {
        isDarkLine = darkLine;
    }

}
