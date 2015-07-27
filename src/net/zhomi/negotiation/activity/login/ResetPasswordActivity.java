package net.zhomi.negotiation.activity.login;

import org.json.JSONException;
import org.json.JSONObject;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.BaseActivity;
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class ResetPasswordActivity extends BaseActivity {

	private Button submit;
	private EditText newpsw_et;
	private EditText confirmpsw_et;
	private String newpsw;
	private String confirmpsw;
	private String phone;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resetpassword);
		initView();
		Intent intent = getIntent();
		phone = intent.getStringExtra("phone");
	}

	private void initView() {
		initTitle();
		newpsw_et = (EditText) findViewById(R.id.resetpsw_new_et);

		confirmpsw_et = (EditText) findViewById(R.id.resetpsw_confirm_et);
		submit = (Button) findViewById(R.id.resetpws_submit_bn);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		newpsw = newpsw_et.getText().toString().trim();
		confirmpsw = confirmpsw_et.getText().toString().trim();
		if (newpsw.length() < 6 || newpsw.length() > 20) {
			showMsg("请输入符合规范的密码!");
			newpsw_et.requestFocus();
			return;
		}
		if (!newpsw.equals(confirmpsw)) {
			showMsg("两次输入密码不一致！");
			confirmpsw_et.requestFocus();
			return;
		}
		new ResetPswTask().execute(phone, newpsw,confirmpsw);
	}

	private class ResetPswTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String phone = params[0];
			String newpsw = params[1];
			String confirmpsw = params[2];
			return HttpData.reset_password("user", "find_passwrod", "1.0",
					"2", "1", phone, newpsw, confirmpsw);
		}

		@Override
		protected void onPostExecute(String result){
			super.onPostExecute(result);
			try{
				JSONObject jsonObject=new JSONObject(result);
				String status=JSONUtils.getString(jsonObject, "result", "");
				if(status.equals("1")){
					showMsg("密码设置成功！");
					JSONObject dataJson=JSONUtils.getJSONObject(jsonObject, "data");
					String pwd=JSONUtils.getString(jsonObject, "newpsw", "");
					
					App.instance.userInfo.setPwd(pwd);
					finish();
				}else{
					String errorMsg=JSONUtils.getString(jsonObject, "msg", "");
					showMsg(errorMsg);
				}
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
}
