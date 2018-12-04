package image.helpers;

import de.biomedical_imaging.ij.steger.Line;
import de.biomedical_imaging.ij.steger.LineDetector;
import de.biomedical_imaging.ij.steger.Lines;
import de.biomedical_imaging.ij.steger.OverlapOption;
import ij.CompositeImage;
import ij.IJ;
import ij.ImagePlus;
import ij.ImageStack;
import ij.gui.Overlay;
import ij.gui.PolygonRoi;
import ij.measure.Calibration;
import ij.process.FloatPolygon;
import ij.process.ImageProcessor;
import image.models.nanotubesRidgeDetection.RidgeLineReport;
import image.models.nanotubesRidgeDetection.RidgeLinesReport;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class RidgeHelper {

    private ImagePlus imp;
    private ArrayList<Lines> lines;

    public RidgeHelper(ImagePlus imp){
        this.imp=imp;
        this.lines=new ArrayList<>();
    }

    public void runRidgeDetection(double sigma, double upperThresh, double lowerThresh, double minLength, double maxLength) {
        LineDetector detect = new LineDetector();
        lines.add(detect.detectLines(imp.getChannelProcessor(), sigma, upperThresh, lowerThresh, minLength, maxLength, false, true, true, true, OverlapOption.SLOPE));
    }

    public ImagePlus getImp() {
        return imp;
    }

    public ArrayList<Lines> getLines() {
        return lines;
    }

    public ArrayList<Double> retrieveMeanData() {
        if(lines.size()==0) return null;
        Calibration cal = imp.getCalibration();
        Iterator rt2 = lines.iterator();
        ArrayList<Double> meanData = new ArrayList<>();
        while (rt2.hasNext()) {
            Lines contours = (Lines) rt2.next();
            Iterator junctions = contours.iterator();

            while (junctions.hasNext()) {
                Line c = (Line) junctions.next();
                double j = 0.0D;
                for (int i = 0; i < c.getNumber(); ++i) {
                    j += (double) (c.getLineWidthL()[i] + c.getLineWidthR()[i]);
                }
                if(j>=1.0D)
                meanData.add(j / (double) c.getNumber() * cal.pixelWidth);
            }
        }
        return meanData;
    }

    public void createResultsTable(ArrayList<RidgeLineReport> ridgeLineReports, ArrayList<RidgeLinesReport> ridgeLinesReports) {
        if(lines.size()==0) return;
        Calibration cal = imp.getCalibration();
        Iterator rt2 = lines.iterator();
        double totalMeanWidth=0.0D;
        double totalMeanLength=0.0D;
        int totalNonZero=0;
        while (rt2.hasNext()) {
            Lines contours = (Lines) rt2.next();
            Iterator junctions = contours.iterator();
            while (junctions.hasNext()) {
                Line c = (Line) junctions.next();
                double j = 0.0D;

                for (int i = 0; i < c.getNumber(); ++i) {
                    ridgeLineReports.add(new RidgeLineReport(
                            String.valueOf(contours.getFrame()),
                            String.valueOf(c.getID()),
                            String.valueOf(i + 1),
                            String.valueOf(c.getXCoordinates()[i] * cal.pixelWidth),
                            String.valueOf(c.getYCoordinates()[i] * cal.pixelHeight),
                            String.valueOf(c.estimateLength() * cal.pixelHeight),
                            String.valueOf(Math.abs(c.getIntensity()[i])),
                            String.valueOf(Math.abs(c.getAsymmetry()[i])),
                            String.valueOf((c.getLineWidthL()[i] + c.getLineWidthR()[i]) * cal.pixelWidth),
                            String.valueOf((double) c.getAngle()[i]),
                            String.valueOf(c.getContourClass().toString().substring(5))));
                    j += (double) (c.getLineWidthL()[i] + c.getLineWidthR()[i]);
                }
                //here we exclude lines with width == 0; //noise
                if (j/(double)c.getNumber()*cal.pixelWidth!=0) {
                    ridgeLinesReports.add(new RidgeLinesReport(
                            String.valueOf(contours.getFrame()),
                            String.valueOf(c.getID()),
                            String.valueOf(c.estimateLength() * cal.pixelWidth),
                            String.valueOf(j / (double) c.getNumber() * cal.pixelWidth)));
                    totalNonZero++;
                }
                totalMeanLength+=c.estimateLength() * cal.pixelWidth;
                totalMeanWidth+=j / (double) c.getNumber() * cal.pixelWidth;
            }
        }
        ridgeLinesReports.add(0,new RidgeLinesReport(
                "Average",
                "Average",
                String.valueOf(totalMeanLength/totalNonZero),
                String.valueOf(totalMeanWidth/totalNonZero)));
    }

    public ImagePlus makeBinary() {
        if(lines.size()==0) return null;
        ImagePlus binary = IJ.createHyperStack(imp.getTitle() + " Detected segments", imp.getWidth(), imp.getHeight(), imp.getNChannels(), imp.getStackSize() / imp.getNChannels(), 1, 8);
        binary.copyScale(imp);
        ImageProcessor binaryProcessor = binary.getProcessor();
        binaryProcessor.invertLut();
        if (imp.getCompositeMode() > 0) {
            ((CompositeImage) binary).setLuts(imp.getLuts());
        }
        ImageStack is = binary.getImageStack();
        ImageProcessor ip = binary.getProcessor();
        Iterator var5 = this.lines.iterator();

        label58:
        while (var5.hasNext()) {
            Lines contours = (Lines) var5.next();
            Iterator var7 = contours.iterator();

            while (true) {
                float[] x;
                int[] x_poly_r;
                int[] y_poly_r;
                Polygon LineSurface;
                int j;

                if (!var7.hasNext()) {
                    continue label58;
                }

                Line c = (Line) var7.next();
                x = c.getXCoordinates();
                float[] y = c.getYCoordinates();
                x_poly_r = new int[x.length];
                y_poly_r = new int[x.length];
                LineSurface = new Polygon();
                ip = is.getProcessor(c.getFrame());
                ip.setLineWidth(1);
                ip.setColor(255);
                for (j = 0; j < x.length; ++j) {
                    if (j > 0) {
                        ip.drawLine(Math.round(x[j - 1]), Math.round(y[j - 1]), Math.round(x[j]), Math.round(y[j]));
                    }
                }
                for (j = 0; j < x.length; ++j) {
                    if (j < x.length) {
                        LineSurface.addPoint(x_poly_r[x.length - 1 - j], y_poly_r[x.length - 1 - j]);
                    }
                }
                ip.fillPolygon(LineSurface);
            }
        }
        return binary;
    }

    public Overlay displayContours() {
        //this.imp.setOverlay((Overlay)null);
        if(lines.size()==0) return null;
        Overlay ovpoly = new Overlay();
        double px_r = 0.0D;
        double py_r = 0.0D;
        double px_l = 0.0D;
        double py_l = 0.0D;

        int k;
        for(k = 0; k < lines.size(); ++k) {
            for (int pointpoly = 0; pointpoly < ((Lines) lines.get(k)).size(); ++pointpoly) {
                FloatPolygon pointroi = new FloatPolygon();
                FloatPolygon position = new FloatPolygon();
                FloatPolygon polyL = new FloatPolygon();
                Line cont = (Line) ((Lines) lines.get(k)).get(pointpoly);
                int num_points = cont.getNumber();
                double last_w_r = 0.0D;
                double last_w_l = 0.0D;

                for (int polyRoiMitte = 0; polyRoiMitte < num_points; ++polyRoiMitte) {
                    double px = (double) cont.getXCoordinates()[polyRoiMitte];
                    double py = (double) cont.getYCoordinates()[polyRoiMitte];
                    double nx = Math.sin((double) cont.getAngle()[polyRoiMitte]);
                    double ny = Math.cos((double) cont.getAngle()[polyRoiMitte]);

                    px_r = px + (double) cont.getLineWidthR()[polyRoiMitte] * nx;
                    py_r = py + (double) cont.getLineWidthR()[polyRoiMitte] * ny;
                    px_l = px - (double) cont.getLineWidthL()[polyRoiMitte] * nx;
                    py_l = py - (double) cont.getLineWidthL()[polyRoiMitte] * ny;


                    pointroi.addPoint(px + 0.5D, py + 0.5D);

                    if (last_w_r > 0.0D && cont.getLineWidthR()[polyRoiMitte] > 0.0F) {
                        position.addPoint(px_r + 0.5D, py_r + 0.5D);
                    }

                    if (last_w_l > 0.0D && cont.getLineWidthL()[polyRoiMitte] > 0.0F) {
                        polyL.addPoint(px_l + 0.5D, py_l + 0.5D);
                    }


                    last_w_r = (double) cont.getLineWidthR()[polyRoiMitte];
                    last_w_l = (double) cont.getLineWidthL()[polyRoiMitte];

                }

                PolygonRoi var38 = new PolygonRoi(pointroi, 6);
                var38.setStrokeColor(Color.red);
                int position1 = ((Lines) lines.get(k)).getFrame();

                //position1 = imp.getCurrentSlice();


                var38.setPosition(position1);
                ovpoly.add(var38);
                if (polyL.npoints > 1) {
                    PolygonRoi posx = new PolygonRoi(polyL, 6);
                    posx.setStrokeColor(Color.green);
                    position1 = ((Lines) lines.get(k)).getFrame();
                    posx.setPosition(position1);
                    ovpoly.add(posx);
                    PolygonRoi posy = new PolygonRoi(position, 6);
                    posy.setStrokeColor(Color.green);
                    posy.setPosition(position1);
                    ovpoly.add(posy);
                }
            }
        }
           /*     if(this.showIDs) {
                    int var39 = (int)pointroi.xpoints[pointroi.npoints / 2];
                    int var40 = (int)pointroi.ypoints[pointroi.npoints / 2];
                    TextRoi tr = new TextRoi(var39, var40, "" + cont.getID());
                    tr.setCurrentFont(new Font("SansSerif", 0, 9));
                    tr.setIgnoreClipRect(true);
                    tr.setStrokeColor(Color.orange);
                    tr.setPosition(((Junctions)this.resultJunction.get(k)).getFrame());
                    ovpoly.add(tr);
                }
            }
        }

        if(this.showJunctionPoints) {
            for(k = 0; k < this.resultJunction.size(); ++k) {
                FloatPolygon var34 = new FloatPolygon();

                for(int var35 = 0; var35 < ((Junctions)this.resultJunction.get(k)).size(); ++var35) {
                    var34.addPoint((double)((Junction)((Junctions)this.resultJunction.get(k)).get(var35)).x + 0.5D, (double)((Junction)((Junctions)this.resultJunction.get(k)).get(var35)).y + 0.5D);
                }

                PointRoi var36 = new PointRoi(var34);
                var36.setShowLabels(false);
                int var37 = ((Junctions)this.resultJunction.get(k)).getFrame();
                if(!this.doStack || this.isPreview) {
                    var37 = this.imp.getCurrentSlice();
                }

                var36.setPosition(var37);
                ovpoly.add(var36);
            }
        }

        if(ovpoly.size() > 0) {
            this.imp.setOverlay(ovpoly);
        }*/
        return  ovpoly;
    }


}
