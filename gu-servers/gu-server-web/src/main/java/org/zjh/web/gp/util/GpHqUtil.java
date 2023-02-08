package org.zjh.web.gp.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.zjh.util.HttpUtil;
import org.zjh.web.gp.entity.GpHq;
import org.zjh.web.gp.entity.GpInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class GpHqUtil {

	static String uri = "https://q.stock.sohu.com/hisHq?code=cn_%s&start=%s&end=%s&stat=0&order=D&period=d&callback=historySearchHandler&rt=jsonp";
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("historySearchHandler(12345)".substring(21,26));
		GpInfo gpinfo = new GpInfo();
		gpinfo.setCode("600105");
		gpinfo.setName("");
		List<GpHq> list = getHqData(gpinfo, "20230120", "20230120");
		System.out.println(list);
	}

	public static List<GpHq> getHqData(GpInfo gp, String st, String et){
		String url = String.format(uri, gp.getCode(),st,et);
		List<GpHq> hqlist = new ArrayList<GpHq>();
		String result = HttpUtil.doget(url);
		if(result != null && result.length()>26){
			result = result.substring(21, result.length()-2);
		}else{
			return hqlist;
		}
		JSONArray ja = JSONArray.parseArray(result);
		ja = ja.getJSONObject(0).getJSONArray("hq");
		for (int i = 0; i < ja.size(); i++) {
			JSONArray jsonk = ja.getJSONArray(i);
			GpHq hq = new GpHq(gp.getCode(),gp.getName(),jsonk);
			hqlist.add(hq);
		}
		avg(hqlist);
		return hqlist;
	}
	
	public static void avg(List<GpHq> list){
		if(list ==null || list.size()==0){
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			float [] avg = GpHqAvgUtil.avg(list, i);
			JSONArray ja = new JSONArray();
			for (int j = 0; j < avg.length; j++) {
				ja.add(avg[j]);
			}
			list.get(i).setAvg(ja.toString());
		}
	}
}
