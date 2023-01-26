package manage.tool.user;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/09/06 11:23
 **/
@Data
@Accessors(chain = true)
public class PcUserInfo {
    private String id;
    private String account;
    private String name;
    private String email;
    private String phone;
    private String token;
    private List<String> roleIds;
    private List<String> roleName;
    private List<String> roleCodes;
}
