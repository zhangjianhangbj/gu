package org.zjh.web.gp.util;

import java.util.ArrayList;
import java.util.List;

public class LoadGpHqData {
	/**
	 * 是否加载中  true 加载中  false 未加载 
	 */
	public static boolean isload = false;
	/**
	 * 加载状态 0启动 1停止
	 */
	public static int status =0; 
	public static Integer gp_num =0;
	public static Integer gp_total = 0;
	public static List<String> codeList = new ArrayList<String>();

	public static void clear(){
		status = 0;
		isload = false;
		gp_num=0;
		gp_total = 0;
		codeList.clear();
	}
}
