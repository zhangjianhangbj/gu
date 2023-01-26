package manage.tool.enums.auditresult;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核结果状态的枚举
 */
@Getter
@AllArgsConstructor
public enum AuditResultStatusEnum {

  NOT_COMPLATED(-1, "审核结果异常"),
  COMPLATED(0, "审核结果通过 ");

  private Integer code;
  private String desc;


}
