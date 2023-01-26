package org.zjh.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import manage.tool.bean.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.zjh.util.DateConvertUtil;
import org.zjh.web.gp.entity.GpHq;
import org.zjh.web.gp.entity.GpInfo;
import org.zjh.web.gp.service.IGpHqService;
import org.zjh.web.gp.service.IGpInfoService;
import org.zjh.web.gp.util.GpHqUtil;
import org.zjh.web.gp.util.LoadGpHqData;
import org.zjh.web.gp.vo.GpInfoReq;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author jianhang.zhang zhangjianhang@richinfo.cn
 * 
 * version <br>
 * Copyright (C) 2014-2015 richinfo <br>
 *           This program is protected by copyright laws. <br>
 *           Program Name:中国移动商城搜索引擎.
 * <br>
 *
 * Description: 定时任务 定时更新加载提示词库
 * 
 *
 * CreateTime: 2018年4月17日  下午5:32:34
 *
 * Change History:
 *
 *        Date             CR Number              Name              Description of change
 *
 *
 */
@Configuration
@EnableScheduling
public class SchedulingWordConfig {
    private static Logger log = LoggerFactory.getLogger(SchedulingWordConfig.class);
    @Autowired
    IGpInfoService gpInfoService;
    
    @Autowired
    IGpHqService gpHqService; 
    
    @Scheduled(cron = "${scheduling.corn.update.hq:0 0 18 * * ?}") // 每10分钟执行一次 cron = "0 0/1 * * * ?"
    public void updateHQ() {
    	log.info("update.hq init...");
		gpHqService.updateGpHq();
		
    }
}
