package com.imadcn.framework.common.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class IPUtil {

	public static String localhost = "127.0.0.1";

	/**
	 * 获取本机IP(非127.0.0.1)
	 * 
	 * @return
	 * @throws SocketException
	 */
	public static String getLocalIp() throws SocketException {
		String localIp = null;
		InetAddress inetAddress = null;
		Enumeration<NetworkInterface> allNetInterfaces = null;

		allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			Enumeration<InetAddress> addresses = netInterface.getInetAddresses();

			while (addresses.hasMoreElements()) {
				inetAddress = (InetAddress) addresses.nextElement();

				if (inetAddress != null && inetAddress instanceof Inet4Address) {
					String host = inetAddress.getHostAddress();

					if (localhost.equals(host)) {
						continue;
					}

					localIp = host;
				}
			}
		}

		return localIp;
	}

	/**
	 * 将x.x.x.x形式的IP地址转换成十进制整数
	 * @param strIp 字符串型IP
	 * @return 整数型IP
	 */
	public static int ip2Int(String strIp) {
		try {
			int[] ip = new int[4];
			String[] ipArr = strIp.split("\\.");
			ip[0] = Integer.parseInt(ipArr[0]);
			ip[1] = Integer.parseInt(ipArr[1]);
			ip[2] = Integer.parseInt(ipArr[2]);
			ip[3] = Integer.parseInt(ipArr[3]);
			return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
		} catch (Exception e) {
			throw new IllegalArgumentException("invalid IP " + strIp);
		}
		
	}

	/**
	 * 将十进制整数形式转换成x.x.x.x形式的ip地址
	 * @param intIp 整数型IP
	 * @return 字符串型IP
	 */
	public static String int2Ip(int intIp) {
		StringBuffer sb = new StringBuffer();
		sb.append(String.valueOf((intIp >>> 24))).append(".");
		sb.append(String.valueOf((intIp & 0x00FFFFFF) >>> 16)).append(".");
		sb.append(String.valueOf((intIp & 0x0000FFFF) >>> 8)).append(".");
		sb.append(String.valueOf((intIp & 0x000000FF)));
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String strIp = "8.8.8.8";
		int intIp = 134744072;
		System.out.println(ip2Int(strIp));
		System.out.println(int2Ip(intIp));
	}
}
