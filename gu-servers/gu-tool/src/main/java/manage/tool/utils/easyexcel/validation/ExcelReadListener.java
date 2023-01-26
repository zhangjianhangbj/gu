package manage.tool.utils.easyexcel.validation;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import manage.tool.utils.easyexcel.pojo.InValidDataResp;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/08/05 15:40
 **/
@Data
@Slf4j
@EqualsAndHashCode(callSuper=false)
@Accessors(chain = true)
public class ExcelReadListener<T> extends AnalysisEventListener<T> {

    private static final int BATCH_COUNT = 1000;

    List<T> list = new ArrayList<T>();

    /**
     * excel反射的类
     */
    private Class<T> clazz;

    private Integer libraryId;

    /**
     * 失败结果集
     */
    private List<InValidDataResp> errList = new ArrayList<>();

    /**
     * 检查数据的service
     */
    private ExcelCheckManager<T> excelCheckManager;

    public ExcelReadListener(ExcelCheckManager<T> excelCheckManager,Class<T> clazz){
        this.excelCheckManager = excelCheckManager;
        this.clazz = clazz;
    }

    public ExcelReadListener(ExcelCheckManager<T> excelCheckManager,Class<T> clazz,Integer libraryId){
        this.excelCheckManager = excelCheckManager;
        this.clazz = clazz;
        this.libraryId = libraryId;
    }


    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(T data, AnalysisContext context) {
        String errMsg;
        try {
            //根据excel数据实体中的javax.validation + 正则表达式来校验excel数据
            errMsg = EasyExcelValidHelper.validateEntity(data);
        } catch (Exception e) {
            errMsg = "解析数据出错";
            e.printStackTrace();
        }
        if (!StringUtils.isEmpty(errMsg)){
            InValidDataResp excelCheckErrDto = new InValidDataResp(context.readRowHolder().getRowIndex(), errMsg);
            errList.add(excelCheckErrDto);
        }else{
            list.add(data);
        }
        //每1000条处理一次
        if (list.size() > BATCH_COUNT){
            //校验
            errList.addAll(excelCheckManager.checkImportExcel(list,context,libraryId));
            list.clear();
        }
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

}
