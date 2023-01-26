package manage.tool.enums.auditresult;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核结果类型 resultType的枚举
 */
@Getter
@AllArgsConstructor
public enum AuditResultTypeEnum {

  REBORT(1, "机审结果"),
  CHANNEL(2, "人审结果"),
  REAL_TIME(3,"实时质检结果");

  private Integer code;
  private String desc;


}
