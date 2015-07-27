package net.zhomi.negotiation.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.fragment.BrandFragment;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.nogotiation.adapter.TextAdapter;

import android.content.Context;
import android.graphics.Region;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class ViewMiddle extends LinearLayout implements ViewBaseAction {

	private ListView regionListView;
	private ListView plateListView;
	/** 一级业态分类 **/
	public static List<Map<String, String>> firstCateList;
	public static List<String> firstCateArr = new ArrayList<String>();
	/** 二级业态分类 **/
	public static List<Map<String, String>> secondCateList;
	public static List<String> secondCateArr = new ArrayList<String>();
	private TextAdapter plateListViewAdapter;
	private TextAdapter earaListViewAdapter;
	private OnSelectListener mOnSelectListener;
	private int tEaraPosition = 0;
	private int tBlockPosition = 0;
	private String showString = "业态";
	private String currentFirstCateId = "";
	private String mCurrentSecondCateId = "";

	public ViewMiddle(Context context) {
		super(context);
		init(context);
	}

	public ViewMiddle(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public void updateShowText(String showArea, String showBlock) {
		if (showArea == null || showBlock == null) {
			return;
		}
		// setDefaultSelect();
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_double, this, true);
		regionListView = (ListView) findViewById(R.id.listView);
		plateListView = (ListView) findViewById(R.id.listView2);
		setBackgroundDrawable(getResources().getDrawable(
				R.drawable.choosearea_bg_mid));

		earaListViewAdapter = new TextAdapter(context, firstCateArr,
				R.drawable.choose_item_selected,
				R.drawable.choose_eara_item_selector);
		earaListViewAdapter.setTextSize(17);
		earaListViewAdapter.setSelectedPositionNoNotify(tEaraPosition);
		regionListView.setAdapter(earaListViewAdapter);
		earaListViewAdapter
				.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, int position) {
						if (position < firstCateArr.size()) {
							String firstCateId = firstCateList.get(position)
									.get(firstCateArr.get(position));
							if (firstCateId.equals(currentFirstCateId)) {
								return;
							}
							new GetSecondCateTask().execute(firstCateId);
							currentFirstCateId = firstCateId;
						}
					}
				});

		plateListViewAdapter = new TextAdapter(context, secondCateArr,
				R.drawable.choose_item_right,
				R.drawable.choose_plate_item_selector);
		plateListViewAdapter.setTextSize(15);
		// plateListViewAdapter.setSelectedPositionNoNotify(tBlockPosition);
		plateListView.setAdapter(plateListViewAdapter);
		plateListViewAdapter
				.setOnItemClickListener(new TextAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, final int position) {
						showString = secondCateArr.get(position);
						String currentSecondCateId=secondCateList.get(position).get(showString);
						if (mCurrentSecondCateId.equals(currentSecondCateId)) {
							return;
						}
						mCurrentSecondCateId=currentSecondCateId;
						if (mOnSelectListener != null) {
							mOnSelectListener.getValue(currentFirstCateId,currentSecondCateId);
						}

					}
				});
		new GetFirstCateTask().execute();
		setDefaultSelect();

	}

	public void setDefaultSelect() {
		regionListView.setSelection(tEaraPosition);
		plateListView.setSelection(tBlockPosition);
	}

	public String getShowText() {
		return showString;
	}

	public void setOnSelectListener(OnSelectListener onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	public interface OnSelectListener {
		public void getValue(String firstCateId,String secondCateId);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
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
						firstCateArr = new ArrayList<String>();
						firstCateList = new ArrayList<Map<String, String>>();
						for (int i = 0; i < jsonArray.length(); i++) {
							String id = JSONUtils.getString(
									jsonArray.getJSONObject(i), "id", "");
							String name = JSONUtils.getString(
									jsonArray.getJSONObject(i), "name", "");
							firstCateArr.add(name);
							Map<String, String> map = new HashMap<String, String>();
							map.put(name, id);
							firstCateList.add(map);
						}
						earaListViewAdapter.refreshUi(firstCateArr);
						String firstCateId = firstCateList.get(0).get(
								firstCateArr.get(0));
						new GetSecondCateTask().execute(firstCateId);
						currentFirstCateId = firstCateId;
					}
				} else {
					String errorMsg = JSONUtils
							.getString(jsonObject, "msg", "");
				}
			} catch (JSONException e) {
			}
		}
	}

	private class GetSecondCateTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String id = params[0];
			return HttpData.getFirstCate("cate", "cate_list", "1.0", "2", "2",
					id);
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
						secondCateArr = new ArrayList<String>();
						secondCateList = new ArrayList<Map<String, String>>();
						for (int i = 0; i < jsonArray.length(); i++) {
							String id = JSONUtils.getString(
									jsonArray.getJSONObject(i), "id", "");
							String name = JSONUtils.getString(
									jsonArray.getJSONObject(i), "name", "");
							secondCateArr.add(name);
							Map<String, String> map = new HashMap<String, String>();
							map.put(name, id);
							secondCateList.add(map);
						}
						plateListViewAdapter.refreshUi(secondCateArr);
					}
				} else {
					String errorMsg = JSONUtils
							.getString(jsonObject, "msg", "");
				}
			} catch (JSONException e) {
			}
		}

	}

}
