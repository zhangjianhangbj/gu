package org.zjh.web.task.mapper;

import java.util.List;
import java.util.Map;

import org.zjh.web.task.entity.TaskReportGpPool;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张建航
 * @since 2023-01-18
 */
public interface TaskReportGpPoolMapper extends BaseMapper<TaskReportGpPool> {

	public List<TaskReportGpPool> listByParams(Map<String,Object> params);
	
	public List<Map<String,Object>> facetListByParams(Map<String,Object> params);
}
