package manage.tool.utils;

import org.dozer.DozerBeanMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import cn.hutool.core.collection.CollectionUtil;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/08/07 14:38
 **/
@Component
public class DozerConverter {
    @Resource
    private DozerBeanMapper dozerBeanMapper;

    public DozerConverter() {
    }

    public <T> List<T> mapList(List srcList, Class<T> destClass) {
        if (CollectionUtil.isEmpty(srcList)) {
            return null;
        }
        List<T> destList = new ArrayList(srcList.size());
        Iterator var4 = srcList.iterator();

        while(var4.hasNext()) {
            Object e = var4.next();
            T t = this.dozerBeanMapper.map(e, destClass);
            destList.add(t);
        }

        return destList;
    }

    public <T> T map(Object srcObj, Class<T> destClass) {
        return this.dozerBeanMapper.map(srcObj, destClass);
    }
}
