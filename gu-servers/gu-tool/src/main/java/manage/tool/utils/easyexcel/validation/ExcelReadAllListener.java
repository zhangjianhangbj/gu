package manage.tool.utils.easyexcel.validation;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import manage.tool.enums.ResultCodeEnum;
import manage.tool.exception.BaseException;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/08/05 15:40
 **/
@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ExcelReadAllListener<T> extends AnalysisEventListener<T> {

    List<T> list = new ArrayList<T>();

    /**
     * excel反射的类
     */
    private Class<T> clazz;


    public ExcelReadAllListener() {
    }

    public ExcelReadAllListener(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        log.info("excel解析执行 invoke 方法");
        list.add(data);
    }

    @Override
    @SneakyThrows
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        Map<Integer, String> head = getIndexNameMap(clazz);
        //解析到的excel表头和实体配置的进行比对
        Set<Integer> keySet = head.keySet();
        for (Integer key : keySet) {
            if (StringUtils.isEmpty(headMap.get(key))) {
                throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(), "表头与模板不一致，请参照模板填写");
            }
            if (!headMap.get(key).equals(head.get(key))) {
                throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(), "表头与模板不一致，请参照模板填写");
            }
        }
    }

    public Map<Integer, String> getIndexNameMap(Class clazz) throws NoSuchFieldException {

        Map<Integer, String> result = new HashMap<>();
        Field field;
        //获取类中所有的属性
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            field = clazz.getDeclaredField(fields[i].getName());
            field.setAccessible(true);
            //获取类中所有的属性
            //获取根据注解的方式获取ExcelProperty修饰的字段
            ExcelProperty excelProperty = field.getAnnotation(ExcelProperty.class);
            if (excelProperty != null) {
                //索引值
                int index = excelProperty.index();
                //字段值
                String[] values = excelProperty.value();

                StringBuilder value = new StringBuilder();
                for (String v : values) {
                    value.append(v);
                }
                result.put(index, value.toString());
            }
        }

        return result;

    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        log.info("所有数据解析完成！");
    }

    public List<T> getReadData() {
        return list;
    }
}
