package net.zhomi.negotiation.customer;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.AddNegotiationRecordActivity;
import net.zhomi.negotiation.activity.BaseActivity;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.bean.DetailsBean;
import net.zhomi.negotiation.fragment.HomeFragment;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.nogotiation.adapter.CustomerDeatailAdapter;

public class CustomerDetailsActivity extends BaseActivity {

	private TextView call, add;
	private ListView deatailListView;
	private CustomerDeatailAdapter customerDeatailAdapter;
	private List<DetailsBean> deatailList;

	List<DetailsBean> details = new ArrayList<DetailsBean>();
	/** 传入的name值 **/
	private String customerName;
	/** 传入的id值 **/
	private String customerId;

	private int page = 1;// 页数
	private int perPage = 10;// 每页的条数

	private boolean isStopRefresh = false;// 是否停止刷新
	private boolean isStopLoadMore = false;// 是否停止加载更多

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customerdetails);
		Intent intent = getIntent();
		customerName = intent.getStringExtra(HomeFragment.HOMENAME);
		customerId = intent.getStringExtra(HomeFragment.HOMEID);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		initTitle();
		title.setText(customerName);
		back.setVisibility(View.VISIBLE);
		call = (TextView) findViewById(R.id.details_call);
		add = (TextView) findViewById(R.id.details_record);
		deatailListView = (ListView) findViewById(R.id.customer_details);
		deatailList = new ArrayList<DetailsBean>();
		customerDeatailAdapter = new CustomerDeatailAdapter(deatailList, this);
		deatailListView.setAdapter(customerDeatailAdapter);
		call.setOnClickListener(this);
		add.setOnClickListener(this);
		back.setOnClickListener(this);
		new GetCustomerDetailTask().execute(customerId);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.details_call:
			Intent intent1 = new Intent();
			intent1.setAction(Intent.ACTION_DIAL);
			startActivity(intent1);
			break;
		case R.id.details_record:
			Intent intent2 = new Intent();
			intent2 = new Intent(this, AddNegotiationRecordActivity.class);
			startActivity(intent2);
			break;
		case R.id.public_back_img:
			finish();
			break;
		}
	}

	private class GetCustomerDetailTask extends
			AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String id = params[0];
			return HttpData.getCustomerDetail("user", "cust_detail", "1.0",
					"2", id, App.getInstance().userInfo.getName(),
					App.getInstance().userInfo.getMd5(), String.valueOf(page),
					String.valueOf(perPage));
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
					JSONObject listJsonObject = JSONUtils.getJSONObject(
							jsonObject, "data");
					JSONArray listArr = JSONUtils.getJSONArray(listJsonObject,
							"lists");
					if (listArr.length() > 0) {
						deatailList = getCustomerDetailList(deatailList,
								listArr, isStopLoadMore);
						customerDeatailAdapter.refreshUi(deatailList);
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

	}

	private List<DetailsBean> getCustomerDetailList(List<DetailsBean> mList,
			JSONArray resultArr, boolean isLoadMore) {
		List<DetailsBean> list = new ArrayList<DetailsBean>();
		for (int i = 0; i < resultArr.length(); i++) {
			DetailsBean detailsBean = new DetailsBean();
			try {
				detailsBean.setId(JSONUtils.getString(
						resultArr.getJSONObject(i), "id", ""));
				detailsBean.setTime(JSONUtils.getString(
						resultArr.getJSONObject(i), "time", ""));
				detailsBean.setLocation(JSONUtils.getString(
						resultArr.getJSONObject(i), "loc", ""));
				detailsBean.setNote(JSONUtils.getString(
						resultArr.getJSONObject(i), "note", ""));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			list.add(detailsBean);
		}
		if (isLoadMore) {
			mList.addAll(list);
		} else {
			mList = list;
		}
		return mList;
	}
}
