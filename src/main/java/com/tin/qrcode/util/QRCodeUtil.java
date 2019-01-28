package com.tin.qrcode.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;

/**
 * 二维码工具类
 * @author Tin
 *
 */
public class QRCodeUtil extends LuminanceSource {
	
	public static BitMatrix getMatrix(String content, int width, int height) throws WriterException {
		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
		// hints.put(EncodeHintType.ERROR_CORRECTION,
		// ErrorCorrectionLevel.M);

		BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
		return bitMatrix;
	}

	public static BufferedImage createQRCodeBufferedImage(String content, int width, int height) throws WriterException {
		BitMatrix bitMatrix = getMatrix(content, width, height);
		BufferedImage image = toBufferedImage(bitMatrix);
		return image;
	}
	
	/**
	 * 自定义白边宽度
	 * @param content
	 * @param width
	 * @param height
	 * @param margin
	 * @return
	 * @throws WriterException
	 */
	public static BufferedImage createQRCodeBufferedImage(String content, int width, int height, int margin) throws WriterException {
		BitMatrix bitMatrix = getMatrix(content, width, height);
		bitMatrix = QRCodeUtil.updateBit(bitMatrix, margin);
		BufferedImage image = toBufferedImage(bitMatrix);
		return image;
	}

	/**
	 * 生成二维码文件
	 * @param content
	 * 			二维码内容
	 * @param width
	 * 			二维码宽
	 * @param height
	 * 			二维码高
	 * @param format
	 * 			图片格式，后缀
	 * @param file
	 * 			生成的二维码文件
	 * @throws WriterException
	 * @throws IOException
	 */
	public static void createQRCodeFile(String content, int width, int height, String format, File file) throws WriterException, IOException {
		BitMatrix bitMatrix = getMatrix(content, width, height);
		writeToFile(bitMatrix, format, file);
	}

	public void writeToStream(String content, int width, int height, String format, OutputStream stream) throws WriterException, IOException {
		BitMatrix bitMatrix = getMatrix(content, width, height);
		writeToStream(bitMatrix, format, stream);
	}

	/**
	 * 解析二维码
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws NotFoundException
	 */
	public static Result decode(File file) throws IOException, NotFoundException {
		BufferedImage image = ImageIO.read(file);
		LuminanceSource source = new QRCodeUtil(image);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

		Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
		hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

		MultiFormatReader formatReader = new MultiFormatReader();
		Result result = formatReader.decode(binaryBitmap, hints);

		System.out.println("result = " + result.toString());
		System.out.println("resultFormat = " + result.getBarcodeFormat());
		System.out.println("resultText = " + result.getText());
		
		return result;
	}
	
	/**
	 * 自定义白边边框宽度
	 * 
	 * @param matrix
	 * @param margin
	 * @return
	 */
	public static BitMatrix updateBit(BitMatrix matrix, int margin) {
		int tempM = margin * 2;
		// 获取二维码图案的属性
		int[] rec = matrix.getEnclosingRectangle();
		int resWidth = rec[2] + tempM;
		int resHeight = rec[3] + tempM;
		// 按照自定义边框生成新的BitMatrix
		BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
		resMatrix.clear();
		// 循环，将二维码图案绘制到新的bitMatrix中
		for (int i = margin; i < resWidth - margin; i++) {
			for (int j = margin; j < resHeight - margin; j++) {
				if (matrix.get(i - margin + rec[0], j - margin + rec[1])) {
					resMatrix.set(i, j);
				}
			}
		}
		return resMatrix;
	}
	
	/**
	 * 设置 logo
	 * 二维码 添加 logo图标 处理的方法, 模仿微信自动生成二维码效果，有圆角边框，
	 * 		logo和二维码间有空白区，logo带有灰色边框
	 * 
	 * @author Administrator sangwenhao
	 * 
	 * @param matrixImage
	 *            源二维码图片
	 * @return 返回带有logo的二维码图片
	 * @throws IOException
	 * @author Administrator sangwenhao
	 */
	public BufferedImage LogoMatrix(BufferedImage matrixImage) throws IOException {
		/**
		 * 读取二维码图片，并构建绘图对象
		 */
		Graphics2D g2 = matrixImage.createGraphics();

		int matrixWidth = matrixImage.getWidth();
		int matrixHeigh = matrixImage.getHeight();

		/**
		 * 读取Logo图片
		 */
		BufferedImage logo = ImageIO.read(new File("E:\\heack.jpg"));

		// 开始绘制图片
		g2.drawImage(logo, matrixWidth / 5 * 2, matrixHeigh / 5 * 2, matrixWidth / 5, matrixHeigh / 5, null);// 绘制
		BasicStroke stroke = new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke);// 设置笔画对象
		// 指定弧度的圆角矩形
		RoundRectangle2D.Float round = new RoundRectangle2D.Float(matrixWidth / 5 * 2, matrixHeigh / 5 * 2,
				matrixWidth / 5, matrixHeigh / 5, 20, 20);
		g2.setColor(Color.white);
		g2.draw(round);// 绘制圆弧矩形

		// 设置logo 有一道灰色边框
		BasicStroke stroke2 = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
		g2.setStroke(stroke2);// 设置笔画对象
		RoundRectangle2D.Float round2 = new RoundRectangle2D.Float(matrixWidth / 5 * 2 + 2, matrixHeigh / 5 * 2 + 2,
				matrixWidth / 5 - 4, matrixHeigh / 5 - 4, 20, 20);
		g2.setColor(new Color(128, 128, 128));
		g2.draw(round2);// 绘制圆弧矩形

		g2.dispose();
		matrixImage.flush();
		return matrixImage;
	}
	
	// MatrixToImageWriter --------------------------------------------------
	private static final int BLACK = 0xFF000000;
	private static final int WHITE = 0xFFFFFFFF;
	
	public static BufferedImage toBufferedImage(BitMatrix matrix) {
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);
			}
		}
		return image;
	}

	public static void writeToFile(BitMatrix matrix, String format, File file) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, file)) {
			throw new IOException("Could not write an image of format " + format + " to " + file);
		}
	}

	public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) throws IOException {
		BufferedImage image = toBufferedImage(matrix);
		if (!ImageIO.write(image, format, stream)) {
			throw new IOException("Could not write an image of format " + format);
		}
	}
	
	// BufferedImageLuminanceSource --------------------------------------------------
	private final BufferedImage image;
	private final int left;
	private final int top;
	
	public QRCodeUtil(BufferedImage image) {
		this(image, 0, 0, image.getWidth(), image.getHeight());
	}

	public QRCodeUtil(BufferedImage image, int left, int top, int width, int height) {
		super(width, height);

		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();
		if (left + width > sourceWidth || top + height > sourceHeight) {
			throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
		}

		for (int y = top; y < top + height; y++) {
			for (int x = left; x < left + width; x++) {
				if ((image.getRGB(x, y) & 0xFF000000) == 0) {
					image.setRGB(x, y, 0xFFFFFFFF); // = white
				}
			}
		}

		this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
		this.image.getGraphics().drawImage(image, 0, 0, null);
		this.left = left;
		this.top = top;
	}

	@Override
	public byte[] getRow(int y, byte[] row) {
		if (y < 0 || y >= getHeight()) {
			throw new IllegalArgumentException("Requested row is outside the image: " + y);
		}
		int width = getWidth();
		if (row == null || row.length < width) {
			row = new byte[width];
		}
		image.getRaster().getDataElements(left, top + y, width, 1, row);
		return row;
	}

	@Override
	public byte[] getMatrix() {
		int width = getWidth();
		int height = getHeight();
		int area = width * height;
		byte[] matrix = new byte[area];
		image.getRaster().getDataElements(left, top, width, height, matrix);
		return matrix;
	}

	@Override
	public boolean isCropSupported() {
		return true;
	}

	@Override
	public LuminanceSource crop(int left, int top, int width, int height) {
		return new QRCodeUtil(image, this.left + left, this.top + top, width, height);
	}

	@Override
	public boolean isRotateSupported() {
		return true;
	}

	@Override
	public LuminanceSource rotateCounterClockwise() {

		int sourceWidth = image.getWidth();
		int sourceHeight = image.getHeight();

		AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);

		BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);

		Graphics2D g = rotatedImage.createGraphics();
		g.drawImage(image, transform, null);
		g.dispose();

		int width = getWidth();
		return new QRCodeUtil(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
	}

}

