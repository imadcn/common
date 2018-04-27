package com.imadcn.framework.common.secret;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * Base64 编解码工具类
 * @author Hinsteny
 * @version $ID: Base64Utils 2018-04-16 19:35 All rights reserved.$
 */
public class Base64Utils {

    /**
     * 默认的字符编码
     */
    private static String default_charset = "8859_1";

    /**
     * 进行base64加码
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64Encodes(String data) throws UnsupportedEncodingException {
        String charset = default_charset;
        byte[] srcs = data.getBytes(charset);
        byte[] dst = base64Encode(srcs);
        return new String(dst, charset);
    }

    /**
     * 进行base64加码
     * @param data
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64Encodes(String data, String charset) throws UnsupportedEncodingException {
        byte[] srcs = data.getBytes(charset);
        byte[] dst = base64Encode(srcs);
        return new String(dst, charset);
    }

    /**
     * 进行base64加码
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64Encodes(byte[] data) {
        byte[] dst = base64Encode(data);
        return new String(dst);
    }

    /**
     * 进行base64加码
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64Encodes(byte[] data, String charset) throws UnsupportedEncodingException {
        byte[] dst = base64Encode(data);
        return new String(dst, charset);
    }

    /**
     * 进行base64解码
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64Decodes(String data) throws UnsupportedEncodingException {
        String charset = default_charset;
        byte[] srcs = data.getBytes(charset);
        byte[] dst = base64Decode(srcs);
        return new String(dst, charset);
    }

    /**
     * 进行base64解码
     * @param data
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64Decode(String data, String charset) throws UnsupportedEncodingException {
        byte[] srcs = data.getBytes(charset);
        byte[] dst = base64Decode(srcs);
        return new String(dst, charset);
    }

    /**
     * 进行base64解码
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64Decodes(byte[] data) throws UnsupportedEncodingException {
        byte[] dst = base64Decode(data);
        return new String(dst);
    }

    /**
     * 进行base64解码
     * @param data
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String base64Decodes(byte[] data, String charset) throws UnsupportedEncodingException {
        byte[] dst = base64Decode(data);
        return new String(dst, charset);
    }

    /**
     * 对字节数组进行base64加码
     * @param data
     * @return
     */
    public static byte[] base64Encode(byte[] data) {
        byte[] dst = Base64.getEncoder().encode(data);
        return dst;
    }

    /**
     * 对字节数组进行base64解码
     * @param data
     * @return
     */
    public static byte[] base64Decode(byte[] data) {
        byte[] dst = Base64.getDecoder().decode(data);
        return dst;
    }

    /**
     * test, Use this base64 utils to compare with sun.misc.BASE64Encoder
     * @param args
     */
    public static void main(String[] args) throws IOException {
        String data = "走遍世界的心不能停...O(∩_∩)O哈哈~";
        String result_this = null, result_sun = null;
        System.out.println("============== Base64 加码[默认字符编码] ==============");
        result_this = Base64Utils.base64Encodes(data.getBytes());
//        result_sun = new BASE64Encoder().encode(data.getBytes());
        System.out.println(String.format("this: [%s] equals sun : [%s]  is [%s]", result_this, result_sun, result_this.equals(result_sun)));

        System.out.println("============== Base64 解码[默认字符编码] ==============");
        result_this = Base64Utils.base64Decodes(result_this.getBytes());
//        result_sun = new String(new BASE64Decoder().decodeBuffer(result_sun));
        System.out.println(String.format("this: [%s] equals sun : [%s]  is [%s]", result_this, result_sun, result_this.equals(result_sun)));

        System.out.println("============== Base64 加码[指定字符编码] ==============");
        result_this = Base64Utils.base64Encodes(data, "UTF-8");
//        result_sun = new BASE64Encoder().encode(data.getBytes("UTF-8"));
        System.out.println(String.format("this: [%s] equals sun : [%s]  is [%s]", result_this, result_sun, result_this.equals(result_sun)));

        System.out.println("============== Base64 解码[指定字符编码] ==============");
        result_this = Base64Utils.base64Decodes(result_this.getBytes(), "UTF-8");
//        result_sun = new String(new BASE64Decoder().decodeBuffer(result_sun), "UTF-8");
        System.out.println(String.format("this: [%s] equals sun : [%s]  is [%s]", result_this, result_sun, result_this.equals(result_sun)));

    }
}
