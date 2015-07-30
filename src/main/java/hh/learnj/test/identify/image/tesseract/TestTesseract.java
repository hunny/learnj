package hh.learnj.test.identify.image.tesseract;

import hh.learnj.test.identify.utils.IdentifyUtils;
import hh.learnj.test.image.utils.ImageArithmetic;

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
		
		ImageArithmetic.imageGrayScale(img);
		g2d.drawImage(img, null, 0, imgHeight * 1);
		
		ImageArithmetic.imageGrayRevert(img);
		g2d.drawImage(img, null, 0, imgHeight * 2);
		
		ImageArithmetic.imageBinarization(img, 255 / 2);
		g2d.drawImage(img, null, 0, imgHeight * 3);
		
	    g2d.dispose();
	    
	    ImageIO.write(bufferedImage, "PNG", new File("abc.png"));
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
