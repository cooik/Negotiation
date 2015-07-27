/**
 * 
 */
package net.zhomi.negotiation.bean;

import net.zhomi.negotiation.utils.PinYinUtil;
import android.R.string;
import android.text.TextUtils;

/** 

 * @ClassName: ProvinceBean 

 * @Description:省(这里用一句话描述这个类的作用) 

 * @author 杨守志

 * @date 2014-11-7 下午1:47:18 

 */
public class ProvinceBean {
	private String provinceName;//省份名称
	private String provinceCode;//省份编码
	private String pyProvincetName = "";
	public ProvinceBean(){
		
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
		if (!TextUtils.isEmpty(this.provinceName)) {
			pyProvincetName=PinYinUtil.getPinYin(this.provinceName);
		}
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getPyProvinceName() {
		return pyProvincetName;
	}

	

}
