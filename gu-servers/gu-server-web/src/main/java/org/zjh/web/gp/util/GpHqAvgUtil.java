package org.zjh.web.gp.util;

import java.util.List;

import org.zjh.web.gp.entity.GpHq;

public class GpHqAvgUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println();
	}
	
	public static float [] avg(List<GpHq> list, int index){
		float [] avg = new float[4];
		if(list ==null)
			return avg;
		
		float v =0f;
		int count = 0;
		for (int i = index; i < list.size(); i++) {
			v += list.get(i).e;
			count++;
			if(count==3){
				String value = String.format("%.2f", v/3);
				avg[0] = Float.parseFloat(value);
			}
			if(count==5){
				String value = String.format("%.2f", v/5);
				avg[1] = Float.parseFloat(value);
			}
			if(count==10){
				String value = String.format("%.2f", v/10);
				avg[2] = Float.parseFloat(value);
			}
			if(count==20){
				String value = String.format("%.2f", v/20);
				avg[3] = Float.parseFloat(value);
				return avg;
			}
		}
		return avg;
	}
	
}
