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
}
