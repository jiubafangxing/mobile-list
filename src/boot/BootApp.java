package boot;

import bean.MobileConstant;
import data.DataGenerator;
import data.IndexMaker;

import java.io.File;

public class BootApp {
    public static void main(String[] args) {
            //生成数据
            DataGenerator dataGenerator = new DataGenerator();
            dataGenerator.generateTelData();

            IndexMaker indexMaker = new IndexMaker(new File(MobileConstant.PARENT_PATH));
            //读取数据
            indexMaker.readData();
            //进行索引
            indexMaker.sortToIndexFile();

    }
}
