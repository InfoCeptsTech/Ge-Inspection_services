package com.ge.inspection.ir.util;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class ImageUtil {

	public static ImageIcon resizeImageIcon( ImageIcon imageIcon , Integer width , Integer height )
	{
	    BufferedImage bufferedImage = new BufferedImage( width , height , BufferedImage.TRANSLUCENT );

	    Graphics2D graphics2D = bufferedImage.createGraphics();
	    graphics2D.drawImage( imageIcon.getImage() , 0 , 0 , width , height , null );
	    graphics2D.dispose();

	    return new ImageIcon( bufferedImage , imageIcon.getDescription() );
	}
}
