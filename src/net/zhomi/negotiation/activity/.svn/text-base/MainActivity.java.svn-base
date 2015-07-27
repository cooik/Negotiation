/**  
 * @Package net.zhomi.negotiation.activity 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author yangshouzhi
 * @date 2015-7-15 下午3:31:45 
 */
package net.zhomi.negotiation.activity;

import java.util.ArrayList;
import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.fragment.BrandFragment;
import net.zhomi.negotiation.fragment.CustomerFragment;
import net.zhomi.negotiation.fragment.HomeFragment;
import net.zhomi.negotiation.fragment.UserCenterFragment;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * @ClassName: MainActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author yangshouzhi
 * @date 2015-7-15 下午3:31:45
 */
public class MainActivity extends BaseFragmentActivity implements
		OnPageChangeListener {
	private ViewPager viewPager;
	private RadioGroup group;
	private List<Fragment> fragments;
	private Button new_customer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
	}

	private void initView() {
		initTitle();
		title.setText("首页");
		fragments = new ArrayList<Fragment>();
		fragments.add(new HomeFragment());
		fragments.add(new CustomerFragment());
		fragments.add(new BrandFragment());
		fragments.add(new UserCenterFragment());
		viewPager = (ViewPager) findViewById(R.id.main_pager);
		viewPager.setAdapter(new FragmentPagerAdapter(
				getSupportFragmentManager()) {

			@Override
			public int getCount() {
				return fragments.size();
			}

			@Override
			public Fragment getItem(int position) {
				return fragments.get(position);
			}
		});
		group = (RadioGroup) findViewById(R.id.main_radiogroup);
		group.check(R.id.main_home_rb);
		findViewById(R.id.main_home_rb).setOnClickListener(this);
		findViewById(R.id.main_customer_rb).setOnClickListener(this);
		findViewById(R.id.main_brand_rb).setOnClickListener(this);
		findViewById(R.id.main_user_rb).setOnClickListener(this);
		if (title != null) {
			title.setText(this.getResources()
					.getString(R.string.main_navi_home));
		}
		viewPager.setOnPageChangeListener(this);
		menu_add.setOnClickListener(this);
		menu_search.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id = v.getId();
		switch (id) {
		case R.id.main_home_rb:
			viewPager.setCurrentItem(0, true);
			settitle(R.string.main_navi_home);
			hidemenu();
			break;
		case R.id.main_customer_rb:
			viewPager.setCurrentItem(1, true);
			settitle(R.string.main_navi_customer_management);
			setmenu();
			break;
		case R.id.main_brand_rb:
			viewPager.setCurrentItem(2, true);
			title.setVisibility(View.GONE);
			ll_title.setVisibility(View.VISIBLE);
			hidemenu();
			break;
		case R.id.main_user_rb:
			settitle(R.string.main_navi_user_center);
			viewPager.setCurrentItem(3, true);
			hidemenu();
			break;
		case R.id.public_title_add:
		case R.id.public_title_search:
			showPopupWindow(v);
			break;

		default:
			break;
		}
	}

	private void showPopupWindow(View v) {
		View contentView = LayoutInflater.from(this).inflate(
				R.layout.pup_window, null);
		final PopupWindow popupWindow = new PopupWindow(contentView,
				LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		new_customer = (Button) contentView.findViewById(R.id.new_customer);
		new_customer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				popupWindow.dismiss();
				startActivity(new Intent(MainActivity.this,
						AddNewCustomerActivity.class));
			}
		});
		popupWindow.setTouchable(true);
		WindowManager.LayoutParams params = this.getWindow().getAttributes();
		params.alpha = 0.5f;
		this.getWindow().setAttributes(params);
		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.d("BaseFragmentActivity", "onTouch:");
				WindowManager.LayoutParams params = MainActivity.this
						.getWindow().getAttributes();
				params.alpha = 1f;
				MainActivity.this.getWindow().setAttributes(params);
				return false;
			}
		});

		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.color.no_color));
		popupWindow.showAtLocation(findViewById(R.id.main),
				Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 0);

	}

	private void hidemenu() {
		if (menu_add.getVisibility() == View.VISIBLE) {
			menu_add.setVisibility(View.GONE);

		}
		if (menu_search.getVisibility() == View.VISIBLE) {
			menu_search.setVisibility(View.GONE);
		}
	}

	private void setmenu() {
		if (menu_add.getVisibility() == View.GONE
				|| menu_add.getVisibility() == View.INVISIBLE) {
			menu_add.setVisibility(View.VISIBLE);

		}
		if (menu_search.getVisibility() == View.GONE
				|| menu_search.getVisibility() == View.INVISIBLE) {
			menu_search.setVisibility(View.VISIBLE);
		}
	}

	private void settitle(int titleString) {
		if (ll_title.getVisibility() == View.VISIBLE) {
			ll_title.setVisibility(View.GONE);
		}
		if (title.getVisibility() == View.GONE
				|| title.getVisibility() == View.INVISIBLE) {
			title.setVisibility(View.VISIBLE);
		}
		title.setText(this.getResources().getString(titleString));
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		switch (arg0) {
		case 0:
			group.check(R.id.main_home_rb);
			settitle(R.string.main_navi_home);
			hidemenu();
			break;
		case 1:
			group.check(R.id.main_customer_rb);
			settitle(R.string.main_navi_customer_management);
			setmenu();
			break;
		case 2:
			group.check(R.id.main_brand_rb);
			title.setVisibility(View.GONE);
			ll_title.setVisibility(View.VISIBLE);
			hidemenu();
			break;
		case 3:
			group.check(R.id.main_user_rb);
			settitle(R.string.main_navi_user_center);
			hidemenu();
			break;
		default:
			break;
		}
	}

}
