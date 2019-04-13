package com.example.flipperplayer;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class MainActivity extends Activity implements OnClickListener {
	// 准备图片资源
	private int[] imgs = { R.drawable.photo1, R.drawable.photo2,
			R.drawable.photo3 };
	private ViewFlipper flipper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		flipper = (ViewFlipper) findViewById(R.id.images);

		// 初始化视图
		for (int i = 0; i < imgs.length; i++) {
			LayoutInflater inflater = LayoutInflater.from(this);
			View convertView = inflater.inflate(R.layout.item_view, null);

			// 创建imageview控件
			ImageView iv = (ImageView) convertView.findViewById(R.id.item_img);
			iv.setImageResource(imgs[i]);
			iv.setScaleType(ImageView.ScaleType.FIT_XY);

			// 创建TextView
			TextView view = (TextView) convertView.findViewById(R.id.item_name);
			view.setText("第" + i + "涨");

			// 将imageview控件放入ViewFlipper中，参数一：imageview控件，参数二：控件在布局中的位置，默认居中父窗口
			flipper.addView(convertView);
		}
		// 图片播放的时间
		flipper.setFlipInterval(5000);
		// 是否允许自动播放
		flipper.setAutoStart(true);
		// 调用isFlipping方法来判断ViewFlipper是否在进行页面的轮播或者切换，isAutoStart是否允许自动播放
		if (flipper.isAutoStart() && !flipper.isFlipping()) {
			// 启动，开始播放
			flipper.startFlipping();
		}

		findViewById(R.id.s).setOnClickListener(this);
		findViewById(R.id.x).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.s:
			flipper.showPrevious();
			break;

		default:
			flipper.showNext();
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
