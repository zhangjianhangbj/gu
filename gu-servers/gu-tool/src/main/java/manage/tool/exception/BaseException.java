package manage.tool.exception;


import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class BaseException extends RuntimeException {

    String code;

    public BaseException() {
        super();
    }

    public BaseException(String msg){
        super(msg);
    }

    public BaseException(String code, String msg) {
        super(msg);
        this.code = code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
