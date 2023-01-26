package org.zjh.web.task.mapper;

import java.util.List;
import java.util.Map;

import org.zjh.web.task.entity.TaskInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 张建航
 * @since 2023-01-18
 */
public interface TaskInfoMapper extends BaseMapper<TaskInfo> {
	public List<TaskInfo> list(Map<String,Object> params);
}
