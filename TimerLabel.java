package Click;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;

import javax.swing.Timer;


//ゲーム時間を測定するLabel
public class TimerLabel extends JLabel implements ActionListener {

	/*
	 * ms ミリ秒
	 * s  秒
	 * minute　分
	 *
	 */
	public int ms = 0;
	public int s = 0;
	public int minute = 0;
	private String msstr = "", sstr = "", minstr = "";
	
	//00;00;00のみセットしておく
	public TimerLabel(){
		
		this.setFont(new Font("MS ゴシック",Font.BOLD,26));
		this.setText("00:00:00");
		this.setVisible(true);

	}

	public void TimeSokutei( ){
		ms = 0;
		s = 0;
		minute = 0;
		
		//測定スタート
		ClickInfo.sokutei = new Timer(10, this); // 10ミリ秒毎にactionPerformedを呼び出し
		ClickInfo.sokutei.start();
	}
	public void actionPerformed(ActionEvent e){
		ms++;
		
		//1秒にする
		if( ms == 100 ){
			s++;
			ms = 0;
		}
		//１分にする
		if( s == 60 ){

			minute++;
			s = 0;
		}
		//int->string変換
		msstr = String.valueOf(ms);
		sstr = String.valueOf(s);
		minstr = String.valueOf(minute);


		//二桁では無い場合0を先頭につけたす
		/******関数かしたほうははやい？*******/
		if( ms < 10){
			msstr = "0" + ms;
		}
		if( s < 10){
			sstr = "0" + s;
		}
		if( minute < 10){
			minstr= "0" + minute;
		}

		//時間をセット
		this.setText(minstr + ":" + sstr + ":" + msstr + "" );
	}

}
