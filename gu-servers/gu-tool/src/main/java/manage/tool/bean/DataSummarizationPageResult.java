package manage.tool.bean;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 数据统计分页返回数据
 *
 * @author 晓
 * @version 1.0
 * @date 2022/10/27 16:59
 */
@ApiModel("数据统计分页返回数据")
@Data
public class DataSummarizationPageResult<T> implements Serializable {

    @ApiModelProperty("查询数据list")
    private T content;
    @ApiModelProperty("记录总数")
    private long totalElements;
    @ApiModelProperty("记录总页数")
    private int totalPages;
    @ApiModelProperty("是否最后一页")
    private boolean last;
    @ApiModelProperty("当前页码")
    private int page = 1;
    @ApiModelProperty("每页展示条数")
    private int pagSize = 10;
    @ApiModelProperty("当前页数据记录数")
    private int numberOfElements;
    @ApiModelProperty("是否第一页")
    private boolean first;

    public DataSummarizationPageResult() {
    }

    public DataSummarizationPageResult(PageRequest bean, Integer total, T data,Integer numOfElements) {
        this.totalElements = (long)total;
        if (total == 0) {
            this.totalPages = 0;
            this.first = false;
            this.last = false;
            this.pagSize = 0;
            this.page = 0;
            this.numberOfElements = 0;
        } else {

            int mod = total % bean.getPageSize();
            if (mod == 0) {
                this.totalPages = total / bean.getPageSize();
            } else if (mod > 0) {
                this.totalPages = total / bean.getPageSize() + 1;
            } else {
                this.totalPages = 0;
            }

            this.page = bean.getPage();
            this.pagSize = bean.getPageSize();
            this.numberOfElements = numOfElements;

            if (bean.getPage() == this.totalPages) {
                this.last = true;
            } else {
                this.last = false;
            }

            if (bean.getPage() == 1) {
                this.first = true;
            } else {
                this.first = false;
            }
        }
        this.content = data;
    }

}
