package org.zjh.web.gp.vo;

import io.swagger.annotations.ApiModelProperty;

public class GpInfoReq {
	@ApiModelProperty("股票交易锁")
    private String exchange;

    @ApiModelProperty("股票代码")
    private String code;

    @ApiModelProperty("股票名称")
    private String name;

    @ApiModelProperty("所属行业")
    private String category;
    
    @ApiModelProperty("开始时间")
    private String startTime;
    
    @ApiModelProperty("结束时间")
    private String endTime;

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
    
}
