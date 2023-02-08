package org.zjh.web.gp.entity;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import org.zjh.util.DataTypeUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldFill;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author 张建航
 * @since 2023-01-18
 */
@Data
@TableName("gp_hq")
@ApiModel(value = "GpHq对象", description = "")
public class GpHq implements Serializable {
	
	public GpHq(){}
	public GpHq(String code,String name,JSONArray ja){
		this.date = ja.getString(0);
		this.s = ja.getFloatValue(1);
		this.e = ja.getFloatValue(2);
		this.udm = ja.getFloatValue(3);
		this.udr = DataTypeUtil.getFloatP(ja.get(4), 0f);
		this.l = ja.getFloatValue(5);
		this.h = ja.getFloatValue(6);
		this.n = ja.getIntValue(7);
		this.m = ja.getFloatValue(8);
		this.ch = DataTypeUtil.getFloatP(ja.get(9), 0f);
		this.zf = (this.h-this.l)/(this.e-this.udm)*100;
		this.zf = Float.parseFloat(String.format("%.2f", this.zf));
		this.code = code;
		
//		this.name = name;
	}

    public static final long serialVersionUID = 1L;

    @ApiModelProperty("股票代码")
    public String code;

    @ApiModelProperty("日期")
    public String date;

    @ApiModelProperty("开盘价")
    public Float s;

    @ApiModelProperty("收盘价")
    public Float e;

    @ApiModelProperty("最高价")
    public Float h;

    @ApiModelProperty("最低价")
    public Float l;

    @ApiModelProperty("涨跌幅度")
    public Float udr;

    @ApiModelProperty("涨跌额")
    public Float udm;

    @ApiModelProperty("交易量")
    public Integer n;

    @ApiModelProperty("交易额")
    public Float m;

    @ApiModelProperty("换手")
    public Float ch;

    @ApiModelProperty("振幅")
    public Float zf;

    @ApiModelProperty("均线")
    public String avg;
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Float getS() {
		return s;
	}

	public void setS(Float s) {
		this.s = s;
	}

	public Float getE() {
		return e;
	}

	public void setE(Float e) {
		this.e = e;
	}

	public Float getH() {
		return h;
	}

	public void setH(Float h) {
		this.h = h;
	}

	public Float getL() {
		return l;
	}

	public void setL(Float l) {
		this.l = l;
	}

	public Float getUdr() {
		return udr;
	}

	public void setUdr(Float udr) {
		this.udr = udr;
	}

	public Float getUdm() {
		return udm;
	}

	public void setUdm(Float udm) {
		this.udm = udm;
	}

	public Integer getN() {
		return n;
	}

	public void setN(Integer n) {
		this.n = n;
	}

	public Float getM() {
		return m;
	}
	public void setM(Float m) {
		this.m = m;
	}
	public Float getCh() {
		return ch;
	}
	public void setCh(Float ch) {
		this.ch = ch;
	}
	public Float getZf() {
		return zf;
	}

	public void setZf(Float zf) {
		this.zf = zf;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}

	public JSONArray getAvgJson() {
		if(this.avg == null || "".equals(this.avg)){
			return new JSONArray();
		}
		return JSONArray.parseArray(this.avg);
	}
}
