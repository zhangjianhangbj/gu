package org.zjh.web.task.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import manage.tool.bean.PageResult;
import manage.tool.bean.Result;

import org.zjh.report.trend.StatisticUpTrend;
import org.zjh.report.util.StatisticUtil;
import org.zjh.util.DateConvertUtil;
import org.zjh.util.GpInfoCache;
import org.zjh.web.gp.entity.GpHq;
import org.zjh.web.gp.service.IGpHqService;
import org.zjh.web.gp.vo.GpInfoReq;
import org.zjh.web.task.entity.TaskInfo;
import org.zjh.web.task.entity.TaskReportGpPool;
import org.zjh.web.task.mapper.TaskInfoMapper;
import org.zjh.web.task.service.ITaskInfoService;
import org.zjh.web.task.service.ITaskReportGpPoolService;
import org.zjh.web.task.vo.QueryPageReq;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TaskInfoServiceImpl extends ServiceImpl<TaskInfoMapper, TaskInfo> implements ITaskInfoService {
	private Logger log = LoggerFactory.getLogger(TaskInfoServiceImpl.class);
	@Autowired
	public IGpHqService gpHqService;
	
	@Autowired
	public ITaskReportGpPoolService taskReportGpPoolService;
	
	@Autowired
	public TaskInfoMapper taskInfoMapper;

	@Override
	public Result<String> statisticAll(Integer type, Integer day) {
		// TODO Auto-generated method stub
		GpInfoReq gr = new GpInfoReq();
		String startTime = DateConvertUtil.getYesterdayDate(day, "yyyy-MM-dd");
		gr.setStartTime(startTime);
		List<GpHq> list = gpHqService.listByDate(gr);
		if(list ==null || list.size()==0){
			return Result.success("没有结果");
		}
		String code = list.get(0).getCode();
		List<GpHq> hqlist = new ArrayList<GpHq>();
		List<TaskReportGpPool> trgpList = new ArrayList<TaskReportGpPool>();
		for (int i = 0; i < list.size(); i++) {
			if(!code.equals(list.get(i).getCode())){
				Float f = StatisticUpTrend.statistic(hqlist);
				TaskReportGpPool trgp = new TaskReportGpPool();
				trgp.setCategory(GpInfoCache.getGpInfo(code).getCategory());
				trgp.setExchange(GpInfoCache.getGpInfo(code).getExchange());
				trgp.setName(GpInfoCache.getGpInfo(code).getName());
				trgp.setCode(code);
				trgp.setScore(f);
				trgp.setCreateTime(new Date());
				trgpList.add(trgp);
				hqlist.clear();
				code = list.get(i).getCode();
			}
			hqlist.add(list.get(i));
		}
		
		saveReportGpPool(trgpList);
		return Result.success("统计结果:"+trgpList.size()+"条");
	}

	private void saveReportGpPool(List<TaskReportGpPool> trgpList) {
		List<TaskReportGpPool> ls = new ArrayList<TaskReportGpPool>();
		for (int i = 0; i < trgpList.size(); i++) {
			ls.add(trgpList.get(i));
			if(ls.size()>=500){
				log.info("add TaskReportGpPool {}/{}",i,trgpList.size());
				boolean b = taskReportGpPoolService.saveBatch(trgpList);
				ls = new ArrayList<TaskReportGpPool>();
			}
		}
		boolean b = taskReportGpPoolService.saveBatch(ls);
		log.info("add TaskReportGpPool end {}",trgpList.size());
	}

	@Override
	public Result<PageResult<TaskInfo>> list(QueryPageReq qpr) {
		// TODO Auto-generated method stub
		
		PageHelper.startPage(qpr.getPage(), qpr.getPageSize());
	
	    //查询样本信息
	    List<TaskInfo> list = taskInfoMapper.list(qpr.getParams());
	    if (list == null || list.size() == 0) {
	        return new Result<PageResult<TaskInfo>>();
	    }
		
		PageResult<TaskInfo> pr = new PageResult<TaskInfo>(qpr,list.size(),list);
		return Result.success(pr);
	}
	
}
