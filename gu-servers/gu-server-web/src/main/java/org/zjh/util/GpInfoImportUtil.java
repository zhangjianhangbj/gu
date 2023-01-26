package org.zjh.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.zjh.web.gp.entity.GpInfo;

import com.alibaba.druid.sql.ast.statement.SQLLateralViewTableSource;

public class GpInfoImportUtil {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<GpInfo> list = read("E:\\work\\gu\\data\\gu-sz.txt");
		write(list, "E:\\work\\gu\\data\\gu-sz.sql");
	}
	
	public static List<GpInfo> read(String path){
		InputStream inputStream;
		try {
			inputStream = new FileInputStream(path);
			return read(inputStream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 读取文件 转换成GpInfo 对象list 集合
	 * @param inputStream
	 * @return
	 */
	public static List<GpInfo> read(InputStream inputStream){
		
		IoRWUtil ir = IoRWUtil.createRead(inputStream);
		List<GpInfo> list = new ArrayList<GpInfo>();
		String line = "";
		//读取第一行不要
		ir.readLine();
		while((line = ir.readLine())!=null){
			line = line.trim();
			//序	代码	名称	市盈率(动)	所属行业	市净率	总股本	总市值	流通股本	流通市值	交易所
			String [] vs = line.split("\t");
			GpInfo gp = new GpInfo();
			gp.setCode(vs[1]);
			gp.setName(vs[2]);
			gp.setPb(DataTypeUtil.getFloat(vs[3],0f));
			gp.setCategory(vs[4]);
			gp.setPe(DataTypeUtil.getFloat(vs[5],0f));
			gp.setEquityTotal(DataTypeUtil.getLongUnit(vs[6], 0l));
			gp.setEquity(DataTypeUtil.getLongUnit(vs[8], 0l));
			gp.setExchange(vs[10]);
			list.add(gp);
		}
		ir.close();
		return list;
	}

	public static void write (List<GpInfo> list, String path){
		if(list ==null){
			return;
		}
		IoRWUtil iw = IoRWUtil.createWriter(path);
		for (int i = 0; i < list.size(); i++) {
			iw.write(list.get(i).toSql());
			iw.newLine();
		}
		iw.close();
	}
}
