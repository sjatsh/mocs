package smtcl.mocs.utils.device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

/**
 * @ClassName:	DBHelper.java
 * @Description:网络连接服务
 * @Author:		yt
 * @CreateDate:	2013/10/18
 * @Version:	v1.0.6
 *
 */
public class DBHelper {
	
	private HttpClient httpClient;
	private HttpPost httpPost;
	private HttpResponse httpResponse;
	public static boolean isTimeOut;
	
	/**
	 * @MethodName:	getHttpPost
	 * @Description:获取HttpPost
	 */
	@SuppressWarnings("finally")
	private HttpPost getHttpPost(String apiPath, List<NameValuePair> paramList) {
		HttpPost httpPost = null;
		try {
			httpPost = new HttpPost(apiPath);
			httpPost.setEntity(new UrlEncodedFormEntity(paramList, HTTP.UTF_8));
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			return httpPost;
		}
	}
	/**
	 * @MethodName:	getHttpClient
	 * @Description:获取HttpClient
	 */
	private HttpClient getHttpClient() {
		if (httpClient == null) {
			HttpParams httpParams = new BasicHttpParams();    	
	    	HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
	    	HttpConnectionParams.setSoTimeout(httpParams, 15000);
	    	httpClient = new DefaultHttpClient(httpParams);
		}
    	return httpClient;
	}
	/**
	 * @MethodName:	getJsonString
	 * @Description:获取Json返回信息
	 */
	@SuppressWarnings("finally")
	public String getJsonString(String namespace,String methodName, Map<String, String> methodParamMap,String classModel,String IP) {
		String apiPath = "http://"+IP+"/"+namespace+"/"+classModel+"/"+methodName+".json";
		System.err.println("apiPath:"+apiPath);
		// 获取Post参数列表
		List<NameValuePair> paramList = getParamList(methodParamMap);
		StringBuilder builder = new StringBuilder();
		try {
			// 获取HttpPost
			httpPost = getHttpPost(apiPath, paramList);
			// 获取HttpClient
			httpClient = getHttpClient();
			// 获取HttpResponse
			httpResponse = getHttpResponse(httpClient, httpPost);
			HttpEntity entity = httpResponse.getEntity();
			if (entity != null) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"));
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.append(line + "\n");
				}
				reader.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {			
			return builder.toString();
		}
	}
	/**
	 * @MethodName:	getParamList
	 * @Description:获取Pos参数列表
	 */
	private List<NameValuePair> getParamList(Map<String, String> methodParamMap) {
		List<NameValuePair> paramList  = new ArrayList<NameValuePair>();
		// 创建key
		List<String> keyList = new ArrayList<String>();
		// 遍历
		Iterator<String> iterator = methodParamMap.keySet().iterator();
		while (iterator.hasNext()) {
			keyList.add(iterator.next());
		}
		// 按key
		Collections.sort(keyList);
		
		for (int i = 0; i < keyList.size(); i++) 
			paramList.add(new BasicNameValuePair(keyList.get(i), methodParamMap.get(keyList.get(i))));
		return paramList;
	}
	/**
	 * @MethodName:	getHttpResponse
	 * @Description:获取HttpResponse
	 */
	private HttpResponse getHttpResponse(HttpClient client, HttpPost post) {
		HttpResponse response = null;
		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) isTimeOut = false;
			else post.abort();
		} catch (SocketTimeoutException e) {
			isTimeOut = true;
		}catch (Exception e) {
			isTimeOut = true;
		} 
		return response;
	}
	/**
	 * @MethodName:	getTimeOut
	 * @Description:判断是否网络超时
	 */
	public static boolean checkNetIsTimeOut(){
		return isTimeOut;
	}
}
