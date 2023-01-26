package manage.tool.handle;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import manage.tool.user.PcUserInfo;
import manage.tool.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

/**
 * @author: zhangyanfei
 * @description: 自定义元数据处理器
 * @create: 2022/07/16 17:11
 **/
@Slf4j
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    private static final String CREATOR = "creator";
    private static final String CREATOR_NAME = "creatorName";
    private static final String UPDATOR = "updator";
    private static final String UPDATOR_NAME = "updatorName";

    @Resource
    private UserService userService;

    /**
     * 插入元对象字段填充（用于插入时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        PcUserInfo userInfo = userService.getUserInfo();
        if (userInfo == null) {
            return;
        }

        this.strictInsertFill(metaObject, CREATOR, String.class, userInfo.getId());
        this.strictInsertFill(metaObject, CREATOR_NAME, String.class, userInfo.getName());
        this.strictUpdateFill(metaObject, UPDATOR, String.class, userInfo.getId());
        this.strictUpdateFill(metaObject, UPDATOR_NAME, String.class, userInfo.getName());
    }

    /**
     * 更新元对象字段填充（用于更新时对公共字段的填充）
     *
     * @param metaObject 元对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        PcUserInfo userInfo = userService.getUserInfo();
        if (userInfo == null) {
            return;
        }
        this.strictUpdateFill(metaObject, UPDATOR, String.class, userInfo.getId());
        this.strictUpdateFill(metaObject, UPDATOR_NAME, String.class, userInfo.getName());
    }
}
