package net.zhomi.negotiation.activity;

import net.zhaomi.negotiation.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class AddNewCustomerActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addcustomer);
		initView();
	}

	private void initView() {
		initTitle();
		title.setText("新建用户");
		back.setBackgroundResource(R.drawable.ic_launcher);
		menu_search.setBackgroundResource(R.drawable.ic_launcher);
		back.setVisibility(View.VISIBLE);
		menu_search.setVisibility(View.VISIBLE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(AddNewCustomerActivity.this, "new customer",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});
		menu_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(AddNewCustomerActivity.this, "提交",
						Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}
}
