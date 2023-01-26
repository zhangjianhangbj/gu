package manage.tool.bean;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: zhangyanfei
 * @description:
 * @create: 2022/07/25 14:43
 **/
@Data
public class PageRequest implements Serializable {
  private static final long serialVersionUID = -5460916727522078000L;
  @ApiModelProperty("当前页码")
  private Integer page = 1;
  @ApiModelProperty("每页展示条数")
  private Integer pageSize = 10;
  @ApiModelProperty("当前每页展示条数")
  private Integer currentSize;

  public int getPageSize() {
    if (this.pageSize == 0) {
      this.pageSize = 10;
    }
    return this.pageSize;
  }

  public Integer getCurrentSize() {
    if (this.page < 1) {
      this.page = 1;
    }

    if (this.pageSize == 0) {
      this.pageSize = 10;
    }

    this.currentSize = (this.page - 1) * this.pageSize;
    return this.currentSize;
  }

public Integer getPage() {
	return page;
}

public void setPage(Integer page) {
	this.page = page;
}

public void setPageSize(Integer pageSize) {
	this.pageSize = pageSize;
}

public void setCurrentSize(Integer currentSize) {
	this.currentSize = currentSize;
}
  
}
