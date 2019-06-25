package bean;

import java.io.Serializable;

/**
 * 移动数据展示
 */
public class MobileDataVo implements Serializable {
    private Integer index;
    private String name;
    private String fileName;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
