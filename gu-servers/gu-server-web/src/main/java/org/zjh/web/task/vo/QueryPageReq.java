package org.zjh.web.task.vo;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import manage.tool.bean.PageRequest;

@ApiModel(value = "审核结果检索列表请求体", description = "审核结果检索列表请求体")
public class QueryPageReq extends PageRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "检索过滤参数")
	private Map<String, Object> params = null;

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

}
