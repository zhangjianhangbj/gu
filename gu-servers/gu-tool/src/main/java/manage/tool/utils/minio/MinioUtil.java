package manage.tool.utils.minio;


import com.itextpdf.text.exceptions.BadPasswordException;
import com.itextpdf.text.pdf.PdfReader;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.ApiOperation;
import manage.tool.enums.ResultCodeEnum;
import manage.tool.exception.BaseException;
import io.minio.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import manage.tool.user.PcUserInfo;
import manage.tool.user.UserService;
import manage.tool.utils.water.WaterMarkUtil;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.tika.exception.EncryptedDocumentException;
import org.apache.tika.parser.ParseContext;


/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/09/07 13:47
 **/

@Component
@Slf4j
public class MinioUtil {

    @Autowired
    private MinioClient minioClient;
    @Resource
    private UserService userService;

    /**
     * office docx pptx xlsx 设置密码保护被检测到的类型
     */
    private static final String MIME_X_TIKA_OOXML_PROTECTED = "application/x-tika-ooxml-protected";
    /**
     * wps docx pptx xlsx 设置密码保护被检测到的类型
     */
    private static final String MIME_X_TIKA_MSOFFICE = "application/x-tika-msoffice";

    /**
     * office/wps doc
     */
    private static final String MIME_MSWORD = "application/msword";
    /**
     * office/wps ppt
     */
    private static final String MIME_MSPOWERPOINT = "application/vnd.ms-powerpoint";
    /**
     * office/wps xls
     */
    private static final String MIME_MSEXCEL = "application/vnd.ms-excel";
    /**
     * doc ppt xls
     */
    private static final List<String> MS_OLD_MIMETYPES = Arrays.asList(MIME_MSWORD, MIME_MSEXCEL, MIME_MSPOWERPOINT);

    /**
     * pdf
     */
    private static final String PDF_DETECT = "application/pdf";

    private static final List<String> DEFAULT_SUFFIX = Arrays.asList("pptx","pdf","docx","xlsx");


    /**
     * 附件桶名
     */
    @Value("${minio.bucketName}")
    private String bucketName;
    /**
     * 判断bucket是否存在，不存在则创建
     */
    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建存储桶
     */
    @SneakyThrows
    public void makeBucket(String bucketName) {
        if (!bucketExists(bucketName)) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 删除一个空桶 如果存储桶存在对象不为空时，删除会报错。
     */
    @SneakyThrows
    public void removeBucket(String bucketName) {
        minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
    }

    /**
     * 本地文件上传
     *
     * @return
     */
    @SneakyThrows
    public FileInfo upload(MultipartFile file, Boolean encrypt ) {
        String fileName = getUuidFileName(file.getOriginalFilename());
        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
        if(!DEFAULT_SUFFIX.contains(suffix)){
            throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(),"支持 docx, xlsx, pptx, pdf 其他文件不支持");
        }
        if(file.getSize() <= 0){
            throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(),"文件大小为0KB,不能上传");
        }
        //创建桶
        makeBucket(bucketName);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setPath( bucketName+fileName);
        //将导出数据转为输入流
        if (encrypt) {
            //检查上传的文件是否已经加密
            if(checkPwdProtected(file.getInputStream())){
                throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(),"已加密文件不能再加密！");
            }
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            //生成uid 32位密码
            String password = UUID.randomUUID().toString();

            //文件加密
            FileEncryUtils.encryFile(file.getInputStream(), output, suffix, password);
            if (output.size() <= 0) {
                throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(), "加密失败，上传的文件格式不支持");
            }
            //将输出流转换 输入流
            ByteArrayInputStream byteInput = new ByteArrayInputStream(output.toByteArray());
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                    byteInput,byteInput.available(),-1).contentType(file.getContentType())
                    .build());
            fileInfo.setPassword(password);
        } else {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                    file.getInputStream(),file.getSize(),-1).contentType(file.getContentType())
                    .build());
        }
        return fileInfo;
    }

    /**
     * 上传文件,无需加密,不限制类型
     * @param file
     * @return
     */
    @SneakyThrows
    public FileInfo upload(MultipartFile file ) {
        String fileName = getUuidFileName(file.getOriginalFilename());
//        String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
//        if(!DEFAULT_SUFFIX.contains(suffix)){
//            throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(),"支持 docx, xlsx, pptx, pdf 其他文件不支持");
//        }
        if(file.getSize() <= 0){
            throw new BaseException(ResultCodeEnum.PARAM_ERROR.getCode(),"文件大小为0KB,不能上传");
        }
        //创建桶
        makeBucket(bucketName);
        FileInfo fileInfo = new FileInfo();
        fileInfo.setPath( bucketName+fileName);
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                            file.getInputStream(),file.getSize(),-1).contentType(file.getContentType())
                    .build());
        return fileInfo;
    }
    @ApiOperation("文件下载")
    @SneakyThrows
    public void downloadFile(String filePath,  HttpServletResponse response,String password,Boolean isWater) {
        String fileUrl = getFileName(filePath);
        String bucket = getBucketName(filePath);
        //设置响应头
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        //setContentType 设置发送到客户机的响应的内容类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        //minio下载文件流
        InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucket)
                        .object(fileUrl)
                        .build());
        //备份InputStream流
        ByteArrayOutputStream baos = cloneInputStream(stream);
        InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());
        InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());

        //如果不需要加水印 或者 密码为空且文件受保护，则直接下载，不用加水印
        if(!isWater || (StringUtils.isEmpty(password) &&  checkPwdProtected(stream1))){
            log.info("文件没有加水印直接下载，isWater：{},password:{}",isWater,password);
            BufferedInputStream buffInputStream = new BufferedInputStream(stream2, 1024 * 10);
            //设置文件大小
            byte buf[] = new byte[1024*10];
            int length = 0;
            OutputStream outputStream = response.getOutputStream();
            while ((length = buffInputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            response.setContentLength(length);
            response.flushBuffer();
            //关闭流
            buffInputStream.close();
            stream2.close();
            outputStream.close();
            log.info("文件导出:"+filePath);
        }else {
            PcUserInfo pcUserInfo = userService.getUserInfo();
            String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String content = pcUserInfo.getAccount() + date;
            //需要加水印
            WaterMarkUtil.addWaterMark(fileName, stream2, response.getOutputStream(), content, password);
        }

    }

    private static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检测文档(word,powerpoint,excel)是否设有密码保护
     * @return true 表示设有密码保护；false表示没有密码保护
     * @throws IOException FileNotFoundException 文件不存在；其他
     * @throws SAXException
     */
    public  boolean checkPwdProtected(InputStream stream) throws IOException, SAXException {
        String detect = new Tika().detect(stream);
        if (MIME_X_TIKA_OOXML_PROTECTED.equals(detect) || MIME_X_TIKA_MSOFFICE.equals(detect)) {
            log.info("下载的文件受密码保护");
            return true;
        } else if(PDF_DETECT.equals(detect)){
            try{
                new PdfReader(stream);
            }catch (BadPasswordException e){
                log.info("pdf文件受密码保护");
                return true;
            }
        }else if (MS_OLD_MIMETYPES.contains(detect)) {
            return checkMsmime(stream);
        }
        return false;
    }

    /**
     * 检查 doc ppt xls 是否设有密码保护
     */
    private  boolean checkMsmime(InputStream stream) throws IOException, SAXException {
        Metadata metadata = new Metadata();
        ContentHandler handler = new DefaultHandler();
        ParseContext context = new ParseContext();
        try{
            new AutoDetectParser().parse(stream, handler, metadata, context);
        } catch (TikaException e) {
            // doc 加密保护
            if (e instanceof EncryptedDocumentException) {
                return true;
            }
            // office docx 加密保护
            if (e.getCause() instanceof org.apache.poi.EncryptedDocumentException) {
                return true;
            }
            System.out.println(e.getMessage());
            return true;
        }
        return false;
    }

    @SneakyThrows
    public FileInfo downloadEncryptFile(MultipartFile file,HttpServletResponse response) {
        FileInfo fileInfo = new FileInfo();
        if (file==null) {
            String data = "文件下载失败";
            OutputStream ps = response.getOutputStream();
            ps.write(data.getBytes("UTF-8"));
            return null;
        }
        String fileName = file.getOriginalFilename();
        fileInfo.setOriginalFilename(fileInfo.getOriginalFilename());
        try {
            // 获取文件对象
            InputStream input = file.getInputStream();
            //加密后输出流
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            //生成uid 32位密码
            String password = UUID.randomUUID().toString();
            String suffix = fileName.substring(fileName.lastIndexOf(".")+1);
            fileInfo.setPassword(password);
            //文件加密
            FileEncryUtils.encryFile(input, output,suffix , password);
            if (output.size() <= 0) {
                throw new BaseException(ResultCodeEnum.BAD_REQUEST.getCode(), "加密失败");
            }
            //将加密后输出流转换 输入流
            ByteArrayInputStream byteInput = new ByteArrayInputStream(output.toByteArray());
            byte buf[] = new byte[1024];
            int length = 0;
            response.reset();
            String originalFilename = file.getOriginalFilename();
            response.setHeader("Content-Disposition", "attachment;fileName=\"" + new String((originalFilename).getBytes("utf-8"), "ISO8859-1") + "\"");
            response.setContentType("application/octet-stream");
            response.setCharacterEncoding("UTF-8");
            OutputStream outputStream = response.getOutputStream();
            // 输出文件
            while ((length = byteInput.read(buf)) > 0) {
                outputStream.write(buf, 0, length);
            }
            // 关闭输出流
            outputStream.close();
        } catch (Exception ex) {
            log.error(ex.getMessage());
            response.setHeader("Content-type", "text/html;charset=UTF-8");
            String data = "文件下载失败";
            OutputStream ps = response.getOutputStream();
            ps.write(data.getBytes("UTF-8"));
        }
        return fileInfo;
    }

    /**
     * 获取桶名称
     * @param fileName
     * @return
     */
    public static String getBucketName(String fileName){
        return fileName.substring(0,fileName.indexOf("/"));
    }

    /**
     * 获取文件名称
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName){
        return fileName.substring(fileName.indexOf("/")+1);
    }




//    /**
//     * 预览图片
//     * @param fileName
//     * @return
//     */
//    public String preview(String fileName){
//        // 查看文件地址
//        GetPresignedObjectUrlArgs build = new GetPresignedObjectUrlArgs().builder().bucket(bucketName).object(fileName).method(Method.GET).build();
//        try {
//            String url = minioClient.getPresignedObjectUrl(build);
//            return url;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }


    /**
     * 生成随机文件名
     *
     * @param originalFilename
     * @return
     */
    public String getUuidFileName(String originalFilename) {
        return StrUtil.SLASH + DateUtil.format(new Date(), "yyyy-MM-dd") + StrUtil.SLASH + UUID.randomUUID() + StrUtil.SLASH + originalFilename;
    }

    /**
     * 设置桶的访问策略
     */
    @SneakyThrows
    public void afterPropertiesSet() {
        String policyJson = "{\n" +
                "\t\"Version\": \"2012-10-17\",\n" +
                "\t\"Statement\": [{\n" +
                "\t\t\"Effect\": \"Allow\",\n" +
                "\t\t\"Principal\": {\n" +
                "\t\t\t\"AWS\": [\"*\"]\n" +
                "\t\t},\n" +
                "\t\t\"Action\": [\"s3:GetBucketLocation\", \"s3:ListBucket\", \"s3:ListBucketMultipartUploads\"],\n" +
                "\t\t\"Resource\": [\"arn:aws:s3:::" + bucketName + "\"]\n" +
                "\t}, {\n" +
                "\t\t\"Effect\": \"Allow\",\n" +
                "\t\t\"Principal\": {\n" +
                "\t\t\t\"AWS\": [\"*\"]\n" +
                "\t\t},\n" +
                "\t\t\"Action\": [\"s3:AbortMultipartUpload\", \"s3:DeleteObject\", \"s3:GetObject\", \"s3:ListMultipartUploadParts\", \"s3:PutObject\"],\n" +
                "\t\t\"Resource\": [\"arn:aws:s3:::" + bucketName + "/*\"]\n" +
                "\t}]\n" +
                "}\n";
        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(bucketName).config(policyJson).build());
        log.info("buckets：【{}】,创建[readwrite]策略成功！", bucketName);
    }


}


