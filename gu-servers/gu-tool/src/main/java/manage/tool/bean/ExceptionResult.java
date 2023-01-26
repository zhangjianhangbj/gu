package manage.tool.bean;

import lombok.Data;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/07/16 16:40
 **/
@Data
public class ExceptionResult {
	private String code;
	private String message;
	private String stackMsg;

	public ExceptionResult() {
	}

	public static final ExceptionResult error(String errorCode, String message,
			String stackMsg) {
		ExceptionResult exceptionResult = new ExceptionResult();
		exceptionResult.setCode(errorCode);
		exceptionResult.setMessage(message);
		exceptionResult.setStackMsg(stackMsg);
		return exceptionResult;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStackMsg() {
		return stackMsg;
	}

	public void setStackMsg(String stackMsg) {
		this.stackMsg = stackMsg;
	}

}
