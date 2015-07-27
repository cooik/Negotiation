/**  
 * @Package net.zhomi.negotiation.activity.login 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author yangshouzhi
 * @date 2015-7-15 下午4:56:47 
 */
package net.zhomi.negotiation.activity.login;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.BaseActivity;
import net.zhomi.negotiation.activity.MainActivity;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.negotiation.utils.SystemUtils;

/**
 * @ClassName: LoginActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author yangshouzhi
 * @date 2015-7-15 下午4:56:47
 */
public class LoginActivity extends BaseActivity {
	private EditText phoneEdit;
	private EditText psdEdit;
	private Button loginBtn;
	private TextView registertv,forgetpswtv;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_login);
		initView();
		
		
	}

	/**
	 * 初始化view
	 * 
	 * @return: void
	 */
	private void initView() {
		initTitle();
		title.setText("登录");
		phoneEdit = (EditText) findViewById(R.id.login_phone_edt);
		psdEdit = (EditText) findViewById(R.id.login_psd_edt);
		registertv = (TextView) findViewById(R.id.login_register);
		forgetpswtv = (TextView) findViewById(R.id.login_forgetpsw);
		// 测试
		phoneEdit.setText("15371031902");
		psdEdit.setText("888888");
		loginBtn = (Button) findViewById(R.id.login_btn);
		loginBtn.setOnClickListener(this);
		registertv.setOnClickListener(this);
		forgetpswtv.setOnClickListener(this);
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
		case R.id.login_btn:
			String phone = phoneEdit.getText().toString().trim();
			String password = psdEdit.getText().toString().trim();
			if (!SystemUtils.isNetworkAvailable(this)) {
				showMsg("无可用的网络，请检查网络连接!");
				return;
			}
			if (phone == null || TextUtils.isEmpty(phone)) {
				showMsg("请输入登录的手机号!");
				phoneEdit.requestFocus();
				return;
			}
			if (!SystemUtils.isMobile1(phone)) {
				showMsg("请输入正确的手机号!");
				phoneEdit.requestFocus();
				return;
			}
			if (password == null || TextUtils.isEmpty(password)) {
				showMsg("请输入密码!");
				psdEdit.requestFocus();
				return;
			}
			App.getInstance().userInfo.setName(phone);
			new LoginTask().execute(phone, password);

			break;
		case R.id.login_register:

			Intent intent1 = new Intent(LoginActivity.this,
					RegisterActivity.class);
			startActivity(intent1);

			break;
		case R.id.login_forgetpsw:
			Intent intent2 = new Intent(LoginActivity.this,
					FindPasswordActivity.class);
			startActivity(intent2);

		default:
			break;
		}
	}

	private class LoginTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String username = params[0];
			String pwd = params[1];
			return HttpData.login("user", "login", "1.0", "2", username, pwd);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			if (result==null||TextUtils.isEmpty(result)) {
				showMsg("登录失败，请检查网络连接!");
				return;
			}
			try {
				JSONObject jsonObject = new JSONObject(result);
				String state = JSONUtils.getString(jsonObject, "result", "");
				if (state.equals("1")) {
					JSONObject dataJson = JSONUtils.getJSONObject(jsonObject,
							"data");
					String pwdMd5 = JSONUtils.getString(dataJson, "pwd", "");
					// 保存md5值
//					Log.e("ysz", "pwdMd5=" + pwdMd5+" ");
					App.instance.userInfo.setMd5(pwdMd5);
					LoginActivity.this.startActivity(new Intent(
							LoginActivity.this, MainActivity.class));
					finish();

				} else {
					String errorMsg = JSONUtils
							.getString(jsonObject, "msg", "");
					showMsg(errorMsg);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog("", "正在登录...");
		}

	}

}
