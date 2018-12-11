package image.models.nanotubes;

public class NanoSummaryReports {
    private String frame;
    private String contourId;
    private String length;
    private String meanLineWidth;

    public NanoSummaryReports(String frame, String contourId, String length, String meanLineWidth) {
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

    public Double getLength() {
        return Double.valueOf(length);
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Double getMeanLineWidth() {
        return Double.valueOf(meanLineWidth);
    }

    public void setMeanLineWidth(String meanLineWidth) {
        this.meanLineWidth = meanLineWidth;
    }

    @Override
    public String toString() {
        return "frame='" + frame +", contourId='" + contourId +", length='" + length + ", meanLineWidth='" + meanLineWidth;
    }
}
