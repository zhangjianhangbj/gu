package org.zjh.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zjh.web.gp.entity.GpInfo;

public class GpInfoCache {

	private static Map<String,GpInfo> map = new HashMap<String, GpInfo>();
	
	public static void loadGpInfo(List<GpInfo> list){
		if(list == null){
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			String code = list.get(i).getCode();
			map.put(code, list.get(i));
		}
	}
	
	public static GpInfo getGpInfo(String code){
		return map.get(code);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
