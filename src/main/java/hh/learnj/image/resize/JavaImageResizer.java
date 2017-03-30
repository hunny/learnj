/**
 * 
 */
package hh.learnj.image.resize;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This class will resize all the images in a given folder
 * 
 * @author
 *
 */
public class JavaImageResizer {

	public static void main(String[] args) throws IOException {

		String basePath = "D:/work/code/learnj/src/main/resources/icons/";
		File folder = new File(basePath);
		File[] listOfFiles = folder.listFiles();
		System.out.println("Total No of Files:" + listOfFiles.length);
		BufferedImage img = null;
		BufferedImage tempJPG = null;
		File newFileJPG = null;
		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				System.out.println("File " + listOfFiles[i].getName());
				img = ImageIO.read(new File(basePath + listOfFiles[i].getName()));
				tempJPG = createResizedCopy(img, 60, 60, false);
				newFileJPG = new File(basePath + listOfFiles[i].getName());
				ImageIO.write(tempJPG, "png", newFileJPG);
			}
		}
		System.out.println("DONE");
	}
	
	public static BufferedImage createResizedCopy(Image originalImage, int scaledWidth, int scaledHeight, boolean preserveAlpha) {
		System.out.println("resizing...");
		int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
		BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
		Graphics2D g = scaledBI.createGraphics();
		if (preserveAlpha) {
			g.setComposite(AlphaComposite.Src);
		}
		g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null);
		g.dispose();
		return scaledBI;
	}
}
