package net.zhomi.negotiation.activity;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.utils.DialogTool;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author 作者:yangshouzhi E-mail:1554668839@qq.com
 * @date 创建时间：2015-7-8 下午2:56:16
 * @version 1.0
 * @parameter
 * @since
 * @return
 */
public class BaseFragmentActivity extends FragmentActivity implements
		OnClickListener {
	private static String TAG = "BaseFragmentActivity";

	/** 标题栏--标题 **/
	protected TextView title;
	/** 标题栏－右侧操作按钮 */
	protected ImageView menu_search;
	protected ImageView menu_add;

	protected LinearLayout ll_title;
	private Toast mToast;

	protected Button btn_merchant;
	protected Button btn_brand;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化标题栏
	 */
	protected void initTitle() {

		title = (TextView) findViewById(R.id.public_title_text);
		ll_title = (LinearLayout) findViewById(R.id.ll_title);
		btn_merchant = (Button) findViewById(R.id.merchant);
		btn_brand = (Button) findViewById(R.id.brand);
		menu_search = (ImageView) findViewById(R.id.public_title_search);
		menu_add = (ImageView) findViewById(R.id.public_title_add);
		menu_search.setImageResource(R.drawable.home_search);
		menu_add.setImageResource(R.drawable.add_customer);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
	}

	protected void showProgressDialog(String strTitle, int nStringId) {
		showProgressDialog(strTitle, getString(nStringId));
	}

	protected ProgressDialog mProgressDialog;

	/**
	 * 显示进度对话框
	 * 
	 * @param strTitle
	 * @param strMessage
	 */
	protected void showProgressDialog(String strTitle, String strMessage) {
		if (mProgressDialog == null) {
			mProgressDialog = new ProgressDialog(BaseFragmentActivity.this);
			mProgressDialog.setCancelable(false);
		}
		mProgressDialog.setTitle(strTitle);
		mProgressDialog.setMessage(strMessage);
		if (!mProgressDialog.isShowing()) {
			mProgressDialog.dismiss();
		}
		mProgressDialog.show();
	}

	/**
	 * 隐藏进度对话框
	 */
	protected void dismissProgressDialog() {
		DialogTool.dismissDialog(mProgressDialog);
	}

	/**
	 * 显示临时提示信息
	 * 
	 * @param msg
	 */
	protected void showMsg(String msg) {
		if (mToast == null) {
			mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
		}
		mToast.show();
	}

	/**
	 * 显示临时提示信息
	 * 
	 * @param msg
	 */
	protected void showMsg(int resId) {
		if (mToast == null) {
			mToast = Toast.makeText(this, resId, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(resId);
		}
		mToast.show();
	}

}
