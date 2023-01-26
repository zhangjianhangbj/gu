package manage.tool.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import manage.tool.enums.ResultCodeEnum;
import manage.tool.enums.ReturnCode;
import lombok.Data;

/**
 * @description:
 * @author: liyh
 * @time: 2020/12/21 10:47
 */
@Data
@ApiModel("全局响应数据")
public class Result<T> {

	public Result(){
		this.code = ResultCodeEnum.SUCCESS.getCode();
		this.message= ResultCodeEnum.SUCCESS.getMsg();
	}

	public Result(String code, String mess){
		this.code = code;
		this.message= mess;
	}

	public Result(String code, String mess, T data){
		this.code = code;
		this.message= mess;
		this.data= data;
	}

	@ApiModelProperty("响应码")
    private String code;

	@ApiModelProperty("响应信息")
    private String message;

	@ApiModelProperty("响应数据")
    private T data;

	public static final <T> Result<T> success(T t) {
		Result result = new Result();
		result.setData(t);
		return result;
	}
	
	public static final <T> Result<T> error(String msg) {
      Result result = new Result(ResultCodeEnum.ERROR.getCode(),msg);
      return result;
  }

	public static final <T> Result<T> error(String code, String message) {
		Result result = new Result();
		result.setCode(code);
		result.setMessage(message);
		return result;
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
