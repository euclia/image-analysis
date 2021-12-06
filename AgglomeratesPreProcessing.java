import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.*;

public class My_Plugin implements PlugIn {

	public void run(String arg) {
		ImagePlus imp = IJ.getImage();
		//IJ.run("Brightness/Contrast...");
		IJ.run(imp, "Apply LUT", "");
		IJ.run(imp, "Smooth", "");
		IJ.run(imp, "Smooth", "");
		IJ.run(imp, "Smooth", "");
		IJ.setAutoThreshold(imp, "Huang dark");
		Prefs.blackBackground = false;
		IJ.run(imp, "Convert to Mask", "");
		IJ.setAutoThreshold(imp, "Huang dark");
		IJ.run(imp, "Erode", "");
		IJ.setAutoThreshold(imp, "Huang dark");
		IJ.run(imp, "Watershed", "");
		IJ.setAutoThreshold(imp, "Huang dark");
		IJ.run(imp, "Erode", "");
		IJ.run("Set Measurements...", "area standard centroid perimeter bounding fit shape feret's integrated median skewness kurtosis area_fraction redirect=None decimal=4");
		IJ.run(imp, "Analyze Particles...", "size=100-Infinity circularity=0.50-1.00 show=Outlines display exclude clear summarize add");
	}

}
