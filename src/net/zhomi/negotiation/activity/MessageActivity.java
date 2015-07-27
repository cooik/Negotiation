package net.zhomi.negotiation.activity;

import java.util.ArrayList;
import java.util.List;

import net.zhaomi.negotiation.R;
import net.zhomi.negotiation.bean.MsgBean;
import net.zhomi.nogotiation.adapter.MsgAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class MessageActivity extends BaseActivity {
	List<MsgBean> msg = new ArrayList<MsgBean>();
	ListView msg_lv;
	MsgAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_message);
		initMsg();
		initView();

	}

	private void initView() {
		initTitle();
		title.setText("消息");
		back.setBackgroundResource(R.drawable.ic_launcher);
		back.setVisibility(View.VISIBLE);
		msg_lv = (ListView) findViewById(R.id.lv_msg);
		adapter = new MsgAdapter(this, R.layout.message_items, msg);
		msg_lv.setAdapter(adapter);

	}

	private void initMsg() {
		MsgBean msg1 = new MsgBean();
		MsgBean msg2 = new MsgBean();
		msg1.setDate("2015-08-08");
		msg1.setTime("12:00:00");
		msg2.setDate("2015-07-07");
		msg2.setTime("1:00:00");
		msg1.setContent("最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯");
		msg2.setContent("最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯最新资讯");
		msg.add(msg1);
		msg.add(msg2);

	}
}
