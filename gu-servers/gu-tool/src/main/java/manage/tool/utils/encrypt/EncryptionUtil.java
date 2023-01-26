package manage.tool.utils.encrypt;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * 加密算法工具类。 他融合了多种加密算法 AES CBC 模式。
 * @author zhangjianhang
 *
 */
public class EncryptionUtil {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    try {
      String d = EncryptionUtil.encrypt(1, "23232223", "swwcde3fe34f4frt", "1ci5crnda6ojzgtr");
      System.out.println(d);
      d = EncryptionUtil.decrypt(1, d, "swwcde3fe34f4frt", "1ci5crnda6ojzgtr");
      System.out.println(d);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    
  }

  /**
   * 加密
   * @param type 算法类型 1 AES CBC模式 2.
   * @param data 数据内容
   * @param key 密钥
   * @param iv 偏移量
   */
  public static String encrypt(int type, String data, String key, String iv) throws Exception {
    if(type ==1){
      return AESCBCUtils.encrypt(data, "utf-8", key, iv);
    }
    return null;
  }

  /**
   * 解密 
   * @param type 算法类型 1 AES CBC模式 2.
   * @param data 数据内容
   * @param key 密钥
   * @param iv 偏移量
   * @return
   * @throws Exception
   */
  public static String decrypt(int type, String data, String key, String iv) throws Exception {
    if(type ==1){
      return AESCBCUtils.decrypt(data, "utf-8", key, iv);
    }
    return null;
  }

  public static String genAesSecret(){
    try {
      KeyGenerator kg = KeyGenerator.getInstance("AES");
      //下面调用方法的参数决定了生成密钥的长度，可以修改为128, 192或256
      kg.init(128);
      SecretKey sk = kg.generateKey();
      byte[] b = sk.getEncoded();
      String secret =new String(Base64.getEncoder().encode(b));
      return secret;
    }
    catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      throw new RuntimeException("没有此算法");
    }
  }



}
