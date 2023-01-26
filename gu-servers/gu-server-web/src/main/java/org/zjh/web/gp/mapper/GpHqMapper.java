package org.zjh.web.gp.mapper;

import java.util.List;

import org.zjh.web.gp.entity.GpHq;
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
public interface GpHqMapper extends BaseMapper<GpHq> {

	List<GpHq> listByDate(GpInfoReq gp);
}
