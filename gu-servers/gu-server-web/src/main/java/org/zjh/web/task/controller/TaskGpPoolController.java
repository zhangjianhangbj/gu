package org.zjh.web.task.controller;

import org.zjh.web.task.service.ITaskGpPoolService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/task/taskGpPool")
public class TaskGpPoolController {

    private Logger log = LoggerFactory.getLogger(TaskGpPoolController.class);

    @Autowired
    public ITaskGpPoolService taskGpPoolService;


}
