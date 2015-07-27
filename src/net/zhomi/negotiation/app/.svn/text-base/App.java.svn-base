package net.zhomi.negotiation.app;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zhomi.negotiation.fragment.BrandFragment;
import net.zhomi.negotiation.model.UserInfo;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.negotiation.utils.SystemUtils;
import net.zhomi.nogotiation.adapter.FirstCateSpinnerAdapter;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

public class App extends Application implements UncaughtExceptionHandler {
	public static App instance;
	public UserInfo userInfo = new UserInfo();
	public static Map<String, Long> map;// 用来存放倒计时的时间
	/** 一级业态分类 **/
	public static List<Map<String, String>> firstCateList;
	public static String[] firstCateArr;

	/** 商业一刻新闻分类 **/
	public static List<Map<String, String>> newsCateList;
	public static String[] newsCateArr;

	private List<Activity> activitys = new ArrayList<Activity>();

	public App() {
		instance = this;
	}

	public static App getInstance() {
		return instance;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		if (userInfo == null) {
			userInfo = new UserInfo();
		}
		new GetFirstCateTask().execute();
		new GetNewsCateListTask().execute();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	public boolean isLogin() {
		// if (userInfo == null || userInfo.uid == 0) {
		// return false;
		// }
		return true;
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		System.out.println("uncaughtException");
		ex.printStackTrace();
		StackTraceElement[] es = ex.getStackTrace();
		for (StackTraceElement e : es) {
			SystemUtils.print(e.toString());
		}
		SystemUtils.print(ex.getLocalizedMessage());
	}

	/**
	 * 添加activity
	 * 
	 * @param activity
	 */
	public void addActivity(Activity activity) {
		activitys.add(activity);
	}

	public void removeActivity(Activity activity) {
		activitys.remove(activity);
	}

	public void clearActivity() {
		for (Activity activity : activitys) {
			activity.finish();
		}
		activitys.clear();
	}

	/**
	 * 退出程序
	 */
	public void exit() {
		for (Activity activity : activitys) {
			activity.finish();
		}
		activitys.clear();
		android.os.Process.killProcess(android.os.Process.myPid());
	}

	private class GetFirstCateTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			return HttpData.getFirstCate("cate", "cate_list", "1.0", "2", "1",
					"");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				String status = JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					JSONArray jsonArray = JSONUtils.getJSONArray(jsonObject,
							"data");
					Log.e("ysz", "" + jsonArray.length());
					if (jsonArray.length() > 0) {
						firstCateArr = new String[jsonArray.length() + 1];
						firstCateList = new ArrayList<Map<String, String>>();
						firstCateArr[0] = "请选择一级业态";
						Map<String, String> map1 = new HashMap<String, String>();
						map1.put(firstCateArr[0], "");
						firstCateList.add(map1);
						for (int i = 0; i < jsonArray.length(); i++) {
							String id = JSONUtils.getString(
									jsonArray.getJSONObject(i), "id", "");
							String name = JSONUtils.getString(
									jsonArray.getJSONObject(i), "name", "");
							firstCateArr[i + 1] = name;
							Map<String, String> map2 = new HashMap<String, String>();
							map2.put(name, id);
							firstCateList.add(map2);
						}
					}
				} else {
					String errorMsg = JSONUtils
							.getString(jsonObject, "msg", "");
					Toast.makeText(App.this.getApplicationContext(), errorMsg,
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				Toast.makeText(App.this.getApplicationContext(), "获取一级业态失败!",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private class GetNewsCateListTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			return HttpData.getNewsCateList("news", "cate_list", "1.0", "2");
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result == null || TextUtils.isEmpty(result)) {
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(result);
				String status = JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					JSONObject dataJsonObject=JSONUtils.getJSONObject(jsonObject, "data");
					JSONArray listsArr=JSONUtils.getJSONArray(dataJsonObject, "lists");
					if (listsArr.length()>0) {
						newsCateArr=new String[listsArr.length()+1];
						newsCateList=new ArrayList<Map<String,String>>();
						newsCateArr[0]="全部新闻";
						Map<String, String> mapFirst=new HashMap<String, String>();
						mapFirst.put(newsCateArr[0], "");
						newsCateList.add(mapFirst);
						for (int i = 0; i < listsArr.length(); i++) {
							String id=JSONUtils.getString(listsArr.getJSONObject(i), "id", "");
							String name=JSONUtils.getString(listsArr.getJSONObject(i), "name", "");
							newsCateArr[i+1]=name;
							Map<String, String> map=new HashMap<String, String>();
							map.put(name, id);
							newsCateList.add(map);
							Log.e("ysz", "newslistArr="+newsCateArr.length);
						}
					}
				} else {
					String errorMsg = JSONUtils
							.getString(jsonObject, "msg", "");
					Toast.makeText(getApplicationContext(), errorMsg,
							Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}
}
