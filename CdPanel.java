package Click;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

//カウントダウンを表示するパネル
public class CdPanel extends JPanel implements ActionListener {
	int cnt = 5;//始まりのカウントを５からにしたいため
	private Timer ctimer;
	JLabel countlabel = new JLabel("" + cnt, JLabel.CENTER);//Labelにカウントダウンを表示していく


	public CdPanel(){

		//ラベルを付け加えるのみ
		this.setLayout(new BorderLayout());
		countlabel.setFont(new Font("MS ゴシック",Font.BOLD,70));
		this.add(countlabel, BorderLayout.CENTER);
		this.setVisible(true);
	}
	//タイマースタート
	public void CountDown( ){
		ctimer = new Timer(1000, this); // 1秒毎にactionPerformedを呼び出し
		ctimer.start();
	}
	public void actionPerformed(ActionEvent e){

		//cntが０の場合、ゲームを開始させる
		if ( cnt == 0 ){

			//スタートを表示して、ゲーム画面に移る
			countlabel.setText("スタート!!");
			cnt--;
		}
		else if( cnt == -1){
			ctimer.stop();
			cnt = 5; //リスタートのときようにセットしておく
			countlabel.setText("" + cnt);

			//ゲーム画面にスイッチ
			ClickInfo.layout.last(ClickInfo.cardPanel);

			//ゲーム時間の測定開始
			ClickInfo.tLabel.TimeSokutei();	
		}
		else{
			countlabel.setText("" + cnt );
			cnt--;
		}
	}
}

