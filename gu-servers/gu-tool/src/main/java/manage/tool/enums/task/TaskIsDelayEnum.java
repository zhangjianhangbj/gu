package manage.tool.enums.task;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/12/15 17:35
 **/
@AllArgsConstructor
@Getter
public enum TaskIsDelayEnum {

    NO_DELAY(1,"无延迟执行"),
    DELAY(2,"延迟执行"),
    FINISH(3,"执行完成");

    private Integer code;
    private String msg;

}
