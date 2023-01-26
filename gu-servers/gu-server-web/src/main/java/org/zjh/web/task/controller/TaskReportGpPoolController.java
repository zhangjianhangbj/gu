package org.zjh.web.task.controller;

import manage.tool.bean.PageResult;

import org.zjh.web.task.entity.TaskReportGpPool;
import org.zjh.web.task.service.ITaskReportGpPoolService;
import org.zjh.web.task.vo.QueryPageReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/taskReport")
public class TaskReportGpPoolController {

    private Logger log = LoggerFactory.getLogger(TaskReportGpPoolController.class);

    @Autowired
    public ITaskReportGpPoolService taskReportGpPoolService;

    @ApiOperation(value = "统计报表查询")
	@PostMapping("/list")
	public PageResult<TaskReportGpPool> list(@RequestBody QueryPageReq qpr) {
    	PageResult<TaskReportGpPool> pr = taskReportGpPoolService.list(qpr);
    	return pr;
    }
    
}
