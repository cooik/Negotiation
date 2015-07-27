package net.zhomi.negotiation.brand;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.BaseFragmentActivity;
import net.zhomi.negotiation.brand.fragment.BrandBusinessFragment;
import net.zhomi.negotiation.brand.fragment.BrandContactsFragment;
import net.zhomi.negotiation.brand.fragment.BrandFilesFragment;
import net.zhomi.negotiation.utils.SystemUtils;

public class BrandDetailActivity extends BaseFragmentActivity implements
		OnPageChangeListener {
	/** 导航下面的横条 */
	private TextView naviLine;
	/** 导航RadioGroup */
	private RadioGroup navigation;
	/** ViewPager */
	private ViewPager pager;
	/** 导航下面横条的宽度 */
	private int width;
	private List<Fragment> fragments;
	private String cityId;
	private String brandId;
	private String lon;
	private String lat;

	private BrandBusinessFragment brandBusinessFragment;
	private BrandContactsFragment brandContactsFragment;
	private BrandFilesFragment brandFilesFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brand_detail);
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		lon = bundle.getString("lon");
		lat = bundle.getString("lat");
		cityId = bundle.getString("cityid");
		brandId = bundle.getString("brandid");
		Log.d("debuginfo", brandId);
		initView();
	}

	private void initView() {
		initTitle();
		title.setText("品牌库");
		naviLine = (TextView) findViewById(R.id.brand_navi_show);
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) naviLine
				.getLayoutParams();
		width = params.width = SystemUtils.getScreenWidth(this) / 3;
		naviLine.setLayoutParams(params);

		// 导航RadioGroup及RadioButton
		navigation = (RadioGroup) findViewById(R.id.activity_brand_navigation);
		findViewById(R.id.brand_navi_1).setOnClickListener(this);
		findViewById(R.id.brand_navi_2).setOnClickListener(this);
		findViewById(R.id.brand_navi_3).setOnClickListener(this);

		pager = (ViewPager) findViewById(R.id.activity_brand_viewpager);
		pager.setOnPageChangeListener(this);
		fragments = new ArrayList<Fragment>();
		brandBusinessFragment = new BrandBusinessFragment(cityId, brandId, lon,
				lat);
		brandContactsFragment = new BrandContactsFragment(brandId);
		brandFilesFragment = new BrandFilesFragment(brandId);
		fragments.add(brandBusinessFragment);// 旗下商家
		fragments.add(brandContactsFragment);// 联系人
		fragments.add(brandFilesFragment);// 品牌档案
		pager.setCurrentItem(0);
		brandBusinessFragment.getData();
		pager.setAdapter(new FragmentPagerAdapter(this
				.getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				return fragments.get(arg0);
			}
		});
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.brand_navi_1:
			setNaviShow(0);
			pager.setCurrentItem(0);
			break;
		case R.id.brand_navi_2:
			setNaviShow(1);
			pager.setCurrentItem(1);
			break;
		case R.id.brand_navi_3:
			setNaviShow(2);
			pager.setCurrentItem(2);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setNaviShow(arg0);
		switch (arg0) {
		case 0:
			navigation.check(R.id.brand_navi_1);
			brandBusinessFragment.getData();
			break;
		case 1:
			navigation.check(R.id.brand_navi_2);
			brandContactsFragment.getData();
			break;
		case 2:
			navigation.check(R.id.brand_navi_3);
			break;

		default:
			break;
		}
	}

	private void setNaviShow(int index) {
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) naviLine
				.getLayoutParams();
		params.leftMargin = index * width;
		naviLine.setLayoutParams(params);
	}

}
