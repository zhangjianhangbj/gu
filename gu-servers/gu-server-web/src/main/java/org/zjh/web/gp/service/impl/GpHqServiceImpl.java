package org.zjh.web.gp.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.zjh.common.SchedulingWordConfig;
import org.zjh.util.DateConvertUtil;
import org.zjh.web.gp.entity.GpHq;
import org.zjh.web.gp.entity.GpInfo;
import org.zjh.web.gp.mapper.GpHqMapper;
import org.zjh.web.gp.mapper.GpInfoMapper;
import org.zjh.web.gp.service.IGpHqService;
import org.zjh.web.gp.util.GpHqUtil;
import org.zjh.web.gp.vo.GpInfoReq;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
public class GpHqServiceImpl extends ServiceImpl<GpHqMapper, GpHq> implements IGpHqService {
	
	private static Logger log = LoggerFactory.getLogger(SchedulingWordConfig.class);
	
	@Autowired
	public GpHqMapper gpHqMapper;
	
	@Autowired
	public GpInfoMapper gpInfoMapper;
	
	@Override
	public List<GpHq> listByDate(GpInfoReq gp) {
		// TODO Auto-generated method stub
		return gpHqMapper.listByDate(gp);
	}

	@Override
	public void updateGpHq() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				log.info("update gphq start...");
				GpInfoReq gr = new GpInfoReq();
				List<GpInfo> list = gpInfoMapper.listByParam(gr);
				
				if(list == null){
					return ;
				}
				List<GpHq> listhq = new ArrayList<GpHq>(); 
				String startTime = DateConvertUtil.getYesterdayDate(-30, "yyyyMMdd");
				String endTime = "20230120";//DateConvertUtil.getDateTimeNow("yyyyMMdd");
				int flag= 0;
				int count =0;
				List<String> codes = new ArrayList<String>();
				for (int i = 0; i < list.size(); i++) {
					GpInfo gpinfo = list.get(i);
					//flag 大于20 说名 今天是节假日， 没有股票行情 直接退出
					if(flag >=20){
						log.info("update exit codes:{}", codes);
						break;
					}
					try{
						List<GpHq> hqlist = GpHqUtil.getHqData(gpinfo, startTime, endTime);
						
						//判断股票行情是否最新的。 防止节假日没有行情 更新浪费资源
						if(!endTime.equals(hqlist.get(0).getDate().replaceAll("-", ""))){
							if(flag >= 0){
								flag++;
								codes.add(hqlist.get(0).getCode());
							}
							continue;
						}else{
							flag =-1;
						}
						
						listhq.add(hqlist.get(0));
						if(listhq.size()>=100){
							log.info("update gphq size:{}", count);
							count = count + listhq.size();
							saveBatch(listhq);
							listhq = new ArrayList<GpHq>();
						}
						
					}catch(Exception e){
						log.error("save hq error :"+gpinfo.getName(),e);
					}
				}
				if(listhq.size()>0){
					count = count + listhq.size();
					saveBatch(listhq);
				}
				log.info("update gphq size:{}", count);
			}
		}).start();
	}

}
