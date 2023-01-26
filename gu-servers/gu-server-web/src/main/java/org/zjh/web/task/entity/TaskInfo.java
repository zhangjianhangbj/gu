package org.zjh.web.task.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableId;

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
@TableName("task_info")
@ApiModel(value = "TaskInfo对象", description = "")
public class TaskInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("任务类型：1.5日均线买法 2.其他")
    private Integer type;

    @ApiModelProperty("任务执行时间")
    private Date exceTime;
    
    @ApiModelProperty("任务 执行范围  0 个股， 1 全部")
    private Integer exceRange;
    
    @ApiModelProperty("任务执行类型  1 执行一次, 2 每天执行")
    private Integer exceType;
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getExceTime() {
		return exceTime;
	}

	public void setExceTime(Date exceTime) {
		this.exceTime = exceTime;
	}

	public Integer getExceRange() {
		return exceRange;
	}

	public void setExceRange(Integer exceRange) {
		this.exceRange = exceRange;
	}

	public Integer getExceType() {
		return exceType;
	}

	public void setExceType(Integer exceType) {
		this.exceType = exceType;
	}

}
