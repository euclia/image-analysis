package image.models.nanotubesRidgeDetection;

import de.biomedical_imaging.ij.steger.Junctions;
import de.biomedical_imaging.ij.steger.Lines;
import ij.ImagePlus;

import java.util.ArrayList;

public class RidgeResult {

    private ArrayList<Lines> resultLines;
    private ArrayList<Junctions> resultJunction;
    private ArrayList<RidgeLineReport> ridgeLineReport;
    private ArrayList<RidgeLinesReport> ridgeLinesReport;
    private ImagePlus resultImage;
    private ImagePlus initialImage;
    private ImagePlus histogram;
    public RidgeResult() {
        resultLines= new ArrayList<>();
        resultJunction = new ArrayList<>();
        ridgeLineReport = new ArrayList<>();
        ridgeLinesReport = new ArrayList<>();
    }

    public RidgeResult(ArrayList<Lines> resultLines, ArrayList<Junctions> resultJunction, ImagePlus resultImage) {
        this.resultLines = resultLines;
        this.resultJunction = resultJunction;
        this.resultImage = resultImage;
    }

    public ImagePlus getHistogram() {
        return histogram;
    }

    public void setHistogram(ImagePlus histogram) {
        this.histogram = histogram;
    }

    public ArrayList<RidgeLineReport> getRidgeLineReport() {
        return ridgeLineReport;
    }

    public void setRidgeLineReport(ArrayList<RidgeLineReport> ridgeLineReport) {
        this.ridgeLineReport = ridgeLineReport;
    }

    public ArrayList<RidgeLinesReport> getRidgeLinesReport() {
        return ridgeLinesReport;
    }

    public void setRidgeLinesReport(ArrayList<RidgeLinesReport> ridgeLinesReport) {
        this.ridgeLinesReport = ridgeLinesReport;
    }

    public ArrayList<Lines> getResultLines() {
        return resultLines;
    }

    public void setResultLines(ArrayList<Lines> resultLines) {
        this.resultLines = resultLines;
    }

    public ArrayList<Junctions> getResultJunction() {
        return resultJunction;
    }

    public void setResultJunction(ArrayList<Junctions> resultJunction) {
        this.resultJunction = resultJunction;
    }

    public ImagePlus getResultImage() {
        return resultImage;
    }

    public void setResultImage(ImagePlus resultImage) {
        this.resultImage = resultImage;
    }

    public ImagePlus getInitialImage() {
        return initialImage;
    }

    public void setInitialImage(ImagePlus initialImage) {
        this.initialImage = initialImage;
    }
}
