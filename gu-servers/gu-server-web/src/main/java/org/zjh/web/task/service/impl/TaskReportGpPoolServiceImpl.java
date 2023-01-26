package org.zjh.web.task.service.impl;

import java.util.List;
import java.util.Map;

import manage.tool.bean.PageResult;

import org.zjh.web.task.entity.TaskReportGpPool;
import org.zjh.web.task.mapper.TaskReportGpPoolMapper;
import org.zjh.web.task.service.ITaskReportGpPoolService;
import org.zjh.web.task.vo.QueryPageReq;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 张建航
 * @since 2023-01-18
 */
@Service
public class TaskReportGpPoolServiceImpl extends ServiceImpl<TaskReportGpPoolMapper, TaskReportGpPool> implements ITaskReportGpPoolService {

	@Autowired
	private TaskReportGpPoolMapper reportGpPoolMapper;
	
	@Override
	public PageResult<TaskReportGpPool> list(QueryPageReq qpr) {
		// TODO Auto-generated method stub
		
		PageHelper.startPage(qpr.getPage(), qpr.getPageSize());

        //查询样本信息
        List<TaskReportGpPool> list = reportGpPoolMapper.listByParams(qpr.getParams());
        if (list == null || list.size() == 0) {
            return new PageResult<TaskReportGpPool>();
        }
		List<Map<String,Object>> facetList = reportGpPoolMapper.facetListByParams(qpr.getParams());
		PageResult<TaskReportGpPool> pr = new PageResult<TaskReportGpPool>(qpr,list.size(),list);
		pr.setFacetList(facetList);
		
		return pr;
	}

}
