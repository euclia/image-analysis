//
// ImageJ macro to preprocess TEM images of NPs with aggregated clusters 
// that allows separating the NPs and getting measurements even 
// from adjacent NPs
// This only performs preprocessing on the image.
// After running the macro, the image can be fed into 
// https://nanoimage.cloud.nanosolveit.eu/nanoImage/ for processing and measurements.
// 
// By the Unit of Process Control & Informatics, School of Chemical Engineering, NTUA
// https://www.chemeng.ntua.gr/labs/control_lab/index.html
	run("Apply LUT");
	run("Smooth");
	run("Smooth");
	run("Smooth");
	setAutoThreshold("Huang");
	setOption("BlackBackground", false);
	run("Convert to Mask");
	run("Erode");
	run("Watershed");
	run("Erode");

