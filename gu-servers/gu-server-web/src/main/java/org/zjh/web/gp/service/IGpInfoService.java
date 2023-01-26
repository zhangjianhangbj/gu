package org.zjh.web.gp.service;

import java.util.List;

import org.zjh.web.gp.entity.GpInfo;
import org.zjh.web.gp.vo.GpInfoReq;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 张建航
 * @since 2023-01-18
 */
public interface IGpInfoService extends IService<GpInfo> {

	public List<GpInfo> list(GpInfoReq gpInfoReq);
	
}
