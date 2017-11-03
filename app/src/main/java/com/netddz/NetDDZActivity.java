package com.netddz;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

@SuppressLint("HandlerLeak")
public class NetDDZActivity extends Activity {

	public final static int MENU = 0;
	public final static int GAME = 1;
	public final static int EXIT = 2;
	public final static int SMALL_CARD = 3;
	public final static int WRONG_CARD = 4;
	public final static int EMPTY_CARD = 5;
	public final static int NO_CARD = 6;
	public static int SCREEN_WIDTH;
	public static int SCREEN_HEIGHT;
	public static double SCALE_VERTICAL;
	public static double SCALE_HORIAONTAL;
	public static Handler handler;
	private MenuView mv;
	private GameView gv;
	private static NetDDZActivity instance = null;

	public static NetDDZActivity getInstance(){
		return instance;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		instance = this;
		DisplayMetrics dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		SCREEN_WIDTH = dm.widthPixels;
		SCREEN_HEIGHT = dm.heightPixels;
		if (SCREEN_HEIGHT > SCREEN_WIDTH) {
			int temp = SCREEN_HEIGHT;
			SCREEN_HEIGHT = SCREEN_WIDTH;
			SCREEN_WIDTH = temp;
		}
		System.out.println(SCREEN_HEIGHT + "X" + SCREEN_WIDTH);
		SCALE_VERTICAL = SCREEN_HEIGHT / 320.0;
		SCALE_HORIAONTAL = SCREEN_WIDTH / 480.0;
		System.out.println(SCALE_VERTICAL + " and " + SCALE_HORIAONTAL);

		mv = new MenuView(this);
		gv = new GameView(this.getApplicationContext());
		setContentView(gv);
		handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);

				switch (msg.what) {
					case 0 :
						setContentView(mv);
						break;
					case 1 :
						setContentView(gv);
						break;
					case 2 :
						finish();
						break;
					case 3 :
						Toast.makeText(getApplicationContext(), "你的牌太小！", Toast.LENGTH_SHORT)
								.show();
						break;
					case 4 :
						Toast.makeText(getApplicationContext(), "出牌不符合规则！", Toast.LENGTH_SHORT)
								.show();
						break;
					case 5 :
						Toast.makeText(getApplicationContext(), "请出牌！", Toast.LENGTH_SHORT).show();
						break;
					case 6 :
						Toast.makeText(getApplicationContext(), "没有可用牌", Toast.LENGTH_SHORT).show();
						break;
				}
			}

		};

		first();
	}

	String[] names = {"A","B","C"};
	//模拟网络发牌//获取牌 from net
	public void first(){
		int[] allCards = new int[54];// 一副扑克牌
		for (int i = 0; i < allCards.length; i++) {
			allCards[i] = i;
		}
		CardsManager.shuffle(allCards);//洗牌，
		int[][] playerCards = new int[4][17];
		for (int i = 0; i < 51; i++) {
            playerCards[i / 17][i % 17] = allCards[i];
        }
		playerCards[3][0] = allCards[51];
		playerCards[3][1] = allCards[52];
		playerCards[3][2] = allCards[53];
		int dizhi = CardsManager.getBoss();
		gv.desk.init(playerCards,dizhi);
	}

	private int[] person1 = {};
	private int[] person2 = {};

	public int[] getOtherPerson(int currentId){
		if(currentId == 1){
			person1 = gv.desk.players[currentId].getPokeWanted(gv.desk.cardsOnDesktop);
			return person1;
		}else if(currentId == 2){
			person2 = gv.desk.players[currentId].getPokeWanted(gv.desk.cardsOnDesktop);
			return person2;
		}
		return null;
	}

	//接收数据
	private void receiveData(String string){
		//解析
		if(true){
			//房间进入人

		}else if(true){
			//三个人
			//游戏开始 发牌及当前属于哪一个位置

		}else if(true){
			//接收别人的牌，确定下一个发牌人

		}else if(true){
			//结束后信息显示

		}else if(true){
			//退出房间

		}
	}

	//发送数据
	public void sendCards(int[] chupaiCards){
		//发送牌的信息
		Log.i("netddz",chupaiCards.toString());
	}

	//进入房间，自动发送开始
	public void startGame(){

	}
}
