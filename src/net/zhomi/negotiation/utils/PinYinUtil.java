/**
 * 
 */
package net.zhomi.negotiation.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/** 

 * @ClassName: PinYinUtil 

 * @Description: 中文转化为拼音(这里用一句话描述这个类的作用) 

 * @author 杨守志

 * @date 2014-11-7 下午1:58:15 

 */
public class PinYinUtil {
	/**
	 * 将字符串中的中文转化为拼音，其他字符不变
	 * @param inputString
	 * @return
	 */
	public static String getPinYin(String inputString){
		HanyuPinyinOutputFormat format=new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		char[] input=inputString.trim().toCharArray();
		String output="";
		 try {
	            for (int i = 0; i < input.length; i++) {
	                if (java.lang.Character.toString(input[i]).
	                matches("[\\u4E00-\\u9FA5]+")) {
	                    String[] temp = PinyinHelper.
	                    toHanyuPinyinStringArray(input[i],
	                    format);
	                    output += temp[0];
	                } else
	                    output += java.lang.Character.toString(
	                    input[i]);
	            }
	        } catch (BadHanyuPinyinOutputFormatCombination e) {
	            e.printStackTrace();
	        }
	        return output;
	}

}
