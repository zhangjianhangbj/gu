package org.zjh.web.gp.mapper;

import java.util.List;

import org.zjh.web.gp.entity.GpInfo;
import org.zjh.web.gp.vo.GpInfoReq;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张建航
 * @since 2023-01-18
 */
public interface GpInfoMapper extends BaseMapper<GpInfo> {

	public List<GpInfo> listByParam(GpInfoReq gpInfoReq);
}
