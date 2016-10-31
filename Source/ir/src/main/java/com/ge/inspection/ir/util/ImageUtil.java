package com.ge.inspection.ir.util;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.springframework.stereotype.Component;

@Component("imageUtil")
public class ImageUtil {
	static {

        System.setProperty("java.awt.headless", "false");
	}
	public byte[] captureImage(String path,List<Double> viewportOffset) {
		byte[] imgByte=null;
        try {
            Robot robot = new Robot();
            String format = "jpg";
            String fileName = "PartialScreenshot." + format;
            System.out.println(viewportOffset);
            
            
            Double top=Double.valueOf(String.valueOf(viewportOffset.get(0)));
            Double bottom=Double.valueOf(String.valueOf(viewportOffset.get(1)));
            Double right=Double.valueOf(String.valueOf(viewportOffset.get(2)));
            Double left=Double.valueOf(String.valueOf(viewportOffset.get(3)));
            
            Rectangle captureRect = new Rectangle(top.intValue(),bottom.intValue(),right.intValue(),left.intValue());
            BufferedImage screenFullImage = robot.createScreenCapture(captureRect);
            /*
            WritableRaster raster = screenFullImage .getRaster();
            DataBuffer data   =  raster.getDataBuffer();
            */
            ImageIO.write(screenFullImage, format, new File(fileName));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            System.out.println(screenFullImage);
            
            ImageIO.write(screenFullImage, format, baos);
            imgByte= baos.toByteArray();
             
            System.out.println("A partial screenshot saved!");
        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
        return imgByte;
    }
	
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
	
	public static byte[] getImageBinary(String originalLocation){
		byte[] data=null;
		try {
         
	    Path path = Paths.get(originalLocation);
	    data = Files.readAllBytes(path);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	
	public static boolean isCompressedFilePresent(String location){
		File imageFile=new File(location);
		if(imageFile.exists() && !imageFile.isDirectory()) { 
		    return true;
		}
		return false;
	}
}
