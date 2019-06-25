package data;

import bean.*;
import util.FileNameUtil;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 索引阅读器
 */
public class IndexReader implements Serializable {


    private PageResult<MobileDataVo> selectByCommonQuery(String name){
        CommonQuery commonQuery = new CommonQuery();
        commonQuery.setName(name);
        return selectByCommonQuery(Optional.empty(), commonQuery);
    }

    private PageResult<MobileDataVo> selectByCommonQuery(CommonQuery commonQuery){
        return selectByCommonQuery(Optional.empty(), commonQuery);
    }

    public PageResult<MobileDataVo> selectByCommonQuery(Optional<PageQuery> pageQueryOptional, CommonQuery commonQuery) {
        PageQuery pageQuery = pageQueryOptional.orElse(PageQuery.defaultPageQuery());
        PageResult<MobileDataVo> mobileDataVoPageResult = new PageResult<>();

        String fileName = FileNameUtil.searchFileNameForIndex(commonQuery.getName());
        Integer maxSize = FileNameUtil.getMaxSize();
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(fileName,"rw")){
            Long length = randomAccessFile.length();
            Long allPages = length / maxSize;
            mobileDataVoPageResult.setAllPages(allPages);
            Long currentPage = pageQuery.getCurrentPage();
            Long size = pageQuery.getSize();
            Long end = currentPage *  size * maxSize;
            Long start = (currentPage -1)*  size * maxSize  ;
            Long contextSize = end -start;
            byte[] byteArray = new byte[contextSize.intValue()];
            randomAccessFile.seek(start);
            int read = randomAccessFile.read(byteArray, 0, contextSize.intValue());//(start.intValue()), end.intValue());
            if(read > 0){
                String pageStr = new String(byteArray);
                List<MobileDataVo> list =  resolve(pageStr);
                mobileDataVoPageResult.setRecords(list);
            }
            mobileDataVoPageResult.setPageSize(pageQuery.getSize());


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mobileDataVoPageResult;


    }

    /**
     * @param pageStr 指定大小的字符串
     * @return 返回解析后的数据
     */
    private List<MobileDataVo> resolve(String pageStr) {
        List<MobileDataVo> list = new ArrayList<>();
        String[] mobileDataStrArray = pageStr.trim().split(MobileConstant.INDEX_SPILX);
        for (int i = 0; i <mobileDataStrArray.length; i++) {
            //"安馥珊=F:\linkman\mobile2.txt=335;"
            String   mobileDataStr =   mobileDataStrArray[i].trim();
            String[] dataArray = mobileDataStr.split(MobileConstant.INNER_DATA_SPILX);
            String name = dataArray[0];
            String fileName = dataArray[1];
            String index = dataArray[2];
            MobileDataVo mobileDataVo = new MobileDataVo();
            mobileDataVo.setFileName(fileName);
            mobileDataVo.setIndex(Integer.parseInt(index));
            mobileDataVo.setName(name);
            list.add(mobileDataVo);
        }
        return list;
    }


}
