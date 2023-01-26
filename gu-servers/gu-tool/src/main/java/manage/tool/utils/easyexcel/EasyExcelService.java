package manage.tool.utils.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.exception.ExcelAnalysisException;
import com.alibaba.excel.exception.ExcelCommonException;
import com.alibaba.excel.write.metadata.WriteSheet;

import manage.tool.enums.ResultCodeEnum;
import manage.tool.exception.BaseException;
import org.apache.poi.EncryptedDocumentException;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import manage.tool.user.PcUserInfo;
import manage.tool.user.UserService;
import manage.tool.utils.easyexcel.validation.ExcelCheckManager;
import manage.tool.utils.easyexcel.validation.ExcelReadAllListener;
import manage.tool.utils.easyexcel.validation.ExcelReadListener;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import manage.tool.utils.water.WaterMarkHandler;

/**
 * @author: zhangyanfei
 * @description: excel导入导出工具
 * @create: 2022/08/05 13:59
 **/
@Component
@Slf4j
public class EasyExcelService {
    @Resource
    private HttpServletResponse response;
    @Resource
    private UserService userService;

    private static final String SUFFIX_EXCEL = ".xlsx";


    /**
     *  将数据转换为excel文件
     * @param fileName
     * @param dataList
     * @return
     * @param <T>
     */
    @SneakyThrows
    public <T> MultipartFile  toFile(String fileName,List<T> dataList) {
        if (CollectionUtils.isEmpty(dataList)) {
            log.error("导出数据列表为空");
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        EasyExcel.write(byteArrayOutputStream,dataList.get(0).getClass())
                .sheet("sheet1")
                .doWrite(dataList);

        return new MockMultipartFile(fileName, fileName, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", byteArrayOutputStream.toByteArray());
    }

    @SneakyThrows
    public <T> String exportEncrypt(String fileName, List<T> dataList) {
        PcUserInfo pcUserInfo = userService.getUserInfo();
        if (CollectionUtils.isEmpty(dataList)) {
            log.error("导出数据列表为空");
        }
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
//        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition",
                "attachment;filename=" +URLEncoder.encode(fileName,"UTF-8"));
        //生成uid 32位密码
        String password = UUID.randomUUID().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String content = pcUserInfo.getAccount()+date;
        ExcelWriter writer = EasyExcel.write(response.getOutputStream(), dataList.get(0).getClass()).password(password).inMemory(true).build();
        WriteSheet sheet = EasyExcel
                .writerSheet(0,"sheet1")
                .registerWriteHandler(new WaterMarkHandler(content))
                .build();
        writer.write(dataList,sheet);
        writer.finish();
        return password;
    }

    /**
     * 导出多个sheet页的excel
     *
     * @param fileName   文件名
     * @param sheetNames sheet页名称列表
     * @param dataLists  数据列表
     */
    @SneakyThrows
    @SuppressWarnings("rawtypes")
    public String exportMultipleSheetEncrypt(String fileName, List<String> sheetNames, List<List> dataLists, List<Class> classes) {
        //设置response 防止中文乱码
        //setResponse(fileName);
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
//        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition",
                "attachment;filename=" +URLEncoder.encode(fileName,"UTF-8"));
        ExcelWriter excelWriter = null;
        //生成uid 32位密码
        String password = UUID.randomUUID().toString();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        PcUserInfo pcUserInfo = userService.getUserInfo();
        String content = pcUserInfo.getAccount()+date;
        try {
            excelWriter = EasyExcelFactory.write(response.getOutputStream()).password(password).inMemory(true).build();
            for (int i = 0; i < sheetNames.size(); i++) {
                WriteSheet writeSheet = EasyExcelFactory.writerSheet(i, sheetNames.get(i)).head(classes.get(i))
                        .registerWriteHandler(new WaterMarkHandler(content)).build();
                excelWriter.write(dataLists.get(i), writeSheet);
            }
        } finally {
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
        return password;
    }

    @SneakyThrows
    private void setResponse(String fileName){
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
    }

    /**
     * 读取 Excel(多个 sheet) 转换为输入类 支持validation 注解验证
     *
     * @param inputStream 输入流
     * @param rowModel    实体类映射
     * @return Excel 数据 list
     */
    @SneakyThrows
    public <T> ExcelReadListener<T> readExcel(InputStream inputStream, ExcelCheckManager<T> checkManager,
                                              Class<T> rowModel, Integer libraryId) {
        ExcelReadListener<T> excelReadListener = new ExcelReadListener<>(checkManager,rowModel,libraryId);
        EasyExcel.read(inputStream, rowModel, excelReadListener).sheet().doRead();
        return excelReadListener;
    }

    @SneakyThrows
    public <T> List<T> readExcel(InputStream inputStream, Class<T> rowModel) {
        ExcelReadAllListener<T> excelReadListener = new ExcelReadAllListener<>(rowModel);
        try {
            EasyExcel.read(inputStream, rowModel, excelReadListener).sheet().doRead();
        }catch (EncryptedDocumentException e){
            throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(), "请勿上传加密的文件");
        }catch (ExcelAnalysisException e){
            throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(), "请勿上传损坏的文件");
        }catch (ExcelCommonException e){
            throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(), "请勿上传损坏的文件");
        }catch (Exception e){
            throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(), "文件读取失败,请检查文件是否正常");
        }
        return excelReadListener.getReadData();
    }
    /**
     * 文件上传
     */


}
