package org.zjh.util;

import java.text.DecimalFormat;

public class DataMathUtil {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    long d1 = 20;
    long d2 = 0l;
    Double f = division(Double.valueOf(d1), Double.valueOf(d2),"#.0000");
    System.out.println(f);
   
  }
  
  /**
   * 两个数相除，四舍五入 保留小数
   * @param d1
   * @param d2
   * @return
   */
  public static Double division(Double d1, Double d2,String format){
    if(d1== null || d1 == 0)
      return 0d;
    if(d2 ==null || d2 == 0){
      return Double.parseDouble(d1.toString());
    }
    double d = d1/d2;
    String f = new DecimalFormat(format).format(d);
    return Double.parseDouble(f);
    
  }
  
  /**
   * 两个数相除，四舍五入 保留小数
   * @param d1
   * @param d2
   * @return
   */
  public static Double division(Double d1, Double d2,Integer percent,String format){
    if(d1== null || d1 == 0){
      return 0d;
    }
    if(d2 ==null || d2 == 0){
      return Double.parseDouble(d1.toString());
    }
    double d = (d1/d2)*percent;
    String f = new DecimalFormat(format).format(d);
    return Double.parseDouble(f);
    
  }
  
//  public static Double division(Object d1, Object d2,String format){
//    if(d1== null || "0".equals(d1.toString()))
//      return 0d;
//    if(d2 ==null){
//      return Double.parseFloat(d1.toString());
//    }
//    double d = Double.valueOf(d1.toString())/Double.valueOf(d2.toString());
//    String f = new DecimalFormat(format).format(d);
//    return Float.parseFloat(f);
//    
//  }
  
  
  public static Double format(Object obj,String format){
    if(obj ==null)
      return 0d;
    try{
    Double d = Double.parseDouble(obj.toString());
    
    String f = new DecimalFormat(format).format(d);
    return Double.parseDouble(f);
    }catch(Exception ex){
      return 0d;
    }
    
  }

}
