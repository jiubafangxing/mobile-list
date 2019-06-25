package data;

import bean.MobileConstant;
import bean.MobileMetaData;
import net.sourceforge.pinyin4j.PinyinHelper;
import util.FileNameUtil;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * KISS 原则指导下 逐步完善
 * 索引构建器
 */
public class IndexMaker {
    private File file;
    private List<File> dataFileList = new LinkedList<>();



    public IndexMaker(File file) {
        this.file = file;
    }


    /**
     * 读取文件中的按一定规则存储的数据
     * 到一个list中，这里没有考虑到数据的大小问题
     * TODO： 需要进行优化 数据量很大的情况
     */
    public void readData() {
        File[] files = file.listFiles();
        File[] filesNotNull = Optional.ofNullable(files).orElse(new File[0]);
        Arrays.stream(filesNotNull)
                .filter(file -> !file.isDirectory())
                .forEach(file -> dataFileList.add(file));
    }

    public void sortToIndexFile() {
        try {
            List<String> dataList = new ArrayList<>();
            dataFileList.forEach(file -> analize(dataList, file));
            List<MobileMetaData> mobileMetaData = organizeData(dataList);
            Map<String, List<MobileMetaData>> reSortMobileData = reSort(mobileMetaData);
            saveIndex(reSortMobileData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 建立索引文件
     *
     * @param reSortMobileData 已经进行按首字母进行归类的通讯录元素据map集合
     * @throws IOException 在适当的时候抛出异常
     */
    private void saveIndex(Map<String, List<MobileMetaData>> reSortMobileData) throws IOException {
        for (Map.Entry<String, List<MobileMetaData>> next : reSortMobileData.entrySet()) {
            List<MobileMetaData> value = next.getValue();
            String newfileName = MobileConstant.INDEX_PREFIX_PATH + File.separator + next.getKey() + "-" + MobileConstant.INDEX_SUFFIX_PATH + "." + MobileConstant.INDEX_FILETYPE;
            //删除旧内容
            FileWriter fileWriter = new FileWriter(newfileName);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();

            //建立新索引
            RandomAccessFile randomAccessFile = new RandomAccessFile(newfileName, "rw");
            StringBuilder datatoStore = new StringBuilder();
            for (MobileMetaData md : value) {
                StringBuilder datatoStore2 = new StringBuilder();
                datatoStore2.append(md.getName()).append("=").append(md.getFileName()).append("=").append(md.getPosition()).append(";");

                System.out.println(datatoStore2.toString().getBytes().length);
                String fillBlankStr = fillIntoBlank(datatoStore2.toString());
                System.out.println(fillBlankStr.getBytes().length+"|");
                datatoStore.append(fillBlankStr);
            }
            randomAccessFile.write(datatoStore.toString().getBytes());
        }
    }

    /**填充" "
     * @param value
     * @return
     */
    private  String fillIntoBlank(String value) {
        int size = FileNameUtil.getMaxSize() - value.getBytes().length;
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer i = 0; i < size; i++) {
            stringBuilder.append(" ");
        }
        return value + stringBuilder.toString();
    }

    /**
     * 将数据组织成 以姓名首字母 分类的map集合
     *
     * @param mobileMetaData 通讯录元数据
     * @return 不需返回数据
     */
    private Map<String, List<MobileMetaData>> reSort(List<MobileMetaData> mobileMetaData) {
        //对首字母进行分析，并归类
        List<MobileMetaData> mobileMetaData1 = Optional.ofNullable(mobileMetaData).orElse(new ArrayList<>());
        Map<String, List<MobileMetaData>> dataMap = new ConcurrentHashMap<>();

        for (MobileMetaData metaData : mobileMetaData1) {
            String name = metaData.getName();
            char familyName = name.charAt(0);
            char firstAlphabet = PinyinHelper.toHanyuPinyinStringArray(familyName)[0].toCharArray()[0];

            List<MobileMetaData> saveDataList = dataMap.get(Character.toString(firstAlphabet));
            saveDataList = Optional.ofNullable(saveDataList).orElse(new ArrayList<>());

            saveDataList.add(metaData);
            dataMap.put(Character.toString(firstAlphabet), saveDataList);

        }

        return dataMap;
    }

    /**
     * @param dataList 由字符串构成的通讯录列表
     * @return 不需返回数据
     */
    private List<MobileMetaData> organizeData(List<String> dataList) {
        List<MobileMetaData> mobileMetaDataList = new ArrayList<>();
        List<String> dataWithoutNull = Optional.ofNullable(dataList).orElse(new ArrayList<>());
        dataWithoutNull.forEach(mobileStr -> {
            String trimStr = mobileStr.trim();
            String[] split = trimStr.split("=");
            String data = split[0];
            String origin = split[1];
            String index = split[2];
            String[] dataSpilt = data.split("-");
            MobileMetaData mobileMetaData = new MobileMetaData();
            mobileMetaData.setFileName(origin);
            mobileMetaData.setPosition(index);
            mobileMetaData.setMobileNum(dataSpilt[0]);
            System.out.println(dataSpilt[0]);
            mobileMetaData.setName(dataSpilt[1]);
            mobileMetaDataList.add(mobileMetaData);


        });
        //组织数据
        return mobileMetaDataList;
    }

    private void analize(List<String> dataList, File file) {
        //读取文件，分割数据
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw")) {
            byte[] values = new byte[24];
            int index = 0;
            while (randomAccessFile.read(values) != -1) {
                String s = new String(values);
                String trimStr = s.trim();
                String storeStr = trimStr + "=" + file.getAbsolutePath() + "=" + index;
                dataList.add(storeStr);
                index++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
