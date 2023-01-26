package manage.tool.enums.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2023/01/03 14:14
 **/
@AllArgsConstructor
@Getter
public enum TaskExecuteStatusEnum {
    WAITING(0,"待审核/待质检/检索中"),
    GOING(1,"审核中/质检中/检索完成"),
    FINISH(2,"审核完成/质检完成/下架完成");

    private Integer code;

    private String msg;
}
