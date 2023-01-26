package org.zjh.web.gp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
@TableName("gp_info")
@ApiModel(value = "GpInfo对象", description = "")
public class GpInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("股票交易锁")
    private String exchange;

    @ApiModelProperty("股票代码")
    private String code;

    @ApiModelProperty("股票名称")
    private String name;

    @ApiModelProperty("所属行业")
    private String category;

    @ApiModelProperty("市盈率")
    private Float pb;

    @ApiModelProperty("市净率")
    private Float pe;

    @ApiModelProperty("流通股本")
    private Long equity;

    @ApiModelProperty("总股本")
    private Long equityTotal;

    @ApiModelProperty("连续收阳天数")
    private Integer lianyang;

    @ApiModelProperty("上市时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    @ApiModelProperty("更新时间")
    public Date updateTime;
    
    @ApiModelProperty("删除标识 1删除 0 未删除")
    public int delFlag;

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

	public Float getPb() {
		return pb;
	}

	public void setPb(Float pb) {
		this.pb = pb;
	}

	public Float getPe() {
		return pe;
	}

	public void setPe(Float pe) {
		this.pe = pe;
	}

	public Long getEquity() {
		return equity;
	}

	public void setEquity(Long equity) {
		this.equity = equity;
	}

	public Long getEquityTotal() {
		return equityTotal;
	}

	public void setEquityTotal(Long equityTotal) {
		this.equityTotal = equityTotal;
	}

	public Integer getLianyang() {
		return lianyang;
	}

	public void setLianyang(Integer lianyang) {
		this.lianyang = lianyang;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public String toSql(){
		String sql = String.format("INSERT INTO `gp_info` (`code`, `name`, `category`, `pb`, `pe`, `equity`, `equity_total`, `exchange`) VALUES ('%s', '%s', '%s', %f, %f, %d, %d, '%s');",this.code,this.name,this.category,this.pb,this.pe,this.equity,this.equityTotal,this.exchange);
		return sql;
	}

}
