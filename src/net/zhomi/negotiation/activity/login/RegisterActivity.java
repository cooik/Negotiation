/**  
 * @Package net.zhomi.negotiation.activity.login 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author yangshouzhi
 * @date 2015-7-15 下午4:58:43 
 */
package net.zhomi.negotiation.activity.login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.activity.BaseActivity;
import net.zhomi.negotiation.utils.SystemUtils;

/**
 * @ClassName: RegisterActivity
 * @Description: TODO(注册)
 * @author yangshouzhi
 * @date 2015-7-15 下午4:58:43
 */
public class RegisterActivity extends BaseActivity {
	private EditText phoneEdt;
	private EditText pwdEdt;
	private Button nextstepBtn;
	public static final String PHONE="phone";
	public static final String PWD="password";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		initView();
	}
	private void initView(){
		initTitle();
		title.setText("注册");
		phoneEdt=(EditText)findViewById(R.id.register_phone_edt);
		pwdEdt=(EditText)findViewById(R.id.register_pwd_edt);
		nextstepBtn=(Button)findViewById(R.id.register_step_one);
		nextstepBtn.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		super.onClick(v);
		int id=v.getId();
		switch (id) {
		case R.id.register_step_one:
			String phoneNum=phoneEdt.getText().toString().trim();
			String pwd=pwdEdt.getText().toString().trim();
			if (TextUtils.isEmpty(phoneNum)) {
				showMsg("请填写手机号!");
				phoneEdt.requestFocus();
				return;
			}
			if (!SystemUtils.isMobile1(phoneNum)) {
				showMsg("请填写正确的手机号!");
				phoneEdt.requestFocus();
				return;
			}
			if (TextUtils.isEmpty(pwd)) {
				showMsg("请输入密码!");
				pwdEdt.requestFocus();
				return;
			}
			if (pwd.length()<6||pwd.length()>20) {
				showMsg("请输入符合规范的密码!");
				pwdEdt.requestFocus();
				return;
			}
			Intent intent=new Intent(this, RegisterAuthCodeActivity.class);
			intent.putExtra(PHONE, phoneNum);
			intent.putExtra(PWD, pwd);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}
}
