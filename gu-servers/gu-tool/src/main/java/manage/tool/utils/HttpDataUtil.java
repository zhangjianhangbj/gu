package manage.tool.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import manage.tool.utils.encrypt.EncryptionUtil;
import manage.tool.utils.encrypt.SignUtil;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;

/**
 * 请求报文生成 , 负责根据数据报文生成 请求报文， 请求流水号，请求数据加密， 请求时间， 生成请求签名等。
 * @author zhangjianhang
 *
 */
public class HttpDataUtil {

  
  /**
   * 请求保文生成 
   * @param data 数据报文
   * @param privatekey 密钥
   * @param iv AES CBC加密偏移量
   * @return 请求报文 transactionId 流水号， reqTime 时间戳，data 加密数据 ，sign 签名
   */
  public static HttpData body(JSONObject data, String privatekey, String iv){
    HttpData body = new HttpData();
    String encryptbody = "";
    try {
      //数据报文加密
      encryptbody = EncryptionUtil.encrypt(1, data.toString(), privatekey, iv);
      if(encryptbody == null){
        return null;
      }
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //生成流水号
    String transactionId = getTransactionId(Math.abs(encryptbody.hashCode()));
    //请求时间
    String reqTime = getNowDateFormatCustom("yyyy-MM-dd HH:mm:ss");
    //请求签名
    String sign = SignUtil.createSign(transactionId, reqTime, privatekey, encryptbody);
    
    body.setTransactionId(transactionId);
    body.setData(encryptbody);
    body.setReqTime(reqTime);
    body.setSign(sign);
    
    return body;
  }
  
  /**
   * 请求流水号生成，生成规则 yyyyMMddHHmmss + 4位随机数
   * @param seed 随机数种子（建议数据报文hashcode做为随机种子）
   * @return 
   */
  private static String getTransactionId(int seed){
    String times = getNowDateFormatCustom("yyyyMMddHHmmss");
    return times + getRanNum(seed, 10000);
  }
  
  /**
   * 随机数 生成
   * @param seed 随机数种子
   * @param len 随机数长度， 100 返回2位数 1000 返回3位数
   * @return
   */
  private static String getRanNum(int seed, int len){
    Random ran = new Random(seed);
    int num = (int)(ran.nextDouble()*len);
    return String.valueOf(num);
  }
  
  private static String getNowDateFormatCustom(String pattern){
    if(StringUtils.isEmpty(pattern)){
      pattern = "yyyy-MM-dd HH:mm:ss";
    }
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    return sdf.format(new Date());
  }

}
