package image.models.nanotubesRidgeDetection;

public class RidgeLineReport {
    private String frame;
    private String contourId;
    private String pos;
    private String xCoordinates;
    private String yCoordinates;
    private String length;
    private String contrast;
    private String asymmetry;
    private String lineWidth;
    private String angleOfNormal;
    private String clasS;

    public RidgeLineReport(String frame, String contourId, String pos, String xCoordinates, String yCoordinates, String length, String contrast, String asymmetry, String lineWidth, String angleOfNormal, String clasS) {
        this.frame = frame;
        this.contourId = contourId;
        this.pos = pos;
        this.xCoordinates = xCoordinates;
        this.yCoordinates = yCoordinates;
        this.length = length;
        this.contrast = contrast;
        this.asymmetry = asymmetry;
        this.lineWidth = lineWidth;
        this.angleOfNormal = angleOfNormal;
        this.clasS = clasS;
    }

    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getContourId() {
        return contourId;
    }

    public void setContourId(String contourId) {
        this.contourId = contourId;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public Double getxCoordinates() {
        return Double.valueOf(xCoordinates);
    }

    public void setxCoordinates(String xCoordinates) {
        this.xCoordinates = xCoordinates;
    }

    public Double getyCoordinates() {
        return Double.valueOf(yCoordinates);
    }

    public void setyCoordinates(String yCoordinates) {
        this.yCoordinates = yCoordinates;
    }

    public Double getLength() {
        return Double.valueOf(length);
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Double getContrast() {
        return Double.valueOf(contrast);
    }

    public void setContrast(String contrast) {
        this.contrast = contrast;
    }

    public Double getAsymmetry() {
        return Double.valueOf(asymmetry);
    }

    public void setAsymmetry(String asymmetry) {
        this.asymmetry = asymmetry;
    }

    public Double getLineWidth() {
        return Double.valueOf(lineWidth);
    }

    public void setLineWidth(String lineWidth) {
        this.lineWidth = lineWidth;
    }

    public Double getAngleOfNormal() {
        return Double.valueOf(angleOfNormal);
    }

    public void setAngleOfNormal(String angleOfNormal) {
        this.angleOfNormal = angleOfNormal;
    }

    public String getClasS() {
        return clasS;
    }

    public void setClasS(String clasS) {
        this.clasS = clasS;
    }

    @Override
    public String toString() {
        return "RidgeLineReport{" +
                "frame='" + frame + '\'' +
                ", contourId='" + contourId + '\'' +
                ", pos='" + pos + '\'' +
                ", xCoordinates='" + xCoordinates + '\'' +
                ", yCoordinates='" + yCoordinates + '\'' +
                ", length='" + length + '\'' +
                ", contrast='" + contrast + '\'' +
                ", asymmetry='" + asymmetry + '\'' +
                ", lineWidth='" + lineWidth + '\'' +
                ", angleOfNormal='" + angleOfNormal + '\'' +
                ", clasS='" + clasS + '\'' +
                '}';
    }
}
