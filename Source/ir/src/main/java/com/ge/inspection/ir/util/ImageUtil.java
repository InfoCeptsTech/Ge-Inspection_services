package com.ge.inspection.ir.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageUtil {

	
	public static String storeAndCompressedFile(String originalLocation,String compLocation){
		File imageFile = new File(originalLocation);
	    File compressedImageFile = new File(compLocation+"/"+imageFile.getName());
		try {
	    
	    InputStream is = new FileInputStream(imageFile);
	    OutputStream os = new FileOutputStream(compressedImageFile);

	    float quality = 0.7f; // Change this as needed

	    BufferedImage image;
		image = ImageIO.read(is);
		
	    // get all image writers for JPG format
	    Iterator<ImageWriter> writers = ImageIO
	            .getImageWritersByFormatName("jpg");

	    if (!writers.hasNext())
	        throw new IllegalStateException("No writers found");

	    ImageWriter writer = (ImageWriter) writers.next();
	    ImageOutputStream ios = ImageIO.createImageOutputStream(os);
	    writer.setOutput(ios);

	    // set compression quality
	    ImageWriteParam param = writer.getDefaultWriteParam();

	    param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	    param.setCompressionQuality(quality);

	    writer.write(null, new IIOImage(image, null, null), param);
	   
	    writer.dispose();
	    ios.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return compressedImageFile.getName();
	}
	
	public static boolean isCompressedFilePresent(String location){
		File imageFile=new File(location);
		if(imageFile.exists() && !imageFile.isDirectory()) { 
		    return true;
		}
		return false;
	}
}
