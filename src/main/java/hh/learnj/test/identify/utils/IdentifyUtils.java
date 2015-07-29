package hh.learnj.test.identify.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class IdentifyUtils {
	
	/**
	 * 分离图片
	 * @param fileName
	 * @throws Exception
	 */
	public static void splitImage(String fileName) throws Exception {
		BufferedImage img = ImageIO.read(new File(fileName));
		int width = img.getWidth();
		int height = img.getHeight();
		int w = 11;//宽
		int h = 20;//高
		int x = 5;//X坐标
		int y = 0;//Y坐标
		int n = 0;
		System.out.println("width:" + width + ", height:" + height);
		while (true) {
			BufferedImage subImage = img.getSubimage(x, y, w, h);
			ImageIO.write(subImage, "PNG", new File("C:/Users/Hunny.hu/Desktop/result/" + (n++) + ".png"));
			x += w;
			System.out.println("count:" + n);
			if (x > width) {
				break;
			}
		}
	}
	
	public static void createImage(String fileName, int width, int height) throws Exception {
		BufferedImage bufferedImage = new BufferedImage(width, height,
	            BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
	    // Draw graphics
	    g2d.setColor(Color.WHITE);
	    g2d.fillRect(0, 0, width, height);
	    g2d.setPaint(Color.red);
        g2d.setFont(new Font("Serif", Font.BOLD, 20));
        String s = "1";
        FontMetrics fm = g2d.getFontMetrics();
        int x = fm.stringWidth(s);//bufferedImage.getWidth() - 
        int y = fm.getHeight();
        g2d.drawString(s, x, y);
	    g2d.dispose();
	    ImageIO.write(bufferedImage, "PNG", new File(fileName + "/abc.png"));
	}
	
	public static void main(String... args) throws Exception {
//		splitImage("C:/Users/Hunny.hu/Desktop/src_img.png");
		createImage("C:/Users/Hunny.hu/Desktop", 50, 50);
	}
	
}
