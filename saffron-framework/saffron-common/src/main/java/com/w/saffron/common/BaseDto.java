package com.w.saffron.common;

/**
 * @author w
 * @since 2023/6/13
 */
public class BaseDto {

    private Long id;

    private String searchText;

    private Integer current = 1;

    private Integer pageSize = 20;

    private String sortKey = "updateTime";

    private String sortOrder = "DESC";

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize<0||pageSize>200){
            return;
        }
        this.pageSize = pageSize;
    }
}
