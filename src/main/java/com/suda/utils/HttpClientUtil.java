package com.suda.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.BufferedReader;
import java.io.IOException;
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


	public String sendGet(String url) throws IOException {
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setContentCharset("utf-8");
		GetMethod getMethod = null;
		getMethod = new GetMethod(url);
		List<Header> headers = new ArrayList<Header>();
		Header header = new Header();
		header.setName("Accept");
		header.setValue("text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		headers.add(header);
		Header header2 = new Header();
		header2.setName("Accept-Encoding");
		header2.setValue("gzip, deflate, sdch");
		headers.add(header2);

		Header header3 = new Header();
		header3.setName("Connection");
		header3.setValue("keep-alive");
		headers.add(header3);

//		Header header4 = new Header();
//		header4.setName("Cache-Control");
//		header4.setValue("max-age=0");
//		headers.add(header4);

		/*Header header5 = new Header();
		header5.setName("Upgrade-Insecure-Requests");
		header5.setValue("1");
		headers.add(header5);*/

		Header header6 = new Header();
		header6.setName("User-Agent");
		header6.setValue("Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36");
		headers.add(header6);

		Header header7 = new Header();
		header7.setName("Accept-Language");
		header7.setValue("zh-CN,zh;q=0.8");
		headers.add(header7);

			 Header header8 = new Header();
			 header8.setName("Cookie");
			 header8.setValue("__cfduid=d3f909d1d577bd9921cc6c1e609e9f8061508979408; UM_distinctid=15f562e801b0-03feb63200578c-5e1c3513-13c680-15f562e801cf7; PHPSESSID=ss3ag4r023208s4fvoo6r2i1k4; a1800_pages=19; a1800_times=12; __tins__19291800=%7B%22sid%22%3A%201513307942423%2C%20%22vd%22%3A%201%2C%20%22expires%22%3A%201513309742423%7D; __51cke__=; __51laig__=19; CNZZDATA1264605686=1555870955-1508977521-%7C1513307000; Hm_lvt_af0cc1060196e78360bd49de46d9b704=1513301859; Hm_lpvt_af0cc1060196e78360bd49de46d9b704=1513307943");
			 headers.add(header8);

		/*Header header9 = new Header();
		header9.setName("Content-Length");
		header9.setValue(" 2");
		headers.add(header9);*/

		httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		int code = httpClient.executeMethod(getMethod);
		byte[] responseBody = getMethod.getResponseBody();
		System.out.println(new String(responseBody));
				 return  new String(responseBody);

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
			List<Header> headers = new ArrayList<Header>();
			headers.add(new Header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
			headers.add(new Header("Connection", "keep-alive"));
			headers.add(new Header("Cache-Control", "max-age=0"));
			headers.add(new Header("Upgrade-Insecure-Requests", "1"));
			headers.add(new Header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36"));
			httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
			int code = httpClient.executeMethod(getMethod);
			//text = getMethod.getResponseBodyAsString();
			 BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), "utf-8"));
			 StringBuffer stringBuffer = new StringBuffer();
			 String str = "";
			 while((str = reader.readLine())!=null){
				 stringBuffer.append(str);
			 }
			 text = stringBuffer.toString();
		  }
		  catch(Exception e){
		  }finally{
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
		  }
		return text;
	}

	public String sendDataGet(String url){
		GetMethod getMethod = null;
		String text = "";
		try{
			url = initGetData(url, new HashMap<String, String>());
			getMethod = new GetMethod(url);
			HttpClient httpClient;
			MultiThreadedHttpConnectionManager defaultManager = new MultiThreadedHttpConnectionManager();
			httpClient = new org.apache.commons.httpclient.HttpClient(defaultManager);
			HttpConnectionManagerParams params = defaultManager.getParams();
			List<Header> headers = new ArrayList<Header>();
			headers.add(new Header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8"));
			headers.add(new Header("Connection", "keep-alive"));
			headers.add(new Header("Cache-Control", "max-age=0"));
			headers.add(new Header("Upgrade-Insecure-Requests", "1"));
			headers.add(new Header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.76 Mobile Safari/537.36"));
			httpClient.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
			httpClient.getParams().setContentCharset("utf-8");

			HttpConnectionManagerParams configParams = httpClient.getHttpConnectionManager().getParams();
			configParams.setConnectionTimeout(5*1000);  //设置请求超时5秒钟 根据业务调整
			configParams.setSoTimeout(2*1000);  //设置等待数据超时时间2秒钟 根据业务调整
			httpClient.getParams().setConnectionManagerTimeout(1000L);//该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大 ()

			int code = httpClient.executeMethod(getMethod);
			//text = getMethod.getResponseBodyAsString();
			BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), "utf-8"));
			StringBuffer stringBuffer = new StringBuffer();
			String str = "";
			while((str = reader.readLine())!=null){
				stringBuffer.append(str);
			}
			text = stringBuffer.toString();
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
            BufferedReader reader = new BufferedReader(new InputStreamReader(getMethod.getResponseBodyAsStream(), "utf-8"));
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
//		map.put("date", simpleDateFormat.format(date1));
//		map.put("appver", "1.0.2.2");
//		map.put("appvid", "1.0.2.2");
//		map.put("network", "wifi");


		String str = null;
			str = httpClientUtil.sendDataGet("http://www.kuwantiyu.com/", map);
			System.out.println(str);


		//JSONObject jsonObject = JSON.parseObject(str);

		//System.out.println(simpleDateFormat.format(date1));
		//System.out.println(jsonObject.get("code"));

//		try {
//			byte[] bytes = "\\u516c\\u725b".getBytes("Unicode");
//			String strs = new String(bytes, "UTF-8");
//
//			System.out.println(CharacterConvert.unicodeToString("\u516c\u725b"));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
	}
}
