package com.imadcn.framework.common.net;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http 客户端请求助手
 * 
 * @author yc
 * @since 2017年1月10日
 */
public final class HttpClientHelper {
	/**
	 * 日志对象
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientHelper.class);
	/**
	 * 请求语言
	 */
	private static final String ACCEPT_LANGUAGE = "Accept-Language";
	/**
	 * zh-cn
	 */
	private static final String ZH_CN = "zh-cn";
	/**
	 * utf-8 字符编码
	 */
	private static final String UTF_8 = "UTF-8";

	private static ExecutorService executorService = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024));

	/**
	 * 获取HttpClient对象
	 * 
	 * @return CloseableHttpClient
	 */
	protected static CloseableHttpClient getHttpClient() throws KeyManagementException, NoSuchAlgorithmException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		return httpclient;
	}

	/**
	 * 得到post方式的Http请求对象
	 * 
	 * @param uri 请求地址
	 * @return
	 */
	protected static HttpPost getHttpPost(String uri) {
		// 创建post请求
		return new HttpPost(uri);
	}

	/**
	 * 得到get方式的Http请求对象
	 * 
	 * @param uri 请求地址
	 * @param paramMap 请求参数
	 * @return 
	 * @throws UnsupportedEncodingException
	 */
	protected static HttpGet getHttpGet(String uri, Map<String, String> paramMap) throws UnsupportedEncodingException {
		StringBuilder str = new StringBuilder();
		str.append(uri);
		// 创建get请求
		if (paramMap != null && !paramMap.isEmpty()) {
			if (!uri.contains("?")) {
				str.append("?");
			}
			str.append(formatParamMap(paramMap));
		}
		return new HttpGet(str.toString());
	}

	/**
	 * 设置请求报头
	 * 
	 * @param httpGet get请求Http
	 * @return 
	 */
	protected static HttpGet setHeader(HttpGet httpGet, Map<String, String> headerMap) {
		if (headerMap != null) {
			for (Map.Entry<String, String> map : headerMap.entrySet()) {
				httpGet.setHeader(map.getKey(), map.getValue());
			}
		}
		// 设置接收语言
		httpGet.setHeader(ACCEPT_LANGUAGE, ZH_CN);
		return httpGet;
	}

	/**
	 * 设置请求报头
	 * 
	 * @param httpPost post请求
	 * @param headerMap 头信息Map
	 * @return
	 */
	protected static HttpPost setHeader(HttpPost httpPost, Map<String, String> headerMap) {
		if (headerMap != null) {
			for (Map.Entry<String, String> map : headerMap.entrySet()) {
				httpPost.setHeader(map.getKey(), map.getValue());
			}
		}
		// 设置接收语言
		httpPost.setHeader(ACCEPT_LANGUAGE, ZH_CN);
		return httpPost;
	}

	/**
	 * 
	 * 发送POST请求
	 * 
	 * @param uri
	 *            请求地址
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sendPostRequest(String uri) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return sendPostRequest(uri, null);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sendPostRequest(String uri, Map<String, String> paramMap) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return sendPostRequest(uri, paramMap, null, UTF_8);
	}

	/**
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @param headerMap
	 *            请求头
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sendPostRequest(String uri, Map<String, String> paramMap, Map<String, String> headerMap) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return sendPostRequest(uri, paramMap, headerMap, UTF_8);
	}

	/**
	 * 发送POST请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @param headerMap
	 *            请求头
	 * @param code
	 *            请求参数编码
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sendPostRequest(String uri, Map<String, String> paramMap, Map<String, String> headerMap, String code) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException {
		LOGGER.debug("execute sendPostRequest begin");
		long startTime = System.currentTimeMillis();
		// 创建客户端
		CloseableHttpClient httpclient = getHttpClient();
		LOGGER.info("sendRequest url = " + uri);
		HttpPost post = getHttpPost(uri);
		setHeader(post, headerMap);
		String responseBody = null;
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		if (paramMap != null) {
			// 设置post参数
			for (Map.Entry<String, String> m : paramMap.entrySet()) {
				paramList.add(new BasicNameValuePair(m.getKey(), m.getValue()));
				LOGGER.info("Param KEY = [" + m.getKey() + "] & VALUE = [" + m.getValue() + "]");
			}
			if (paramList != null && paramList.size() > 0) {
				UrlEncodedFormEntity uef = new UrlEncodedFormEntity(paramList, code);
				post.setEntity(uef);
			}
		}
		try {
			HttpResponse response = httpclient.execute(post);
			responseBody = readInputStream(response.getEntity().getContent());
			LOGGER.info("\n" + responseBody + "\n");
			LOGGER.info("sendPostRequest method execute time is [" + (System.currentTimeMillis() - startTime) + "] ms");
		} catch (Exception e) {
			LOGGER.error("execute sendPostRequest exception ", e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("execute close httpclient exception ", e);
			}
		}
		LOGGER.debug("execute sendPostRequest end");
		return responseBody;
	}

	/**
	 * 
	 * 发送POST请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param entity
	 *            {@link UrlEncodedFormEntity} 请求实体
	 * @param headerMap
	 *            请求头
	 * @param code
	 *            请求参数编码
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sendPostRequest(String uri, UrlEncodedFormEntity entity, Map<String, String> headerMap, String code) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException {
		LOGGER.debug("execute sendPostRequest begin");
		long startTime = System.currentTimeMillis();
		// 创建客户端
		CloseableHttpClient httpclient = getHttpClient();
		LOGGER.info("sendRequest url = " + uri);
		HttpPost post = getHttpPost(uri);
		String responseBody = null;
		try {
			post.setEntity(entity);
			setHeader(post, headerMap);
			HttpResponse response = httpclient.execute(post);
			responseBody = readInputStream(response.getEntity().getContent());
			LOGGER.info("\n" + responseBody + "\n");
			LOGGER.info("sendPostRequest method execute time is [" + (System.currentTimeMillis() - startTime) + "] ms");
		} catch (Exception e) {
			LOGGER.error("execute sendPostRequest exception ", e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("execute close httpclient exception ", e);
			}
		}
		LOGGER.debug("execute sendPostRequest end");
		return responseBody;
	}

	/**
	 * 发送GET请求
	 * 
	 * @param uri
	 *            请求地址
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sendGetRequest(String uri) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return sendGetRequest(uri, null, null);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sendGetRequest(String uri, Map<String, String> paramMap) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return sendGetRequest(uri, paramMap, null);
	}

	/**
	 * 发送Get请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @param headerMap
	 *            请求头
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sendGetRequest(String uri, Map<String, String> paramMap, Map<String, String> headerMap) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException {
		LOGGER.debug("execute sendGetRequest begin");
		long startTime = System.currentTimeMillis();
		// 创建客户端
		CloseableHttpClient httpclient = getHttpClient();
		HttpGet get = getHttpGet(uri, paramMap);
		LOGGER.info("sendGetRequest url = " + get.getURI());
		setHeader(get, headerMap);
		String responseBody = null;
		try {
			HttpResponse response = httpclient.execute(get);
			responseBody = readInputStream(response.getEntity().getContent());
			LOGGER.info("\n" + responseBody + "\n");
			LOGGER.info("sendGetRequest method execute time is [" + (System.currentTimeMillis() - startTime) + "] ms");
		} catch (Exception e) {
			LOGGER.error("execute sendGetRequest exception ", e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("execute close httpclient exception ", e);
			}
		}
		LOGGER.debug("execute sendGetRequest end");
		return responseBody;
	}

	/**
	 * 发送GET请求
	 * 
	 * @param uri
	 *            请求地址
	 * @return {@link Byte} byte[]
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] sendGetRequestStream(String uri) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException {
		LOGGER.debug("execute sendGetRequestStream begin");
		long startTime = System.currentTimeMillis();
		// 创建客户端
		CloseableHttpClient httpclient = getHttpClient();
		LOGGER.info("sendGetRequestStream url = " + uri);
		HttpGet get = getHttpGet(uri, null);
		setHeader(get, null);
		byte imgdata[] = null;
		try {
			HttpResponse response = httpclient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
				int ch;
				while ((ch = instream.read()) != -1) {
					bytestream.write(ch);
				}
				imgdata = bytestream.toByteArray();
				bytestream.close();
				instream.close();
			}
			LOGGER.info("sendGetRequestStream method execute time is [" + (System.currentTimeMillis() - startTime) + "] ms");
		} catch (Exception e) {
			LOGGER.error("execute sendGetRequestStream exception ", e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("execute close httpclient exception ", e);
			}
		}
		LOGGER.debug("execute sendGetRequestStream end");
		return imgdata;
	}

	/**
	 * 发送xml字符串请求
	 * 
	 * @param uri 请求地址
	 * @param xmlStr xml字符串
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public static String sendPostXmlRequest(String uri, String xmlStr) throws KeyManagementException, NoSuchAlgorithmException {
		LOGGER.debug("execute sendPostXmlRequest begin");
		long startTime = System.currentTimeMillis();
		// 创建客户端
		CloseableHttpClient httpclient = getHttpClient();
		LOGGER.info("sendPostXmlRequest url = " + uri);
		HttpPost post = getHttpPost(uri);
		String responseBody = "";
		try {
			StringEntity myEntity = new StringEntity(xmlStr, "UTF-8");
			LOGGER.info("sendPostXmlRequest XML \n {}", xmlStr);
			post.addHeader("Content-Type", "text/xml");
			post.setEntity(myEntity);
			HttpResponse response = httpclient.execute(post);
			responseBody = readInputStream(response.getEntity().getContent());
			LOGGER.info("\n" + responseBody + "\n");
			LOGGER.info("sendPostXmlRequest method execute time is [" + (System.currentTimeMillis() - startTime) + "] ms");
		} catch (Exception e) {
			LOGGER.error("execute sendPostXmlRequest exception ", e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("execute close httpclient exception ", e);
			}
		}
		LOGGER.debug("execute sendPostXmlRequest end");
		return responseBody;
	}
	
	/**
	 * 发送xml字符串请求
	 * 
	 * @param uri 请求地址
	 * @param xmlStr xml字符串
	 * @param headerMap
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public static String sendPostXmlRequest(String uri, String xmlStr, Map<String, String> headerMap) throws KeyManagementException, NoSuchAlgorithmException {
		LOGGER.debug("execute sendPostXmlRequest begin");
		long startTime = System.currentTimeMillis();
		// 创建客户端
		CloseableHttpClient httpclient = getHttpClient();
		LOGGER.info("sendPostXmlRequest url = " + uri);
		HttpPost post = getHttpPost(uri);
		setHeader(post, headerMap);
		String responseBody = "";
		try {
			StringEntity myEntity = new StringEntity(xmlStr, "UTF-8");
			LOGGER.info("sendPostXmlRequest XML \n {}", xmlStr);
			post.addHeader("Content-Type", "text/xml");
			post.setEntity(myEntity);
			HttpResponse response = httpclient.execute(post);
			responseBody = readInputStream(response.getEntity().getContent());
			LOGGER.info("\n" + responseBody + "\n");
			LOGGER.info("sendPostXmlRequest method execute time is [" + (System.currentTimeMillis() - startTime) + "] ms");
		} catch (Exception e) {
			LOGGER.error("execute sendPostXmlRequest exception ", e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("execute close httpclient exception ", e);
			}
		}
		LOGGER.debug("execute sendPostXmlRequest end");
		return responseBody;
	}
	
	/**
	 * 发送Json字符串请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param jsonStr
	 *            JSON字符串
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public static String sendPostJsonRequest(String uri, String jsonStr) throws KeyManagementException, NoSuchAlgorithmException {
		LOGGER.debug("execute sendPostJsonRequest begin");
		long startTime = System.currentTimeMillis();
		// 创建客户端
		CloseableHttpClient httpclient = getHttpClient();
		LOGGER.info("sendPostJsonRequest url = " + uri);
		HttpPost post = getHttpPost(uri);
		String responseBody = "";
		try {
			StringEntity myEntity = new StringEntity(jsonStr, "UTF-8");
			LOGGER.info("sendPostJsonRequest JSON \n {}", jsonStr);
			post.addHeader("Content-Type", "application/json");
			post.setEntity(myEntity);
			HttpResponse response = httpclient.execute(post);
			responseBody = readInputStream(response.getEntity().getContent());
			LOGGER.info("\n" + responseBody + "\n");
			LOGGER.info("sendPostJsonRequest method execute time is [" + (System.currentTimeMillis() - startTime) + "] ms");
		} catch (Exception e) {
			LOGGER.error("execute sendPostJsonRequest exception ", e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("execute close httpclient exception ", e);
			}
		}
		LOGGER.debug("execute sendPostJsonRequest end");
		return responseBody;
	}

	/**
	 * 发送Json字符串请求
	 * @param uri
	 * @param jsonStr
	 * @param headerMap
	 * @return
	 * @throws KeyManagementException
	 * @throws NoSuchAlgorithmException
	 */
	public static String sendPostJsonRequest(String uri, String jsonStr, Map<String, String> headerMap) throws KeyManagementException, NoSuchAlgorithmException {
		LOGGER.debug("execute sendPostJsonRequest begin");
		long startTime = System.currentTimeMillis();
		// 创建客户端
		CloseableHttpClient httpclient = getHttpClient();
		LOGGER.info("sendPostJsonRequest url = " + uri);
		HttpPost post = getHttpPost(uri);
		setHeader(post, headerMap);
		String responseBody = "";
		try {
			StringEntity myEntity = new StringEntity(jsonStr, "UTF-8");
			LOGGER.info("sendPostJsonRequest JSON \n {}", jsonStr);
			post.addHeader("Content-Type", "application/json");
			post.setEntity(myEntity);
			HttpResponse response = httpclient.execute(post);
			responseBody = readInputStream(response.getEntity().getContent());
			LOGGER.info("\n" + responseBody + "\n");
			LOGGER.info("sendPostJsonRequest method execute time is [" + (System.currentTimeMillis() - startTime) + "] ms");
		} catch (Exception e) {
			LOGGER.error("execute sendPostJsonRequest exception ", e);
		} finally {
			try {
				httpclient.close();
			} catch (IOException e) {
				LOGGER.error("execute close httpclient exception ", e);
			}
		}
		LOGGER.debug("execute sendPostJsonRequest end");
		return responseBody;
	}

	/**
	 * 处理返回文件流
	 * 
	 * @param inputStream
	 *            {@link InputStream} 输入流
	 * @return
	 * @throws IOException
	 */
	private static String readInputStream(InputStream inputStream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, UTF_8));
		StringBuffer buffer = new StringBuffer();
		String line;
		while ((line = in.readLine()) != null) {
			buffer.append(line + "\n");
		}
		inputStream.close();
		return buffer.toString();
	}

	/**
	 * 
	 * 格式化参数
	 * 
	 * @param paramMap
	 *            输入参数
	 * @return
	 */
	public static String formatParamMap(Map<String, String> paramMap) throws UnsupportedEncodingException {
		return formatParamMap(paramMap, UTF_8);
	}

	/**
	 * 
	 * 格式化参数
	 * 
	 * @param paramMap
	 *            输入参数
	 * @param code
	 *            url编码格式
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String formatParamMap(Map<String, String> paramMap, String code) throws UnsupportedEncodingException {
		StringBuilder builder = new StringBuilder();
		if (paramMap != null && !paramMap.isEmpty()) {
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				String key = entry.getKey();
				String value = URLEncoder.encode(entry.getValue().toString(), code);
				builder.append(key).append("=").append(value).append("&");
			}
			// 去掉最后一个&
			builder.deleteCharAt(builder.length() - 1);
		}
		return builder.toString();
	}

	/**
	 * 
	 * 获取参数对象
	 * 
	 * @param paramMap
	 * @param code
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static UrlEncodedFormEntity getHttpParamLength(Map<String, String> paramMap, String code) throws UnsupportedEncodingException {
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		UrlEncodedFormEntity uef = null;
		if (paramMap != null) {
			// 设置post参数
			for (Map.Entry<String, String> m : paramMap.entrySet()) {
				paramList.add(new BasicNameValuePair(m.getKey(), m.getValue()));
				LOGGER.info("Param KEY = [" + m.getKey() + "] & VALUE = [" + m.getValue() + "]");
			}
			if (paramList != null && paramList.size() > 0) {
				uef = new UrlEncodedFormEntity(paramList, code);
			}
		}
		return uef;
	}

	/**
	 * 异步发送发送POST请求
	 * 
	 * @param uri
	 *            请求地址
	 * @return
	 */
	public static Future<String> asyncSendPostRequest(String uri) throws KeyManagementException, NoSuchAlgorithmException, UnsupportedEncodingException {
		return asyncSendPostRequest(uri, null, null, UTF_8);
	}

	/**
	 * 异步发送发送POST请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @return
	 */
	public static Future<String> asyncSendPostRequest(String uri, Map<String, String> paramMap) {
		return asyncSendPostRequest(uri, paramMap, null, UTF_8);
	}

	/**
	 * 异步发送发送POST请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @param headerMap
	 *            请求头
	 * @return
	 */
	public static Future<String> asyncSendPostRequest(String uri, Map<String, String> paramMap, Map<String, String> headerMap) {
		return asyncSendPostRequest(uri, paramMap, headerMap, UTF_8);
	}

	/**
	 * 异步发送发送POST请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @param headerMap
	 *            请求头
	 * @param code
	 *            请求参数编码
	 * @return
	 */
	public static Future<String> asyncSendPostRequest(String uri, Map<String, String> paramMap, Map<String, String> headerMap, String code) {
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return sendPostRequest(uri, paramMap, headerMap, code);
			}
		};
		return executorService.submit(callable);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param uri
	 *            请求地址
	 * @return
	 */
	public static Future<String> asyncSendGetRequest(String uri) {
		return asyncSendGetRequest(uri, null, null);
	}

	/**
	 * 发送GET请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @return
	 */
	public static Future<String> asyncSendGetRequest(String uri, Map<String, String> paramMap) {
		return asyncSendGetRequest(uri, paramMap, null);
	}

	/**
	 * 发送Get请求
	 * 
	 * @param uri
	 *            请求地址
	 * @param paramMap
	 *            请求参数
	 * @param headerMap
	 *            请求头
	 * @return
	 */
	public static Future<String> asyncSendGetRequest(String uri, Map<String, String> paramMap, Map<String, String> headerMap) {
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return sendGetRequest(uri, paramMap, headerMap);
			}
		};
		return executorService.submit(callable);
	}
}
