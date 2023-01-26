package manage.tool.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 平台通知类型枚举  同biz服务中的枚举类
 * @author
 */
@Getter
@AllArgsConstructor
public enum PlatformNoticeTypeEnum {

  REALTIME(1, "实时审核结果通知"),
  OFFLINE(2, "内容下架获取要求数据通知"),
  TAG_VIOLATE(3,"处置标签通知"),
  REVIEW(4,"回查审核同步通知"),
  OFFLINE_SYNC(5,"内容下架同步通知");

  private Integer code;
  private String desc;


}
