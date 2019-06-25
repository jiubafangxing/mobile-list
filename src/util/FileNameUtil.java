package util;

import bean.MobileConstant;
import net.sourceforge.pinyin4j.PinyinHelper;

import java.io.File;

public class FileNameUtil {
    /**
     * @param name 由汉字构成的字符串
     * @return 返回文件名
     */
    public static String searchFileNameForIndex(String name){
        char firstAlphabet = getFirstAlphabet(name);
        return searchFileNameForIndex(firstAlphabet);
    }

    /**
     * @param firstAlphabet  a-z
     * @return 返回对应的索引文件名
     */
    public static String searchFileNameForIndex(char firstAlphabet){
        return MobileConstant.INDEX_PREFIX_PATH + File.separator + firstAlphabet + "-" + MobileConstant.INDEX_SUFFIX_PATH + "." + MobileConstant.INDEX_FILETYPE;
    }

    /**
     *
     * @param name 汉字
     * @return 返回汉字首字母
     */
    public static char getFirstAlphabet(String name){
        //TODO 目前不支持模糊查询 只能依赖首字母进行查询
        char firstAlphabet = PinyinHelper.toHanyuPinyinStringArray(name.toCharArray()[0])[0].toCharArray()[0];
        return firstAlphabet;
    }


    /**
     * 获得索引最大的长度
     * @return
     */
    public static Integer getMaxSize(){
        String indexMaxSize = Integer.MAX_VALUE+"";
        return MobileConstant.DATA_FILE_NAME.getBytes().length+ indexMaxSize.getBytes().length+12;
    }
}
