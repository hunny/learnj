package hh.learnj.test.identify.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
	
	/**
	 * 根据亮度设个阈值
	 * 
	 * @param colorInt
	 * @return
	 */
	public static boolean isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
			return true;
		}
		return false;
	}

	/**
	 * 根据亮度设个阈值
	 * @param colorInt
	 * @return
	 */
	public static boolean isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
			return true;
		}
		return false;
	}
	
	public static Map<Integer, Map<String, String>> cutImage(BufferedImage img) throws Exception {
		int width = img.getWidth();
		int height = img.getHeight();
		Integer k = 0;
		Map<Integer, Map<String, String>> borderMap = new HashMap<Integer, Map<String, String>>();
		for (int m = 1; m < width - 1; m++) {
			int black = 0;
			int white = 0;
			for (int n = 1; n < height - 1; n++) {
				if (isWhite(img.getRGB(m, n))) {
					white ++;
//					System.out.print("■");
				} else {
					black ++;
//					System.out.print("□");
				}
			}
//			System.out.println();
			Map<String, String> map = borderMap.get(k);
			if (white == 0) {
				if (null == map) {
					map = new HashMap<String, String>();
					map.put("x1", m + "");
					map.put("s", "0");//刚刚擦边
				} else if ("1".equals(map.get("s"))) {
					map.put("x2", (m + 1) + "");
					map.put("s", "2");//跨越
				}
				borderMap.put(k, map);
				if ("2".equals(map.get("s"))) {
					k ++;
				}
			} else {//通过中
				if (null != map && "0".equals(map.get("s"))) {
					map.put("x1", (m - 1) + "");
					map.put("s", "1");
					borderMap.put(k, map);
				}
			}
		}
//		System.out.println(borderMap);
		return borderMap;
	}
	
	public static void imagePrinter(BufferedImage img) {
		int width = img.getWidth();
		int height = img.getHeight();
		for (int m = 0; m < height; m++) {
			for (int n = 0; n < width; n++) {
				if (isWhite(img.getRGB(n, m))) {
					System.out.print("■");
				} else {
					System.out.print("□");
				}
			}
			System.out.println();
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
//		createImage("C:/Users/Hunny.hu/Desktop", 50, 50);
//		BufferedImage img = ImageIO.read(new File(fileName));
		BufferedImage img = ImageIO.read(IdentifyUtils.class.getResourceAsStream("/hh/learnj/test/identify/code02/code02.png"));
		Map<Integer, Map<String, String>> result = cutImage(img);
		int heigth = img.getHeight();
		for (Map.Entry<Integer, Map<String, String>> m : result.entrySet()) {
			Map<String, String> section = m.getValue();
			String x1 = section.get("x1");
			String x2 = section.get("x2");
			if (null != x1 && null != x2) {
				int w1 = Integer.parseInt(x1);
				int w2 = Integer.parseInt(x2);
				BufferedImage subimage = img.getSubimage(w1, 0, w2 - w1, heigth);
				imagePrinter(subimage);
			}
		}
		imagePrinter(img);
//		ImageIO.write(img, "PNG", new File("C:/Users/Hunny.hu/Desktop/k.png"));
	}
	
}
