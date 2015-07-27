package net.zhomi.negotiation.utils;

import java.io.File;

import net.zhomi.negotiation.app.App;

import android.os.Environment;
import android.text.TextUtils;



public class FilePaths {

	public static String getApkFilePath(){
		return SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
				File.separator + "apk";
	}
	
	public static String getAvatarTempFilePath(){
		return SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
				File.separator + "tempavatar";
	}
	
	public static String getPosterSavePath(String strName){
		return SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
				File.separator + "poster" + File.separator + strName;
	}
	
	public static String getAvatarSavePath(String strAvatarUrl){
		return SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
				File.separator + "avatar" + File.separator + strAvatarUrl;
	}
	
	public static String getLiveImageSavePath(){
		return SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
				File.separator + "live";
	}
	
	/**
	 * ���ļ�����urlת�����ļ�url�� �ļ�url��ɣ�sd�������� �� cache �� urlfile �� ����url����sha1���� �� �ļ���ʽ
	 * @param strUrl
	 * @return 
	 */
	public static String getUrlFileCachePath(String strUrl){
		if(TextUtils.isEmpty(strUrl)) {
			return null;
		}
		
		int i = strUrl.lastIndexOf(".");
		String fileFormat = "";
		if(i != -1) {
			fileFormat = strUrl.substring(i);
		}
		return SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
				File.separator + "urlfile" + File.separator + Encrypter.encryptBySHA1(strUrl) + fileFormat;
	}
	
	/**
	 * 
	 * @param strUrl
	 * @return 
	 */
	public static String getKnowledgeFilePath(String strUrl) {
		if(TextUtils.isEmpty(strUrl)) {
			return null;
		}
		
		int i = strUrl.lastIndexOf(".");
		String fileFormat = "";
		if(i != -1) {
			fileFormat = strUrl.substring(i);
		}
		return SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
				File.separator + "knowledge" + File.separator + Encrypter.encryptBySHA1(strUrl) + fileFormat;
	}
	
	public static String getCameraSaveFilePath(){
//		return SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
//				File.separator + "camera.jpg";
		return SystemUtils.getExternalCachePath(App.instance.getApplicationContext()) + 
				File.separator + "camera.jpg";
	}
	
	public static String getChatPictureChooseFilePath(){
		return SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
				File.separator + "choose.jpg";
	}
	public static String getRecordChooseFilePath(){
		return SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
				File.separator + "record.mp3";
	}
	
	public static String getSchoolLogoFilePath(){
		String path = SystemUtils.getCachePath(App.instance.getApplicationContext()) + 
				File.separator + "logo";
		File file = new File(path);
		if(!file.exists()) {
			file.mkdirs();
		}
		file = null;
		return path;
	}
	
}
