package bean;

import java.io.Serializable;

/**
 * 公共查询封装
 */
public class CommonQuery implements Serializable {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
