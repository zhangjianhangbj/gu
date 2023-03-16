package org.zjh.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.zjh.util.GpInfoCache;
import org.zjh.web.gp.entity.GpInfo;
import org.zjh.web.gp.mapper.GpInfoMapper;
import org.zjh.web.gp.vo.GpInfoReq;

/**
 * 初始化基础数据加载到内存
 * @author jianhang.zhang zhangjianhang@richinfo.cn
 *
 */
@Component
@Order(value = 1)
public class InitCommandLineRunner implements CommandLineRunner {

  private static Logger log = LoggerFactory.getLogger(InitCommandLineRunner.class);

  @Autowired
  private GpInfoMapper gpInfoMapper;


  /**
   * 初始化设置 场景， 业务code 初始编码
   */
  @Override
  public void run(String... arg0) throws Exception {

	log.info("init load start...");
    GpInfoReq gr = new GpInfoReq();
	List<GpInfo> list = gpInfoMapper.listByParam(gr);
	GpInfoCache.loadGpInfo(list);
    log.info("init load end...");
  }


}
