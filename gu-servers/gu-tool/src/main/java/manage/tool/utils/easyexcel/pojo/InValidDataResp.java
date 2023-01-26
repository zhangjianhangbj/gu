package manage.tool.utils.easyexcel.pojo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/08/08 16:32
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InValidDataResp {
    /**
     * 行号
     */
    @ApiModelProperty("错误数据行号")
    private Integer rowNum;
    /**
     * 无效原因
     */
    @ApiModelProperty("错误信息")
    private String msg;

}
