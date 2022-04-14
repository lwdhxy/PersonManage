package cn.pzhu.pserson.util;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtil {

    public static String getLoginname(String username) {
        String s = captureName(getPingYin(username.substring(0, 1)));
        String pinYinHeaderChar = getPinYinHeaderChar(username.substring(1));
        return s+pinYinHeaderChar;
    }

    public static String getPingYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (java.lang.Character.toString(t1[i]).matches(
                        "[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += java.lang.Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    public static String getPinYinHeaderChar(String str){
        String convert="";
        for ( int i = 0; i < str.length(); i++ ) {
            char word=str.charAt(i);
            String[] pinYinArray=PinyinHelper.toHanyuPinyinStringArray(word);
            if ( pinYinArray!=null ){
                convert+=pinYinArray[0].charAt(0);
            }else {
                convert+=word;
            }
        }
        return convert;
    }

    public static String captureName(String name){
        char[] cs=name.toCharArray();
        cs[0]-=32;
        return String.valueOf(cs);
    }


}
