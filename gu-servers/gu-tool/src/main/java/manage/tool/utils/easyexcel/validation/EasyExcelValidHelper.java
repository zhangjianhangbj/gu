package manage.tool.utils.easyexcel.validation;

import com.alibaba.excel.annotation.ExcelProperty;

import java.lang.reflect.Field;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/08/09 15:02
 **/
public class EasyExcelValidHelper {
    private EasyExcelValidHelper(){}

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> String validateEntity(T obj) throws NoSuchFieldException {
        StringBuilder result = new StringBuilder();
        Set<ConstraintViolation<T>> set = validator.validate(obj, Default.class);
        if (set != null && !set.isEmpty()) {
            for (ConstraintViolation<T> cv : set) {
                Field declaredField = obj.getClass().getDeclaredField(cv.getPropertyPath().toString());
                ExcelProperty annotation = declaredField.getAnnotation(ExcelProperty.class);
                result.append(annotation.value()[0]+cv.getMessage()).append(";");
            }
        }
        return result.toString();
    }
}
