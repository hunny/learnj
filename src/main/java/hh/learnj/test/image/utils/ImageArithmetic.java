package hh.learnj.test.image.utils;

import hh.learnj.test.identify.utils.IdentifyUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageArithmetic {
	
	//TODO Walsh变换
	

	/**
	 * 灰度化，灰度值=0.3R+0.59G+0.11B 浮点算法：Gray=R*0.3+G*0.59+B*0.11
	 * @param img
	 * @return
	 */
	public static BufferedImage imageGrayScale(BufferedImage img) {
		return imageGrayScale(img, 1);
	}
	
	/**
	 * 256色转灰度
	 * @param img
	 * @param type
	 * @return
	 */
	public static BufferedImage imageGrayScale(BufferedImage img, int type) {
		int height = img.getHeight();
		int width = img.getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = img.getRGB(x, y);
				int gray = calcGray(rgb, type);
				Color newColor = new Color(gray, gray, gray);
				img.setRGB(x, y, newColor.getRGB());
			}
		}
		return img;
	}

	/**
	 * 算法介绍（百度百科）： 什么叫灰度图？任何颜色都有红、绿、蓝三原色组成，假如原来某点的颜色为RGB(R，G，B)，
	 * 那么，我们可以通过下面几种方法，将其转换为灰度：
	 * 
	 * 1.浮点算法：Gray=R*0.3+G*0.59+B*0.11 　　 2.整数方法：Gray=(R*30+G*59+B*11)/100 　　
	 * 3.移位方法：Gray =(R*28+G*151+B*77)>>8; 4.平均值法：Gray=（R+G+B）/3; 　　
	 * 5.仅取绿色：Gray=G； 　　
	 * 通过上述任一种方法求得Gray后，将原来的RGB(R,G,B)中的R,G,B统一用Gray替换，形成新的颜色RGB
	 * (Gray,Gray,Gray)，用它替换原来的RGB(R,G,B)就是灰度图了。 灰度分为256阶。所以，用灰度表示的图像称作灰度图。
	 * 
	 * @param rgb
	 * @param type
	 * @return
	 */
	protected static int calcGray(int rgb, int type) {
		Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
		switch (type) {
		case 1: //浮点算法：Gray=R*0.3+G*0.59+B*0.11
			return (int) (0.3 * color.getRed() + 0.59 * color.getGreen() + 0.11 * color
					.getBlue());
		case 2://整数方法：Gray=(R*30+G*59+B*11)/100 
			return (int) (30 * color.getRed() + 59 * color.getGreen() + 11 * color
					.getBlue()) / 100;
		case 3://移位方法：Gray =(R*28+G*151+B*77)>>8
			return (28 * color.getRed() + 151 * color.getGreen() + 77 * color
					.getBlue()) >> 8;
		case 4://平均值法：Gray=（R+G+B）/3; 
			return (color.getRed() + color.getGreen() + color.getBlue()) / 3;
		case 5://仅取绿色：Gray=G
			return color.getGreen();
		}
		//默认，仅取绿色
		return color.getGreen();
	}

	/**
	 * 灰度反转
	 * 
	 * @param img
	 * @return
	 */
	public static BufferedImage imageGrayRevert(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = img.getRGB(x, y);
				Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
				Color newColor = new Color(255 - color.getRed(),
						255 - color.getGreen(), 255 - color.getBlue());
				img.setRGB(x, y, newColor.getRGB());
			}
		}
		return img;
	}

	/**
	 * 二值化，取图片的平均灰度作为阈值，
	 * 低于该值的全都为0，
	 * 高于该值的全都为255 
	 * average += 255 - color.getBlue();
	 * 常用的二值化方法则有：双峰法、P参数法、迭代法和OTSU法
	 * @param img
	 * @param average
	 * @return
	 */
	public static BufferedImage imageBinarization(BufferedImage img, int average) {
		int height = img.getHeight();
		int width = img.getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = img.getRGB(x, y);
				Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
				int value = 255 - color.getBlue();
				if (value > average) {
					Color newColor = new Color(0, 0, 0);
					img.setRGB(x, y, newColor.getRGB());
				} else {
					Color newColor = new Color(255, 255, 255);
					img.setRGB(x, y, newColor.getRGB());
				}
			}
		}
		return img;
	}
	
	public static void main(String[] args) throws Exception {
		
		BufferedImage img = ImageIO.read(IdentifyUtils.class.getResourceAsStream("/hh/learnj/test/identify/image/tesseract/test.png"));
		
		int width = img.getWidth();
		int imgHeight = img.getHeight();
		int height = img.getHeight() * 4;
		BufferedImage bufferedImage = new BufferedImage(width, height,
	            BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(img, 0, 0, width, imgHeight, null);
		
		ImageArithmetic.imageGrayScale(img, 4);
		g2d.drawImage(img, null, 0, imgHeight * 1);
		
		ImageArithmetic.imageGrayRevert(img);
		g2d.drawImage(img, null, 0, imgHeight * 2);
		
		ImageArithmetic.imageBinarization(img, 255 / 2);
		g2d.drawImage(img, null, 0, imgHeight * 3);
		
	    g2d.dispose();
	    File file = new File("abc.png");
	    ImageIO.write(bufferedImage, "PNG", file);
	    System.out.println("File saved: " + file.getAbsolutePath());
	}

}
