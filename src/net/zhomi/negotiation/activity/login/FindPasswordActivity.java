package net.zhomi.negotiation.activity.login;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.BaseActivity;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.negotiation.view.TimeButton;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class FindPasswordActivity extends BaseActivity {

	TimeButton bn_getcode;
	Button bn_next;
	EditText phone_et;
	EditText code_et;
	TextView tipTextView;
	private String phone;
	String pwd;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_findpassword);
		initView();
	}

	void initView() {
		initTitle();
		setTitle("找回密码");
		phone_et = (EditText) findViewById(R.id.findpassword_phone_et);
		code_et = (EditText) findViewById(R.id.findpassword_code_et);
		bn_getcode = (TimeButton) findViewById(R.id.findpassword_getcode_bn);
		bn_getcode.setTextBefore("获取验证码");
		bn_getcode.setTextAfter("秒后重新获取");
		bn_getcode.setOnClickListener(this);
		bn_next = (Button) findViewById(R.id.findpasswordnext_bn);
		bn_next.setOnClickListener(this);

		tipTextView = (TextView) findViewById(R.id.findpassword_tv);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		bn_getcode.onDestroy();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.findpassword_getcode_bn:
			phone = phone_et.getText().toString().trim();
			String finalStr = encryptPhone(phone);
			new GetCodeTask().execute(phone, "1", finalStr);
			if (bn_getcode.getText().toString().equals("获取验证码")) {
				bn_getcode.setClickable(true);
			} else {
				bn_getcode.setClickable(false);
			}
			break;
		case R.id.findpasswordnext_bn:
			String code = code_et.getText().toString().trim();
			if (TextUtils.isEmpty(code)) {
				showMsg("请输入验证码！");
				code_et.requestFocus();
				return;
			}
			new validateCodeTask().execute(phone, code);
			break;
		default:
			break;
		}
	}

	private class GetCodeTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String phone = params[0];
			String type = params[1];
			String code = params[2];
			return HttpData.getCode("other", "get_vcode", "1.0", "2", phone,
					type, code);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			try {
				JSONObject jsonObject = new JSONObject(result);
				String status = JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					tipTextView.setText("验证码已发往" + phone + "请等待");
					String errorMsg = JSONUtils
							.getString(jsonObject, "msg", "");
					showMsg(errorMsg);
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
			showProgressDialog("", "正在获取验证码...");
		}
	}

	private class validateCodeTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String mobile = params[0];
			String code = params[1];
			return HttpData.validateode("other", "validate_code", "1.0", "2",
					mobile, code);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			try {
				JSONObject jsonObject = new JSONObject(result);
				String status = JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
				Intent intent=new Intent();
				intent.putExtra("phone", phone);
				intent.setClass(FindPasswordActivity.this, ResetPasswordActivity.class);
				startActivity(intent);
				
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
		}

	}

	/**
	 * 对手机号进行加密
	 * 
	 * @param phone
	 * @return
	 */
	private String encryptPhone(String phone) {
		String finalString = "";
		if (null == phone || phone.isEmpty()) {
			return "";
		} else {
			byte[] phoneByte = phone.getBytes();
			// Base64加密
			String a = Base64.encode(phoneByte);
			Log.e("ysz", "a=   " + a);
			// b=a+a前三位+a后四位
			String b = a + a.substring(0, 3)
					+ a.substring(a.length() - 4, a.length());
			Log.e("ysz", "b=   " + b);
			// c=md5(b)
			String c = new String(Hex.encodeHex(DigestUtils.md5(b)));
			Log.e("ysz", "c=   " + c);
			// d=md5(c)
			String d = new String(Hex.encodeHex(DigestUtils.md5(c)));
			Log.e("ysz", "d=   " + d);
			// e=base64(d)
			byte[] dByte = d.getBytes();
			// Base64加密
			String e = Base64.encode(dByte);
			Log.e("ysz", "e=   " + e);
			// f=e前四位+e第八位开始的四位+e第十六位开始的四位+最后一位+==共是13位，加上两个=，共15位
			String f = e.substring(0, 4) + e.substring(8, 12)
					+ e.substring(16, 20)
					+ e.substring(e.length() - 1, e.length()) + "==";
			Log.e("ysz", "f=   " + f);
			finalString = f;
		}

		return finalString;
	}
}