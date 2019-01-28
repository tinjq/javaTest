package com.tin.qrcode;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;

import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.tin.bean.CardBean;
import com.tin.qrcode.util.BufferedImageLuminanceSource;
import com.tin.qrcode.util.MatrixToImageWriter;
import com.tin.qrcode.util.QRCodeUtil;

public class Test {

	public static void main(String[] args) {
		try {
			CardBean bean = new CardBean();
			bean.setName("田景泉");
			bean.setTel("15111112222");
			bean.setOrg("杰度信息技术有限公司");
			bean.setAdr("江苏南京雨花台区");
			bean.setEmail("1967694532@qq.com");
			bean.setNote("我的名片测试");
			bean.setTitle("我的名片");
			bean.setUrl("https://www.baidu.com/");
			bean.setTelCell("025-11112222");

			String content = buildContent(bean);
			content = "https://www.baidu.com/";

			JSONObject json = new JSONObject();
//			json.put("name", "zwm");
			json.put("url", "https://www.baidu.com/");
			content = json.toString();// 内容
			
			

//			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
//			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
//			// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
//			// hints.put(EncodeHintType.ERROR_CORRECTION,
//			// ErrorCorrectionLevel.M);
//
//			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, 280, 280, hints);
//
//			bitMatrix = updateBit(bitMatrix, 10);
//			BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
//
//			BufferedImage image = createQRCodeWithLogo(content, 280, 280, new File("D:/logo.png"));
			
			File file = new File("D:/card" + System.currentTimeMillis() + ".png");
			
//			QRCodeUtil.createQRCodeFile(content, 280, 280, "png", file);
			BitMatrix matrix = QRCodeUtil.getMatrix(content, 280, 280);
			matrix = updateBit(matrix, 10);
			MatrixToImageWriter.writeToFile(matrix, "png", file);
			
			
			decode(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create qrcode with specified hint and logo
	 *
	 * @param data
	 * @param width
	 * @param height
	 * @param logoFile
	 * @return
	 */
	public static BufferedImage createQRCodeWithLogo(String data, int width, int height, File logoFile) {
		try {
			BufferedImage qrcode = QRCodeUtil.createQRCodeBufferedImage(data, width, height);
			BufferedImage logo = ImageIO.read(logoFile);
			int deltaHeight = height - logo.getHeight();
			int deltaWidth = width - logo.getWidth();

			BufferedImage combined = new BufferedImage(height, width, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) combined.getGraphics();
			g.drawImage(qrcode, 0, 0, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			g.drawImage(logo, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);

			return combined;
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void decode(File file) {
		try {
			MultiFormatReader formatReader = new MultiFormatReader();

			BufferedImage image = ImageIO.read(file);

			LuminanceSource source = new BufferedImageLuminanceSource(image);

			Binarizer binarizer = new HybridBinarizer(source);

			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);

			Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
			hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

			Result result = formatReader.decode(binaryBitmap, hints);

			System.out.println("result = " + result.toString());
			System.out.println("resultFormat = " + result.getBarcodeFormat());
			System.out.println("resultText = " + result.getText());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自定义白边边框宽度
	 * 
	 * @param matrix
	 * @param margin
	 * @return
	 */
	private static BitMatrix updateBit(BitMatrix matrix, int margin) {
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

	public static String buildContent(CardBean data) {
		StringBuffer sb = new StringBuffer();
		sb.append("BEGIN:VCARD\nVERSION:3.0");

		if (data.getName() != null) {
			sb.append("\n N:");
			sb.append(data.getName());
		}

		if (data.getEmail() != null) {
			sb.append("\n EMAIL:");
			sb.append(data.getEmail());
		}

		if (data.getTel() != null) {
			sb.append("\n TEL:");
			sb.append(data.getTel());
		}

		if (data.getTelCell() != null) {
			sb.append("\n TEL;CELL:");
			sb.append(data.getTelCell());
		}

		if (data.getAdr() != null) {
			sb.append("\n ADR:");
			sb.append(data.getAdr());
		}

		if (data.getOrg() != null) {
			sb.append("\n ORG:");
			sb.append(data.getOrg());
		}

		if (data.getTitle() != null) {
			sb.append("\n TITLE:");
			sb.append(data.getTitle());
		}

		if (data.getUrl() != null) {
			sb.append("\n URL:");
			sb.append(data.getUrl());
		}

		if (data.getNote() != null) {
			sb.append("\n NOTE:");
			sb.append(data.getNote());
		}

		sb.append("\n END:VCARD");

		return sb.toString();
	}

}
