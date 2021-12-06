package image.helpers;

import ij.ImagePlus;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.EDM;
import ij.plugin.filter.ParticleAnalyzer;
import ij.process.AutoThresholder;
import ij.process.AutoThresholder.Method;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import static ij.process.ImageProcessor.BLACK_AND_WHITE_LUT;
import static ij.process.ImageProcessor.OVER_UNDER_LUT;
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

//import static ij.process.ImageProcessor.;
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

            this.imagePlus.getProcessor().setAutoThreshold(threshold, false, RED_LUT);

            ResultsTable rt = new ResultsTable();
            Analyzer analyzer = new Analyzer(this.imagePlus, measurements, rt);
            analyzer.measure();

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

    public SphericalReport countParticles(String thresholdType, int measurements) {
        List<ParticleResult> resultsMap = new ArrayList<>();
        SphericalReport theResult;
        try {

            if (this.imagePlus.getProcessor() == null) {
                theResult = new SphericalReport(resultsMap, null);
            } else {
                calibrateImage(scale);
                this.imagePlus.getProcessor().setAutoThreshold(thresholdType, false, RED_LUT);

                ResultsTable rt = new ResultsTable();
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

    public BufferedImage getWatershed() {

        this.imagePlus.getProcessor().setAutoThreshold(Method.IJ_IsoData, false, RED_LUT);
        this.imagePlus.updateAndDraw();
        this.imagePlus.getProcessor().autoThreshold();

        ij.plugin.filter.EDM edm = new EDM();
        edm.setup("watershed", this.imagePlus);
        edm.toWatershed(this.imagePlus.getProcessor());
        return this.imagePlus.getBufferedImage();
    }

}
