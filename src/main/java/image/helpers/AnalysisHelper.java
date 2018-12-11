package image.helpers;

public class AnalysisHelper {

	final double PI = 3.14;
	private double volume = 0.0;

	public double findApproximateVolume(double circularity, double feretDiameter, double length)
	{
		if (circularity > 0.750){
			volume = this.getEqualSphericalVolume(feretDiameter);
		} else {
			volume = this.getEqualCylinderVolume(feretDiameter, length);
		}
		return volume;
	}

	private double getEqualCylinderVolume(double feretDiameter, double length)
	{
		return PI*feretDiameter*length/2;
	}

	private double getEqualSphericalVolume(double feretDiameter)
	{
		double radius = (feretDiameter/2);
		double volume = Math.pow(radius, 3)*PI*4/3;
		return volume;
	}

	public double getSurfaceDiameter(double area)
	{
		return Math.sqrt(area/PI);
	}

	public double getVolumeDiameter(double volume)
	{
		return Math.cbrt(6*volume/PI);
	}

	public double getVolumeToSurface(double volume, double area)
	{
		return 6*volume / area;
	}

	public double getSphericity(double volume, double area) {
		return Math.pow(PI, 1/3)*Math.pow(volume, 2/3)*6/area;
	}

	public double getCircularity (double volume, double area) {
		return 4*PI*area/Math.sqrt(volume);
	}
}
