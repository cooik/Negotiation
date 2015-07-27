package net.zhomi.negotiation.utils;

import net.zhomi.negotiation.app.App;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferenceManager {
	public static final String PREFERENCE_FILE = "FILE";
	
	public static final String SP_NAME = "tcpjw";
	public static final String LOGIN_NAME = "loginname";
	public static final String LOGIN_PASSWORD = "password";
	public static final String NAME = "name";
	public static final String UID = "uid";
	public static final String CODE = "code";

	/**
	 * 是否推送
	 */
	public static final String IS_PUSH_ON = "isPushOn";

	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	public SharedPreferenceManager(Context context, String file) {
		sp = context.getSharedPreferences(file, Context.MODE_PRIVATE);
		editor = sp.edit();
	}

	public void setUserName(String userName) {
		editor.putString(LOGIN_NAME, userName);
		editor.commit();
	}

	public String getUserName() {
		return getString(LOGIN_NAME, "");
	}

	public void setUserPasswd(String passwd) {
		editor.putString(LOGIN_PASSWORD, passwd);
		editor.commit();
	}

	public String getUserPasswd() {
		return getString(LOGIN_PASSWORD, "");
	}

	public void setName(String name) {
		editor.putString(NAME, name);
		editor.commit();
	}

	public String getName() {
		return getString(NAME, "");
	}

	public void setUserUid(String uid) {
		editor.putString(UID, uid);
		editor.commit();
	}

	public String getUid() {
		return getString(UID, "");
	}

	public void setUserCode(String code) {
		editor.putString(CODE, code);
		editor.commit();
	}

	public String getUserCode() {
		return getString(CODE, "");
	}

	public void setIsPushOn(boolean isPushOn) {
		editor.putBoolean(IS_PUSH_ON, isPushOn);
		editor.commit();
	}

	public boolean getIsPushOn() {
		return getBoolean(IS_PUSH_ON, true);
	}

	private Boolean getBoolean(String tag, Boolean defaultValue) {
		if (sp == null) {
			sp = getSharedPreferences(App.instance);
		}

		return sp.getBoolean(tag, defaultValue);
	}
	public void putString(String tag,String value){
		editor.putString(tag, value);
		editor.commit();
	}
	public String getString(String tag, String defaultValue) {

//	private String getString(String tag, String defaultValue) {
		if (sp == null) {
			sp = getSharedPreferences(App.instance);
		}

		return sp.getString(tag, defaultValue);
	}

	private int getInt(String tag, int defaultValue) {
		if (sp == null) {
			sp = getSharedPreferences(App.instance);
		}

		return sp.getInt(tag, defaultValue);
	}

	private long getLong(String tag, long defaultValue) {
		if (sp == null) {
			sp = getSharedPreferences(App.instance);
		}
		return sp.getLong(tag, defaultValue);
	}

	public Editor getEdit() {
		return editor;
	}

	public static SharedPreferences getSharedPreferences(Context context) {
		return context.getSharedPreferences(SP_NAME, 0);
	}

}
