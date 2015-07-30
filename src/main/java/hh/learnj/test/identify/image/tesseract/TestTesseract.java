package hh.learnj.test.identify.image.tesseract;

import hh.learnj.test.identify.utils.IdentifyUtils;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class TestTesseract {

	public static void main(String[] args) throws Exception {
		BufferedImage img = ImageIO.read(IdentifyUtils.class.getResourceAsStream("/hh/learnj/test/identify/image/tesseract/test.png"));
		
		int width = img.getWidth();
		int imgHeight = img.getHeight();
		int height = img.getHeight() * 4;
		BufferedImage bufferedImage = new BufferedImage(width, height,
	            BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bufferedImage.createGraphics();
		g2d.drawImage(img, 0, 0, width, imgHeight, null);
		
		imageGrayScale(img);
		g2d.drawImage(img, null, 0, imgHeight * 1);
		ImageIO.write(img, "PNG", new File("1.png"));
		
		imageGrayRevert(img);
		g2d.drawImage(img, null, 0, imgHeight * 2);
		ImageIO.write(img, "PNG", new File("2.png"));
		
		imageBinarization(img, 255 / 2);
		ImageIO.write(img, "PNG", new File("3.png"));
		g2d.drawImage(img, null, 0, imgHeight * 3);
		
	    g2d.dispose();
	    
	    ImageIO.write(bufferedImage, "PNG", new File("abc.png"));
	}

	/**
	 * 灰度化，灰度值=0.3R+0.59G+0.11B
	 * 
	 * @param img
	 * @return
	 */
	public static BufferedImage imageGrayScale(BufferedImage img) {
		int height = img.getHeight();
		int width = img.getWidth();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = img.getRGB(x, y);
				Color color = new Color(rgb); // 根据rgb的int值分别取得r,g,b颜色。
				int gray = (int) (0.3 * color.getRed() + 0.59
						* color.getGreen() + 0.11 * color.getBlue());
				Color newColor = new Color(gray, gray, gray);
				img.setRGB(x, y, newColor.getRGB());
			}
		}
		return img;
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
	 * 二值化，取图片的平均灰度作为阈值，低于该值的全都为0，高于该值的全都为255
	 * average += 255 - color.getBlue();
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
	
	public static void command() {
		// List<String> cmd = new ArrayList<String>(); // 存放命令行参数的数组
		// cmd.add(tessPath + "\\tesseract");
		// cmd.add("");
		// cmd.add(outputFile.getName()); // 输出文件位置
		// cmd.add(LANG_OPTION); // 字符类别
		// cmd.add("eng"); // 英文，找到tessdata里对应的字典文件。
		// ProcessBuilder pb = new ProcessBuilder();
		// pb.directory(imageFile.getParentFile());
		// cmd.set(1, tempImage.getName()); // 把图片文件位置放在第一个位置
		// pb.command(cmd); // 执行命令行
		// pb.redirectErrorStream(true); // 通知进程生成器是否合并标准错误和标准输出,把进程错误保存起来。
		// Process process = pb.start(); // 开始执行进程
		// int w = process.waitFor(); // 当前进程停止,直到process停止执行，返回执行结果. 
	}

}
