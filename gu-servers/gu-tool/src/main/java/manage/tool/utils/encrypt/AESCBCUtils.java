package manage.tool.utils.encrypt;

import java.security.Security;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * AES加密工具 模式：CBC 补码方式：PKCS7Padding
 * 
 * @author zhangjianhang
 *
 */
public class AESCBCUtils {
  public static boolean initialized = false;
  final Base64.Decoder decoder = Base64.getDecoder();
  final Base64.Encoder encoder = Base64.getEncoder();

  /**
   * 加密
   * 
   * @param sSrc
   * @param encodingFormat
   * @param sKey
   * @param ivParameter
   * @return
   */
  public static String encrypt(String sSrc, String encodingFormat, String sKey, String ivParameter)
      throws Exception {
    if (StringUtils.isEmpty(sSrc)) {
      return sSrc;
    }

    initialize();
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");

    byte[] raw = sKey.getBytes();
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
    cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
    byte[] encrypted = cipher.doFinal(sSrc.getBytes(encodingFormat));
    return Base64.getEncoder().encodeToString(encrypted);
  }

  /** BouncyCastle作为安全提供，防止我们加密解密时候因为jdk内置的不支持改模式运行报错。 **/
  public static void initialize() {
    if (initialized)
      return;
    Security.addProvider(new BouncyCastleProvider());
    initialized = true;
  }

  /**
   * 解密
   * 
   * @param sSrc 密文
   * @param encodingFormat 编码格式 utf-8
   * @param sKey 密钥
   * @param ivParameter 偏移量
   * @return
   * @throws Exception
   */
  public static String decrypt(String sSrc, String encodingFormat, String sKey, String ivParameter)
      throws Exception {
    if (StringUtils.isEmpty(sSrc)) {
      return sSrc;
    }

    initialize();
    byte[] raw = sKey.getBytes("utf-8");
    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
    IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
    // https://www.cjavapy.com//article/697
    cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
    byte[] encrypted1 = Base64.getDecoder().decode(sSrc); // /parseHexStr2Byte(sSrc);//先用base64解密
    byte[] original = cipher.doFinal(encrypted1);
    String originalString = new String(original, encodingFormat);
    return originalString;
  }


  /**
   * 将二进制转换成十六进制
   * 
   * @param buf
   * @return
   */
  private static String parseByte2HexStr(byte buf[]) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < buf.length; i++) {
      String hex = Integer.toHexString(buf[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex.toUpperCase());
    }
    return sb.toString();
  }

  /**
   * 将十六进制转换为二进制
   * 
   * @param hexStr
   * @return
   */
  private static byte[] parseHexStr2Byte(String hexStr) {
    if (hexStr.length() < 1) {
      return null;
    } else {
      byte[] result = new byte[hexStr.length() / 2];
      for (int i = 0; i < hexStr.length() / 2; i++) {
        int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
        int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
        result[i] = (byte) (high * 16 + low);
      }
      return result;
    }
  }

  public static void main(String[] args) {
    // https://www.cjavapy.com//article/697
    try {
      // System.out.println(AESCBCUtils.decrypt("LCTdLeRGzcfs%2Bc4YiYmHgA%3D%3D","utf-8","EWo6IG58iA8W7eZt","1ci5crnda6ojzgtr"));
      // System.out.println(AESCBCUtils.encrypt("13051956913","utf-8","EWo6IG58iA8W7eZt","1ci5crnda6ojzgtr"));
      // System.out.println(AESCBCUtils.decryptPhone("LCTdLeRGzcfs+c4YiYmHgA==","EWo6IG58iA8W7eZt","1ci5crnda6ojzgtr"));
      System.out.println(AESCBCUtils.encrypt("13051956913", "utf-8", "sdfsdfwef3ewfsdvwer34dsv",
          "w2q22234r3dww24r"));
      System.out.println(AESCBCUtils.decrypt("U6UwYtOfejUwjrOGgAs5JA==", "utf-8",
          "sdfsdfwef3ewfsdvwer34dsv", "w2q22234r3dww24r"));

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
