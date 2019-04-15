# FlipperPlayer 滚动图片 电视背景滚动图片效果 IPTV桌面滚动图片效果 图片倒影 滚动广告图
### 文章内容
 1. ViewFlipper简单使用
 2. 图片倒影 
 3. 遥控器左右键切换图片
 4. Demo地址
 <hr/>
 我又没钱买奔驰，我也来谈996，再说我又不是刘*东，也没法带这些996的兄弟们一起到国外去喝奶茶。不对不对，跑题了。<br/><br/>好，那么下面我们来进入正题。话说我如果是电商王子，我就叫小马，我全力支持996这种工作模式，别问我为什么，因为我对钱没有兴趣。<br/><br/>嗨，终究是沉浸于逻辑程序逻辑中无法自拔了，写个博客都要跑题半天。好吧话不多说，话说这个IPTV桌面背景图片自动播放效果，嗯，IPTV之所以有这般夕阳红的景象多得益于*电的垄断式运营模式。靠<br/><br/>跑题了！
 
 <hr/>
 
### ViewFlipper简单使用
xml定义

```
<ViewFlipper
        android:id="@+id/images"
        android:layout_width="match_parent"
        android:layout_height="match_parent" >
</ViewFlipper>
```
Activity怎么弄

```
    private int[] imgs = { R.drawable.photo1, R.drawable.photo2,
			R.drawable.photo3 };
	private ViewFlipper flipper;
	private int delayMillis = 5 * 1000;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		flipper = (ViewFlipper) findViewById(R.id.images);
		LayoutInflater inflater = LayoutInflater.from(this);

		for (int i = 0; i < imgs.length; i++) {
			ImageView iv=new ImageView ();
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
			iv.setBackgroundResource( imgs[i]);
			flipper.addView(iv);
		}
		// 图片播放的时间
		flipper.setFlipInterval(delayMillis);
		// 是否允许自动播放
		flipper.setAutoStart(true);
		// 启动，开始播放
		if (flipper.isAutoStart() && !flipper.isFlipping()) {
			flipper.startFlipping();
		}
}
```
<hr/>

###  图片倒影 
自动播放图片就是这么简单，不加一点动画或图片处理什么的也太对不起996大佬对我们的厚爱了，下面做一下图片倒影处理；

```
public static Bitmap createReflectedImage(Context context, int drawable) {
		Bitmap bmp = BitmapFactory.decodeResource(context.getResources(),
				drawable);
		final int reflectionGap = 1;
		int width = bmp.getWidth();
		int height = bmp.getHeight();

		Matrix matrix = new Matrix();
		matrix.preScale(1, -1);
	
		Bitmap reflectionImage = Bitmap.createBitmap(bmp, 0, height / 2, width,
				height / 2, matrix, false);

		Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
				(height + height / 5), Config.ARGB_8888);

		Canvas canvas = new Canvas(bitmapWithReflection);
		canvas.drawBitmap(bmp, 0, 0, null);

		Paint defaultPaint = new Paint();
		canvas.drawRect(0, height, width, height + reflectionGap, defaultPaint);
		canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

		Paint paint = new Paint();
		LinearGradient shader = new LinearGradient(0, bmp.getHeight(), 0,
				bitmapWithReflection.getHeight() + reflectionGap, 0x70000000,
				0x00000000, TileMode.MIRROR);

		paint.setShader(shader);
		paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));

		canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
				+ reflectionGap, paint);

		return bitmapWithReflection;
	}
```
<hr/>

### 遥控器左右键切换图片
完了该滚的也滚了，该倒的也倒过来了，别误解，我说的是图片哈，下面来说一下实际应该场景吧！这玩意一般可以用于滚动广告，酒店服务介绍界面等，好吧，举一个栗子，遥控器左右键切换；

```
@Override
public boolean dispatchKeyEvent(KeyEvent event) {
		int keyCode = event.getKeyCode();
		final boolean uniqueDown = event.getRepeatCount() == 0
				&& event.getAction() == KeyEvent.ACTION_DOWN;

		// 上键上一页
		if (uniqueDown && keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
			flipper.showPrevious();
			pauseFlipping();
			return true;
		}

		// 按右键下一页
		else if (uniqueDown && keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
			flipper.showNext();
			pauseFlipping();
			return true;
		}

		return super.dispatchKeyEvent(event);
	}

/**
 * 暂停一会了
 */
public void pauseFlipping() {
		flipper.stopFlipping();
		if (startFlippingTask != null) {
			startFlippingTask.cancel();
			startFlippingTask = null;
		}
		startFlippingTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				handler.sendEmptyMessage(1);
			}
		};
		mTimer.schedule(startFlippingTask, delayMillis);
}
	
/**
 * 开始播放
 */	
public Timer mTimer = new Timer();
public TimerTask startFlippingTask;
@SuppressLint("HandlerLeak")
Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			flipper.startFlipping();
		};
};
```
来来来，总结一下，其实不用写这一推没用，只是为了好看一点罢了，当然你可以做一下手势判断，做左右滑动切换，看下面这里：

 * flipper.showPrevious();//上一张 
 * flipper.showNext();//下一张
 * flipper.startFlipping();//开始播放
<hr/>

### Demo地址
GitHub:https://github.com/Life1412378121/FlipperPlayer.git
CSDN:https://download.csdn.net/my
<img src="https://img-blog.csdnimg.cn/20190415194005388.gif"/>
