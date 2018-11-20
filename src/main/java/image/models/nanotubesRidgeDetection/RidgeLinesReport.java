package image.models.nanotubesRidgeDetection;

public class RidgeLinesReport  {
    private String frame;
    private String contourId;
    private String length;
    private String meanLineWidth;

    public RidgeLinesReport(String frame, String contourId, String length, String meanLineWidth) {
        this.frame = frame;
        this.contourId = contourId;
        this.length = length;
        this.meanLineWidth = meanLineWidth;
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getMeanLineWidth() {
        return meanLineWidth;
    }

    public void setMeanLineWidth(String meanLineWidth) {
        this.meanLineWidth = meanLineWidth;
    }

    @Override
    public String toString() {
        return "frame='" + frame +", contourId='" + contourId +", length='" + length + ", meanLineWidth='" + meanLineWidth;
    }
}
