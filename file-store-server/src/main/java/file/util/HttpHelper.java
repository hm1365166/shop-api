package file.util;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpHelper {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(HttpHelper.class);

	//private static PoolingHttpClientConnectionManager poolManager;
	// 请求配置
	private static RequestConfig requestConfig;
	// 字符编码
	private static final String CHARSET = "UTF-8";
	// 15秒
	private static final int MAX_TIMEOUT = 120000; // 毫秒

	static {
		// 设置连接池
		//poolManager = new PoolingHttpClientConnectionManager();
		// 设置连接池大小
		//poolManager.setMaxTotal(100);
		//poolManager.setDefaultMaxPerRoute(poolManager.getMaxTotal());
		//获取配置器
		RequestConfig.Builder configBuilder = RequestConfig.custom();
		// 设置连接超时
		configBuilder.setConnectTimeout(MAX_TIMEOUT);
		// 设置读取超时
		configBuilder.setSocketTimeout(MAX_TIMEOUT);
		// 设置从连接池获取连接实例的超时
		configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
		// 在提交请求之前 测试连接是否可用
		configBuilder.setStaleConnectionCheckEnabled(true);
		requestConfig = configBuilder.build();
	}

	/**
	 * SLL Client
	 *
	 * @return
	 */
	public static CloseableHttpClient createSSLClient() {
		try {

			SSLContext sslContext = new org.apache.http.conn.ssl.SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();

			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new X509HostnameVerifier() {
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}

				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}

				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}

				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
			});
			return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultRequestConfig(requestConfig).build();
			//.setConnectionManager(poolManager).setDefaultRequestConfig(requestConfig).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	/**
	 * Http Client
	 *
	 * @return
	 */
	public static CloseableHttpClient createHttpClient() {
		CloseableHttpClient httpClient = null;
		try {
			httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
		} catch (Exception e) {
			logger.error("used custom http client error:", e);
			httpClient = HttpClients.createDefault();
		}
		return httpClient;
	}

	/**
	 * 参数转换
	 *
	 * @return
	 */
	public static List<org.apache.http.NameValuePair> convertParameter(Map<String, Object> paramMap) {
		List<org.apache.http.NameValuePair> pairList = null;
		if (paramMap != null && paramMap.size() > 0) {
			pairList = new ArrayList<org.apache.http.NameValuePair>(paramMap.size());
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null) {
					pairList.add(new BasicNameValuePair(entry.getKey().trim(), entry.getValue().toString().trim()));
				}
			}
		}
		return pairList;
	}

	/**
	 * 参数转换
	 *
	 * @return
	 */
	public static List<org.apache.http.NameValuePair> convertParameterStr(Map<String, String> paramMap) {
		List<org.apache.http.NameValuePair> pairList = null;
		if (paramMap != null && paramMap.size() > 0) {
			pairList = new ArrayList<org.apache.http.NameValuePair>(paramMap.size());
			for (Map.Entry<String, String> entry : paramMap.entrySet()) {
				if (entry.getKey() != null && entry.getValue() != null) {
					pairList.add(new BasicNameValuePair(entry.getKey().trim(), entry.getValue().toString().trim()));
				}
			}
		}
		return pairList;
	}

	/**
	 *
	 * 适配http或https
	 */
	public static String post(String url, String json) throws IOException {
		if (null == url || "".equals(url)) {
			return "";
		}
		if (url.startsWith("http:")) {
			return doPost(url, json);
		} else if (url.startsWith("https:")) {
			return doPostSSL(url, json);
		} else {
			return "";
		}
	}

	/**
	 * 发送 SSL POST 请求(HTTPS)
	 *
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public static String doPostSSL(String url, Map<String, Object> paramMap) throws IOException {
		return doPostSSL(url, convertParameter(paramMap));
	}

	/**
	 * 发送 SSL POST 请求(HTTPS)
	 *
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> sendPostSSL(String url, Map<String, String> paramMap) throws IOException {
		return sendPostSSL(url, convertParameterStr(paramMap));
	}

	/**
	 * 发送 SSL POST 请求(HTTPS)
	 *
	 * @param url
	 * @param paramList
	 * @return
	 * @throws IOException
	 */
	public static String doPostSSL(String url, List<org.apache.http.NameValuePair> paramList) throws IOException {
		CloseableHttpClient httpClient = createSSLClient();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String result = null;
		try {
			httpPost.setConfig(requestConfig);
			if (paramList != null && paramList.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(paramList, CHARSET));
			}
			response = httpClient.execute(httpPost);

			StatusLine sl = response.getStatusLine();
			int statusCode = 0;
			String phrase = "";
			if (sl != null) {
				statusCode = sl.getStatusCode();
				phrase = sl.getReasonPhrase();
			}

			if (statusCode != HttpStatus.SC_OK) {
				logger.info("request ! url={}, params={}, status-code={}, reason-phrase={}", url, paramList != null ? JSONArray.toJSONString(paramList) : "", statusCode, phrase);
				httpPost.abort();
				return null;
			}

			result = EntityUtils.toString(response.getEntity());
			EntityUtils.consume(response.getEntity());
		} catch (SocketTimeoutException e) {
			logger.error("request failure! request timed out, url=" + url, e);
		} catch (Exception e) {
			logger.error("request failure! url=" + url, e);
		} finally {
			close(httpPost, response, httpClient);
		}
		return result;
	}

	/**
	 * 发送 SSL POST 请求(HTTPS)
	 *
	 * @param url
	 * @param paramList
	 * @return
	 * @throws IOException
	 */
	public static Map<String, String> sendPostSSL(String url, List<org.apache.http.NameValuePair> paramList) throws IOException {
		CloseableHttpClient httpClient = createSSLClient();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		Map<String, String> result = null;
		try {
			httpPost.setConfig(requestConfig);
			if (paramList != null && paramList.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(paramList, CHARSET));
			}
			response = httpClient.execute(httpPost);

			StatusLine sl = response.getStatusLine();
			int statusCode = 0;
			String phrase = "";
			if (sl != null) {
				statusCode = sl.getStatusCode();
				phrase = sl.getReasonPhrase();
			}

			if (statusCode != HttpStatus.SC_OK) {
				logger.info("request ! url={}, params={}, status-code={}, reason-phrase={}", url, paramList != null ? JSONArray.toJSONString(paramList) : "", statusCode, phrase);
				httpPost.abort();
				return null;
			}

			result = readReqStr(response.getEntity().getContent());
			EntityUtils.consume(response.getEntity());
		} catch (SocketTimeoutException e) {
			logger.error("request failure! request timed out, url=" + url, e);
		} catch (Throwable e) {
			logger.error("request failure! url=" + url, e);
		} finally {
			close(httpPost, response, httpClient);
		}
		return result;
	}

	/**
	 * 发送 SSL POST 请求(HTTPS)，JSON形式
	 *
	 * @param url
	 *            API接口URL
	 * @param json
	 *            JSON对象
	 * @return
	 */
	public static String doPostSSL(String url, String json) throws IOException {
		CloseableHttpClient httpClient = createSSLClient();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String result = null;
		try {
			httpPost.setConfig(requestConfig);
			if (json != null && json.trim().length() > 0) {
				StringEntity stringEntity = new StringEntity(json, CHARSET);// 解决中文乱码问题
				stringEntity.setContentEncoding(CHARSET);
				stringEntity.setContentType("application/json");
				httpPost.setEntity(stringEntity);
			}
			response = httpClient.execute(httpPost);

			StatusLine sl = response.getStatusLine();
			int statusCode = 0;
			String phrase = "";
			if (sl != null) {
				statusCode = sl.getStatusCode();
				phrase = sl.getReasonPhrase();
			}

			if (statusCode != HttpStatus.SC_OK) {
				logger.info("request ! url={}, params={}, status-code={}, reason-phrase={}", url, json, statusCode, phrase);
				httpPost.abort();
				return null;
			}

			result = EntityUtils.toString(response.getEntity(), CHARSET);
			EntityUtils.consume(response.getEntity());
		} catch (SocketTimeoutException e) {
			logger.error("request failure! request timed out, url=" + url, e);
		} catch (Exception e) {
			logger.error("request failure! url=" + url, e);
		} finally {
			close(httpPost, response, httpClient);
		}
		return result;
	}

	/**
	 * 发送 SSL GET 请求(HTTPS)
	 *
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public static String doGetSSL(String url, Map<String, Object> paramMap) throws IOException {
		return doGetSSL(url, convertParameter(paramMap));
	}

	/**
	 * 发送 SSL GET 请求(HTTPS)
	 * @param url
	 * @param paramList
	 * @return
	 * @throws IOException
	 */
	public static String doGetSSL(String url, List<org.apache.http.NameValuePair> paramList) throws IOException {
		CloseableHttpClient httpClient = createSSLClient();
		CloseableHttpResponse response = null;
		HttpGet httpGet = null;
		String result = null;
		try {
			if (paramList != null && paramList.size() > 0) {
				url = url + "?" + URLEncodedUtils.format(paramList, CHARSET);
			}
			httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			response = httpClient.execute(httpGet);

			StatusLine sl = response.getStatusLine();
			int statusCode = 0;
			String phrase = "";
			if (sl != null) {
				statusCode = sl.getStatusCode();
				phrase = sl.getReasonPhrase();
			}

			if (statusCode != HttpStatus.SC_OK) {
				logger.info("request ! url={}, params={}, status-code={}, reason-phrase={}", url, paramList != null ? JSONArray.toJSONString(paramList) : "", statusCode, phrase);
				httpGet.abort();
				return null;
			}

			result = EntityUtils.toString(response.getEntity());
			EntityUtils.consume(response.getEntity());
		} catch (SocketTimeoutException e) {
			logger.error("request failure! request timed out, url=" + url, e);
		} catch (Exception e) {
			logger.error("request failure! url=" + url, e);
		} finally {
			close(httpGet, response, httpClient);
		}
		return result;
	}

	/**
	 * 发送 POST 请求(HTTP)
	 *
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, Map<String, Object> paramMap) throws IOException {
		return doPost(url, convertParameter(paramMap));
	}

	/**
	 * 发送 POST 请求(HTTP)
	 *
	 * @param url
	 * @param paramList
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, List<org.apache.http.NameValuePair> paramList) throws IOException {
		CloseableHttpClient httpClient = createHttpClient();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String result = null;
		try {
			httpPost.setConfig(requestConfig);
			if (paramList != null && paramList.size() > 0) {
				httpPost.setEntity(new UrlEncodedFormEntity(paramList, CHARSET));
			}

			//httpPost.setHeader("referer", "http://localhost/yyfax-front/login");
			response = httpClient.execute(httpPost);

			StatusLine sl = response.getStatusLine();
			int statusCode = 0;
			String phrase = "";
			if (sl != null) {
				statusCode = sl.getStatusCode();
				phrase = sl.getReasonPhrase();
			}

			if (statusCode != HttpStatus.SC_OK) {
				logger.info("request ! url={}, params={}, status-code={}, reason-phrase={}", url, paramList != null ? JSONArray.toJSONString(paramList) : "", statusCode, phrase);
				httpPost.abort();
				return null;
			}

			result = EntityUtils.toString(response.getEntity());
			EntityUtils.consume(response.getEntity());
		} catch (SocketTimeoutException e) {
			logger.error("request failure! request timed out, url=" + url, e);
		} catch (Exception e) {
			logger.error("request failure! url=" + url, e);
		} finally {
			close(httpPost, response, httpClient);
		}
		return result;
	}

	/**
	 * 发送 POST 请求(HTTP),JSON形式
	 *
	 * @param url
	 * @param json
	 *            对象
	 * @return
	 * @throws IOException
	 */
	public static String doPost(String url, String json) throws IOException {
		CloseableHttpClient httpClient = createHttpClient();
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		String result = null;
		try {
			if (json != null && json.trim().length() > 0) {
				StringEntity stringEntity = new StringEntity(json, CHARSET);// 解决中文乱码问题
				stringEntity.setContentEncoding(CHARSET);
				stringEntity.setContentType("application/json");
				httpPost.setEntity(stringEntity);
			}
			httpPost.setConfig(requestConfig);
			response = httpClient.execute(httpPost);

			StatusLine sl = response.getStatusLine();
			int statusCode = 0;
			String phrase = "";
			if (sl != null) {
				statusCode = sl.getStatusCode();
				phrase = sl.getReasonPhrase();
			}

			if (statusCode != HttpStatus.SC_OK) {
				logger.info("request ! url={}, params={}, status-code={}, reason-phrase={}", url, json, statusCode, phrase);
				httpPost.abort();
				return null;
			}

			result = EntityUtils.toString(response.getEntity(), CHARSET);
			EntityUtils.consume(response.getEntity());
		} catch (SocketTimeoutException e) {
			logger.error("request failure! request timed out, url=" + url, e);
		} catch (Exception e) {
			logger.error("request failure! url=" + url, e);
		} finally {
			close(httpPost, response, httpClient);
		}
		return result;
	}

	/**
	 * 发送 GET 请求(HTTP)
	 *
	 * @param url
	 * @param paramMap
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url, Map<String, Object> paramMap) throws IOException {
		return doGet(url, convertParameter(paramMap));
	}

	/**
	 * 发送 GET 请求(HTTP)
	 *
	 * @param url
	 * @param paramList
	 * @return
	 * @throws IOException
	 */
	public static String doGet(String url, List<org.apache.http.NameValuePair> paramList) throws IOException {
		CloseableHttpClient httpClient = createHttpClient();
		CloseableHttpResponse response = null;
		HttpGet httpGet = null;
		String result = null;
		try {
			if (paramList != null && paramList.size() > 0) {
				url = url + "?" + URLEncodedUtils.format(paramList, CHARSET);
			}
			httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			response = httpClient.execute(httpGet);

			StatusLine sl = response.getStatusLine();
			int statusCode = 0;
			String phrase = "";
			if (sl != null) {
				statusCode = sl.getStatusCode();
				phrase = sl.getReasonPhrase();
			}

			if (statusCode != HttpStatus.SC_OK) {
				logger.info("request ! url={}, params={}, status-code={}, reason-phrase={}", url, paramList != null ? JSONArray.toJSONString(paramList) : "", statusCode, phrase);
				httpGet.abort();
				return null;
			}

			result = EntityUtils.toString(response.getEntity());
			EntityUtils.consume(response.getEntity());
		} catch (SocketTimeoutException e) {
			logger.error("request failure! request timed out, url=" + url, e);
		} catch (Exception e) {
			logger.error("request failure! url=" + url, e);
		} finally {
			close(httpGet, response, httpClient);
		}
		return result;
	}

	/**
	 * 关闭连接释放资源
	 *
	 * @param httpRequest
	 * @param response
	 * @param httpClient
	 */
	private static void close(HttpRequestBase httpRequest, CloseableHttpResponse response, CloseableHttpClient httpClient) {
		try {
			if (httpRequest != null) {
				httpRequest.abort();
			}
			if (response != null) {
				response.close();
			}
			if (httpClient != null) {
				httpClient.close();
			}
		} catch (Exception e) {
			logger.error("HttpClient关闭连接释放资源异常!", e);
		}
	}

	/**
	 *返回的是一个json字符串 转换成一个map<String,String>
	 *
	 * @param is
	 * @return
	 * @throws Throwable
	 */
	protected static Map<String, String> readReqStr(InputStream is) throws Throwable {
		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != reader) {
					reader.close();
				}
			} catch (IOException e) {

			}
		}
		ObjectMapper objm = new ObjectMapper();
		Map<String, String> params = objm.readValue(sb.toString(), Map.class);
		return params;
	}
}
