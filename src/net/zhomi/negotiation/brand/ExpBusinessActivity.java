package net.zhomi.negotiation.brand;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.BaseActivity;
import net.zhomi.negotiation.view.ExpandTabView;
import net.zhomi.negotiation.view.ViewLeft;
import net.zhomi.negotiation.view.ViewMiddle;
import net.zhomi.negotiation.view.ViewRight;
/**
 * 商家库
 * @author yangshouzhi
 *
 */
public class ExpBusinessActivity extends BaseActivity {
	private ExpandTabView expandTabView;
	private ArrayList<View> mViewArray = new ArrayList<View>();
	private ViewLeft viewLeft;
	private ViewMiddle viewMiddle;
	private ViewRight viewRight;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_exp_business);
		initView();
		initVaule();
		initListener();
	}
	private void initView()
	{
		initTitle();
		menu_search.setVisibility(View.VISIBLE);
		title.setText("商家库");
		back.setBackgroundResource(R.drawable.ic_launcher);
		back.setVisibility(View.VISIBLE);
		expandTabView = (ExpandTabView) findViewById(R.id.expandtab_view_businesss);
		viewLeft = new ViewLeft(this);
		viewMiddle = new ViewMiddle(this);
		viewRight = new ViewRight(this);
	}
	private void initVaule(){
		mViewArray.add(viewLeft);
		mViewArray.add(viewMiddle);
		mViewArray.add(viewRight);
		ArrayList<String> mTextArray = new ArrayList<String>();
		mTextArray.add("距离");
		mTextArray.add("1");
		mTextArray.add("距离");
		expandTabView.setValue(mTextArray, mViewArray);
		expandTabView.setTitle(viewLeft.getShowText(), 0);
		expandTabView.setTitle(viewMiddle.getShowText(), 1);
		expandTabView.setTitle(viewRight.getShowText(), 2);
	}
	private void initListener() {

		viewLeft.setOnSelectListener(new ViewLeft.OnSelectListener() {

			@Override
			public void getValue(String distance, String showText) {
				onRefresh(viewLeft, showText);
			}
		});

		viewMiddle.setOnSelectListener(new ViewMiddle.OnSelectListener() {

			@Override
			public void getValue(String firstCateId,String secondCateId) {

//				onRefresh(viewMiddle, showText);

			}
		});

		viewRight.setOnSelectListener(new ViewRight.OnSelectListener() {

			@Override
			public void getValue(String showText) {
				onRefresh(viewRight, showText);
			}

		});
	}

	private void onRefresh(View view, String showText) {

		expandTabView.onPressBack();
		int position = getPositon(view);
		if (position >= 0 && !expandTabView.getTitle(position).equals(showText)) {
			expandTabView.setTitle(showText, position);
		}
		Toast.makeText(ExpBusinessActivity.this, showText, Toast.LENGTH_SHORT)
				.show();

	}

	private int getPositon(View tView) {
		for (int i = 0; i < mViewArray.size(); i++) {
			if (mViewArray.get(i) == tView) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public void onBackPressed() {

		if (!expandTabView.onPressBack()) {
			finish();
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
	}

}
