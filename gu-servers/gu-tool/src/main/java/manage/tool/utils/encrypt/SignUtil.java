package manage.tool.utils.encrypt;

import com.alibaba.fastjson.JSONObject;

import org.apache.commons.lang3.StringUtils;

import manage.tool.utils.HttpData;
//import com.alibaba.nacos.common.utils.MD5Utils;

/**
 * 签名工具,提供签名生成，签名验证功能
 * 
 * @author zhangjianhang
 *
 */
public class SignUtil {
  public static void main(String[] args) {
    JSONObject JSON = new JSONObject();
    JSON.put("sign", "");
    String data = JSON.getString("sign");
    System.out.println(data);
  }

  /**
   * 创建签名
   * 
   * @param transactionId 流水号
   * @param reqTime 时间戳
   * @param privatekey 密钥
   * @param data 加密数据
   * @return
   */
  public static String createSign(String transactionId, String reqTime, String privatekey,
      String data) {
    String value = transactionId + reqTime + privatekey + data;
    
    return MD5Utils.MD5(value);
  }

  /**
   * 验证签名 0 通过 1不通过
   * 
   * @param transactionId 流水号
   * @param reqTime 时间戳
   * @param privatekey 密钥
   * @param data 加密数据
   * @param sign 签名
   * @return
   */
  public static int checkSign(String transactionId, String reqTime, String data,
      String sign, String privatekey) {
    String nsign = createSign(transactionId, reqTime, privatekey, data);
    if (sign.equals(nsign)) {
      return 0;
    }
    return 1;
  }

  /**
   * 验证签名
   * 
   * @param json transactionId 流水号， reqTime 时间戳，data 加密数据 ，sign 签名
   * @param privatekey 密钥
   * @return 0.通过 1.不通过 2.json对象格式不正确
   */
  public static int checkSign(JSONObject json, String privatekey) {
    String transactionId = json.getString("transactionId");
    String reqTime = json.getString("reqTime");
    String data = json.getString("data");
    String sign = json.getString("sign");
    if (StringUtils.isEmpty(transactionId) || StringUtils.isEmpty(reqTime)
        || StringUtils.isEmpty(data) || StringUtils.isEmpty(sign)) {
      return 2;
    }
    return checkSign(transactionId, reqTime, data, sign, privatekey);

  }

  /**
   * 验证签名
   * 
   * @param hd 请求报文内容 transactionId 流水号， reqTime 时间戳，data 加密数据 ，sign 签名
   * @param privatekey 密钥
   * @return 0.通过 1.不通过 2.json对象格式不正确
   */
  public static int checkSign(HttpData hd, String privatekey) {
    String transactionId = hd.getTransactionId();
    String reqTime = hd.getReqTime();
    String data = hd.getData();
    String sign = hd.getSign();
    if (StringUtils.isEmpty(transactionId) || StringUtils.isEmpty(reqTime)
        || StringUtils.isEmpty(data) || StringUtils.isEmpty(sign)) {
      return 2;
    }

    return checkSign(transactionId, reqTime, data, sign, privatekey);
  }

}
