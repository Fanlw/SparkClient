package cn.bluethink.ecssparkClient.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PageView<T> {

	/**
	 * 当前页
	 */
    private Integer pageNum;
    /**
     * 每页的数量
     */
    private Integer pageSize;
    
    /**
     * 当前页的数量
     */
    private Integer size;

    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 总页数
     */
    private Integer pages;
    
    /**
     * 前一页
     */
    private Integer prePage;
    
    /**
     * 下一页
     */
    private Integer nextPage;

    /**
     * 是否为第一页
     */
    private Boolean isFirstPage;
    
    /**
     * 是否为最后一页
     */
    private Boolean isLastPage;
    
    /**
     * 是否有前一页
     */
    private Boolean hasPreviousPage;
    
    /**
     * 是否有下一页
     */
    private Boolean hasNextPage;
    
    /**
     * 导航页码数
     */
    @JsonIgnore
    private Integer navigatePages;
    
    /**
     * 所有导航页号
     */
    private Integer[] navigatepageNums;
    
    /**
     * 导航条上的第一页
     */
    private Integer navigateFirstPage;
    
    /**
     * 导航条上的最后一页
     */
    private Integer navigateLastPage;
    
    /**
     * 结果集
     */
    private List<T> list = new ArrayList<>();
    
    public PageView() {}

    public PageView(List<T> list, Pageable pageable, Page<?> page) {
        this.list.addAll(list);
        if(this.list != null) {
        	this.size = this.list.size();
        }
        
        if(pageable != null && page != null) {
        	this.pageNum = pageable.getPageNumber() + 1;
            this.pageSize = pageable.getPageSize();
            
            this.total = page.getTotalElements();
            this.pages = page.getTotalPages();
            this.isFirstPage = page.isFirst() && page.getTotalElements() > 0;
            this.isLastPage = page.isLast() && page.getTotalElements() > 0;
            this.prePage = this.isFirstPage ? 1 : this.pageNum - 1;
            this.nextPage = this.isLastPage ? this.pages : this.pageNum + 1;
            this.hasPreviousPage = !(this.isFirstPage) && this.total > 0;
            this.hasNextPage = !(this.isLastPage) && this.total > 0;
            
            if (list instanceof Collection) {
                this.navigatePages = 8;
                //计算导航页
                calcNavigatepageNums();
                //计算前后页，第一页，最后一页
                calcPage();
                //判断页面边界
                judgePageBoudary();
            }
        }
    }
    
    /**
     * 计算导航页
     */
    private void calcNavigatepageNums() {
        //当总页数小于或等于导航页码数时
        if (pages <= navigatePages) {
            navigatepageNums = new Integer[pages];
            for (int i = 0; i < pages; i++) {
                navigatepageNums[i] = i + 1;
            }
        } else { //当总页数大于导航页码数时
            navigatepageNums = new Integer[navigatePages];
            int startNum = pageNum - navigatePages / 2;
            int endNum = pageNum + navigatePages / 2;

            if (startNum < 1) {
                startNum = 1;
                //(最前navigatePages页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            } else if (endNum > pages) {
                endNum = pages;
                //最后navigatePages页
                for (int i = navigatePages - 1; i >= 0; i--) {
                    navigatepageNums[i] = endNum--;
                }
            } else {
                //所有中间页
                for (int i = 0; i < navigatePages; i++) {
                    navigatepageNums[i] = startNum++;
                }
            }
        }
    }

    /**
     * 计算前后页，第一页，最后一页
     */
    private void calcPage() {
        if (navigatepageNums != null && navigatepageNums.length > 0) {
            navigateFirstPage = navigatepageNums[0];
            navigateLastPage = navigatepageNums[navigatepageNums.length - 1];
            if (pageNum > 1) {
                prePage = pageNum - 1;
            }
            if (pageNum < pages) {
                nextPage = pageNum + 1;
            }
        }
    }

    /**
     * 判定页面边界
     */
    private void judgePageBoudary() {
        isFirstPage = pageNum == 1;
        isLastPage = pageNum == pages || pages == 0;;
        hasPreviousPage = pageNum > 1;
        hasNextPage = pageNum < pages;
    }
    
    public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPages() {
		return pages;
	}

	public void setPages(Integer pages) {
		this.pages = pages;
	}

	public Integer getPrePage() {
		return prePage;
	}

	public void setPrePage(Integer prePage) {
		this.prePage = prePage;
	}

	public Integer getNextPage() {
		return nextPage;
	}

	public void setNextPage(Integer nextPage) {
		this.nextPage = nextPage;
	}

	public Boolean getIsFirstPage() {
		return isFirstPage;
	}

	public void setIsFirstPage(Boolean isFirstPage) {
		this.isFirstPage = isFirstPage;
	}

	public Boolean getIsLastPage() {
		return isLastPage;
	}

	public void setIsLastPage(Boolean isLastPage) {
		this.isLastPage = isLastPage;
	}

	public Boolean getHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(Boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}

	public Boolean getHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(Boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public Integer getNavigatePages() {
		return navigatePages;
	}

	public void setNavigatePages(Integer navigatePages) {
		this.navigatePages = navigatePages;
	}

	public Integer[] getNavigatepageNums() {
		return navigatepageNums;
	}

	public void setNavigatepageNums(Integer[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}

	public Integer getNavigateFirstPage() {
		return navigateFirstPage;
	}

	public void setNavigateFirstPage(Integer navigateFirstPage) {
		this.navigateFirstPage = navigateFirstPage;
	}

	public Integer getNavigateLastPage() {
		return navigateLastPage;
	}

	public void setNavigateLastPage(Integer navigateLastPage) {
		this.navigateLastPage = navigateLastPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("PageResources{");
        sb.append("pageNum=").append(pageNum);
        sb.append(", pageSize=").append(pageSize);
        sb.append(", size=").append(size);
        sb.append(", total=").append(total);
        sb.append(", pages=").append(pages);
        sb.append(", list=").append(list);
        sb.append(", prePage=").append(prePage);
        sb.append(", nextPage=").append(nextPage);
        sb.append(", isFirstPage=").append(isFirstPage);
        sb.append(", isLastPage=").append(isLastPage);
        sb.append(", hasPreviousPage=").append(hasPreviousPage);
        sb.append(", hasNextPage=").append(hasNextPage);
        sb.append(", navigatePages=").append(navigatePages);
        sb.append(", navigateFirstPage=").append(navigateFirstPage);
        sb.append(", navigateLastPage=").append(navigateLastPage);
        sb.append(", navigatepageNums=");
        if (navigatepageNums == null) sb.append("null");
        else {
            sb.append('[');
            for (int i = 0; i < navigatepageNums.length; ++i)
                sb.append(i == 0 ? "" : ", ").append(navigatepageNums[i]);
            sb.append(']');
        }
        sb.append('}');
        return sb.toString();
    }

}
