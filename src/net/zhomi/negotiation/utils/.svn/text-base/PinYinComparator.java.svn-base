/**
 * 
 */
package net.zhomi.negotiation.utils;

import java.util.Comparator;

import net.zhomi.negotiation.bean.ProvinceBean;



/** 

 * @ClassName: PinYinComparator 

 * @Description: TODO(这里用一句话描述这个类的作用) 

 * @author 杨守志

 * @date 2014-11-7 下午2:06:53 

 */
public class PinYinComparator implements Comparator<ProvinceBean>{

	@Override
	public int compare(ProvinceBean o1, ProvinceBean o2) {
		 String str1 = PinYinUtil.getPinYin(o1.getProvinceName());
	     String str2 = PinYinUtil.getPinYin(o2.getProvinceName());
	     return str1.compareTo(str2);
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

}
