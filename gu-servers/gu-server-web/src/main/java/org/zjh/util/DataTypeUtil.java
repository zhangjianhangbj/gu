package org.zjh.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Object 对象转换基础类型工具
 * 
 * @author EDY
 *
 */
public class DataTypeUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public static Integer getInteger(Object o, Integer def) {
		if (o == null) {
			return def;
		}
		try {
			return Integer.parseInt(o.toString());
		} catch (Exception e) {
			return def;
		}
	}

	public static Long getLong(Object o, Long def) {
		if (o == null) {
			return def;
		}
		try {
			return Long.parseLong(o.toString());
		} catch (Exception e) {
			return def;
		}
	}
	
	public static Long getLongUnit(Object o, Long def) {
		if (o == null) {
			return def;
		}
		String v = o.toString().substring(0, o.toString().length()-1);
		char u = o.toString().charAt(o.toString().length()-1);
		try {
			Double d= Double.parseDouble(v);
			if('亿' == u){
				d = d * 100000000;
			}
			if('万' == u){
				d = d * 10000;
			}
			return d.longValue();
		} catch (Exception e) {
			return def;
		}
	}

	public static Double getDouble(Object o, Double def) {
		if (o == null) {
			return def;
		}
		try {
			return Double.parseDouble(o.toString());
		} catch (Exception e) {
			return def;
		}
	}

	public static Float getFloat(Object o, Float def) {
		if (o == null) {
			return def;
		}
		try {
			return Float.parseFloat(o.toString());
		} catch (Exception e) {
			return def;
		}
	}
	
	public static Float getFloatP(Object o, Float def) {
		if (o == null) {
			return def;
		}
		try {
			return Float.parseFloat(o.toString().replace("%", ""));
		} catch (Exception e) {
			return def;
		}
	}

	public static String getString(Object o, String def) {
		if (o == null) {
			return def;
		}
		return o.toString();
	}

	public static JSONArray getJSONArray(Object o) {
		if (o == null) {
			return null;
		}
		JSONArray ja = JSONArray.parseArray(o.toString());
		return ja;
	}

	public static JSONObject getJSONObject(Object o) {
		if (o == null) {
			return null;
		}
		JSONObject obj = JSONObject.parseObject(o.toString());
		return obj;
	}
}
