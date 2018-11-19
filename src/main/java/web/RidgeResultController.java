package web;

import ij.ImagePlus;
import image.models.RidgeDetection.RidgeLineReport;
import image.models.RidgeDetection.RidgeLinesReport;
import image.models.RidgeDetection.RidgeResult;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

@Named("result2Controller")
@SessionScoped
public class RidgeResultController implements Serializable {
    private final String BLANK_IMAGE_PATH = "/resources/blank.png";

    private RidgeResult ridgeResult;

    public StreamedContent getReportAsCSV(ArrayList<Object> objectArrayList) throws IOException {
          ByteArrayOutputStream out = new ByteArrayOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            Iterator<Object> objectIterator = objectArrayList.iterator();
                CSVPrinter csvPrinter = new CSVPrinter
                        (writer, CSVFormat.DEFAULT.withHeader(objectIterator.next().toString()));
            while (objectIterator.hasNext())
                csvPrinter.printRecord(objectIterator.next().toString());
            csvPrinter.flush();
            csvPrinter.close();
        DefaultStreamedContent file = new DefaultStreamedContent(new ByteArrayInputStream(out.toByteArray()), "text/csv", "report.csv");
        return file;
    }

    @Inject private ServletContext context;

    public void initialize() {
        this.ridgeResult = (RidgeResult) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ridgeresult");
    }

    public DefaultStreamedContent getImgPreview(ImagePlus imagePlus) {
        DefaultStreamedContent imgPreview;
        if (imagePlus != null) {
            BufferedImage temp = imagePlus.getBufferedImage();
            int newWidth = new Double(temp.getWidth() * 0.5).intValue();
            int newHeight = new Double(temp.getHeight() * 0.5).intValue();
            BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = newImage.createGraphics();
            g.drawImage(temp, 0, 0, newWidth, newHeight, null);
            g.dispose();
            ByteArrayOutputStream bas = new ByteArrayOutputStream();
            try {
                ImageIO.write(newImage, "png", bas);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] data = bas.toByteArray();
            InputStream is = new ByteArrayInputStream(data);
            imgPreview = new DefaultStreamedContent(is);
        } else {
            imgPreview = this.getBlankImage();
        }
        return imgPreview;
    }

    private DefaultStreamedContent getBlankImage() {
        InputStream is = context.getResourceAsStream(BLANK_IMAGE_PATH);
        return new DefaultStreamedContent(is);
    }
    public java.util.List<RidgeLinesReport> getRidgeLinesReport() {
        return  ridgeResult.getRidgeLinesReport();
    }
    public java.util.List<RidgeLineReport> getRidgeLineReport() {
        return  ridgeResult.getRidgeLineReport();
    }

    public ImagePlus getInitialImage (){
        return ridgeResult.getInitialImage();
    }

    public ImagePlus getResultImage(){
        return  ridgeResult.getResultImage();
    }

    public ImagePlus getHistogram(){
        return ridgeResult.getHistogram();
    }
}
