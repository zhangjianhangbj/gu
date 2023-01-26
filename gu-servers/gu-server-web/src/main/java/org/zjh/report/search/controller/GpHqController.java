package org.zjh.report.search.controller;

import java.util.List;

import manage.tool.bean.Result;

import org.zjh.web.gp.entity.GpHq;
import org.zjh.web.gp.entity.GpInfo;
import org.zjh.web.gp.service.IGpHqService;
import org.zjh.web.gp.util.GpHqUtil;
import org.zjh.web.gp.vo.GpInfoReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

/**
* <p>
    *  前端控制器
    * </p>
*
* @author 张建航
* @since 2023-01-18
*/

@Api(tags = "股票行情")
@RestController
@RequestMapping("/gp")
public class GpHqController {

    private Logger log = LoggerFactory.getLogger(GpHqController.class);

    @Autowired
    public IGpHqService gpHqService;

    
    
}
