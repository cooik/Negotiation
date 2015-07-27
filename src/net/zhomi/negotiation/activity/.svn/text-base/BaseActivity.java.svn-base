package net.zhomi.negotiation.activity;

/**  
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author yangshouzhi
 * @date 2015-7-11 下午6:25:06 
 */

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.utils.DialogTool;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @ClassName: BaseActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author yangshouzhi
 * @date 2015-7-11 下午6:25:06
 */
public class BaseActivity extends Activity implements OnClickListener {
	/** 标题栏--标题 **/
	protected TextView title;
	/** 标题栏－右侧操作按钮 */
	protected ImageView action;
	/** 标题栏--返回按钮 **/
	protected ImageView back;
	protected ImageView menu_search;
	private Toast mToast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	/**
	 * 初始化标题栏
	 */
	protected void initTitle() {

		title = (TextView) findViewById(R.id.public_title_text);
		if (title != null) {
			title.setOnClickListener(this);
		}
		back = (ImageView) findViewById(R.id.public_back_img);
		if (action != null) {
			action.setOnClickListener(this);
		}
		if (back != null) {
			back.setOnClickListener(this);
		}
		menu_search = (ImageView) findViewById(R.id.public_title_search);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.public_back_img:
			finish();
			break;

		default:
			break;
		}

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
			mProgressDialog = new ProgressDialog(BaseActivity.this);
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
