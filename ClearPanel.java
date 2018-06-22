package Click;

import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import java.awt.event.*;
import java.io.*;


class ClearPanel extends JPanel implements ActionListener
{

	protected JLabel time = new JLabel("",JLabel.CENTER);
	protected JLabel Rname = new JLabel("aa",JLabel.CENTER);
	protected DefaultListModel model;
	protected JList list;
	protected JLabel clear;
	protected JButton Btouroku;
	protected int cnt = 0;
	protected JTextField name;
	protected String path = "", fpath = "";
	protected JScrollPane sp;
	protected ArrayList<String> array = new ArrayList<String>();
	protected JPanel topPanel, ctopPanel, centerpPanel,cTourokuPanel, cRPanel;
	protected ArrayList<Integer> timenum = new ArrayList<Integer>();//ランキングを作成するときに使う

	public ClearPanel() {

		/* topPanel clearとクリアタイムを表示する
		 * ctopPanel　cTourokuPanelとcRPanelをもつ
		 * centerpPanel　ctopPanelをもちリストビューを持つ
		 * cTourokuPanel　名前入力と登録ボタンをもつ
		 * cRPanel　何のランキングかを表示する
		 * 
		 */
		
		//ランキングフォルダ作成
		fpath = new File(".").getAbsoluteFile().getParent() + "/ランキング";
		File newdir = new File(fpath);

        if (!newdir.exists()) {
            //フォルダ作成実行
        	newdir.mkdir();
        }

		
		topPanel = new JPanel();
		ctopPanel = new JPanel();
		centerpPanel = new JPanel();
		cTourokuPanel = new JPanel();
		cRPanel = new JPanel();


		this.setLayout(new BorderLayout());
		//２つのパネルを加える
		this.add( topPanel,  BorderLayout.PAGE_START);
		this.add( centerpPanel,  BorderLayout.CENTER);
		centerpPanel.setLayout(new BorderLayout());
		

		//センターパネルにさらにパネルを追加
		centerpPanel.add( ctopPanel,  BorderLayout.NORTH);


		//クリアのみ表示するラベル
		clear = new JLabel("CLEAR!!",JLabel.CENTER);
		clear.setFont(new Font("MS ゴシック",Font.BOLD,30));
		time.setFont(new Font("MS ゴシック",Font.BOLD,30));
		Rname.setFont(new Font("MS ゴシック",Font.BOLD,30));

		//登録ボタンとリスタートボタン
		Btouroku = new JButton("登録");
		Btouroku.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
		Btouroku.addActionListener(this);
		JButton restart = new JButton("RESTART");
		restart.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 22));
		restart.addActionListener(this);

		//リストビューの設定
		model = new DefaultListModel();
		list = new JList(model);
		list.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 22));
		sp = new JScrollPane();
		sp.getViewport().setView(list);
		sp.setPreferredSize(new Dimension(80,80));
		centerpPanel.add(sp);

		//名前を入力できるようにする
		name = new JTextField(5);
		name.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 18));
		ctopPanel.setLayout(new GridLayout(2,1));
		cTourokuPanel.setLayout(new  GridLayout());

		//テキストフィールドと登録ボタンを追加
		cTourokuPanel.add(name);
		cTourokuPanel.add(Btouroku);
		ctopPanel.add(cTourokuPanel);

		//何のランキングかを示すpanelを追加
		ctopPanel.add(cRPanel);
		cRPanel.add(Rname);

		this.add(restart,BorderLayout.SOUTH);//リスタートボタンをセット

		//クリア時間とクリアラベルを追加
		topPanel.setLayout(new GridLayout());
		topPanel.add(time ,BorderLayout.PAGE_START);
		topPanel.add(clear,BorderLayout.WEST);

	}

	//**:**:**と表示されているものを******のint型で変換するようにする
	public int GetTimeNum(String str){

		String[] strtime;
		strtime = str.split(":");
		return new Integer(strtime[0] + strtime[1] + strtime[2] ).intValue();

	}

	//クリア時間をセットするメソッド
	public void SetTimeText( ){
		time.setText(ClickInfo.tLabel.getText() );
	}

	//リストビューにランキングをセットする
	public void SetListRanking(){

		//ファイル作成
		try{
			File fl = new File( fpath + "/ranking" + ClickInfo.choice + ".txt" );
			/* このインスタンスからそのファイルがまだ存在しない場合だけ
			空の新しいファイル生成します。*/
			fl.createNewFile();		
			path = fl.getAbsolutePath();
		}catch(IOException e){
			System.out.println(e + "例外が発生しました");
		}

		try{
			//ファイルを読み込む
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);

			//読み込んだファイルを１行ずつ画面出力する
			String line;
			while ((line = br.readLine()) != null) {

				//読み込んだ行を追加していく
				array.add(line);
				cnt++;
			}
			//終了処理
			br.close();
			fr.close();

		} catch (IOException ex) {
			//例外発生時処理
			ex.printStackTrace();
		}


		//既にランキングが存在していれば
		if( cnt != 0 ){
			/* 初期データをモデルに追加する */
			StringBuffer sb;
			String[] tempstr;

			for (int i = 0 ; i < cnt ; i++){

				tempstr = array.get(i).split(" ");
				timenum.add(GetTimeNum(tempstr[1]));//ランキング変更で使用するかもしれないので追加しておく

				//リストビューに項目を表示していく
				sb = new StringBuffer();
				sb.append("" + ( i + 1 ) + "位 " + array.get(i));
				model.addElement(new String(sb));
			}
		}
		String rstr = "";

		//何を選んだか場合わけ
		if( ClickInfo.choice == 1 ){
			rstr = "初級ランキング";
		}
		else if( ClickInfo.choice == 2 ){
			rstr = "中級ランキング";
		}
		else{
			rstr ="上級ランキング";
		}
		Rname.setText(rstr);

	}
	public void actionPerformed(ActionEvent e){

		//登録ボタンが押された場合
		if( e.getSource() == Btouroku ){
			String tempname = "";
			//入力名前を取得
			tempname = name.getText();

			//空白でない場合
			if( tempname.equals("") == false ){

				//クリアタイムを取得
				int cleartime = GetTimeNum(time.getText());
				int i;

				//今表示されてるランキングのなかで何番目に入るかを調べるため
				for( i = 0; i < cnt; i++ ){

					if( cleartime < timenum.get(i)){
						break;
					}
				}
				String element = name.getText() + " " +  time.getText();
				array.add(i, element);//i番目にelementを追加する
				cnt++;

				/*ランキングファイル更新作業！！*/

				try{
					File fl = new File( path );

					//存在する場合は削除する
					if( fl.exists()){
						fl.delete();
					}
					//再度作る
					fl.createNewFile();		

				}catch(IOException j){
					System.out.println(j + "例外が発生しました");
				}

				try{
					File fl = new File(path);
					BufferedWriter bw = new BufferedWriter(new FileWriter(fl));

					//arrayに最新のランキングが入っているのでファイルに書き込んでいく
					for( int k = 0; k < cnt; k++ ){
						bw.write(array.get(k));
						bw.newLine();
					}
					bw.close();	

					//登録ボタンはもう使えないようにする
					Btouroku.setEnabled(false);
					name.setText("");
					StringBuffer sb;
					model.clear();

					//リストビューを更新する
					for (int k = 0 ; k < cnt ; k++){
						sb = new StringBuffer();
						sb.append( "" + ( k + 1 ) + "位 " + array.get(k));
						model.addElement(new String(sb));
					}

				}catch( IOException f ){
					System.out.println(f + "例外が発生しました");
				}

			}
		}

		//リスタートが押された場合
		else{

			//初期化作業
			cnt = 0;
			array.clear();
			timenum.clear();
			model.clear();
			path = "";
			name.setText("");
			Btouroku.setEnabled(true);

			//ゲームパネルを削除して（レベルによってゲーム画面が変わるため)、スタート画面に戻す
			ClickInfo.cardPanel.remove(ClickInfo.GPanel);
			ClickInfo.layout.first(ClickInfo.cardPanel);
		}

	}
}
