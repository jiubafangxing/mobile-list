package boot;

import bean.*;
import data.DataGenerator;
import data.IndexMaker;
import data.IndexReader;

import java.io.File;
import java.util.Optional;

public class BootApp {
    public static void main(String[] args) {
            //1生成数据
            DataGenerator dataGenerator = new DataGenerator();
            dataGenerator.generateTelData();

            //2建立索引
            IndexMaker indexMaker = new IndexMaker(new File(MobileConstant.PARENT_PATH));
            //读取数据
            indexMaker.readData();
            //进行索引
            indexMaker.sortToIndexFile();

            //3读取并搜索
            IndexReader indexReader = new IndexReader();
            PageQuery pageQuery = new PageQuery();
            pageQuery.setSize(10L);
            pageQuery.setCurrentPage(1L);
            CommonQuery commonQuery = new CommonQuery();
            commonQuery.setName("值");
            PageResult<MobileDataVo> mobileDataVoPageResult = indexReader.selectByCommonQuery(Optional.empty(), commonQuery);
            for (int i = 0; i <mobileDataVoPageResult.getRecords().size() ; i++) {
                System.out.println(mobileDataVoPageResult.getRecords().get(i).getName());
            }

    }
}
