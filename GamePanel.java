package Click;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.util.ArrayList;
import java.util.Collections;
import java.awt.event.*;


//ゲーム画面のパネル
class GamePanel extends JPanel implements ActionListener
{
	protected JPanel InfoPanel, MainPanel;
	protected  int MAX;
	protected JLabel nextlabel = new JLabel("NEXT --> 1", JLabel.RIGHT);
	protected int j=0;
	protected JButton Bhinto;
	protected JButton[] NumButton;
	protected int[] num;
	protected int smax;

	public  GamePanel() {

		smax = 0;
		/*choiceによって場合分け
		 * choice 1: 3x3
		 * choice 2: 5x5
		 * choice 3: 10x10
		 */
		if( ClickInfo.choice == 1){
			smax = 3;
		}
		else if(ClickInfo.choice == 2){
			smax = 5;
		}
		else{
			smax = 10;
		}
		//MAXはタッチボタンの数
		MAX = smax * smax;
		num = new int[MAX];
		NumButton = new JButton[MAX];
		MainPanel = new JPanel();
		InfoPanel = new JPanel();
		MainPanel.setLayout(new GridLayout(smax,smax));
		InfoPanel.setLayout(new GridLayout() );

		//配列numにランダムで整数が入るようにするメソッド
		shuffle( ); 
		nextlabel.setFont(new Font("MS ゴシック",Font.BOLD,26));

		//ボタンをセットしていく
		for(int i=0; i<MAX; i++){	    
			NumButton[i] = new JButton(num[i]+1+"");
			NumButton[i].setPreferredSize(new Dimension(100,100));
			NumButton[i].setFont(new Font("MS ゴシック",Font.BOLD,25));
			NumButton[i].setActionCommand(num[i]+2+"");
			NumButton[i].setBorder(new LineBorder(Color.BLACK, 1, true));
			NumButton[i].addActionListener(this);
			MainPanel.add(NumButton[i]);
		}
		
		//ヒントボタン
		Bhinto = new JButton("ヒント");
		Bhinto.setActionCommand("ヒント");
		Bhinto.addActionListener(this);

		//ゲーム時間を測定する
		ClickInfo.tLabel = new TimerLabel();
		InfoPanel.add( ClickInfo.tLabel);
		InfoPanel.add(Bhinto);
		InfoPanel.add(nextlabel);
	
		this.setLayout(new BorderLayout());
		this.add(MainPanel,BorderLayout.CENTER);
		this.add(InfoPanel,BorderLayout.PAGE_START);

	}
	
	//配列numにランダムに整数をセットするメソッド
	public  void shuffle( ){
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i=0; i<MAX; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		for(int i=0; i<MAX; i++){
			num[i] = list.get(i);   
		}
	}

	public void actionPerformed(ActionEvent e){

		String es=e.getActionCommand();
		int k;
		
		//ヒントであった場合
		if( es == "ヒント"){
			String nextstr = nextlabel.getText();
			
			//数字の部分のみを取り出す
			nextstr = nextstr.substring(9);
			for(k = 0; k<MAX; k++){
				
				//一致した場合、色を赤にしてあげる
				String bstr = NumButton[k].getText();
				if( bstr.equals(nextstr) ){
					NumButton[k].setForeground(Color.red);
					break;
				}
			}    
		}
		
		//数字のボタンが押された場合
		else{

			//ネクストがさすボタンを押した場合
			if(Integer.parseInt(es) == j+2){
				
				//nextを次の値に更新
				nextlabel.setText("NEXT --> "+es);
				for(k=0; k<MAX; k++){
					
					//押されたボタンを背景をグレイにする
					if(num[k] == j){

						NumButton[k].setForeground(Color.BLACK);	
						NumButton[k].setBackground(Color.lightGray);	
						break;
					}
				}
				//最後のボタンを押した場合
				if(Integer.parseInt(es) == MAX+1){
				
					ClickInfo.sokutei.stop();
					j = -1;
					
					//５００ミリ秒ほど待たせる
					try { Thread.sleep(500); } catch (InterruptedException j) { }

					ClickInfo.ClPanel.SetTimeText();
					ClickInfo.ClPanel.SetListRanking();
					
					//クリア画面はゲームパネルの前に有るのでprevious
					ClickInfo.layout.previous(ClickInfo.cardPanel);
					
					//リスタートよう
					for( int n = 0 ;n < MAX; n++ ){
						NumButton[n].setForeground(Color.BLACK);
					}
					nextlabel.setText("NEXT --> 1");
				}
				j++;
			} else {
				java.awt.Toolkit.getDefaultToolkit().beep();
			}
		}
	}
}