package manage.tool.handle;

import manage.tool.bean.ExceptionResult;
import manage.tool.bean.Result;
import manage.tool.enums.ResultCodeEnum;
import manage.tool.exception.BaseException;
import manage.tool.utils.ExceptionUtils;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/07/16 16:28
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {
	private Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * 全局异常拦截，可针对业务需要进行修改错误级别
   * @param exception
   * @return
   */
  @ExceptionHandler({Exception.class})
  public ResponseEntity handleException(Exception exception) {
    String errorMessage = exception.toString();
    String stackTraceStr = ExceptionUtils.getStackTraceString(exception);
    log.error("Exception", exception);
    return ResponseEntity.status(Integer.parseInt(ResultCodeEnum.ERROR.getCode())).body(
        ExceptionResult.error(ResultCodeEnum.ERROR.getCode(), ResultCodeEnum.ERROR.getMsg(), stackTraceStr));
  }

  @ExceptionHandler({BaseException.class})
  public ResponseEntity handleBaseException(BaseException baseException) {
    log.warn("BaseException", baseException);
    return ResponseEntity.ok(Result.error(baseException.getCode()==null?ResultCodeEnum.BAD_REQUEST.getCode():baseException.getCode(),
            baseException.getMessage()));
  }

  @ExceptionHandler({MaxUploadSizeExceededException.class})
  public ResponseEntity maxUploadSizeExceededException(MaxUploadSizeExceededException e){
    log.warn("MaxUploadSizeExceededException", e);
    return ResponseEntity.ok(Result.error(ResultCodeEnum.PARAM_ERROR.getCode(),"附件大小不可超过5M!"));
  }

  /**
   * 参数校验不正确
   * @param e
   * @return
   */
  @ExceptionHandler({MethodArgumentNotValidException.class})
  public ResponseEntity validExceptionHandler(MethodArgumentNotValidException e) {
    log.warn("GlobalExceptionHandler.MethodArgumentNotValidException", e);
    FieldError fieldError = e.getBindingResult().getFieldError();
    return ResponseEntity.ok(Result.error(ResultCodeEnum.PARAM_ERROR.getCode(), fieldError.getDefaultMessage()));
  }
}
