package manage.tool.enums.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 任务类型枚举
 */
@Getter
@AllArgsConstructor
public enum TaskTypeEnum {

  REVIEW(1, "回查审核"),
  OFFLINE(2, "内容下架"),
  OFFLINE_CHECK(3,"机审离线质检"),
  OFFLINE_CHECK_PERSON(4,"人审离线质检");

  private Integer code;
  private String desc;


}
