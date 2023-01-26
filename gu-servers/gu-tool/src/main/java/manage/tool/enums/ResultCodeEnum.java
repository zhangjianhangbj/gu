package manage.tool.enums;

import lombok.Getter;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/07/16 16:35
 **/
@Getter
public enum ResultCodeEnum {

    /**
     *
     */
    SUCCESS("0000", "操作成功"),
    LOGIN_TIMEOUT("0001", "登录信息已过期,请重新登录"),
    NOT_PERMISSION("0002", "没有操作权限,请联系运维添加权限"),
    ERROR("1000","内部服务错误"),
    PARAM_ERROR("1001", "入参校验不合法"),
    BAD_REQUEST("1002", "业务校验不通过，toast提示语"),
    NOT_FOUND_USER("1003", "登录用户不存在"),
    NOT_MENU_PERMISSION("1004", "用户没有任何菜单按钮权限"),
    PASSWORD_ERROR("1005", "用户名或密码错误"),
    SMS_ERROR("1006", "短信验证码错误或者短信验证码失效"),
    AUDIT_ERROR("1007", "审核异常未匹配到场景"),
    ;

    private String code;
    private String msg;

    ResultCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
    
}

