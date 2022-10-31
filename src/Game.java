// 20205134 조유빈
// 2021년 2학기 JAVA 텀프로젝트

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;


public class Game {

	Solution s = new Solution();	// 정답 들어있는 배열 가져오기 위함

	private final String BG_PIC = "bg.jpg";
	private final String SET_BG_PIC = "setting_background.jpg";
	private final String GAME_BG_PIC = "game_background.jpg";
	private final String READY_PIC = "instruction.png";
	private final String SUSPEND_PIC = "suspend.png";
	private final String FRAME_ICON = "icon.png";
	private final String SUCCESS_IMG = "stars.gif";
	private final String BGM = "bgm.wav";
	private Clip bgmClip;

	private final String X = "x.png";
	ImageIcon success_img = new ImageIcon(SUCCESS_IMG);
	ImageIcon x_img = new ImageIcon(X);

	Color gridButtonColor=Color.white;
	private boolean isCoverPanel = true;	// 설정 패널에서 돌아갈 패널 선택하기 위함 -> true면 커버 패널로, false면 게임 패널로 돌아감
	private final int ROW = 15;
	private final int COLUMN = 15;

	int[][] solution = s.s1;
	int question=0;	// 문제 번호

	int[][] answer = new int[ROW][COLUMN];

	Font customFont;
	JFrame frame;
	JPanel mainPanel;
	CoverPanel coverPanel;
	ReadyPanel readyPanel;
	TransparentPanel finishPanel;
	JPanel coverButtonPanel;
	GamePanel gamePanel;
	TransparentPanel buttonPanel;
	TransparentPanel displayResultPanel;
	TransparentPanel displayGamePanel;
	TransparentPanel displayCardPanel;
	DisplaySuspendPanel displaySuspendPanel;
	TransparentPanel displayGameCardPanel;
	SettingPanel settingPanel;
	GridButton [][]square;
	TransparentPanel displayPanel;
	TransparentPanel gridPanel;
	TransparentPanel gridRowLabelPanel;
	TransparentPanel gridColLabelPanel;
	JLabel timeLabel;
	JLabel infoLabel;
	JLabel resultLabel;
	JLabel starImage;

	JLabel gridLabel;

	GridBagLayout gridBag;
	GridBagConstraints gbc;

	Button backButton;
	Button startButton;
	SettingButton settingButton, gameSettingButton;
	EndButton endButton, gameEndButton;
	Button timerStartButton;
	Button hintButton;
	Button newGameButton;
	Button musicControlButton;

	CardLayout card, displayCard, displayGameCard;


	TimerListener timerListener;
	Timer timer = new Timer(1000, timerListener);

	boolean fontSettingFailed = false;	// 폰트 불러오기 실패하면 true
	boolean timerStarted = false;
	boolean gridClicked = false;	// 마우스 버튼 클릭한 상태로 드래그할 때 여러개 버튼 한번에 칠하기 위한 플래그
	boolean succed = false;	// 게임 성공하면 true

	public static void main(String[] args) {

		new Game().go();


	}

	public void go() {


		try {
			customFont = Font.createFont(Font.TRUETYPE_FONT, new File("neodgm.ttf"));
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);

		}
		catch (IOException|FontFormatException e) {
			System.out.println("폰트 불러오기 실패\n");
			fontSettingFailed=true;	
		} // 버튼 폰트 컴퓨터에 설치할 필요 없이 프로그램에서 이용하기 위함


		getSolution();	// 정답 가져오기

		frame = new JFrame("N O N O G R A M");

		mainPanel = new JPanel();
		coverPanel = new CoverPanel();
		coverButtonPanel = new JPanel();
		gamePanel = new GamePanel();
		displayPanel = new TransparentPanel();
		gridPanel = new TransparentPanel();
		buttonPanel = new TransparentPanel();
		displayResultPanel = new TransparentPanel();
		displayGamePanel = new TransparentPanel();
		displaySuspendPanel = new DisplaySuspendPanel();
		displayGameCardPanel = new TransparentPanel();
		displayCardPanel = new TransparentPanel();
		settingPanel = new SettingPanel();
		readyPanel=new ReadyPanel();
		starImage=new JLabel();

		gridBag = new GridBagLayout();
		gbc = new GridBagConstraints();

		card = new CardLayout();
		mainPanel.setLayout(card);
		mainPanel.add("시작 화면", coverPanel);
		mainPanel.add("게임 화면", gamePanel);
		mainPanel.add("설정 화면", settingPanel);

		frame.add(mainPanel);
		// card.show(mainPanel, "시작 화면");		-> 안 써줘도 됨. CardLayout은 기본값으로 가장 먼저 add된 컴포넌트를 보여줌

		startButton = new Button("시작", 120, 60, 24);
		startButton.addActionListener(new StartListener());
		settingButton = new SettingButton(120, 60, 24);
		endButton = new EndButton(120, 60, 24);
		timerStartButton = new Button("시작", 120, 40, 20);
		timerStartButton.addActionListener(new TimerStartListener());
		gameSettingButton = new SettingButton(120, 40, 20);
		gameEndButton = new EndButton(120, 40, 20);
		newGameButton = new Button("새 게임", 120, 40, 20);
		newGameButton.addActionListener(new NewGameListener());
		hintButton = new Button("힌트", 120, 40, 20);
		hintButton.addActionListener(new HintListener());
		backButton = new Button("이전", 150, 60, 24);
		backButton.addActionListener(new BackListener());
		musicControlButton = new Button("BGM 끄기", 150, 60, 24);
		musicControlButton.addActionListener(new MusicControlListener());


		settingPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.Y_AXIS));
		settingPanel.setBorder(new EmptyBorder(new Insets(250, 100, 250, 100)));	// 상, 좌, 하, 우 여백주기 위함

		musicControlButton.setMaximumSize(new Dimension(150, 60));	// BoxLayout은 setPreferredSize()로는 안에 들어있는 컴포넌트들의 크기 조절 못함 -> setMaximumSize() 해줘야 함
		musicControlButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingPanel.add(musicControlButton);

		settingPanel.add(Box.createVerticalGlue());	// 자동으로 늘어나는 공백 영역

		backButton.setMaximumSize(new Dimension(150, 60));	// BoxLayout은 setPreferredSize()로는 안에 들어있는 컴포넌트들의 크기 조절 못함 -> setMaximumSize() 해줘야 함
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingPanel.add(backButton);


		timeLabel = new JLabel("■TIME■ 00:00");		
		fontSetting(timeLabel, 18, 20);
		timeLabel.setForeground(Color.white);

		infoLabel = new JLabel("문제 #"+question);
		fontSetting(infoLabel, 20, 25);
		infoLabel.setForeground(Color.white);

		resultLabel = new JLabel("+CLEAR+");
		fontSetting(resultLabel, 30, 40);
		resultLabel.setForeground(Color.white);
		

		square = new GridButton[ROW][COLUMN];

		gridPanel.setLayout(new GridLayout(ROW, COLUMN));

		for (int r=0;r<ROW;r++) {
			for (int c=0;c<COLUMN;c++) {
				square[r][c] = new GridButton();
				square[r][c].addMouseListener(new GridButtonMouseListener());
				gridPanel.add(square[r][c]);

			}
		}	// 게임 그리드 버튼 생성

		makeDisplayGamePanel();

		displayCard = new CardLayout();

		displayCardPanel.setLayout(displayCard);
		displayCardPanel.add("준비 화면", readyPanel);
		displayCardPanel.add("게임 진행 화면", displayPanel);

		displayPanel.setLayout(new BorderLayout());

		gridLabel = new JLabel();


		displayResultPanel.setLayout(new BoxLayout (displayResultPanel, BoxLayout.Y_AXIS));

		displayResultPanel.setPreferredSize(new Dimension(170, 0));
		timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);	// 가로 가운데 정렬
		displayResultPanel.add(timeLabel);
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);	// 가로 가운데 정렬
		displayResultPanel.add(infoLabel);
		resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);	// 가로 가운데 정렬
		displayResultPanel.add(resultLabel);	
		resultLabel.setVisible(false);	// 성공하면 보이도록

		displayResultPanel.add(Box.createVerticalGlue());	// 자동으로 늘어나는 공백 영역
		
		starImage.setIcon(success_img);
		starImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		displayResultPanel.add(starImage);
		starImage.setVisible(false);	// 성공하면 보이도록

		
		displayGameCard = new CardLayout();
		displayGameCardPanel.setLayout(displayGameCard);
		displayGameCardPanel.add("디스플레이 게임 화면", displayGamePanel);
		displayGameCardPanel.add("디스플레이 중지 화면", displaySuspendPanel);

		displayPanel.add(BorderLayout.WEST, displayResultPanel);
		displayPanel.add(BorderLayout.CENTER, displayGameCardPanel);

		gamePanel.setLayout(new BorderLayout());
		gamePanel.add(BorderLayout.NORTH, buttonPanel);
		gamePanel.add(BorderLayout.CENTER, displayCardPanel);

		buttonPanel.setLayout(new FlowLayout((FlowLayout.RIGHT), 40, 40));	// 정렬, 좌우간격, 상하간격
		buttonPanel.add(hintButton);	
		hintButton.setVisible(false);	// 게임 진행 화면이 떠 있을 때만 보이도록
		buttonPanel.add(newGameButton);
		newGameButton.setVisible(false);	// 게임 성공하면 보이도록
		buttonPanel.add(timerStartButton);
		buttonPanel.add(gameSettingButton);
		buttonPanel.add(gameEndButton);

		coverButtonPanel.setLayout(new FlowLayout((FlowLayout.CENTER), 50, 60));	// 정렬, 좌우간격, 상하간격

		coverButtonPanel.add(startButton);
		coverButtonPanel.add(settingButton);
		coverButtonPanel.add(endButton);

		coverButtonPanel.setOpaque(false);

		coverPanel.setLayout(new BorderLayout());
		coverPanel.add(coverButtonPanel, BorderLayout.SOUTH);

		timerListener = new TimerListener();
		timer = new Timer(1000, timerListener);


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 700);
		frame.setIconImage(new ImageIcon(FRAME_ICON).getImage());	// 프레임 아이콘 설정
		frame.setResizable(false);	// 창 크기 조정 못하도록
		frame.setVisible(true);


		try {
			bgmClip = AudioSystem.getClip();
			bgmClip.open(AudioSystem.getAudioInputStream(new File(BGM)));	
			bgmClip.loop(Clip.LOOP_CONTINUOUSLY);	// BGM 무한반복
			bgmClip.start();	// BGM 켜기
		}
		catch (Exception e) {
			System.out.println("배경음악 파일 로딩 실패");
		} // BGM 재생



	}

	private class TimerListener implements ActionListener {

		int time = 0;

		public void actionPerformed(ActionEvent e) {
			time++;
			timeLabel.setText(String.format("■TIME■ %02d:%02d",time/60,time%60));
		}

		public void reset() {
			time = 0;
		}

	}

	private class Button extends JButton{

		public Button(String text, int h_size, int v_size, int font_size){
			super(text);

			fontSetting(this, font_size, font_size);

			this.setVerticalAlignment(SwingConstants.CENTER);	// 글씨 세로 가운데 정렬
			this.setForeground(Color.white);	// 글씨 색상 변경
			this.setBackground(Color.black);
			this.setFocusable(false);		// 특정 버튼 focus 안 되도록
			this.setPreferredSize(new Dimension(h_size, v_size));

		}
	}

	class ElementLabel extends JLabel{
		public ElementLabel(String text, int h_align, int v_align) {
			super(text);

			fontSetting(this, 12, 12);

			this.setForeground(Color.white);
			this.setHorizontalAlignment(h_align);
			this.setVerticalAlignment(v_align);
		}
	}

	class CoverPanel extends JPanel{
		public void paintComponent(Graphics g) {
			g.drawImage(new ImageIcon(BG_PIC).getImage(), 0, 0, null);

		}
	}

	class ReadyPanel extends JPanel{
		public void paintComponent(Graphics g) {
			g.drawImage(new ImageIcon(READY_PIC).getImage(), 0, 0, null);

		}
	}


	class GamePanel extends JPanel{
		public void paintComponent(Graphics g) {
			g.drawImage(new ImageIcon(GAME_BG_PIC).getImage(), 0, 0, null);

		}
	}


	class SettingPanel extends JPanel{
		public void paintComponent(Graphics g) {
			g.drawImage(new ImageIcon(SET_BG_PIC).getImage(), 0, 0, null);

		}
	}

	class DisplaySuspendPanel extends JPanel{
		public void paintComponent(Graphics g) {
			g.drawImage(new ImageIcon(SUSPEND_PIC).getImage(), 0, 0, null);

		}
	}

	class TransparentPanel extends JPanel{
		public TransparentPanel() {
			this.setOpaque(false);
		}
	}

	private class NewGameListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			getSolution();

			displayGamePanel.remove(gridRowLabelPanel);	// 다시 만들기 위해 지움
			displayGamePanel.remove(gridColLabelPanel);	// 다시 만들기 위해 지움
			displayGamePanel.remove(gridPanel);	// 다시 만들기 위해 지움
			makeDisplayGamePanel();	// displayGamePanel 다시 만들기 (새 게임 시작)

			for (int r=0;r<ROW;r++) {
				for (int c=0;c<COLUMN;c++) {
					square[r][c].setBackground(Color.white);
					square[r][c].setIcon(null);
				}
			}

			newGameButton.setVisible(false);
			hintButton.setVisible(true);	
			timerStartButton.setVisible(true);
			starImage.setVisible(false);
			resultLabel.setVisible(false);
			timerListener.reset();
			timeLabel.setText("■TIME■ 00:00");
			timerStartButton.setText("중지");
			timer.start();
			infoLabel.setText("문제 #"+question);
			succed=false;

			displayGameCard.show(displayGameCardPanel, "디스플레이 게임 화면");

		}
	}

	private class StartListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			card.show(mainPanel, "게임 화면");

			isCoverPanel=false;	// 커버 화면에서 벗어났으므로
		}
	}

	private class SettingListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			card.show(mainPanel, "설정 화면");
			timer.stop();
			timerStarted=false;
			timerStartButton.setText("시작");	// 게임 중 설정 화면으로 들어가면 타이머 중지
			hintButton.setVisible(false);
		}
	}


	private class EndListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	private class BackListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if (isCoverPanel)
				card.show(mainPanel, "시작 화면");
			else {
				card.show(mainPanel, "게임 화면");
				displayGameCard.show(displayGameCardPanel, "디스플레이 중지 화면");	
			}
			// [이전] 버튼을 누를 때 isCoverPanel이 true면 시작(cover) 화면으로, false면 게임 화면으로 돌아감		

		} 
	}

	private class MusicControlListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (bgmClip.isRunning()) {
				bgmClip.stop();
				musicControlButton.setText("BGM 켜기");	// BGM이 켜져 있으면 BGM 끄고 버튼 텍스트 바꾸기
			}
			else {
				bgmClip.setMicrosecondPosition(0);	// BGM 처음으로 돌리기
				bgmClip.loop(Clip.LOOP_CONTINUOUSLY);	// BGM 무한반복
				bgmClip.start();	// BGM 켜기
				musicControlButton.setText("BGM 끄기");	// BGM이 꺼져 있으면 BGM 켜고 버튼 텍스트 바꾸기
			} 

		}	
	}


	private class TimerStartListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			displayCard.show(displayCardPanel, "게임 진행 화면");

			if (timerStarted==false) {
				timer.start();
				timerStarted=true;
				timerStartButton.setText("중지");	// 중지 상태 일때 시작하고 버튼 텍스트 '중지'로 바꾸기
				hintButton.setVisible(true);

				displayGameCard.show(displayGameCardPanel, "디스플레이 게임 화면");
			}	
			else {
				timer.stop();
				timerStarted=false;
				timerStartButton.setText("시작");	// 게임 진행중 일때 중지 버튼 텍스트 '시작'으로 바꾸기
				hintButton.setVisible(false);

				displayGameCard.show(displayGameCardPanel, "디스플레이 중지 화면");

			}
		} 
	}

	private class HintListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			loop:
				for (int r=0;r<ROW;r++) {
					for (int c=0;c<COLUMN;c++) {
						if ((solution[r][c]==1)&&(square[r][c].getBackground()!=Color.black))  {
							square[r][c].setIcon(null);
							square[r][c].setBackground(Color.black);

							writeAnswerArray();

							isSucced();

							break loop;	// 루프 탈출
						}
						else if ((solution[r][c]==0)&&(square[r][c].getBackground()==Color.black)) {
							square[r][c].setBackground(Color.white);


							writeAnswerArray();

							isSucced();

							break loop;	// 루프 탈출
						}
					}
				}
		} 
	}


	class SettingButton extends Button{

		public SettingButton(int h_size, int v_size, int font_size) {
			super("설정", h_size, v_size, font_size);
			this.addActionListener(new SettingListener());
		}
	}

	class EndButton extends Button{

		public EndButton(int h_size, int v_size, int font_size) {
			super("종료", h_size, v_size, font_size);
			this.addActionListener(new EndListener());
		}
	}

	public void editDisplayGamePanel(JComponent c, int x, int y, double wx, double wy) {

		gbc.gridx = x;	// 컴포넌트 행위치
		gbc.gridy = y;	// 컴포넌트 열위치

		gbc.weightx = wx;	// 그리드의 크기가 변할 경우 컴포넌트의 가로 크기 변화를 나타내는 비율 (0일 경우 고정) 
		gbc.weighty = wy;	// 그리드의 크기가 변할 경우 컴포넌트의 세로 크기 변화를 나타내는 비율 (0일 경우 고정)

		gbc.fill = GridBagConstraints.BOTH;	// 컴포넌트가 격자보다 작을 경우 처리
		// NONE: 컴포넌트 크기 유지
		// BOTH: 격자 크기에 맞춤
		// HORIZONTAL/VERTICAL: 수평/수직만 맞춤

		gbc.insets = new Insets(0, 5, 3, 5);	// 컴포넌트 사이의 간격 (위, 왼쪽, 아래, 오른쪽)

		gridBag.setConstraints(c, gbc);

	}	// GridBagLayout의 GridBagConstraints 설정


	class GridButton extends JButton{

		public GridButton(){
			this.setBackground(Color.white);

		}

	}

	Color firstPressedButtonColor;
	Color changedButtonColor;

	class GridButtonMouseListener implements MouseListener {

		public void mousePressed(MouseEvent e){

			firstPressedButtonColor =getGridButtonColor(e);

			if (!succed) {	// 성공했으면 그리드 버튼 색 변하지 않게
				gridClicked=true;

				if(gridClicked) {

					if (e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK){
						if (firstPressedButtonColor==Color.white)
							colorGridButton(e, Color.black);	// 버튼이 하얀색이고 왼쪽 마우스 클릭했을 때 -> 검은색
						else if (firstPressedButtonColor==Color.black)
							colorGridButton(e, Color.white);	// 버튼이 검은색이고 왼쪽 마우스 클릭했을 때 -> 하얀색
					}

					else if (e.getModifiersEx()==MouseEvent.BUTTON3_DOWN_MASK) {
						if (firstPressedButtonColor==Color.white)
							xGridButton(e);	// 버튼이 하얀색이고 오른쪽 마우스 클릭했을 때 -> X
						else if (firstPressedButtonColor==Color.yellow)
							colorGridButton(e, Color.white);	// 버튼이 X이고 오른쪽 마우스 클릭했을 때 -> 하얀색
					}

				}
			}
		}	// 마우스 버튼 클릭한 상태로(gridClicked=true일 때) 드래그할 때 첫 번째 버튼 칠하기 위함

		public void mouseReleased(MouseEvent e){
			
			if (!succed) {	// 성공했으면 그리드 버튼 색 변하지 않게
			gridClicked=false;

			for (int r=0;r<ROW;r++) {
				for (int c=0;c<COLUMN;c++) {

					writeAnswerArray();
				}
			}

			isSucced();
		}
		}

		public void mouseEntered(MouseEvent e){

			if (!succed) {	// 성공했으면 그리드 버튼 색 변하지 않게
			if(gridClicked) {

				Color EnteredButtonColor =getGridButtonColor(e);

				if (e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK){
					if (changedButtonColor == Color.white) {
						if (EnteredButtonColor == Color.black)
							colorGridButton(e, Color.white);
					}	// 처음 Press한 버튼이 하얀색으로 바뀌었을 때, 다음 버튼의 색이 검은색이면 하얀색으로 변경
					else if (changedButtonColor == Color.black) {
						if (EnteredButtonColor == Color.white)
							colorGridButton(e, Color.black);
					}	// 처음 Press한 버튼이 검은색으로 바뀌었을 때, 다음 버튼의 색이 하얀색이면 검은색으로 변경
				}	// 왼쪽 마우스 클릭했을 때

				else if (e.getModifiersEx()==MouseEvent.BUTTON3_DOWN_MASK) {
					if (changedButtonColor == Color.white) {
						if (EnteredButtonColor == Color.yellow)
							colorGridButton(e, Color.white);
					}	// 처음 Press한 버튼이 하얀색으로 바뀌었을 때, 다음 버튼의 색이 노란색이면 하얀색으로 변경
					else if (changedButtonColor == Color.yellow) {
						if (EnteredButtonColor == Color.white)
							xGridButton(e);
					}	// 처음 Press한 버튼이 X로 바뀌었을 때, 다음 버튼의 색이 하얀이면 X로 변경
				}	// 오른쪽 마우스 클릭했을 때


			}
			}
		}	// 마우스 버튼 클릭한 상태로(gridClicked=true일 때) 드래그할 때 여러개 버튼 한번에 칠하기 위함

		public void mouseClicked(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	}


	private Color getGridButtonColor(MouseEvent e) {

		GridButton g = (GridButton)e.getSource();

		gridButtonColor = g.getBackground();
		return gridButtonColor;
	}

	private void colorGridButton(MouseEvent e, Color color) {

		GridButton g = (GridButton)e.getSource();

		gridButtonColor = g.getBackground();

		g.setIcon(null);
		g.setBackground(color);
		changedButtonColor = color;
	}

	private void xGridButton(MouseEvent e) {


		GridButton g = (GridButton)e.getSource();

		gridButtonColor = g.getBackground();
		g.setBackground(Color.yellow);
		g.setIcon(x_img);
		changedButtonColor = Color.yellow;
	}

	void getSolution() {

		question++;
		if (question>10) {
			question=1;
		} // 10번 문제까지 다 풀었으면 1번으로 돌아감

		switch(question) {
		case 1:
			solution=s.s1;
			break;
		case 2:
			solution=s.s2;
			break;
		case 3:
			solution=s.s3;
			break;
		case 4:
			solution=s.s4;
			break;
		case 5:
			solution=s.s5;
			break;
		case 6:
			solution=s.s6;
			break;
		case 7:
			solution=s.s7;
			break;
		case 8:
			solution=s.s8;
			break;
		case 9:
			solution=s.s9;
			break;
		case 10:
			solution=s.s10;
			break;
		}

	}	// 문제는 순서대로 나옴

	void makeDisplayGamePanel() {


		gridRowLabelPanel = new TransparentPanel();
		gridColLabelPanel = new TransparentPanel();


		gridRowLabelPanel.setLayout(new GridLayout(1, COLUMN, 5, 5));
		gridColLabelPanel.setLayout(new GridLayout(ROW, 1, 5, 5));


		int [][]rowLabel = new int[ROW][(int)(COLUMN/2+1)];
		ElementLabel[] rowLabelElement = new ElementLabel[COLUMN];

		for (int c=0;c<COLUMN;c++) {
			int i=0;
			boolean isOne=false;
			int num=0;

			for (int r=0;r<ROW;r++) {

				if (solution[r][c]==1) {
					i++;
					isOne=true;
				}
				else {
					if (isOne) {
						rowLabel[c][num] = i;
						num++;
						i=0;
						isOne=false;

					}
				}
			}
			if (solution[ROW-1][c]==1)
				rowLabel[c][num] = i;

		}

		for (int i=0;i<ROW;i++) {
			rowLabelElement[i] = new ElementLabel("", SwingConstants.CENTER, SwingConstants.BOTTOM);

			boolean isNull=true;

			rowLabelElement[i].setText("<html>");
			for (int j=0;j<(int)(COLUMN/2+1);j++) {
				if (rowLabel[i][j]!=0) {
					isNull=false;
					rowLabelElement[i].setText(rowLabelElement[i].getText()+"<br/>"+rowLabel[i][j]);
				}	// JLabel 안에서는 \n이 듣지 않음!
			}
			rowLabelElement[i].setText(rowLabelElement[i].getText()+"</html>");

			if (isNull==true) {
				rowLabelElement[i].setText("0");
			}

			gridRowLabelPanel.add(rowLabelElement[i]);

		}	// gridRowLabelPanel 채우기

		int [][]colLabel = new int[COLUMN][(int)(ROW/2+1)];
		ElementLabel[] colLabelElement = new ElementLabel[ROW];

		for (int r=0;r<ROW;r++) {
			int i=0;
			boolean isOne=false;	// 1이 한번이라도 나올 경우 배열에 원소 추가하기 시작
			int num=0;

			for (int c=0;c<COLUMN;c++) {

				if (solution[r][c]==1) {
					i++;
					isOne=true;
				}
				else {
					if (isOne) {
						colLabel[r][num] = i;
						num++;
						i=0;
						isOne=false;

					}
				}
			}
			if (solution[r][COLUMN-1]==1)
				colLabel[r][num] = i;
		}

		for (int i=0;i<ROW;i++) {
			colLabelElement[i] = new ElementLabel("", SwingConstants.RIGHT, SwingConstants.CENTER);
			for (int j=0;j<(int)(ROW/2+1);j++) {

				if (colLabel[i][j]!=0) {
					colLabelElement[i].setText((colLabelElement[i].getText())+" "+colLabel[i][j]);
				}
			}

			if (colLabelElement[i].getText()=="") {
				colLabelElement[i].setText("0");
			}

			gridColLabelPanel.add(colLabelElement[i]);

		} // gridColLabelPanel 채우기

		displayGamePanel.add(gridRowLabelPanel);
		displayGamePanel.add(gridColLabelPanel);
		displayGamePanel.add(gridPanel);

		displayGamePanel.setLayout(gridBag);
		editDisplayGamePanel(gridPanel, 1, 1, 1, 1);
		editDisplayGamePanel(gridRowLabelPanel, 1, 0, 0, 0);
		editDisplayGamePanel(gridColLabelPanel, 0, 1, 0, 0);	// displayGamePanel 레이아웃 편집
	}

	void fontSetting(JComponent c, int default_font_size, int custom_font_size) {
		if (fontSettingFailed) 
			c.setFont(new Font("함초롬돋움", Font.PLAIN, default_font_size));
		else
			c.setFont((customFont.deriveFont(Font.PLAIN, custom_font_size)));
	} // -> 폰트 설정. 커스텀 폰트 불러오기 실패했을 경우 함초롬돋움으로 설정

	void isSucced() {

		if (Arrays.deepEquals(solution, answer)) {
			timer.stop();
			timerStarted=false;

			newGameButton.setVisible(true);
			starImage.setVisible(true);
			resultLabel.setVisible(true);

			timerStartButton.setVisible(false);
			hintButton.setVisible(false);

			succed=true;


		} // deepEquals() -> 다차원 배열 비교. solution과 answer이 같으면 게임 성공!

	}

	void writeAnswerArray() {
		for (int r=0;r<ROW;r++) {
			for (int c=0;c<COLUMN;c++) {

				if (square[r][c].getBackground()==Color.black)
					answer[r][c]=1;					
				else 
					answer[r][c]=0;

			}
		}	
	}	// 그리드 버튼이 검은색이면 1, 하얀색이면 0을 answer 배열에 넣음

}
