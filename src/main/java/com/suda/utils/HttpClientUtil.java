package com.suda.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * httpclient工具类
 * @Class Name HttpClientUtil
 * @Author likang
 * @Create In 2014-6-19
 */
public class HttpClientUtil {

	public HttpClientUtil() {
		
	}

	//默认转换编码
	private String encode = "utf-8";


	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	/**
	 * 将post参数放入map中
	 * @Methods Name initPostData
	 * @Create In 2014-6-17 By 005
	 * @param dataMap
	 * @return NameValuePair[]
	 */
	private NameValuePair[] initPostData(Map<String,String> dataMap) {
		Iterator<String> iterator = dataMap.keySet().iterator();
		int size = dataMap.keySet().size();
		if (size > 0) {
			NameValuePair[] postData = new NameValuePair[size];
			int i = 0;
			while (iterator.hasNext()) {
				String name = iterator.next();
				postData [i] = new NameValuePair(name,dataMap.get(name));
				i++;
			}
			return postData;
		}
		return null;
	}
	
	private String initGetData(String url, Map<String,String> dataMap) {
		Iterator<String> iterator = dataMap.keySet().iterator();
		url += "?";
		int size = dataMap.keySet().size();
		if (size > 0) {
			while (iterator.hasNext()) {
				String name = iterator.next();
				try {
					url += name + "=" + URLEncoder.encode(dataMap.get(name),"utf-8") + "&";
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					url += name + "=" + dataMap.get(name) + "&";
					e.printStackTrace();
				}
			}
			url = url.substring(0,url.length() - 1);
		}
		return url;
	}
	
	/**
	 * 发送post请求
	 * @Methods Name sendData
	 * @Create In 2014-6-19 By likang
	 * @param url
	 * @param dataMap
	 * @return String
	 */
	public String sendData(String url, Map<String,String> dataMap){
		HttpClient httpClient = new HttpClient(); 
		HttpConnectionManagerParams configParams = httpClient.getHttpConnectionManager().getParams();  
        configParams.setConnectionTimeout(60*1000);  
        configParams.setSoTimeout(60*1000);  
        httpClient.getParams().setConnectionManagerTimeout(60*1000l);  
		String text = "";
		//模拟登陆，按实际服务器端要求选用 Post 或 Get 请求方式         
		PostMethod postMethod = new PostMethod(url);           
		//设置登陆时要求的信息，一般就用户名和密码，验证码自己处理了       
		NameValuePair[] data = initPostData(dataMap);
		postMethod.setRequestBody(data);   
		postMethod.setRequestHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.131 Safari/537.36");               
		try {             
			//设置 HttpClient 接收 Cookie,用与浏览器一样的策略            
			httpClient.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);             
			httpClient.executeMethod(postMethod);   
			int code = httpClient.executeMethod(postMethod);  
			text = postMethod.getResponseBodyAsString();
			text = URLDecoder.decode(text, this.getEncode());
		} catch (Exception e) {
			e.printStackTrace();
		}     
		return text;
	}

	public String sendDataGet(String url, Map<String,String> dataMap){
		 GetMethod getMethod = null;
		 String text = "";
		 try{	   
			url = initGetData(url, dataMap);
			getMethod = new GetMethod(url);
			HttpClient httpClient; 
			MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
			httpClient = new org.apache.commons.httpclient.HttpClient(defaultManager);
			HttpConnectionManagerParams params = defaultManager.getParams();
			params.setMaxTotalConnections(10000);
			params.setConnectionTimeout(0);
			params.setSoTimeout(0);
			httpClient.getParams().setConnectionManagerTimeout(0);
			httpClient.getParams().setSoTimeout(0);
			httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
			httpClient.getParams().setContentCharset("utf-8");
			List<Header> headers = new ArrayList<Header>();
			httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
			int code = httpClient.executeMethod(getMethod);
			text = getMethod.getResponseBodyAsString();
		  }
		  catch(Exception e){
		  }finally{
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		  }
		return text;
	}

    public String sendDataGet(String url, Map<String,String> dataMap, int timeout){
        GetMethod getMethod = null;
        String text = "";
        try{
            url = initGetData(url, dataMap);
            getMethod = new GetMethod(url);
            HttpClient httpClient;
            MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
            httpClient = new HttpClient(defaultManager);
            HttpConnectionManagerParams params = defaultManager.getParams();
            params.setMaxTotalConnections(timeout * 1000);
            params.setConnectionTimeout(timeout * 1000);
            params.setSoTimeout(timeout * 1000);
            httpClient.getParams().setConnectionManagerTimeout(timeout * 1000);
            httpClient.getParams().setSoTimeout(timeout * 1000);
            httpClient.getParams().setCookiePolicy(CookiePolicy.IGNORE_COOKIES);
            httpClient.getParams().setContentCharset("utf-8");
            List<Header> headers = new ArrayList<Header>();
            httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
            int code = httpClient.executeMethod(getMethod);
            //text = getMethod.getResponseBodyAsString();
            BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream()));
            StringBuffer stringBuffer = new StringBuffer();
            String str = "";
            while((str = reader.readLine())!=null){
                stringBuffer.append(str);
            }
            text = stringBuffer.toString();
        } catch(Exception e){
        } finally {
            if (getMethod != null) {
                getMethod.releaseConnection();
            }
        }
        return text;
    }

	public static void main(String[] args) {
		HttpClientUtil httpClientUtil = new HttpClientUtil();
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date(1);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date date1 = calendar.getTime();
		//System.out.println(simpleDateFormat.format(date));;
		map.put("date", simpleDateFormat.format(date1));
		map.put("appver", "1.0.2.2");
		map.put("appvid", "1.0.2.2");
		map.put("network", "wifi");
		String str = httpClientUtil.sendDataGet("http://sportsnba.qq.com/match/listByDate", map);
		System.out.println(str);
		JSONObject jsonObject = JSON.parseObject(str);

		//System.out.println(simpleDateFormat.format(date1));
		//System.out.println(jsonObject.get("code"));

		try {
			byte[] bytes = "\\u516c\\u725b".getBytes("Unicode");
			String strs = new String(bytes, "UTF-8");

			System.out.println(CharacterConvert.unicodeToString("\u516c\u725b"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
