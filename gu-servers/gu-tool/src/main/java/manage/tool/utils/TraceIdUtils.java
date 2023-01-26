package manage.tool.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/12/07 12:24
 **/
@Slf4j
public class TraceIdUtils {
    public static final String TRACE_ID = "traceId";
    private static final int MAX_ID_LENGTH = 10;

    public TraceIdUtils() {
    }

    private static String genTraceId() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public static void setTraceId(String traceId) {
        traceId = StringUtils.isBlank(traceId) ? StringUtils.substring(genTraceId(), -10) : traceId;
        MDC.put(TRACE_ID, traceId);
    }

    public static String getTraceId() {
        String traceId = MDC.get(TRACE_ID);
        return StringUtils.isBlank(traceId) ? genTraceId() : traceId;
    }
}