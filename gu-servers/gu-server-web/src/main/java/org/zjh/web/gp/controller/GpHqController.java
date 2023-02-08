package org.zjh.web.gp.controller;

import manage.tool.bean.Result;

import org.zjh.web.gp.service.IGpHqService;
import org.zjh.web.gp.util.LoadGpHqData;

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

@Api(tags = "股票行情")
@RestController
@RequestMapping("/gphq")
public class GpHqController {

    private Logger log = LoggerFactory.getLogger(GpHqController.class);

    @Autowired
    public IGpHqService gpHqService;

    @ApiOperation(value = "停止加载")
	@GetMapping("/updatehq")
	public Result<Boolean> updatehq(@RequestParam String date) {
		log.info("GpHqController update gphq...");
		gpHqService.updateGpHq(date);
		log.info("GpHqController update gphq end");
		
		return Result.success(true);
	}
    
}
