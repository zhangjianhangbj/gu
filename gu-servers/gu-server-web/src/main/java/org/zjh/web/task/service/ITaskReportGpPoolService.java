package org.zjh.web.task.service;

import java.util.List;

import manage.tool.bean.PageResult;

import org.zjh.web.task.entity.TaskReportGpPool;
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
public interface ITaskReportGpPoolService extends IService<TaskReportGpPool> {
	
	public PageResult<TaskReportGpPool> list(QueryPageReq qpr);
}
