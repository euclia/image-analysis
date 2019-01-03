package image.models.nanotubes;

import de.biomedical_imaging.ij.steger.Junctions;
import de.biomedical_imaging.ij.steger.Lines;
import ij.ImagePlus;

import java.util.ArrayList;

public class NanoResult {

    private ArrayList<Lines> resultLines;
    private ArrayList<Junctions> resultJunction;
    private ArrayList<NanoFullReport> nanoFullReport;
    private ArrayList<NanoSummaryReports> nanoSummaryReports;
    private ImagePlus resultImage;
    private ImagePlus initialImage;
    private ImagePlus histogram;
    public NanoResult() {
        resultLines= new ArrayList<>();
        resultJunction = new ArrayList<>();
        nanoFullReport = new ArrayList<>();
        nanoSummaryReports = new ArrayList<>();
    }

    public NanoResult(ArrayList<Lines> resultLines, ArrayList<Junctions> resultJunction, ImagePlus resultImage) {
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

    public ArrayList<NanoFullReport> getNanoFullReport() {
        return nanoFullReport;
    }

    public void setNanoFullReport(ArrayList<NanoFullReport> nanoFullReport) {
        this.nanoFullReport = nanoFullReport;
    }

    public ArrayList<NanoSummaryReports> getNanoSummaryReports() {
        return nanoSummaryReports;
    }

    public void setNanoSummaryReports(ArrayList<NanoSummaryReports> nanoSummaryReports) {
        this.nanoSummaryReports = nanoSummaryReports;
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