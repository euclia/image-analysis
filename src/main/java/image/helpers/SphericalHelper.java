package image.helpers;

import ij.ImagePlus;
import ij.measure.Calibration;
import ij.measure.ResultsTable;
import ij.plugin.filter.Analyzer;
import ij.plugin.filter.ParticleAnalyzer;
import ij.process.ImageConverter;
import ij.process.ImageProcessor;
import image.models.spherical.ParticleResult;
import image.models.spherical.SphericalReport;

import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ij.process.ImageProcessor.RED_LUT;

public class SphericalHelper {

    private ImagePlus imagePlus;
    private ImageConverter imageConverter;

    public SphericalHelper(ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
        this.imageConverter = new ImageConverter(this.imagePlus);
        this.imageConverter.convertToGray8();
    }

    public SphericalReport analyseImage(int measurements, String threshold) throws NullPointerException {
        List<ParticleResult> resultsMap = new ArrayList<>();
        SphericalReport theResult;
        try {
            this.imagePlus.getProcessor().setAutoThreshold(threshold,false,RED_LUT);
            this.imagePlus.updateAndDraw();
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
        try {
            this.imagePlus.getProcessor().setAutoThreshold(threshold,false,RED_LUT);
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

            this.imagePlus.getProcessor().setAutoThreshold(thresholdType,false,RED_LUT);
            this.imagePlus.updateAndDraw();

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

            theResult = new SphericalReport(resultsMap, particleAnalyzer.getOutputImage().getBufferedImage());
            theResult.staticParticle = new ParticleResult(this.calculateAverageModel(rt, readerCSV.headersArray));
            theResult.staticParticle.setId("Average Particle");
            theResult.particleResults.add(0, theResult.staticParticle);
            rt.reset();
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

    public void calibrateImage(double pX, double pY, String unit, ImagePlus imp) {
        double originX = 0.0;
        double originY = 0.0;
        Calibration cal = imp.getCalibration();
        cal.setUnit(unit);
        cal.pixelWidth = pX;
        cal.pixelHeight = pY;
        cal.xOrigin = originX / pX;
        cal.yOrigin = originY / pY;
    }
}
