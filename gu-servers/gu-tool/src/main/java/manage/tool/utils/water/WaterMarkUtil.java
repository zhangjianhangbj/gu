package manage.tool.utils.water;

import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.microsoft.schemas.office.office.CTLock;
import com.microsoft.schemas.vml.CTGroup;
import com.microsoft.schemas.vml.CTShape;
import com.microsoft.schemas.vml.CTShapetype;
import com.microsoft.schemas.vml.CTTextPath;
import com.microsoft.schemas.vml.STExt;
import com.microsoft.schemas.vml.STTrueFalse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ooxml.POIXMLDocumentPart;
import org.apache.poi.openxml4j.opc.PackagePartName;
import org.apache.poi.openxml4j.opc.PackageRelationship;
import org.apache.poi.openxml4j.opc.TargetMode;
import org.apache.poi.poifs.crypt.Decryptor;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFRelation;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPicture;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRPr;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.stream.Stream;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import manage.tool.enums.ResultCodeEnum;
import manage.tool.exception.BaseException;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/10/18 20:47
 **/
@Slf4j
public class WaterMarkUtil {

    private static final String fontName = "????????????";
    private static final String fontSize = "0.5pt";
    private static final String fontColor = "#676767";
    private static final Integer widthPerWord = 5;
    private static final String styleRotation = "-25";
    private static Integer styleTop = 0;


    /**
     * ??????????????? ?????? doc, docx, xls xlsx, ppt, pptx, pdf ?????????????????????
     *
     * @return
     */
    @SneakyThrows
    public static void addWaterMark(String fileName, InputStream inputStream, OutputStream outputStream, String waterMarkContent, String password) {
        int index = fileName.lastIndexOf(".");
        if (index > 0) {
            String suffix = fileName.substring(index + 1);
            switch (suffix) {
                case "pdf":
                    addPDFWaterMark(inputStream, outputStream,password, waterMarkContent);
                    break;
                case "docx":
                    addWordWaterMark(inputStream,outputStream,password,waterMarkContent);
                    break;
                case "xlsx":
                    addExcelWaterMark(inputStream, outputStream, waterMarkContent,password);
                    break;
                case "pptx":
                    addPPTWaterMark(inputStream, outputStream, password,waterMarkContent);
                    break;
                default:
                    throw new BaseException(ResultCodeEnum.BAD_REQUEST.getCode(),"?????? docx, xlsx, pptx, pdf ?????????????????????");
            }

        }
    }

    @SneakyThrows
    public static void addPPTWaterMark(InputStream inputStream, OutputStream outputStream, String password,String waterMarkContent ){
        XMLSlideShow slideShow ;
        if(StringUtils.isNotBlank(password)){
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
            EncryptionInfo encInfo = new EncryptionInfo(poifsFileSystem);
            Decryptor decryptor = Decryptor.getInstance(encInfo);
            decryptor.verifyPassword(password);
            slideShow = new XMLSlideShow(decryptor.getDataStream(poifsFileSystem));
        }else{
            slideShow = new XMLSlideShow(inputStream);
        }

        ByteArrayOutputStream os = WaterMarkHandler.createWaterMark(waterMarkContent);
        PictureData pictureData1 = slideShow.addPicture(os.toByteArray(), PictureData.PictureType.PNG);
        for (XSLFSlide slide : slideShow.getSlides()) {
            //????????????2???
            XSLFPictureShape pictureShape = slide.createPicture(pictureData1);
            pictureShape.setAnchor(new java.awt.Rectangle( 50 ,  25, 300, 200));

            XSLFPictureShape pictureShape2 = slide.createPicture(pictureData1);
            pictureShape2.setAnchor(new java.awt.Rectangle( 150 ,  100, 300, 200));

            XSLFPictureShape pictureShape3 = slide.createPicture(pictureData1);
            pictureShape3.setAnchor(new java.awt.Rectangle( 250 ,  200, 300, 200));
        }
        if(StringUtils.isNotBlank(password)){
            // ????????????
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            Encryptor enc = info.getEncryptor();
            enc.confirmPassword(password);
            // ??????
            POIFSFileSystem fs = new POIFSFileSystem();
            OutputStream os1 = enc.getDataStream(fs);
            slideShow.write(os1);
            os1.flush();
            os1.close();
            fs.writeFilesystem(outputStream);
        }else{
            slideShow.write(outputStream);
        }
    }

    /**
     * pdf ????????????
     *
     * @param inputStream      ????????????
     * @param outputStream     ???????????????
     * @param waterMarkContent ????????????
     * @throws Exception
     */
    public static void addPDFWaterMark(InputStream inputStream, OutputStream outputStream,String password, String... waterMarkContent) throws Exception {
        PdfReader reader;
        if(StringUtils.isNotBlank(password)){
            reader = new PdfReader(inputStream,password.getBytes());
        }else{
            reader = new PdfReader(inputStream);
        }
        // ???PDF??????
        //??????bug???PdfReader not opened with owner password
        PdfReader.unethicalreading = true;
        // ?????????PDF????????????
        PdfStamper stamper = new PdfStamper(reader, outputStream);
        try {
            // ?????? ????????? itext-asian JAR???
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

            PdfGState gs = new PdfGState();
            // ???????????????
            gs.setFillOpacity(0.3f);
            gs.setStrokeOpacity(0.4f);
            int totalPage = reader.getNumberOfPages() + 1;
            for (int i = 1; i < totalPage; i++) {
                // ????????????
                float x = reader.getPageSize(i).getWidth();
                float y = reader.getPageSize(i).getHeight();
                PdfContentByte content = stamper.getOverContent(i);
                content.beginText();
                // ?????????????????????
                content.setGState(gs);
                //????????????
//                content.setColorFill(BaseColor.DARK_GRAY);
                // ?????????????????????
                content.setFontAndSize(baseFont, 15);
                // ????????????,????????????
                for (int j = 0; j < 3; j++) {
                    for (int k = 0; k < 3; k++) {
                        int ji = 0; // ???????????????????????????
                        for (String water : waterMarkContent) {
                            //showTextAligned ??????????????????????????????????????????????????????????????????X????????????Y???????????????????????????
                            content.showTextAligned(Element.ALIGN_CENTER, water, x / 3 * j + 90, y / 3 * k - ji + 45, 25);
                            ji = 20;
                        }
                    }
                }
                content.endText();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // ??????
            if (stamper != null) {
                stamper.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
    }

    /**
     * excel????????????
     *
     * @param inputStream   ???????????????
     * @param outputStream  ???????????????
     * @param waterMarkContent ????????????
     */
    public static void addExcelWaterMark(InputStream inputStream, OutputStream outputStream, String waterMarkContent,String password) throws Exception {
        XSSFWorkbook workbook;
        //???????????????????????????????????????????????????????????????
        if(StringUtils.isNotBlank(password)){
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
            EncryptionInfo encInfo = new EncryptionInfo(poifsFileSystem);
            Decryptor decryptor = Decryptor.getInstance(encInfo);
            decryptor.verifyPassword(password);
            workbook = new XSSFWorkbook(decryptor.getDataStream(poifsFileSystem));
        }else{
            workbook = new XSSFWorkbook(inputStream);
        }
        ByteArrayOutputStream os = WaterMarkHandler.createWaterMark(waterMarkContent);

        int pictureIdx = workbook.addPicture(os.toByteArray(), Workbook.PICTURE_TYPE_PNG);
        POIXMLDocumentPart poixmlDocumentPart = workbook.getAllPictures().get(pictureIdx);
        //????????????Sheet???
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            PackagePartName ppn = poixmlDocumentPart.getPackagePart().getPartName();
            String relType = XSSFRelation.IMAGES.getRelation();
            int size = sheet.getPackagePart().getRelationships().size();
            //?????????????????????????????????????????????????????????????????????????????????
            if(size >= 2){
                continue;
            }
            PackageRelationship pr = sheet.getPackagePart().addRelationship(ppn, TargetMode.INTERNAL, relType, null);
            sheet.getCTWorksheet().addNewPicture().setId(pr.getId());
        }
        if(StringUtils.isNotBlank(password)){
            // ????????????
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            Encryptor enc = info.getEncryptor();
            enc.confirmPassword(password);
            // ??????
            POIFSFileSystem fs = new POIFSFileSystem();
            OutputStream os1 = enc.getDataStream(fs);
            workbook.write(os1);
            os1.flush();
            os1.close();
            fs.writeFilesystem(outputStream);
        }else{
            workbook.write(outputStream);
        }

    }

    /**
     * word?????????
     *
     * @param inputStream
     * @param outputStream
     * @param customText
     * @throws Exception
     */
    public static void addWordWaterMark(InputStream inputStream, OutputStream outputStream, String password,String customText) throws Exception {
        XWPFDocument doc ;
        if(StringUtils.isNotBlank(password)){
            POIFSFileSystem poifsFileSystem = new POIFSFileSystem(inputStream);
            EncryptionInfo encInfo = new EncryptionInfo(poifsFileSystem);
            Decryptor decryptor = Decryptor.getInstance(encInfo);
            decryptor.verifyPassword(password);
            doc = new XWPFDocument(decryptor.getDataStream(poifsFileSystem));
        }else{
            doc = new XWPFDocument(inputStream);
        }

        // ????????????????????????
        for (int lineIndex = -5; lineIndex < 20; lineIndex++) {
            //?????????????????????????????????
            styleTop = 300 * lineIndex;
            waterMarkDocXDocument_0(doc, customText);
        }
        if(StringUtils.isNotBlank(password)){
            // ????????????
            EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile);
            Encryptor enc = info.getEncryptor();
            enc.confirmPassword(password);
            // ??????
            POIFSFileSystem fs = new POIFSFileSystem();
            OutputStream os1 = enc.getDataStream(fs);
            doc.write(os1);
            os1.flush();
            os1.close();
            fs.writeFilesystem(outputStream);
        }else{
            doc.write(outputStream);
        }
    }




    /**
     * ?????????????????????
     *
     * @param doc        ??????????????????docx????????????
     * @param customText ???????????????????????????
     */
    private static void waterMarkDocXDocument_0(XWPFDocument doc, String... customText) {
        // ??????????????????????????? DEFAULT ???Header???????????????
        XWPFHeader header = doc.createHeader(HeaderFooterType.DEFAULT);
        int size = header.getParagraphs().size();
        if (size == 0) {
            header.createParagraph();
        }
        CTP ctp = header.getParagraphArray(0).getCTP();
        byte[] rsidr = doc.getDocument().getBody().getPArray(0).getRsidR();
        byte[] rsidrdefault = doc.getDocument().getBody().getPArray(0).getRsidRDefault();
        ctp.setRsidP(rsidr);
        ctp.setRsidRDefault(rsidrdefault);
        CTPPr ppr = ctp.addNewPPr();
        ppr.addNewPStyle().setVal("Header");
        // ???????????????
        CTR ctr = ctp.addNewR();
        CTRPr ctrpr = ctr.addNewRPr();
        ctrpr.addNewNoProof();
        CTGroup group = CTGroup.Factory.newInstance();
        CTShapetype shapetype = group.addNewShapetype();
        CTTextPath shapeTypeTextPath = shapetype.addNewTextpath();
        shapeTypeTextPath.setOn(STTrueFalse.T);
        shapeTypeTextPath.setFitshape(STTrueFalse.T);
        CTLock lock = shapetype.addNewLock();
        lock.setExt(STExt.VIEW);
        CTShape shape = group.addNewShape();
        shape.setId("PowerPlusWaterMarkObject");
        shape.setSpid("_x0000_s102");
        shape.setType("#_x0000_t136");
        // ???????????????????????????????????????????????????????????????
        //shape.setStyle(getShapeStyle(customText));
        shape.setFillcolor(fontColor);
        // ?????????????????????
        shape.setStroked(STTrueFalse.FALSE);
        // ?????????????????????
        CTTextPath shapeTextPath = shape.addNewTextpath();
        // ???????????????????????????
        shapeTextPath.setStyle("font-family:" + fontName + ";font-size:" + fontSize);
        //shapeTextPath.setString(customText);
        //CTPicture pict = ctr.addNewPict();
        // pict.set(group);

        int position = 0;
        for (String s : customText) {
            // ????????????????????????50???????????????
            s = s + repeatString("\t", 15);
            // ????????????????????????????????????
            s = repeatString(s, 3);
            // ???????????????????????????????????????????????????????????????
            shape.setStyle(getShapeStyle(s,position));
            shapeTextPath.setString(s);
            CTPicture pict = ctr.addNewPict();
            pict.set(group);
            position += 30;
        }


    }


    // ??????Shape???????????????
    private static String getShapeStyle(String customText, Integer position) {
        StringBuilder sb = new StringBuilder();
        // ??????path?????????????????????
        sb.append("position: ").append("absolute");
        // ?????????????????????????????????????????????*???????????????
        sb.append(";width: ").append(customText.length() * widthPerWord).append("pt");
//        sb.append(";width: ").append(200);

        // ????????????
        sb.append(";height: ").append("20pt");
//        sb.append(";z-index: ").append("99999999");
        sb.append(";mso-wrap-edited: ").append("f");
        sb.append(";top: ").append(styleTop);
//        sb.append(";margin-left: ").append("-50pt");
        sb.append(";margin-top: ").append("270pt");
        sb.append(";mso-position-horizontal-relative: ").append("margin");
        sb.append(";mso-position-vertical-relative: ").append("margin");
        sb.append(";mso-position-vertical: ").append("left");
        sb.append(";mso-position-horizontal: ").append("center");
        sb.append(";rotation: ").append(styleRotation);
        return sb.toString();
    }



    /**
     * ???????????????????????????repeats???.
     */
    private static String repeatString(String pattern, int repeats) {
        StringBuilder buffer = new StringBuilder(pattern.length() * repeats);
        Stream.generate(() -> pattern).limit(repeats).forEach(buffer::append);
        return new String(buffer);
    }



}