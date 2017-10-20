package com.imadcn.framework.common.util.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * captcha
 */
public class CaptchaUtil {
	/**
	 * 图片的宽度
	 */
	private static int width = 160;
	/**
	 * 图片的高度
	 */
	private static int height = 40;
	/**
	 * 验证码字符个数
	 */
	private static int codeCount = 5;
	/**
	 * 验证码干扰线数
	 */
	private static int lineCount = 150;

	/**
	 * 
	 * @return
	 */
	public static int getCaptchaNum() {
		return getCaptchaNum(6);
	}

	/**
	 * 
	 * @return
	 */
	public static int getCaptchaNum(int size) {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();

		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}

		int result = 0;

		for (int i = 0; i < size; i++) {
			result = result * 10 + array[i];
		}

		return result;
	}

	/**
	 * 
	 * @return
	 */
	public static String getCaptcha() {
		return createCaptcha(width, height, codeCount, lineCount);
	}

	/**
	 * 
	 * @param width 图片宽
	 * @param height 图片高
	 */
	public static String getCaptcha(int width, int height) {
		return createCaptcha(width, height, codeCount, lineCount);
	}

	/**
	 * 
	 * @param width 图片宽
	 * @param height 图片高
	 * @param codeCount 字符个数
	 * @param lineCount 干扰线条数
	 */
	public static String getCaptcha(int width, int height, int codeCount, int lineCount) {
		return createCaptcha(width, height, codeCount, lineCount);
	}

	public static String createCaptcha(int width, int height, int codeCount, int lineCount) {
		int x = 0;
		int fontHeight = 0;
		int codeY = 0;
		int red = 0;
		int green = 0;
		int blue = 0;

		// 每个字符的宽度
		x = width / (codeCount + 2);
		// 字体的高度
		fontHeight = height - 2;
		codeY = height - 4;

		// 图像buffer
		BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = buffImg.createGraphics();
		// 生成随机数
		Random random = new Random();
		// 将图像填充为白色
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		// 创建字体
		ImgFontByte imgFont = new ImgFontByte();
		Font font = imgFont.getFont(fontHeight);
		g.setFont(font);

		for (int i = 0; i < lineCount; i++) {
			int xs = random.nextInt(width);
			int ys = random.nextInt(height);
			int xe = xs + random.nextInt(width / 8);
			int ye = ys + random.nextInt(height / 8);
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawLine(xs, ys, xe, ye);
		}

		// randomCode记录随机产生的验证码
		StringBuffer randomCode = new StringBuffer();
		char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
				'1', '2', '3', '4', '5', '6', '7', '8', '9' };
		// 随机产生codeCount个字符的验证码。
		for (int i = 0; i < codeCount; i++) {
			String strRand = String.valueOf(codeSequence[random.nextInt(codeSequence.length)]);
			// 产生随机的颜色值，让输出的每个字符的颜色值都将不同。
			red = random.nextInt(255);
			green = random.nextInt(255);
			blue = random.nextInt(255);
			g.setColor(new Color(red, green, blue));
			g.drawString(strRand, (i + 1) * x, codeY);
			// 将产生的四个随机数组合在一起。
			randomCode.append(strRand);
		}
		// 将四位数字的验证码保存到Session中。
		String captcha = randomCode.toString();
		return captcha;
	}

}
