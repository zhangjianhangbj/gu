package org.zjh.report.util;

import java.util.ArrayList;
import java.util.List;

import org.zjh.web.gp.entity.GpHq;

import com.alibaba.fastjson.JSONArray;

public class StatisticUtil {

	public static void main(String[] args) {
	
	}
	
	/**
	 * 统计均线是否连续上涨
	 * @param list 行情列表
	 * @param da 统计均线周期  1 3日，2 5日， 3 10日，  4 20日 
	 * @return
	 */
	public static List<Float> countAvg(List<GpHq> list, int da){
		if(list ==null)
			return null;
		List<Float> res = new ArrayList<Float>();
		Float avg = 0f;
		for (int i = 0; i < list.size(); i++) {
			Float f = list.get(i).getAvgJson().getFloat(da);
			if(f>avg){
				avg = f;
				res.add(1f);
			}else{
				res.add(0f);
			}
		}
		return res;
	}
	
	/**
	 * 统计是否连续收阳
	 * @param list 股票行情
	 * @return
	 */
	public static List<Float> countLianyang(List<GpHq> list){
		if(list ==null)
			return null;
		List<Float> res = new ArrayList<Float>();
		
		for (int i = 0; i < list.size(); i++) {
			Float f = list.get(i).getUdm();
			if(f>0){
				res.add(1f);
			}else{
				res.add(0f);
			}
		}
		return res;
	}

	/**
	 * 统计是否大于均线
	 * @param list 股票行情
	 * @param da 统计均线周期  1 3日，2 5日， 3 10日，  4 20日 
	 * @return
	 */
	public static List<Float> countGtAvg(List<GpHq> list, int da){
		if(list ==null)
			return null;
		List<Float> res = new ArrayList<Float>();
		for (int i = 0; i < list.size(); i++) {
			Float f = list.get(i).getAvgJson().getFloat(da);
			Float e = list.get(i).getE();
			if(e>f){
				res.add(1f);
			}else{
				res.add(0f);
			}
		}
		return res;
	}
	
	/**
	 * 统计是否大于均线
	 * @param list 股票行情
	 * @param da 统计均线周期  1 3日，2 5日， 3 10日，  4 20日 
	 * @return
	 */
	public static List<Float> countAvgUp(List<GpHq> list, int [] da){
		if(list ==null)
			return null;
		List<Float> res = new ArrayList<Float>();
		for (int i = 0; i < list.size(); i++) {
			JSONArray avg = list.get(i).getAvgJson();
			
			if(da.length==2){
				if(avg.getFloat(da[0]) > avg.getFloat(da[1])){
					res.add(1f);
				}else{
					res.add(0f);
				}
			}else{
				boolean flag = true;
				for (int j = 0; j < da.length-1; j++) {
					if(avg.getFloat(da[j]) < avg.getFloat(da[j+1])){
						flag =false;
						break;
					}
				}
				if(flag){
					res.add(1f);
				}else{
					res.add(0f);
				}
			}
		}
		return res;
	}
	
	/**
	 * 统计涨幅区间 是否满足
	 * @param list 股票行情
	 * @return
	 */
	public static List<Float> countUpRange(List<GpHq> list, float [] range){
		if(list ==null)
			return null;
		List<Float> res = new ArrayList<Float>();
		
		for (int i = 0; i < list.size(); i++) {
			Float f = list.get(i).getUdr();
			if(f>=range[0] && f<=range[1]){
				res.add(1f);
			}else{
				res.add(0f);
			}
		}
		return res;
	}
	
	/**
	 * 统计振幅区间 是否满足
	 * @param list 股票行情
	 * @return
	 */
	public static List<Float> countZFRange(List<GpHq> list, float [] range){
		if(list ==null)
			return null;
		List<Float> res = new ArrayList<Float>();
		
		for (int i = 0; i < list.size(); i++) {
			Float f = list.get(i).getZf();
			if(f>=range[0] && f<=range[1]){
				res.add(1f);
			}else{
				res.add(0f);
			}
		}
		return res;
	}
	
}
