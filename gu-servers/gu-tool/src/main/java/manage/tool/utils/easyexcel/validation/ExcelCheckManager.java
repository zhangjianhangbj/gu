package manage.tool.utils.easyexcel.validation;


import com.alibaba.excel.context.AnalysisContext;

import java.util.List;

import manage.tool.utils.easyexcel.pojo.InValidDataResp;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/08/09 15:07
 **/
public interface ExcelCheckManager<T> {
    /**
     * @description: 校验方法
     */
    List<InValidDataResp> checkImportExcel(List<T> list, AnalysisContext context, Integer libraryId);
}
