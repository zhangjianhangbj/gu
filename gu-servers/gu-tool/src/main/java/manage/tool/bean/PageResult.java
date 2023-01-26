package manage.tool.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @description:
 * @author: 张燕飞
 * @time: 2020/12/21 10:47
 */
@Data
public class PageResult<T> implements Serializable{

    private static final long serialVersionUID = 6887389993060457824L;

    @ApiModelProperty("查询数据list")
    private List<T> content;
    
    @ApiModelProperty("聚合分类列表")
    private List<Map<String,Object>> facetList;
    
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

    public PageResult() {
    }

    public PageResult(PageRequest bean, Integer count, List<T> list) {
      this.totalElements = (long)count;
      if (count == 0) {
        this.totalPages = 0;
        this.first = false;
        this.last = false;
        this.pagSize = 0;
        this.page = 0;
        this.numberOfElements = 0;
      } else {

        int mod = count % bean.getPageSize();
        if (mod == 0) {
          this.totalPages = count / bean.getPageSize();
        } else if (mod > 0) {
          this.totalPages = count / bean.getPageSize() + 1;
        } else {
          this.totalPages = 0;
        }

        this.page = bean.getPage();
        this.pagSize = bean.getPageSize();
        if (list == null) {
          this.numberOfElements = 0;
        } else {
          this.numberOfElements = list.size();
        }

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
      this.content = list;
    }

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}

	public List<Map<String,Object>> getFacetList() {
		return facetList;
	}

	public void setFacetList(List<Map<String,Object>> facetList) {
		this.facetList = facetList;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPagSize() {
		return pagSize;
	}

	public void setPagSize(int pagSize) {
		this.pagSize = pagSize;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}
    
}