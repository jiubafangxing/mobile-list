package bean;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;

import java.io.Serializable;
import java.util.List;


/**
 * 分页
 */
public class PageResult<T> implements Serializable {
    private Long currentPage;
    private Long pageSize;
    private Long allPages;
    private List<T> records;

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getAllPages() {
        return allPages;
    }

    public void setAllPages(Long allPages) {
        this.allPages = allPages;
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }
}
