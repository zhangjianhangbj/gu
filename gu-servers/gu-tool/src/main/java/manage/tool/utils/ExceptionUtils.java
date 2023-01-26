package manage.tool.utils;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/07/16 16:44
 **/

public class ExceptionUtils {
  public static String getStackTraceString(Throwable ex) {
    StackTraceElement[] traceElements = ex.getStackTrace();
    StringBuilder traceBuilder = new StringBuilder();
    traceBuilder.append(ex.toString()).append(": ");
    if (traceElements != null && traceElements.length > 0) {
      StackTraceElement[] var3 = traceElements;
      int var4 = traceElements.length;

      for(int var5 = 0; var5 < var4; ++var5) {
        StackTraceElement traceElement = var3[var5];
        traceBuilder.append(traceElement.toString());
        traceBuilder.append("\n");
      }
    }

    return traceBuilder.toString();
  }
}
