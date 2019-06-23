package bean;

import java.io.File;

/**
 * 数据库常量
 */
public class MobileConstant {
   public static final Integer TEL_LEN = 11;
   public static final Integer CHINESE_NAME_LEN = 4;
   public static final String  SPILX = "-";

    /**
     * 项目存储数据根路径
     */
   public static final String PARENT_PATH = "F:\\linkman";
    /**
     * 通讯录数据存储 路径
     */
   public static final String DATA_FILE_NAME = PARENT_PATH + File.separator+"mobile2.txt";
    /**
     * 默认手机号
     */
   public static final String  DEFAULT_MOBILE_NUM = "00000000000";
    /**
     * 默认姓名
     */
    public static final String DEFAULT_MOBILE_NAME = "xxx";

    /**
     * 用来填补空白
     */
    public static final String BLANK = "   ";
}
