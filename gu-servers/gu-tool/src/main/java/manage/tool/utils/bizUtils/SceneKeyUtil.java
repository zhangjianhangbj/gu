package manage.tool.utils.bizUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.cloud.commons.lang.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 场景key 处理工具，场景KEY 是用来查询场景的唯一标识， 一个场景可以生成多个KEY 
 * 生成key规则：是将公司值 +内容属性值+内容范围label拼接起来组成一个场景KEY。
 * 一个场景有多个公司时 要将每个公司+内容属性值+内容范围label 交叉拼接组成多个场景KEY.
 * @author zhangjianhang
 *
 */
public class SceneKeyUtil {

  public static void main(String[] args) {
    // TODO Auto-generated method stub

  }

  /**
   * 根据内容信息 拼接内容场景key。 内容场景key 是表示这个内容可以匹配到哪个场景。
   * 每个场景都会根据 公司， 内容属性，内容范围生成多个内容场景key存储数据库中。  
   * @param json 内容信息
   * @return 内容场景key
   */
  public static String ContentToKey(JSONObject json){
    //获取内容属性
    JSONObject property = json.getJSONObject("property");
    //获取内容归属公司
    String company = json.getString("company");
    //获取内容信息
    JSONArray contentRange = json.getJSONArray("contentRange");
    
    //排序列表
    List<String> list = new ArrayList<String>();
    //内容属性为空不加内容属性到 排序列表里
    if(property != null){
      for (String key: property.keySet()) {
        list.add(key);
      }
    }
    //将内容label 加到排序列表里
    for (int i = 0; i < contentRange.size(); i++) {
      list.add(contentRange.getString(i));
    }
    
    Collections.sort(list);
    //将内容归属公司加入，内容场景 key字符串中  
    StringBuffer sb = new StringBuffer(company);
    //循环便利排序列表 将值加入 内容场景key 字符串中
    for (String key : list) {
      String value ="";
      if(property != null){
        value = property.getString(key);
        if(!StringUtils.isEmpty(value)){
          value = key;
          sb.append(",").append(value);
          continue;
        }
      }
      sb.append(",").append(key);
    }
    return sb.toString();
  }
  
  public static String SceneToKey(JSONObject json){
    
    return "";
  }


}
