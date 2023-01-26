package org.zjh.web.task.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("task_gp_pool")
@ApiModel(value = "TaskGpPool对象", description = "")
public class TaskGpPool implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("task_id")
    private Integer taskId;

    @ApiModelProperty("股票代码")
    private String code;

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
