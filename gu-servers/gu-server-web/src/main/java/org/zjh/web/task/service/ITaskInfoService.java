package org.zjh.web.task.service;

import manage.tool.bean.PageResult;
import manage.tool.bean.Result;

import org.zjh.web.task.entity.TaskInfo;
import org.zjh.web.task.vo.QueryPageReq;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张建航
 * @since 2023-01-18
 */
public interface ITaskInfoService extends IService<TaskInfo> {
	public Result<String> statisticAll(Integer type, Integer day);
	
	public Result<PageResult<TaskInfo>> list(QueryPageReq qpr);
	
}
