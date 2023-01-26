package org.zjh.web.gp.controller;

import java.util.ArrayList;
import java.util.List;

import manage.tool.bean.Result;

import org.zjh.web.gp.entity.GpHq;
import org.zjh.web.gp.entity.GpInfo;
import org.zjh.web.gp.service.IGpHqService;
import org.zjh.web.gp.service.IGpInfoService;
import org.zjh.web.gp.util.GpHqAvgUtil;
import org.zjh.web.gp.util.GpHqUtil;
import org.zjh.web.gp.util.LoadGpHqData;
import org.zjh.web.gp.vo.GpInfoReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 张建航
 * @since 2023-01-18
 */

@Api(tags = "股票基础数据")
@RestController
@RequestMapping("/gpInfo")
public class GpInfoController {

	private Logger log = LoggerFactory.getLogger(GpInfoController.class);
	
	@Autowired
	public IGpInfoService gpInfoService;

	@Autowired
	public IGpHqService gpHqService;

	@ApiOperation(value = "加载全部行情")
	@PostMapping("/download")
	public Result<String> loadAll(GpInfoReq gpReq) {

		List<GpInfo> list = gpInfoService.list(gpReq);
		for (int i = 0; i < list.size(); i++) {
			GpInfo gpinfo = list.get(i);
			List<GpHq> hqlist = GpHqUtil.getHqData(gpinfo,
					gpReq.getStartTime(), gpReq.getEndTime());
			boolean b = gpHqService.saveBatch(hqlist);
		}
		Result<String> result = Result.success("成功加载:" + list.size() + "个股票行情");
		return result;
	}

	@ApiOperation(value = "加载全部行情")
	@GetMapping("/downloadAll")
	public synchronized Result<String> loadAll(String startTime, String endTime) {
		if(LoadGpHqData.isload == true){
			JSONObject json = new JSONObject();
			json.put("已经加载数", LoadGpHqData.gp_num);
			json.put("加载总数", LoadGpHqData.gp_total);
			json.put("加载失败股", LoadGpHqData.codeList);
			return Result.success(json.toString());
		}
		new Thread(new Runnable() {
			@Override
			public void run() {
				LoadGpHqData.isload = true;
				GpInfoReq gr = new GpInfoReq();
				List<GpInfo> list = gpInfoService.list(gr);
				if(list == null){
					return ;
				}
				LoadGpHqData.gp_num = 0;
				LoadGpHqData.gp_total = list.size();
				for (int i = 0; i < list.size(); i++) {
					if(LoadGpHqData.status ==1){
						return ;
					}
					GpInfo gpinfo = list.get(i);
					try{
						List<GpHq> hqlist = GpHqUtil.getHqData(gpinfo, startTime,
								endTime);
						
						boolean b = gpHqService.saveBatch(hqlist);
						if(b ==false){
							LoadGpHqData.codeList.add(gpinfo.getCode());
						}
					}catch(Exception e){
						LoadGpHqData.codeList.add(gpinfo.getCode());
						log.error("save hq error :"+gpinfo.getName(),e);
					}
					LoadGpHqData.gp_num=i;
				}
			}

		}).start();

		Result<String> result = Result.success("开始加载。。。");
		return result;
	}
	
	@ApiOperation(value = "清楚失败数据")
	@GetMapping("/clear")
	public Result<String> clear() {
		
		LoadGpHqData.clear();
		return Result.success("清除成功");
	}
	
	@ApiOperation(value = "停止加载")
	@GetMapping("/stop")
	public Result<String> stop() {
		
		LoadGpHqData.status=1;
		return Result.success("停止成功");
	}
	
	@ApiOperation(value = "停止加载")
	@GetMapping("/status")
	public Result<JSONObject> status() {
		JSONObject json = new JSONObject();
		json.put("已经加载数", LoadGpHqData.gp_num);
		json.put("加载总数", LoadGpHqData.gp_total);
		json.put("加载失败股", LoadGpHqData.codeList);
		
		return Result.success(json);
	}
}
