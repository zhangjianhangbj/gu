package manage.tool.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpData {
  private String transactionId;
  private String data;
  private String reqTime;
  private String sign;
  
  public String getTransactionId() {
    return transactionId;
  }
  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }
  public String getData() {
    return data;
  }
  public void setData(String data) {
    this.data = data;
  }
  public String getReqTime() {
    return reqTime;
  }
  public void setReqTime(String reqTime) {
    this.reqTime = reqTime;
  }
  public String getSign() {
    return sign;
  }
  public void setSign(String sign) {
    this.sign = sign;
  }
  
  public String toString(){
    return this.transactionId+" "+this.reqTime+" "+this.sign+" "+this.data;
  }
  
  public JSONObject toJson(){
    JSONObject json = new JSONObject();
    json.put("transactionId", this.transactionId);
    json.put("data", this.data);
    json.put("reqTime", this.reqTime);
    json.put("sign", this.sign);
    return json;
  }
}
