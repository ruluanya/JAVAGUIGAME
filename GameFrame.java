package Click;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.Timer;

/*ゲームフレームを生成するクラス
 スタート画面
 カウントダウン
 クリアー画面をCardLayoutで貼付ける

 ゲーム画面のパネルは最後に貼付ける。（リスタートをスムーズにするため)

 */
public class GameFrame extends JFrame implements ActionListener{

	protected ButtonGroup group = new ButtonGroup();
	protected JPanel StartPanel;
	protected JPanel CenterPanel, BottomPanel, RadioPanel;
	protected CdPanel CountDownPanel;
	protected Timer timer;
	protected boolean blinkFlag = true;
	protected JPanel btnPanel;
	protected JRadioButton radio1;
	protected JRadioButton radio2;
	protected JRadioButton radio3;

	public static void main(String[] args){

		//ゲームフレーム生成
		new GameFrame();
		
	}

	//コンストラクタ
	GameFrame(){
		/* スタート画面パネル 
		 * StartPanelが最も親のパネル
		 * CenterPanelは点滅するパネル
		 * BottomPanelはスタートボタンのパネル
		 * RadioPanelはラジオボタンを持つパネル
		 * */
		
		StartPanel = new JPanel();
		CenterPanel = new JPanel();
		BottomPanel = new JPanel();
		RadioPanel = new JPanel();

		StartPanel.setLayout(new BorderLayout());
		StartPanel.add( CenterPanel, BorderLayout.CENTER);
		StartPanel.add( BottomPanel, BorderLayout.PAGE_END);
		BottomPanel.setLayout(new BorderLayout() );
		BottomPanel.add(RadioPanel, BorderLayout.CENTER);
		RadioPanel.setLayout(new GridLayout());
		CenterPanel.setLayout(new BorderLayout());

		//タイトル追加
		JLabel Title = new JLabel( "クリックザナンバー", JLabel.CENTER);
		Title.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 50));
		Title.setForeground(Color.white);
		CenterPanel.add( Title, BorderLayout.CENTER );

		//カウントダウンパネル
		CountDownPanel = new CdPanel();
		
		//クリア画面パネル
		ClickInfo.ClPanel = new ClearPanel();

		//カードレイアウト生成
		ClickInfo.cardPanel = new JPanel();
		ClickInfo.layout = new CardLayout();
		ClickInfo.cardPanel.setLayout(ClickInfo.layout);

		//スタート、カウントダウン、クリアパネルを貼付けていく
		ClickInfo.cardPanel.add(StartPanel, "button");
		ClickInfo.cardPanel.add(CountDownPanel, "label");
		ClickInfo.cardPanel.add(ClickInfo.ClPanel, "4" );


		/* スタートボタン */
		JButton startButton = new JButton("スタート");
		startButton.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
		startButton.addActionListener(this);
		startButton.setActionCommand("スタート");
		BottomPanel.add( startButton, BorderLayout.SOUTH);

		//ラジオボタンをセット
		radio1 = new JRadioButton("初級");
		radio2 = new JRadioButton("中級");
		radio3 = new JRadioButton("上級");
		
		radio1.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
		radio2.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
		radio3.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
		
		
		//初めに初級を選択状態にしておく
		radio1.setSelected(true);

		//グループかして一つしかラジオボタンを選択できないようにする
		group.add( radio1 );
		group.add( radio2 );
		group.add( radio3 );

		RadioPanel.add( radio1 ); 
		RadioPanel.add( radio2 ); 
		RadioPanel.add( radio3 ); 

		getContentPane().add(ClickInfo.cardPanel, BorderLayout.CENTER);
		
		//パネルの点滅タイマー
		timer = new Timer(500, new TimerListener() );
		timer.start();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(850, 600);
		this.setTitle("クリックザナンバー");
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent e){
		String cmd = e.getActionCommand();
		boolean status = false;
		int smax = 0;

		status = radio1.isSelected();
		ClickInfo.choice = 1;
		
		if( status == false ){
			status = radio2.isSelected();
			ClickInfo.choice = 2;
		}
		if( status == false ){
			status = radio3.isSelected();
			ClickInfo.choice = 3;
		}

		if (cmd.equals("スタート")){
			ClickInfo.GPanel = new GamePanel();
			ClickInfo.cardPanel.add(ClickInfo.GPanel, "dd" );

			CountDownPanel.setBackground(Color.white);
			ClickInfo.layout.next(ClickInfo.cardPanel);
			CountDownPanel.CountDown( );
			
		}
	}
	
	//点滅パネル
	private class TimerListener implements ActionListener{
		// TODO 自動生成されたコンストラクター・スタブ
		public void actionPerformed(ActionEvent e) {
			if (blinkFlag) {
				changeColorGray();
			} else {
				changeColorLGray();
			}
			blinkFlag = !blinkFlag;
		}
	}
	private void changeColorGray() {
		CenterPanel.setBackground(Color.gray); 
		CenterPanel.setOpaque(true);
	}
	private void changeColorLGray() {
		CenterPanel.setBackground(Color.LIGHT_GRAY);      
		CenterPanel.setOpaque(true);
	}
}
