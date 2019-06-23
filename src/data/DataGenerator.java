package data;

import bean.MobileConstant;
import util.Generator;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Optional;


/**
 * KISS 原则指导下 逐步完善
 * 用于数据生成
 */
public class DataGenerator {


    public DataGenerator() {

    }

    /**
     * 生成通讯录数据
     */
    public  void generateTelData() {
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(MobileConstant.DATA_FILE_NAME, "rw")) {
            int index = 0;
            while (index++ < 100000) {
                String chineseName = Generator.getChineseName();
                String tel = Generator.getTel();
                String telLinkChineseName = tel + MobileConstant.SPILX + chineseName;
                telLinkChineseName = fillingBlank(Optional.of(telLinkChineseName));
                //数据如何存储？
                //为了便于访问需要提前定义数据的大小
                if (telLinkChineseName.getBytes().length > 24) {
                    System.out.println(telLinkChineseName.getBytes().length);
                }
                randomAccessFile.write(telLinkChineseName.getBytes());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 填补名称空白 使得数据长度一致 便于访问
     * @param telLinkChineseName 没有统一长度的通讯录数据字符串
     * @return  不需返回数据
     */
    private  String fillingBlank(Optional<String> telLinkChineseName) {
        String notNullResult = telLinkChineseName.
                orElse(MobileConstant.DEFAULT_MOBILE_NUM + MobileConstant.SPILX + MobileConstant.DEFAULT_MOBILE_NAME);
        return fillIntoBlank(notNullResult);

    }

    private  String fillIntoBlank(String value) {
        Integer size = MobileConstant.CHINESE_NAME_LEN + MobileConstant.TEL_LEN + MobileConstant.SPILX.length() - value.length();
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer i = 0; i < size; i++) {
            stringBuilder.append(MobileConstant.BLANK);
        }
        return value + stringBuilder.toString();
    }

}
