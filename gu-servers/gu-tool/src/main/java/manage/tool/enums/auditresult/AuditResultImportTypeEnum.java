package manage.tool.enums.auditresult;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhangyanfei
 * @description: 审核结果importType枚举
 * @create: 2022/12/29 17:00
 **/
@AllArgsConstructor
@Getter
public enum AuditResultImportTypeEnum {
    REVIEW(1,"回查审核、实时质检"),
    REALTIME(2,"实时审核");

    private Integer code;
    private String msg;
}
