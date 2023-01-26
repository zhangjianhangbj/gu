package org.zjh.web.task.controller;

import manage.tool.bean.PageResult;
import manage.tool.bean.Result;

import org.zjh.web.gp.service.IGpHqService;
import org.zjh.web.task.entity.TaskInfo;
import org.zjh.web.task.service.ITaskInfoService;
import org.zjh.web.task.vo.QueryPageReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;

/**
* <p>
    *  前端控制器
    * </p>
*
* @author 张建航
* @since 2023-01-18
*/

@Api(tags = "")
@RestController
@RequestMapping("/task")
public class TaskInfoController {

    private Logger log = LoggerFactory.getLogger(TaskInfoController.class);

    @Autowired
    public ITaskInfoService taskInfoService;
    
    @ApiOperation(value = "全量统计")
	@GetMapping("/statisticAll")
	public Result<String> statisticAll(Integer type, Integer day) {
    	return taskInfoService.statisticAll(type, day);
    }
    
    @ApiOperation(value = "保存任务")
	@GetMapping("/save")
	public Result<Boolean> save(TaskInfo taskInfo) {
    	boolean b = taskInfoService.save(taskInfo);
    	return Result.success(b);
    }
    
//    @ApiOperation(value = "修改任务")
//	@GetMapping("/update")
//	public Result<Boolean> update(TaskInfo taskInfo) {
//    	boolean b = taskInfoService.update(taskInfo);
//    	return Result.success(b);
//    }
    
    @ApiOperation(value = "任务列表查询")
	@PostMapping("/list")
    public Result<PageResult<TaskInfo>> list(QueryPageReq qpr){
    	return taskInfoService.list(qpr);
    }
    
    @ApiOperation(value = "删除任务")
	@GetMapping("/delete")
    public Result<Boolean> delete(Integer id){
    	taskInfoService.removeById(id);
    	return Result.success(true);
    }
}
