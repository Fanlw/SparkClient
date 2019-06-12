package cn.bluethink.ecssparkClient.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author flw
 * @date 2018年9月10日 
 */
public class JobFilter {
    private String name;
    private List<Integer> status;
    private List<String> ids;
    private List<String> toolNames;
    private Date submitBegin;
    private Date submitEnd;
    private Integer page;
    private Integer pageSize;
    private Set<String> orderBy;
    private Boolean asc = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getStatus() {
        return status;
    }

    public void setStatus(List<Integer> status) {
        this.status = status;
    }

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getToolNames() {
        return toolNames;
    }

    public void setToolNames(List<String> toolNames) {
        this.toolNames = toolNames;
    }

    public Date getSubmitBegin() {
        return submitBegin;
    }

    public void setSubmitBegin(Date submitBegin) {
        this.submitBegin = submitBegin;
    }

    public Date getSubmitEnd() {
        return submitEnd;
    }

    public void setSubmitEnd(Date submitEnd) {
        this.submitEnd = submitEnd;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Set<String> getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Set<String> orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getAsc() {
        return asc;
    }

    public void setAsc(Boolean asc) {
        this.asc = asc;
    }
}
