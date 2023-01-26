package manage.tool.utils.minio;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import manage.tool.enums.ResultCodeEnum;
import manage.tool.exception.BaseException;

import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.record.crypto.Biff8EncryptionKey;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件加密工具类 支持 doc, docx, xls xlsx, ppt, pptx, pdf 文件其他不支持
 *
 * @author zhangjianhang
 */

@Slf4j
public class FileEncryUtils {
    /**
     * 加密WORD文档(doc)
     *
     * @param sourceFilePath 源文件
     * @param targetFilePath 目标文件
     * @param password       密码
     * @return
     * @throws Exception
     */
    public static boolean encrypDOC(String sourceFilePath, String targetFilePath, String password)
            throws Exception {
        return encrypDOC(new FileInputStream(sourceFilePath), new FileOutputStream(targetFilePath), password);
    }


    public static boolean encrypDOC(InputStream input, OutputStream out, String password)
            throws IOException {
        POIFSFileSystem fs = null;
        HWPFDocument doc = null;
        try {
            fs = new POIFSFileSystem(input);
            doc = new HWPFDocument(fs);
            Biff8EncryptionKey.setCurrentUserPassword(password);
            doc.write(out);
            return true;
        } catch (Exception e) {
            log.error("DOC文档加密失败:{}", e);
            return false;
        } finally {
            // FileUtils.close(doc);
            doc.close();
            // FileUtils.close(fs);
            fs.close();
            // FileUtils.close(out);
            out.close();
        }

    }

    /**
     * 加密EXCEL文档(xls)
     *
     * @param sourceFilePath 源文件
     * @param targetFilePath 目标文件
     * @param password       密码
     * @return
     * @throws Exception
     */
    public static boolean encrypXLS(String sourceFilePath, String targetFilePath, String password)
            throws Exception {
        return encrypXLS(new FileInputStream(sourceFilePath), new FileOutputStream(targetFilePath),
                password);
    }

    public static boolean encrypXLS(InputStream in, OutputStream out, String password)
            throws IOException {
        POIFSFileSystem fs = null;
        HSSFWorkbook hwb = null;
        try {
            fs = new POIFSFileSystem(in);
            hwb = new HSSFWorkbook();
            Biff8EncryptionKey.setCurrentUserPassword(password);
            hwb.write(out);
            return true;
        } catch (Exception e) {
            log.error("XLS文档加密失败:{}", e);
            return false;
        } finally {
            // FileUtils.close(hwb);
            // FileUtils.close(fs);
            // FileUtils.close(out);
            hwb.close();
            fs.close();
            out.close();
        }

    }

    /**
     * 加密PPT文档(ppt)
     *
     * @param sourceFilePath 源文件
     * @param targetFilePath 目标文件
     * @param password       密码
     * @return
     * @throws Exception
     */
    public static boolean encrypPPT(String sourceFilePath, String targetFilePath, String password)
            throws Exception {
        return encrypPPT(new FileInputStream(sourceFilePath), new FileOutputStream(targetFilePath), password);
    }

    public static boolean encrypPPT(InputStream input, OutputStream out, String password)
            throws IOException {
//    POIFSFileSystem fs = null;
        HSLFSlideShow hss = null;
        try {
//      fs = new POIFSFileSystem(input);
            hss = new HSLFSlideShow(input);
//      hss = new HSLFSlideShow(fs);
            Biff8EncryptionKey.setCurrentUserPassword(password);
            hss.write(out);
            return true;
        } catch (Exception e) {
            log.error("PPT文档加密失败:{}", e);
            return false;
        } finally {
            // FileUtils.close(hss);
            // FileUtils.close(fs);
            // FileUtils.close(out);
            hss.close();
//      fs.close();
            out.close();
        }

    }

    /**
     * 加密PDF文档
     *
     * @param sourceFilePath 源文件
     * @param targetFilePath 目标文件
     * @param password       密码
     * @return
     * @throws Exception
     */
    public static boolean encrypPDF(String sourceFilePath, String targetFilePath, String password)
            throws Exception {
        return encrypPDF(new FileInputStream(sourceFilePath), new FileOutputStream(targetFilePath), password);
    }

    @SneakyThrows
    public static boolean encrypPDF(InputStream input, OutputStream out, String password)
            throws IOException {
        PdfReader reader = null;
        try {
            // 待加密码的文件
            reader = new PdfReader(input);
            //解决bug：PdfReader not opened with owner password
            PdfReader.unethicalreading = true;
            // 加完密码的文件
            PdfStamper stamper = new PdfStamper(reader, out);
            // 设置密码文件打开密码文件编辑密码
            stamper.setEncryption(password.getBytes(), password.getBytes(), 0, false);
            //关闭流
            stamper.close();
            reader.close();
            return true;
        } catch (Exception e) {
            log.error("文件加密失败",e);
            return false;
        }

    }


    /**
     * 加密xml文档(docx,xlsx,pptx)
     *
     * @param sourceFilePath 源文件
     * @param targetFilePath 目标文件
     * @param password       密码
     * @return
     * @throws Exception
     */
    public static boolean encrypDocx(String sourceFilePath, String targetFilePath, String password)
            throws Exception {
        return encrypDocx(new FileInputStream(sourceFilePath), new FileOutputStream(targetFilePath),
                password);
    }

    @SneakyThrows
    public static boolean encrypDocx(InputStream in, OutputStream out, String password)
            throws IOException {
        POIFSFileSystem fs = null;
        try {
            fs = new POIFSFileSystem();
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            Encryptor enc = info.getEncryptor();
            enc.confirmPassword(password);
            try (OPCPackage opc = OPCPackage.open(in); OutputStream os = enc.getDataStream(fs)) {
                opc.save(os);
            }
            fs.writeFilesystem(out);
            return true;
        }  catch (Exception e) {
            log.error("文件加密失败",e);
            return false;
        } finally{
            fs.close();
            out.close();
        }


    }

    /**
     * 解密pdf
     *
     * @param in
     * @param out
     * @param password
     * @return
     * @throws IOException
     */
    public static boolean decrypPDF(InputStream in, OutputStream out, String password)
            throws IOException {
        //    PdfDocument pdfDoc = null;
        PdfReader reader = null;
        try {
            // 待加密码的文件
            reader = new PdfReader(in);
            PdfReader.unethicalreading = true;
            // 加完密码的文件
            PdfStamper stamper = new PdfStamper(reader, out);
            // 设置密码文件打开密码文件编辑密码
            stamper.setEncryption(password.getBytes(), password.getBytes(), 0, false);
            //关闭流
            stamper.close();
            return true;
        } catch (Exception e) {
            log.error("PDF文档加密失败:{}", e);
            return false;
        } finally {
            reader.close();
        }

    }

    /**
     * 通用解密工具，解密 docx,xslx,pptx文件
     *
     * @param in       输入流
     * @param out      输出流
     * @param password 密码
     * @return 返回 0 密码错误 1 成功解密 2异常错误
     * @throws IOException
     */
    public static boolean decrypDXPX(InputStream in, OutputStream out, String password)
            throws IOException {
        POIFSFileSystem fs = null;
        try {
            fs = new POIFSFileSystem(in);
            EncryptionInfo info = new EncryptionInfo(fs);
            Decryptor d = Decryptor.getInstance(info);
            if (!d.verifyPassword(password)) {
                return false;
            }

            InputStream input = d.getDataStream(fs);
            inToOut(input, out);
            return true;
        } catch (Exception e) {
            log.error("XML文档加密失败:{}", e);
            return false;
        } finally {
            fs.close();
            out.close();
        }
    }

    /**
     * input 字节流转output 字节流
     *
     * @param in
     * @param out
     * @throws IOException
     */
    private static void inToOut(InputStream in, OutputStream out) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int len;
        byte[] bytes = new byte[1024];
        while ((len = in.read(bytes)) != -1) {
            byteArrayOutputStream.write(bytes, 0, len);
        }
        in.close();
        byteArrayOutputStream.close();
        byteArrayOutputStream.writeTo(out);
    }

    /**
     * 文件加密 支持 doc, docx, xls xlsx, ppt, pptx, pdf 文件其他不支持
     *
     * @param sourceFilePath 输入源文件地址
     * @param outFilePath    输出源文件地址
     * @param password       密码
     * @return true解密成功 false 解密失败
     */
    public static boolean encryFile(String sourceFilePath, String outFilePath, String password) {
        boolean flag = false;
        try {
            int index = sourceFilePath.lastIndexOf(".");
            if (index > 0) {
                String suffix = sourceFilePath.substring(index + 1);
                switch (suffix) {
//          case "doc":
//            flag = encrypDOC(sourceFilePath, outFilePath, password);
//            break;
//          case "xls":
//            flag = encrypXLS(sourceFilePath, outFilePath, password);
//            break;
//          case "ppt":
//            flag = encrypPPT(sourceFilePath, outFilePath, password);
//            break;
                    case "pdf":
                        flag = encrypPDF(sourceFilePath, outFilePath, password);
                        break;
                    case "docx":
                    case "xlsx":
                    case "pptx":
                        flag = encrypDocx(sourceFilePath, outFilePath, password);
                        break;
                    default:
                        throw new BaseException(ResultCodeEnum.BAD_REQUEST.getCode(), "支持 docx, xlsx, pptx, pdf 其他文件不支持");
                }

            }
        } catch (Exception e) {
            log.error("文档加密失败:{}", e);
        }
        return flag;
    }

    /**
     * 文件加密 支持 doc, docx, xls xlsx, ppt, pptx, pdf 文件其他不支持
     *
     * @param password 密码
     * @return true解密成功 false 解密失败
     */
    @SneakyThrows
    public static boolean encryFile(InputStream input, OutputStream output, String suffix, String password) {
        boolean flag = false;
        switch (suffix) {
//          case "doc":
//            flag = encrypDOC(input, output, password);
//            break;
//          case "xls":
//            flag = encrypXLS(input, output, password);
//            break;
//          case "ppt":
//            flag = encrypPPT(input, output, password);
//            break;
            case "pdf":
                flag = encrypPDF(input, output, password);
                break;
            case "docx":
            case "xlsx":
            case "pptx":
                flag = encrypDocx(input, output, password);
                break;
            default:
                throw new BaseException(ResultCodeEnum.BAD_REQUEST.getCode(), "支持 docx, xlsx, pptx, pdf 其他文件不支持");
        }
        return flag;
    }

    /**
     * 解密多种文件 支持 docx,xslx,pptx,pdf 文件其他暂时不支持
     *
     * @param sourceFilePath 输入文件
     * @param password       密码
     * @return true解密成功 false 解密失败
     */
    public static boolean decrypFile(String sourceFilePath, OutputStream output, String password) {
        boolean flag = false;
        try {
            int index = sourceFilePath.indexOf(".");
            if (index > 0) {
                String suffix = sourceFilePath.substring(index + 1);
                FileInputStream input = new FileInputStream(sourceFilePath);
                switch (suffix) {
                    // 暂时不支持老版本的doc,xls,ppt解密
                    // case "doc":
                    // LOGGER.warn("无法识别的文件类型");
                    // break;
                    // case "xls":
                    // LOGGER.warn("无法识别的文件类型");
                    // break;
                    // case "ppt":
                    // LOGGER.warn("无法识别的文件类型");
                    // break;
                    case "pdf":
                        flag = decrypPDF(input, output, password);
                        break;
                    case "docx":
                    case "xlsx":
                    case "pptx":
                        flag = decrypDXPX(input, output, password);
                        break;

                    default:
                        log.warn("无法识别的文件类型");
                        break;
                }

            }
        } catch (Exception e) {
            log.error("文档加密失败:{}", e);
        }
        return flag;
    }

    /**
     * 解密多种文件 支持 docx,xslx,pptx,pdf 文件其他暂时不支持
     *
     * @param input    输入流
     * @param output   解密后输出流
     * @param suffix   文件后缀名
     * @param password 密码
     * @return true解密成功 false 解密失败
     */
    public static boolean decrypFile(InputStream input, OutputStream output, String suffix,
                                     String password) {
        boolean flag = false;
        try {
            switch (suffix) {
                // 暂时不支持老版本的doc,xls,ppt解密
                // case "doc":
                // LOGGER.warn("无法识别的文件类型");
                // break;
                // case "xls":
                // LOGGER.warn("无法识别的文件类型");
                // break;
                // case "ppt":
                // LOGGER.warn("无法识别的文件类型");
                // break;
                case "pdf":
                    flag = decrypPDF(input, output, password);
                    break;
                case "docx":
                case "xlsx":
                case "pptx":
                    flag = decrypDXPX(input, output, password);
                    break;

                default:
                    log.warn("无法识别的文件类型");
                    break;
            }
        } catch (Exception e) {
            log.error("文档加密失败:{}", e);
        }
        return flag;
    }

    /**
     * 简单测试
     *
     * @param args
     */
    public static void main(String[] args) {
        // 测试文件加密
        boolean flag = true;
//        FileEncryUtils.encryFile("E:/ceshifile/jiamitest.xlsx", "E:/ceshifile/jiami.xlsx", "World");
        // LOGGER.info(String.valueOf(flag));
        try {
            // decrypDocx(new FileInputStream("E:/ceshifile/test4.xlsx"),new
            // FileOutputStream("E:/ceshifile/testjiemi.xlsx"),"2122");
//      decrypPDF(new FileInputStream("E:/ceshifile/jiami.pdf"), new FileOutputStream(
//          "E:/ceshifile/jiemi.pdf"), "World");
            FileEncryUtils.decrypFile(new FileInputStream("E:/ceshifile/jiami.xlsx"), new FileOutputStream(
                    "E:/ceshifile/jiemi.xlsx"), "xlsx", "World");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
