package org.zjh.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		doget("https://q.stock.sohu.com/hisHq?code=cn_300228&start=20221201&end=20221230&order=D&period=d&callback=historySearchHandler&rt=jsonp");
	}

	public static String doget(String url) {
		String result = null;
		
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();//创建 HttpClient 的实例
		CloseableHttpResponse response = null;
		HttpGet httpGet = new HttpGet(url.toString());//发送请求
        try {
			response = httpClient.execute(httpGet);
			//接收返回
	        int statusCode = response.getStatusLine().getStatusCode();
	        if (response != null && statusCode == HttpStatus.SC_OK) {
	            HttpEntity entity = response.getEntity();//获取结果实体
	            result = EntityUtils.toString(entity, "UTF-8");
	            return result;
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return null;
	}
}
