package org.zjh.web.gp.service.impl;

import java.util.List;

import org.zjh.web.gp.entity.GpInfo;
import org.zjh.web.gp.mapper.GpInfoMapper;
import org.zjh.web.gp.service.IGpInfoService;
import org.zjh.web.gp.vo.GpInfoReq;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
public class GpInfoServiceImpl extends ServiceImpl<GpInfoMapper, GpInfo> implements IGpInfoService {

	@Autowired
	GpInfoMapper gpInfoMapper;
	
	@Override
	public List<GpInfo> list(GpInfoReq gpInfoReq) {
		// TODO Auto-generated method stub
		return gpInfoMapper.listByParam(gpInfoReq);
	}

}
