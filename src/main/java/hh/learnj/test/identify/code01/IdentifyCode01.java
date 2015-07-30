package hh.learnj.test.identify.code01;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * http://blog.csdn.net/problc/article/details/5794460 验证码识别基本分四步，图片预处理，分割，训练，识别
 * 最简单的(固定大小，固定位置，固定字体)
 * 
 * @author Hunny.Hu
 */
public class IdentifyCode01 {

	/**
	 * 根据亮度设个阈值
	 * 
	 * @param colorInt
	 * @return
	 */
	public static int isWhite(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() > 100) {
			return 1;
		}
		return 0;
	}

	/**
	 * 根据亮度设个阈值
	 * 
	 * @param colorInt
	 * @return
	 */
	public static int isBlack(int colorInt) {
		Color color = new Color(colorInt);
		if (color.getRed() + color.getGreen() + color.getBlue() <= 100) {
			return 1;
		}
		return 0;
	}

	/**
	 * 使图像黑白清晰并分离
	 * 
	 * @param picFile
	 * @return
	 * @throws Exception
	 */
	public static BufferedImage removeBackgroud(String picFile)
			throws Exception {
		BufferedImage img = ImageIO.read(new File(picFile));
		int width = img.getWidth();
		int height = img.getHeight();
		for (int x = 0; x < width; ++x) {
			for (int y = 0; y < height; ++y) {
				if (isWhite(img.getRGB(x, y)) == 1) {
					img.setRGB(x, y, Color.WHITE.getRGB());
				} else {
					img.setRGB(x, y, Color.BLACK.getRGB());
				}
			}
		}
		return img;
	}

	/**
	 * 图片切片
	 * 
	 * @param img
	 * @return
	 * @throws Exception
	 */
	public static List<BufferedImage> splitImage(BufferedImage img)
			throws Exception {
		List<BufferedImage> subImgs = new ArrayList<BufferedImage>();
		subImgs.add(img.getSubimage(10, 6, 8, 10));
		subImgs.add(img.getSubimage(19, 6, 8, 10));
		subImgs.add(img.getSubimage(28, 6, 8, 10));
		subImgs.add(img.getSubimage(37, 6, 8, 10));
		return subImgs;
	}

	/**
	 * 加载训练的图片及数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public static Map<BufferedImage, String> loadTrainData() throws Exception {
		Map<BufferedImage, String> map = new HashMap<BufferedImage, String>();
		File dir = new File("train");
		File[] files = dir.listFiles();
		for (File file : files) {
			map.put(ImageIO.read(file), file.getName().charAt(0) + "");
		}
		return map;
	}

	/**
	 * 直接拿分割的图片跟这个十个图片一个像素一个像素的比，相同的点最多的就是结果。
	 * @param img
	 * @param map
	 * @return
	 */
	public static String getSingleCharOCR(BufferedImage img,
			Map<BufferedImage, String> map) {
		String result = "";
		int width = img.getWidth();
		int height = img.getHeight();
		int min = width * height;
		for (BufferedImage bi : map.keySet()) {
			int count = 0;
			Label1: for (int x = 0; x < width; ++x) {
				for (int y = 0; y < height; ++y) {
					if (isWhite(img.getRGB(x, y)) != isWhite(bi.getRGB(x, y))) {
						count++;
						if (count >= min)
							break Label1;
					}
				}
			}
			if (count < min) {
				min = count;
				result = map.get(bi);
			}
		}
		return result;
	}

	public static String getAllOCR(String file) throws Exception {
		BufferedImage img = removeBackgroud(file);
		List<BufferedImage> listImg = splitImage(img);
		Map<BufferedImage, String> map = loadTrainData();
		String result = "";
		for (BufferedImage bi : listImg) {
			result += getSingleCharOCR(bi, map);
		}
		ImageIO.write(img, "JPG", new File("result//" + result + ".jpg"));
		return result;
	}

	public static void downloadImage() {
		// HttpClient httpClient = new HttpClient();
		// GetMethod getMethod = new GetMethod(
		// "http://www.puke888.com/authimg.php");
		// for (int i = 0; i < 30; i++) {
		// try {
		// // 执行getMethod
		// int statusCode = httpClient.executeMethod(getMethod);
		// if (statusCode != HttpStatus.SC_OK) {
		// System.err.println("Method failed: "
		// + getMethod.getStatusLine());
		// }
		// // 读取内容
		// String picName = "img//" + i + ".jpg";
		// InputStream inputStream = getMethod.getResponseBodyAsStream();
		// OutputStream outStream = new FileOutputStream(picName);
		// IOUtils.copy(inputStream, outStream);
		// outStream.close();
		// System.out.println("OK!");
		// } catch (Exception e) {
		// e.printStackTrace();
		// } finally {
		// // 释放连接
		// getMethod.releaseConnection();
		// }
		// }
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
//		for (int i = 0; i < 30; ++i) {
//			String text = getAllOCR("img//" + i + ".jpg");
//			System.out.println(i + ".jpg = " + text);
//		}
		BufferedImage img = removeBackgroud("C:/Users/Hunny.hu/Desktop/src_img.png");
		ImageIO.write(img, "png", new File("C:/Users/Hunny.hu/Desktop/src_img.png"));
	}
}
