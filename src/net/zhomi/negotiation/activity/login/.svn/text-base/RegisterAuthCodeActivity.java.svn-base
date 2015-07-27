package net.zhomi.negotiation.activity.login;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;

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
import net.zhomi.negotiation.app.App;
import net.zhomi.negotiation.model.UserInfo;
import net.zhomi.negotiation.utils.HttpData;
import net.zhomi.negotiation.utils.JSONUtils;
import net.zhomi.negotiation.view.TimeButton;

/***
 * 
 * @author yangshouzhi
 * 
 */
public class RegisterAuthCodeActivity extends BaseActivity {
	private EditText codeEdit;
	private TimeButton getCodeBtn;
	private Button submitBtn;
	private String phone;
	private String pwd;
	
	private TextView tipTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_authcode);
		initView();
		Intent intent=getIntent();
		phone=intent.getStringExtra(RegisterActivity.PHONE);
		pwd=intent.getStringExtra(RegisterActivity.PWD);
		
	}

	private void initView() {
		codeEdit = (EditText) findViewById(R.id.et_input);
		getCodeBtn = (TimeButton) findViewById(R.id.btn_getcode);
		getCodeBtn.setOnClickListener(this);
		getCodeBtn.setTextBefore("获取验证码");
		getCodeBtn.setTextAfter("秒后重新获取");
		submitBtn = (Button) findViewById(R.id.btn_submit);
		submitBtn.setOnClickListener(this);
		
		tipTextView=(TextView)findViewById(R.id.tv_auth_photo);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		getCodeBtn.onDestroy();
	}	

	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.btn_getcode:
			Log.e("ysz", phone);
			String finalStr=encryptPhone(phone);
			//用户注册type为1
			new GetCodeTask().execute(phone,"1",finalStr);
			if (getCodeBtn.getText().toString().equals("获取验证码")) {
				getCodeBtn.setClickable(true);
			}else{
				getCodeBtn.setClickable(false);
			}
			break;
		case R.id.btn_submit:
			String code=codeEdit.getText().toString().trim();
			if (TextUtils.isEmpty(code)) {
				showMsg("请输入验证码!");
				codeEdit.requestFocus();
				return;
			}
			new validateCodeTask().execute(phone,code);
			break;

		default:
			break;
		}
	}

	private class GetCodeTask extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			String phone=params[0];
			String type=params[1];
			String code=params[2];
			return HttpData.getCode("other", "get_vcode", "1.0", "2", phone, type,code);
		}


		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			try {
				JSONObject jsonObject=new JSONObject(result);
				String status=JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					tipTextView.setText("验证码已发往"+phone+"请等待");
					String errorMsg=JSONUtils.getString(jsonObject, "msg", "");
					showMsg(errorMsg);
				}else{
					String errorMsg=JSONUtils.getString(jsonObject, "msg", "");
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
	private class validateCodeTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String mobile=params[0];
			String code=params[1];
			return HttpData.validateode("other", "validate_code", "1.0", "2", mobile, code);
		}


		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			dismissProgressDialog();
			try {
				JSONObject jsonObject=new JSONObject(result);
				String status=JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					//验证码验证成功后，进行用户注册
					new RegisterTask().execute(phone,pwd);
				}else{
					String errorMsg=JSONUtils.getString(jsonObject, "msg", "");
					showMsg(errorMsg);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog("", "用户注册中...");
		}
	}
	private class RegisterTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... params) {
			String username=params[0];
			String password=params[1];
			return HttpData.register("user", "register", "1.0", "2", username, password);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				JSONObject jsonObject=new JSONObject(result);
				String status=JSONUtils.getString(jsonObject, "result", "");
				if (status.equals("1")) {
					showMsg("用户注册成功!");
					JSONObject dataJson=JSONUtils.getJSONObject(jsonObject, "data");
					String username=JSONUtils.getString(dataJson, "username", "");
					String pwd=JSONUtils.getString(dataJson, "pwd", "");
					String face=JSONUtils.getString(dataJson, "face", "");
					//用户注册成功后保存相关的数据信息
					App.instance.userInfo.setName(username);
					App.instance.userInfo.setPwd(pwd);
					App.instance.userInfo.setAvtUrl(face);
					finish();
				}else{
					String errorMsg=JSONUtils.getString(jsonObject, "msg", "");
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
     * @param phone
     * @return
     */
	private  String encryptPhone(String phone) {
		String finalString="";
		if (null == phone || phone.isEmpty()) {
			return "";
		}else{
			byte[] phoneByte=phone.getBytes();
			//Base64加密
			String a=Base64.encode(phoneByte);
			Log.e("ysz", "a=   "+a);
			//b=a+a前三位+a后四位
			String b=a+a.substring(0, 3)+a.substring(a.length()-4, a.length());
			Log.e("ysz", "b=   "+b);
			//c=md5(b)
			String c=new String(Hex.encodeHex(DigestUtils.md5(b)));
			Log.e("ysz", "c=   "+c);
			//d=md5(c)
			String d=new String(Hex.encodeHex(DigestUtils.md5(c)));
			Log.e("ysz", "d=   "+d);
			//e=base64(d)
			byte[] dByte=d.getBytes();
			//Base64加密
			String e=Base64.encode(dByte);
			Log.e("ysz", "e=   "+e);
			//f=e前四位+e第八位开始的四位+e第十六位开始的四位+最后一位+==共是13位，加上两个=，共15位
			String f=e.substring(0, 4)+e.substring(8, 12)+e.substring(16, 20)+e.substring(e.length()-1,e.length())+"==";
			Log.e("ysz", "f=   "+f);
			finalString=f;
		}
		
		return finalString;
	}
}
