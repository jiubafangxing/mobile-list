package bean;


import java.io.Serializable;


/**
 * 分页查询
 */
public class PageQuery implements Serializable {
    /**
     * 目标页
     */
    private Long currentPage;
    /**
     * 分页条数
     */
    private Long size;

    public Long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        this.currentPage = currentPage;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public static  PageQuery defaultPageQuery(){
        PageQuery pageQuery = new PageQuery();
        pageQuery.setCurrentPage(1L);
        pageQuery.setSize(10L);
        return pageQuery ;
    }
}
