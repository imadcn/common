package com.imadcn.framework.common.string;

/**
 * 字节与十六进制字符串互转工具类
 * @author Hinsteny
 * @version $ID: AESUtils 2018-04-15 14:45 All rights reserved.$
 */
public class ByteUtils {

    /**
     * byte数组转化为16进制字符串
     * @param bytes
     * @return
     */
    public static String byteToHexStr(byte[] bytes){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            int source = (bytes[i] & 0xFF);
            String strHex=Integer.toHexString(source);
            sb.append(strHex.charAt(0));
        }
        return  sb.toString();
    }

    /**
     * translate bytes arrays to hex-string
     *
     * @param bytes
     * @return
     */
    public static String byteToHex(byte[] bytes) {
        return byteToHex(bytes, false);
    }

    public static String byteToHex(byte[] bytes, boolean toUpperCase) {
        StringBuilder hex = new StringBuilder();
        String temp;
        for (byte i : bytes) {
            temp = Integer.toHexString(0x00ff & i);
            if (temp.length() == 1) {
                temp = "0".concat(temp);
            }
            hex.append(temp);
        }
        String hexStr = hex.toString();
        return toUpperCase ? hexStr.toUpperCase() : hexStr;
    }

    /**
     * translate hex-string to bytes
     *
     * @param source
     * @return
     */
    public static byte[] hexTBytes(String source) {
        if (null == source || source.length() % 2 != 0) {
            throw new IllegalArgumentException("Illegal parameters");
        }
        byte[] sourceBytes = new byte[source.length() / 2];
        for (int i = 0; i < sourceBytes.length; i++) {
            sourceBytes[i] = (byte) Integer.parseInt(source.substring(i * 2, i * 2 + 2), 16);
        }
        return sourceBytes;
    }

    /**
     * translate hex-bytes to bytes
     *
     * @param data
     * @return
     */
    public static byte[] hexTBytes(byte[] data) {
        if (null == data || data.length % 2 != 0) {
            throw new IllegalArgumentException("Illegal parameters");
        }
        byte[] bytes = new byte[data.length / 2];
        String temp;
        for (int n = 0; n < data.length; n += 2) {
            temp = new String(data, n, 2);
            bytes[n / 2] = (byte) Integer.parseInt(temp, 16);
        }
        return bytes;
    }

}
