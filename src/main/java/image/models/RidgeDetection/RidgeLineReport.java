package image.models.RidgeDetection;

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

    public String getxCoordinates() {
        return xCoordinates;
    }

    public void setxCoordinates(String xCoordinates) {
        this.xCoordinates = xCoordinates;
    }

    public String getyCoordinates() {
        return yCoordinates;
    }

    public void setyCoordinates(String yCoordinates) {
        this.yCoordinates = yCoordinates;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getContrast() {
        return contrast;
    }

    public void setContrast(String contrast) {
        this.contrast = contrast;
    }

    public String getAsymmetry() {
        return asymmetry;
    }

    public void setAsymmetry(String asymmetry) {
        this.asymmetry = asymmetry;
    }

    public String getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(String lineWidth) {
        this.lineWidth = lineWidth;
    }

    public String getAngleOfNormal() {
        return angleOfNormal;
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
}
