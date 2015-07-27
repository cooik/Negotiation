package net.zhomi.negotiation.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.bean.BusinessMomentBean;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.negotiation.view.BusinessMomentSortPopupWindow;
import net.zhomi.negotiation.view.BusinessMomentSortPopupWindow.ChoseOnClikListener;
import net.zhomi.nogotiation.adapter.BusinessMomentAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.Toast;

/**
 * 商业一刻
 * 
 * @author yangshouzhi
 * 
 */
public class BusinessMomentActivity extends BaseActivity implements
		ChoseOnClikListener {
	private ListView businessMomentListView;
	private List<BusinessMomentBean> businessMomentList;
	private BusinessMomentAdapter businessMomentAdapter;

	private BusinessMomentSortPopupWindow myShopPopupWindow;
	/** 内容区域变暗 **/
	private RelativeLayout mCanversLayout;
	private RelativeLayout layout_head;
	private List<Map<String, String>> newsTypeList = new ArrayList<Map<String, String>>();
	private String currentChooseString="全部新闻";//当前选择的新闻类型
	public static final String NEWSID="id";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_moment);
		newsTypeList = App.newsCateList;
		initView();
	}

	private void initView() {
		initTitle();
		title.setText("商业一刻");
		businessMomentListView = (ListView) findViewById(R.id.business_moment_listview);
		businessMomentList = new ArrayList<BusinessMomentBean>();
		businessMomentAdapter = new BusinessMomentAdapter(businessMomentList,
				this);
		businessMomentListView.setAdapter(businessMomentAdapter);
		businessMomentListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BusinessMomentBean bean=businessMomentList.get(position);
				Intent intent=new Intent(BusinessMomentActivity.this, NewsDetailActivity.class);
				intent.putExtra(NEWSID, bean.getId());
				startActivity(intent);
			}
		});
		initData();
		// 分类选项
		mCanversLayout = (RelativeLayout) findViewById(R.id.rl_canvers);
		layout_head = (RelativeLayout) findViewById(R.id.id_home_head);
		new GetNewsListsTask().execute("");

	}

	private void initData() {
		businessMomentList = new ArrayList<BusinessMomentBean>();
		for (int i = 0; i < 10; i++) {
			BusinessMomentBean bean = new BusinessMomentBean();
			businessMomentList.add(bean);
		}
		businessMomentAdapter.refreshUi(businessMomentList);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		businessMomentAdapter.destroyDownloadEvent();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.public_title_text:
			if (myShopPopupWindow == null) {
				myShopPopupWindow = new BusinessMomentSortPopupWindow(this,
						mCanversLayout, this);
			}
			myShopPopupWindow.showPopupWindow();
			myShopPopupWindow.showAsDropDown(layout_head, 0, 0);
			mCanversLayout.setVisibility(View.VISIBLE);
			myShopPopupWindow.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {
					mCanversLayout.setVisibility(View.GONE);
				}
			});
			break;

		default:
			break;
		}
	}

	@Override
	public void chose(String chose) {
		if (currentChooseString.equals(chose)) {
			return;
		}
		currentChooseString=chose;
		Toast.makeText(this, chose, Toast.LENGTH_SHORT).show();
		for (int i = 0; i < newsTypeList.size(); i++) {
			if ( newsTypeList.get(i).containsKey(chose)) {
				String id=newsTypeList.get(i).get(chose);
				new GetNewsListsTask().execute(id);
				break;
			}
		}
	}

	private class GetNewsListsTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String id = params[0];
			return HttpData.getNewsList("news", "lists", "1.0", "2", id);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result == null && TextUtils.isEmpty(result)) {
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(result);
				String status = JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					JSONObject dataJsonObject = JSONUtils.getJSONObject(
							jsonObject, "data");
					JSONArray listsArr = JSONUtils.getJSONArray(dataJsonObject,
							"lists");
					if (listsArr.length() > 0) {
						businessMomentList = new ArrayList<BusinessMomentBean>();
						for (int i = 0; i < listsArr.length(); i++) {
							JSONArray subJsonArray = JSONUtils.getJSONArray(
									listsArr.getJSONObject(i), "sub");
							for (int j = 0; j < subJsonArray.length(); j++) {
								BusinessMomentBean bean = new BusinessMomentBean();
								bean.setTitle(JSONUtils.getString(
										subJsonArray.getJSONObject(j), "title",
										""));
								bean.setDate(JSONUtils.getString(
										subJsonArray.getJSONObject(j), "date",
										""));
								bean.setId(JSONUtils.getString(
										subJsonArray.getJSONObject(j), "id", ""));
								bean.setImgUrl(JSONUtils.getString(
										subJsonArray.getJSONObject(j),
										"img_url", ""));
								businessMomentList.add(bean);
							}
						}
						businessMomentAdapter.refreshUi(businessMomentList);
					}
				} else {
					String errorMsg = JSONUtils
							.getString(jsonObject, "msg", "");
					showMsg(errorMsg);
					return;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
