/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package image.helpers;

import ij.IJ;
import ij.ImageJ;
import ij.ImagePlus;
import ij.Prefs;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.Binary;
import ij.plugin.filter.EDM;
import ij.plugin.filter.MaximumFinder;
import ij.plugin.filter.ParticleAnalyzer;
import ij.process.AutoThresholder;
import ij.process.AutoThresholder.Method;
import ij.process.BinaryProcessor;
import ij.process.ColorProcessor;
import ij.process.FloatProcessor;
import ij.process.ImageProcessor;
import static ij.process.ImageProcessor.RED_LUT;
import image.models.spherical.ParticleResult;
import image.models.spherical.SphericalReport;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.imageio.ImageIO;

import static ij.process.ImageProcessor.RED_LUT;
//import static ij.process.ImageProcessor.;
import static ij.process.ImageProcessor.BLACK_AND_WHITE_LUT;
import java.awt.Color;

/**
 *
 * @author pantelispanka
 */
public class AgglomeratesHelper {

    private ImagePlus imagePlus;
    private Double scale;

    public AgglomeratesHelper(ImagePlus imagePlus, Double scale) {
        this.imagePlus = imagePlus;
        this.scale = scale;
    }

    public SphericalReport analyseImage(int measurements, String threshold) throws NullPointerException {
        List<ParticleResult> resultsMap = new ArrayList<>();
        SphericalReport theResult;
        try {

            calibrateImage(scale);
//            ImagePlus imp = this.imagePlus;
//            IJ.run(imp, "Apply LUT", "");
//            
//            IJ.run(imp, "Smooth", "");
//            IJ.run(imp, "Smooth", "");
//            IJ.run(imp, "Smooth", "");
//            IJ.setAutoThreshold(imp, "Huang dark");
////            
//            Prefs.blackBackground = false;
//            IJ.run(imp, "Convert to Mask", "");
//            IJ.setAutoThreshold(imp, "Huang dark");
//            IJ.run(imp, "Erode", "");
//            IJ.setAutoThreshold(imp, "Huang dark");
//            IJ.run(imp, "Watershed", "");
//            IJ.setAutoThreshold(imp, "Huang dark");
//            IJ.run(imp, "Erode", "");
//            IJ.run("Set Measurements...", "area standard centroid perimeter bounding fit shape feret's integrated median skewness kurtosis area_fraction redirect=None decimal=4");
//            IJ.run(imp, "Analyze Particles...", "size=100-Infinity circularity=0.50-1.00 show=Outlines display exclude clear summarize add");
//            imp = IJ.getImage();

            this.imagePlus.getProcessor().setAutoThreshold(threshold, false, RED_LUT);
            this.imagePlus.getProcessor().autoThreshold();
            this.imagePlus.getProcessor().smooth();
            this.imagePlus.getProcessor().smooth();
            this.imagePlus.getProcessor().smooth();
            this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);
            this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);
            this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);

            ImagePlus temp = new ImagePlus();
            ij.plugin.filter.EDM edm = new EDM();
            edm.setup("watershed", this.imagePlus);
            edm.toWatershed(this.imagePlus.getProcessor());
            ResultsTable rt = new ResultsTable();

            ParticleAnalyzer particleAnalyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_OUTLINES, measurements, rt, 10, 99999);
            particleAnalyzer.setHideOutputImage(true);
            particleAnalyzer.analyze(this.imagePlus);

            String headers = rt.getColumnHeadings().replace('\t', ',');
            StringBuilder sb = new StringBuilder(headers);
            sb.append("\n");
            sb.append(
                    IntStream.range(0, rt.getCounter())
                            .mapToObj(i -> {
                                return rt.getRowAsString(i).replace('\t', ',');
                            }).collect(Collectors.joining("\n"))
            );

            ReaderCSV readerCSV = new ReaderCSV(sb);
            resultsMap = readerCSV.read();
            //makeExtraCalculations(resultsMap);
            theResult = new SphericalReport(resultsMap, this.applyThreshold(threshold));
            theResult.staticParticle = new ParticleResult(this.calculateAverageModel(rt, readerCSV.headersArray));
            theResult.staticParticle.setId("Average Particle");
            theResult.particleResults.add(0, theResult.staticParticle);
            rt.reset();
        } catch (Exception e) {
            theResult = new SphericalReport();
            e.printStackTrace();
        }
        return theResult;
    }

    public BufferedImage applyThreshold(String threshold) {
        ImagePlus temp = new ImagePlus();
//        temp.getProcessor().
        if (this.imagePlus.getBitDepth() > 16) {
            return null;
        }
        try {
            this.imagePlus.getProcessor().setAutoThreshold(threshold, false, RED_LUT);
            this.imagePlus.updateAndDraw();
            temp = this.imagePlus;
        } catch (Exception e) {
            System.out.println("Exception on countParticles");
            e.printStackTrace();
        }
        return temp.getBufferedImage();

    }

    public BufferedImage getWatershed() {

//        Plugin p = new Plugin(this.imagePlus);
//        p.run("");
//        	ImagePlus imp = IJ.getImage();
//		//IJ.run("Brightness/Contrast...");
//		IJ.run(imp, "Apply LUT", "");
//		IJ.run(imp, "Smooth", "");
//		IJ.run(imp, "Smooth", "");
//		IJ.run(imp, "Smooth", "");
//		IJ.setAutoThreshold(imp, "Huang dark");
//		Prefs.blackBackground = false;
//		IJ.run(imp, "Convert to Mask", "");
//		IJ.setAutoThreshold(imp, "Huang dark");
//		IJ.run(imp, "Erode", "");
//		IJ.setAutoThreshold(imp, "Huang dark");
//		IJ.run(imp, "Watershed", "");
//		IJ.setAutoThreshold(imp, "Huang dark");
//		IJ.run(imp, "Erode", "");
//            ImagePlus imp = this.imagePlus;
//            IJ.run(imp, "Apply LUT", "");
//
//            IJ.run(imp, "Smooth", "");
//            IJ.run(imp, "Smooth", "");
//            IJ.run(imp, "Smooth", "");
//            IJ.setAutoThreshold(imp, "Huang dark");
////            
//            Prefs.blackBackground = false;
//            IJ.run(imp, "Convert to Mask", "");
//            IJ.setAutoThreshold(imp, "Huang dark");
//            IJ.run(imp, "Erode", "");
//            IJ.setAutoThreshold(imp, "Huang dark");
//            IJ.run(imp, "Watershed", "");
//            IJ.setAutoThreshold(imp, "Huang dark");
//            IJ.run(imp, "Erode", "");
//        this.imagePlus.getChannelProcessor().setC
//        ColorProcessor cp = new ColorProcessor(this.imagePlus.getBufferedImage());
//        FloatProcessor fp = new FloatProcessor();
//        cp.setBrightness();
//        this.imagePlus.getProcessor().setAutoThreshold(Method.IJ_IsoData, false, RED_LUT);
//        this.imagePlus.createRoiMask();
//        this.imagePlus.createThresholdMask();
//        this.imagePlus.getProcessor().smooth();
//        this.imagePlus.getProcessor().smooth();
//        this.imagePlus.getProcessor().smooth();
//        this.imagePlus.getProcessor().setAutoThreshold(Method.IJ_IsoData, true);
        this.imagePlus.getProcessor().setAutoThreshold(Method.IJ_IsoData, false, RED_LUT);
        this.imagePlus.updateAndDraw();
//        this.imagePlus.getProcessor().set
        this.imagePlus.getProcessor().autoThreshold();
//        this.imagePlus.getProcessor().
//        this.imagePlus.createThresholdMask();
//        this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);
//        this.imagePlus.getProcessor().erode();
//        this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);

        ij.plugin.filter.EDM edm = new EDM();
//        ij.plugin.filter.
        edm.setup("watershed", this.imagePlus);
        edm.toWatershed(this.imagePlus.getProcessor());
//        this.imagePlus.getProcessor().erode();
//        this.imagePlus.getProcessor().erode();
//        this.imagePlus.getProcessor().erode();
//        this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);
////        this.imagePlus.getProcessor().setAutoThreshold("Huang dark");
//        this.imagePlus.getProcessor().erode();
//        edm.setup("watershed", this.imagePlus);
//        edm.toWatershed(this.imagePlus.getProcessor());
//        this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);
////        this.imagePlus.getProcessor().setAutoThreshold("Huang dark");
//        this.imagePlus.getProcessor().erode();
//
//        this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);
//        this.imagePlus.getProcessor().erode();
//        ImagePlus temp = new ImagePlus();
//        
//        temp = this.imagePlus;
        return this.imagePlus.getBufferedImage();
    }

    public BufferedImage applyNoThreshold(String threshold) {
        ImagePlus temp = new ImagePlus();
        if (this.imagePlus.getBitDepth() > 16) {
            return null;
        }
        try {
            this.imagePlus.updateAndDraw();
            temp = this.imagePlus;
        } catch (Exception e) {
            System.out.println("Exception on countParticles");
            e.printStackTrace();
        }
        return temp.getBufferedImage();

    }

    public SphericalReport countParticles(String thresholdType, int measurements) {
        List<ParticleResult> resultsMap = new ArrayList<>();
        SphericalReport theResult;
        try {

            if (this.imagePlus.getProcessor() == null) {
                theResult = new SphericalReport(resultsMap, null);
            } else {
                calibrateImage(scale);

                this.imagePlus.getProcessor().setAutoThreshold(thresholdType, false, RED_LUT);

                this.imagePlus.getProcessor().smooth();
                this.imagePlus.getProcessor().smooth();
                this.imagePlus.getProcessor().smooth();

                this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);
                this.imagePlus.getProcessor().erode();
                this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);
                this.imagePlus.getProcessor().autoThreshold();
                this.imagePlus.getProcessor().convertToByteProcessor(false);
                ij.plugin.filter.EDM edm = new EDM();
                edm.setup("watershed", this.imagePlus);
                edm.toWatershed(this.imagePlus.getProcessor());
//                this.imagePlus.updateAndDraw();
                this.imagePlus.getProcessor().setAutoThreshold(Method.Huang, false);
//                this.imagePlus.getProcessor().setAutoThreshold("Huang dark");
                this.imagePlus.getProcessor().erode();
                ResultsTable rt = new ResultsTable();

//                ParticleAnalyzer particleAnalyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_OUTLINES, measurements, rt, 10, 99999);
//                ParticleAnalyzer particleAnalyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_ROI_MASKS, measurements, rt, 0, 1000000);
                ParticleAnalyzer particleAnalyzer = new ParticleAnalyzer(ParticleAnalyzer.SHOW_OUTLINES, measurements, rt, 10, 99999);
                particleAnalyzer.setHideOutputImage(true);
                particleAnalyzer.analyze(this.imagePlus);

                //Case of error in particle analyzer
                if (particleAnalyzer.getOutputImage() == null) {
                    theResult = new SphericalReport(resultsMap, null);
                } else {
                    String headers = rt.getColumnHeadings().replace('\t', ',');
                    StringBuilder sb = new StringBuilder(headers);
                    sb.append("\n");
                    sb.append(
                            IntStream.range(0, rt.getCounter())
                                    .mapToObj(i -> {
                                        return rt.getRowAsString(i).replace('\t', ',');
                                    }).collect(Collectors.joining("\n"))
                    );

                    ReaderCSV readerCSV = new ReaderCSV(sb);
                    resultsMap = readerCSV.read();

                    theResult = new SphericalReport(resultsMap, particleAnalyzer.getOutputImage().getBufferedImage());
                    theResult.staticParticle = new ParticleResult(this.calculateAverageModel(rt, readerCSV.headersArray));
                    theResult.staticParticle.setId("Average Particle");
                    theResult.particleResults.add(0, theResult.staticParticle);
                    rt.reset();
                }
            }

        } catch (Exception e) {
            theResult = new SphericalReport();
            System.out.println("Exception on countParticles");
            e.printStackTrace();
        }
        return theResult;
    }

    public HashMap<String, String> calculateAverageModel(ResultsTable rt, String[] headers) {
        HashMap<String, String> averageMap = new HashMap<>();
        for (int i = 1; i < headers.length; i++) {
            int idx = rt.getColumnIndex(headers[i]);
            averageMap.put(rt.getColumnHeading(idx), getAverageFromArray(rt.getColumn(idx)));
        }
        return averageMap;
    }

    public String getAverageFromArray(float[] array) {
        float temp = 0;
        for (int i = 0; i < array.length; i++) {
            temp = temp + array[i];
        }
        return String.valueOf(temp / array.length);
    }

    public static void cropAndResize(ImagePlus imp, int targetWidth, int targetHeight) throws Exception {
        ImageProcessor ip = imp.getProcessor();
        System.out.println("size1: " + ip.getWidth() + "x" + ip.getHeight());
        ip.setInterpolationMethod(ImageProcessor.BILINEAR);
        ip = ip.resize(targetWidth * 2, targetHeight * 2);
        System.out.println("size2: " + ip.getWidth() + "x" + ip.getHeight());

        int cropX = ip.getWidth() / 2;
        int cropY = ip.getHeight() / 2;
        ip.setRoi(cropX, cropY, targetWidth, targetHeight);
        ip = ip.crop();
        System.out.println("size3: " + ip.getWidth() + "x" + ip.getHeight());
        BufferedImage croppedImage = ip.getBufferedImage();

        System.out.println("size4: " + ip.getWidth() + "x" + ip.getHeight());
        new ImagePlus("croppedImage", croppedImage).show();

        ImageIO.write(croppedImage, "jpg", new File("cropped.jpg"));
    }

    private void calibrateImage(Double scale) {
        Calibration cal = this.imagePlus.getCalibration();
        Calibration calOrig = cal.copy();
        cal.pixelWidth = 1 / scale;
        cal.pixelHeight = cal.pixelWidth;
        cal.setUnit("nm");
        if (!cal.equals(calOrig)) {
            this.imagePlus.setCalibration(cal);
        }
        this.imagePlus.updateAndDraw();
    }

    public static float[] threshold(ImageProcessor ip, String method) {

        int w = ip.getWidth();
        int h = ip.getHeight();

        float[] mask = new float[w * h];
        AutoThresholder auto = new ij.process.AutoThresholder();
        int thresh = auto.getThreshold(method, ip.getStatistics().histogram);

        int nh = ip.getStatistics().histogram.length;

        ip.setHistogramRange(ip.getStatistics().min, ip.getStatistics().max);
        double a1 = ip.getHistogramMin();
        double a2 = ip.getHistogramMax();
        double d = (a2 - a1) / (1. * nh);
        double tval = a1 + d * (thresh + 1);

        float[] pixels = (float[]) ip.convertToFloatProcessor().getPixels();

        for (int i = 0; i < w * h; i++) {
            if (pixels[i] >= tval) {
                mask[i] = 255.f;
            }
        }
        System.out.println(a1 + " " + a2 + " " + d + " " + thresh + " " + tval + " " + nh);
        return mask;
    }

}
