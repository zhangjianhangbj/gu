package manage.tool.enums.auditresult;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhangyanfei
 * @description: 内容审核异常原因
 * @create: 2022/12/22 18:05
 **/
@AllArgsConstructor
@Getter
public enum AuditExceptionEnum {
    /**
     * 4，5不用了
     * 3走机审异常接口，内容审核结果需单独处理
     */
    type_1(1,"场景匹配失败"),
    type_2(2,"注入内容数据异常无法入库"),
    type_3(3,"机审失败"),
    type_4(4,"机审辅助失败"),
    type_5(5,"预上线机审异常")
    ;

    private Integer code;
    private String msg;
}
