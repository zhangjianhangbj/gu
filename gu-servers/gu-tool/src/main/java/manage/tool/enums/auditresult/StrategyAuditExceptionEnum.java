package manage.tool.enums.auditresult;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhangyanfei
 * @description: 机审策略审核异常枚举
 * @create: 2022/12/29 14:58
 **/
@AllArgsConstructor
@Getter
public enum StrategyAuditExceptionEnum {

    NORMAL(0,"正常"),
    EXCEPTION(1,"异常");
    private Integer code;
    private String mes;

}
