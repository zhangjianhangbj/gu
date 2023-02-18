package org.zjh.report.trend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.zjh.report.util.StatisticUtil;
import org.zjh.web.gp.entity.GpHq;
import org.zjh.web.gp.entity.GpInfo;
import org.zjh.web.gp.util.GpHqUtil;

public class StatisticUpTrend {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GpInfo gp = new GpInfo();
		gp.setCode("000617");
		List<GpHq> list = GpHqUtil.getHqData(gp, "20221013", "20221129");
		List<GpHq> l = new ArrayList<GpHq>();
		for (int i = 6; i >= 0; i--) {
			l.add(list.get(i));
		}
		float f = statistic(l);
		System.out.println(f);
	}
	
	public static Float statistic(List<GpHq> list){
		
		List<Float> ca = StatisticUtil.countAvg(list, 1);
		Float v = sum(ca);
		//均线多头排列
		List<Float> au = StatisticUtil.countAvgUp(list, new int[]{1,2});
		v = v + sum(au);
		//涨幅
		List<Float> ur = StatisticUtil.countUpRange(list, new float[]{2f,5f});
		v = v + sum(ur);
		//振幅
		List<Float> zf = StatisticUtil.countZFRange(list, new float[]{2f,5f});
		v = v + sum(zf);
		//连阳
		//List<Float> ly = StatisticUtil.countLianyang(list);
		//v = v + sum(ly);
		//统计收盘是否大于均线
		List<Float> ga = StatisticUtil.countGtAvg(list,1);
		v = v + sum(ga);
		
		return v;
	}
	
	public static float sum(List<Float> list){
		float v =0;
		if(list == null){
			return v;
		}
		for (int i = 0; i < list.size(); i++) {
			v +=list.get(i);
		}
		return v;
	}
}
