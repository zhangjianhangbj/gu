package manage.tool.enums.auditresult;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核状态的枚举
 */
@Getter
@AllArgsConstructor
public enum AuditStatusEnum {

  NOT_COMPLATED(0, "审核未结束"),
  COMPLATED(1, "审核结束 ");

  private Integer code;
  private String desc;


}
