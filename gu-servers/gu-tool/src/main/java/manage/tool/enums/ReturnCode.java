package manage.tool.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/07/27 17:12
 **/
@AllArgsConstructor
@Getter
public enum ReturnCode {
    SUCCESS(200, "操作成功！"),
    ERROR(400, "失败");

    private int code;
    private String msg;
}
