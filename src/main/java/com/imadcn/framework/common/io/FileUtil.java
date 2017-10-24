package com.imadcn.framework.common.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	
	protected static Logger logger = LoggerFactory.getLogger(FileUtil.class);

	public static final String UNIT_K = "K";

	public static final String UNIT_M = "M";

	public static final String UNIT_G = "G";

	public static String getFileSuffix(String filePath) {
		String suffix = null;
		int nPos = filePath.lastIndexOf('.');

		if (nPos != -1) {
			suffix = filePath.substring(nPos + 1);
		}

		return suffix;
	}

	public static long getFileSize(String filePath) {
		File file = new File(filePath);
		long length = file.length();
		return length;
	}

	public static String getFileName(String filePath) {
		File file = new File(filePath);
		String name = file.getName();
		return name;
	}

	public static String getFilePath(String filePath) {
		String path = null;
		int nPos = filePath.lastIndexOf("/");

		if (nPos == -1) {
			nPos = filePath.lastIndexOf("\\");
		}

		if (nPos != -1) {
			path = filePath.substring(0, nPos + 1);
		}

		return path;
	}

	public static byte[] toByteArray(String filePath) throws IOException {
		File file = new File(filePath);
		ByteArrayOutputStream bos = new ByteArrayOutputStream((int) file.length());
		BufferedInputStream in = null;

		try {
			in = new BufferedInputStream(new FileInputStream(file));
			int buf_size = 1024;
			byte[] buffer = new byte[buf_size];
			int len = 0;

			while (-1 != (len = in.read(buffer, 0, buf_size))) {
				bos.write(buffer, 0, len);
			}

			return bos.toByteArray();
		} finally {
			if (bos != null) {
				bos.close();
			}

			if (in != null) {
				in.close();
			}
		}
	}
	
	/**
	 * 创建文件
	 * @param data
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static File createFile(String data, String filePath) throws FileNotFoundException, IOException {
		File file = new File(filePath);
		if (!file.exists()) {
			file.createNewFile();
		}
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			bw.write(data);
			bw.flush();
		}
		return file;
	}
	
	/**
	 * 创建文件
	 * @param inputStream
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static File createFile(InputStream inputStream, String filePath) throws FileNotFoundException, IOException {
		File file = new File(filePath);
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			String line;
			while ((line = br.readLine()) != null) {
				bw.write(line);
				bw.flush();
			}
		} finally {
			if (br != null) {
				br.close();
			}
			if (bw != null) {
				bw.close();
			}
		}
		return file;
	}

	public static File createFile(byte[] b, String filePath) throws IOException {
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		File file = null;

		try {
			file = new File(filePath);

			if (!file.exists()) {
				file.createNewFile();
			}

			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(b);
			bos.flush();
		} finally {
			if (bos != null) {
				bos.close();
			}
			if (fos != null) {
				fos.close();
			}
		}

		return file;
	}

	public static boolean delete(String filePath) {
		File file = new File(filePath);

		if (file.isFile()) {
			return deleteFile(filePath);
		} else {
			return deleteDirectory(filePath);
		}
	}

	private static boolean deleteFile(String filePath) {
		File file = new File(filePath);

		if (file.exists()) {
			file.delete();
		}

		return true;
	}

	private static boolean deleteDirectory(String filePath) {
		if (!filePath.endsWith(File.separator)) {
			filePath = filePath + File.separator;
		}

		File dirFile = new File(filePath);

		if (dirFile.exists()) {
			File[] files = dirFile.listFiles();

			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					deleteFile(files[i].getAbsolutePath());
				} else {
					deleteDirectory(files[i].getAbsolutePath());
				}
			}
		}

		dirFile.delete();
		return true;
	}

}
