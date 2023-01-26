package manage.tool.user;

import com.github.pagehelper.util.StringUtil;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.LinkedHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import manage.tool.enums.ResultCodeEnum;
import manage.tool.exception.BaseException;
import manage.tool.utils.DozerConverter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/09/05 15:46
 **/
@Service
@Slf4j
public class UserService {

    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private DozerConverter dozerConverter;

    @SneakyThrows
    public PcUserInfo getUserInfo() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (servletRequestAttributes == null){
            throw new BaseException(ResultCodeEnum.NOT_FOUND_USER.getCode(), "用户信息不存在");
        }
        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");
        if (StringUtil.isNotEmpty(token)) {
            log.info("token:{}", token);
            LinkedHashMap map = (LinkedHashMap) redisTemplate.opsForHash().get("token:"+token,"pcUserInfo");
            PcUserInfo userInfo = dozerConverter.map(map,PcUserInfo.class);
            return userInfo;
        }
        throw new BaseException(ResultCodeEnum.NOT_FOUND_USER.getCode(), "用户信息不存在");
    }

}
