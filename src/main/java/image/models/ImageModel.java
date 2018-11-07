package image.models;

import ij.IJ;
import ij.ImagePlus;
import image.helpers.ImageModelHelper;
import image.helpers.ValidationHelper;

import java.awt.image.BufferedImage;

public class ImageModel extends ImagePlus {

	private String filePath;
	private ImagePlus imp;
	public Double porosity = -1.0;
	private int height = -1;
	private int width = -1;


	public ImageModel(String thePath) {
		this.filePath = String.valueOf(thePath);
		this.imp = IJ.openImage(this.filePath);
	}

	public ImageModel(String imageTitle, BufferedImage theImage) {
		this.imp = new ImagePlus(imageTitle, theImage);
	}

	private void validateFilepath(String filePath) {
		if (ValidationHelper.isFilePathValid(filePath)) {
		} else {
			System.out.println("The filepath you are providing is not valid");
		}
	}

	public void displayImageMetaData() {
		ImageModelHelper.readImageAndDisplayMetaData(this.filePath);
	}

	public String getImageType() {
		return ImageModelHelper.getImageType(this);
	}

	public int getImageHeight() {
		if (this.height == -1) {
			this.height = this.imp.getHeight();
		}
		return this.height;
	}

	public int getImageWidth() {
		if (this.width == -1) {
			this.width = this.imp.getWidth();
		}
		return this.width;
	}
}
