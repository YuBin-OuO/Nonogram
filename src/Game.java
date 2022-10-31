// 20205134 ������
// 2021�� 2�б� JAVA ��������Ʈ

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

	Solution s = new Solution();	// ���� ����ִ� �迭 �������� ����

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
	private boolean isCoverPanel = true;	// ���� �гο��� ���ư� �г� �����ϱ� ���� -> true�� Ŀ�� �гη�, false�� ���� �гη� ���ư�
	private final int ROW = 15;
	private final int COLUMN = 15;

	int[][] solution = s.s1;
	int question=0;	// ���� ��ȣ

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

	boolean fontSettingFailed = false;	// ��Ʈ �ҷ����� �����ϸ� true
	boolean timerStarted = false;
	boolean gridClicked = false;	// ���콺 ��ư Ŭ���� ���·� �巡���� �� ������ ��ư �ѹ��� ĥ�ϱ� ���� �÷���
	boolean succed = false;	// ���� �����ϸ� true

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
			System.out.println("��Ʈ �ҷ����� ����\n");
			fontSettingFailed=true;	
		} // ��ư ��Ʈ ��ǻ�Ϳ� ��ġ�� �ʿ� ���� ���α׷����� �̿��ϱ� ����


		getSolution();	// ���� ��������

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
		mainPanel.add("���� ȭ��", coverPanel);
		mainPanel.add("���� ȭ��", gamePanel);
		mainPanel.add("���� ȭ��", settingPanel);

		frame.add(mainPanel);
		// card.show(mainPanel, "���� ȭ��");		-> �� ���൵ ��. CardLayout�� �⺻������ ���� ���� add�� ������Ʈ�� ������

		startButton = new Button("����", 120, 60, 24);
		startButton.addActionListener(new StartListener());
		settingButton = new SettingButton(120, 60, 24);
		endButton = new EndButton(120, 60, 24);
		timerStartButton = new Button("����", 120, 40, 20);
		timerStartButton.addActionListener(new TimerStartListener());
		gameSettingButton = new SettingButton(120, 40, 20);
		gameEndButton = new EndButton(120, 40, 20);
		newGameButton = new Button("�� ����", 120, 40, 20);
		newGameButton.addActionListener(new NewGameListener());
		hintButton = new Button("��Ʈ", 120, 40, 20);
		hintButton.addActionListener(new HintListener());
		backButton = new Button("����", 150, 60, 24);
		backButton.addActionListener(new BackListener());
		musicControlButton = new Button("BGM ����", 150, 60, 24);
		musicControlButton.addActionListener(new MusicControlListener());


		settingPanel.setLayout(new BoxLayout(settingPanel, BoxLayout.Y_AXIS));
		settingPanel.setBorder(new EmptyBorder(new Insets(250, 100, 250, 100)));	// ��, ��, ��, �� �����ֱ� ����

		musicControlButton.setMaximumSize(new Dimension(150, 60));	// BoxLayout�� setPreferredSize()�δ� �ȿ� ����ִ� ������Ʈ���� ũ�� ���� ���� -> setMaximumSize() ����� ��
		musicControlButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingPanel.add(musicControlButton);

		settingPanel.add(Box.createVerticalGlue());	// �ڵ����� �þ�� ���� ����

		backButton.setMaximumSize(new Dimension(150, 60));	// BoxLayout�� setPreferredSize()�δ� �ȿ� ����ִ� ������Ʈ���� ũ�� ���� ���� -> setMaximumSize() ����� ��
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		settingPanel.add(backButton);


		timeLabel = new JLabel("��TIME�� 00:00");		
		fontSetting(timeLabel, 18, 20);
		timeLabel.setForeground(Color.white);

		infoLabel = new JLabel("���� #"+question);
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
		}	// ���� �׸��� ��ư ����

		makeDisplayGamePanel();

		displayCard = new CardLayout();

		displayCardPanel.setLayout(displayCard);
		displayCardPanel.add("�غ� ȭ��", readyPanel);
		displayCardPanel.add("���� ���� ȭ��", displayPanel);

		displayPanel.setLayout(new BorderLayout());

		gridLabel = new JLabel();


		displayResultPanel.setLayout(new BoxLayout (displayResultPanel, BoxLayout.Y_AXIS));

		displayResultPanel.setPreferredSize(new Dimension(170, 0));
		timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);	// ���� ��� ����
		displayResultPanel.add(timeLabel);
		infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);	// ���� ��� ����
		displayResultPanel.add(infoLabel);
		resultLabel.setAlignmentX(Component.CENTER_ALIGNMENT);	// ���� ��� ����
		displayResultPanel.add(resultLabel);	
		resultLabel.setVisible(false);	// �����ϸ� ���̵���

		displayResultPanel.add(Box.createVerticalGlue());	// �ڵ����� �þ�� ���� ����
		
		starImage.setIcon(success_img);
		starImage.setAlignmentX(Component.CENTER_ALIGNMENT);
		displayResultPanel.add(starImage);
		starImage.setVisible(false);	// �����ϸ� ���̵���

		
		displayGameCard = new CardLayout();
		displayGameCardPanel.setLayout(displayGameCard);
		displayGameCardPanel.add("���÷��� ���� ȭ��", displayGamePanel);
		displayGameCardPanel.add("���÷��� ���� ȭ��", displaySuspendPanel);

		displayPanel.add(BorderLayout.WEST, displayResultPanel);
		displayPanel.add(BorderLayout.CENTER, displayGameCardPanel);

		gamePanel.setLayout(new BorderLayout());
		gamePanel.add(BorderLayout.NORTH, buttonPanel);
		gamePanel.add(BorderLayout.CENTER, displayCardPanel);

		buttonPanel.setLayout(new FlowLayout((FlowLayout.RIGHT), 40, 40));	// ����, �¿찣��, ���ϰ���
		buttonPanel.add(hintButton);	
		hintButton.setVisible(false);	// ���� ���� ȭ���� �� ���� ���� ���̵���
		buttonPanel.add(newGameButton);
		newGameButton.setVisible(false);	// ���� �����ϸ� ���̵���
		buttonPanel.add(timerStartButton);
		buttonPanel.add(gameSettingButton);
		buttonPanel.add(gameEndButton);

		coverButtonPanel.setLayout(new FlowLayout((FlowLayout.CENTER), 50, 60));	// ����, �¿찣��, ���ϰ���

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
		frame.setIconImage(new ImageIcon(FRAME_ICON).getImage());	// ������ ������ ����
		frame.setResizable(false);	// â ũ�� ���� ���ϵ���
		frame.setVisible(true);


		try {
			bgmClip = AudioSystem.getClip();
			bgmClip.open(AudioSystem.getAudioInputStream(new File(BGM)));	
			bgmClip.loop(Clip.LOOP_CONTINUOUSLY);	// BGM ���ѹݺ�
			bgmClip.start();	// BGM �ѱ�
		}
		catch (Exception e) {
			System.out.println("������� ���� �ε� ����");
		} // BGM ���



	}

	private class TimerListener implements ActionListener {

		int time = 0;

		public void actionPerformed(ActionEvent e) {
			time++;
			timeLabel.setText(String.format("��TIME�� %02d:%02d",time/60,time%60));
		}

		public void reset() {
			time = 0;
		}

	}

	private class Button extends JButton{

		public Button(String text, int h_size, int v_size, int font_size){
			super(text);

			fontSetting(this, font_size, font_size);

			this.setVerticalAlignment(SwingConstants.CENTER);	// �۾� ���� ��� ����
			this.setForeground(Color.white);	// �۾� ���� ����
			this.setBackground(Color.black);
			this.setFocusable(false);		// Ư�� ��ư focus �� �ǵ���
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

			displayGamePanel.remove(gridRowLabelPanel);	// �ٽ� ����� ���� ����
			displayGamePanel.remove(gridColLabelPanel);	// �ٽ� ����� ���� ����
			displayGamePanel.remove(gridPanel);	// �ٽ� ����� ���� ����
			makeDisplayGamePanel();	// displayGamePanel �ٽ� ����� (�� ���� ����)

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
			timeLabel.setText("��TIME�� 00:00");
			timerStartButton.setText("����");
			timer.start();
			infoLabel.setText("���� #"+question);
			succed=false;

			displayGameCard.show(displayGameCardPanel, "���÷��� ���� ȭ��");

		}
	}

	private class StartListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			card.show(mainPanel, "���� ȭ��");

			isCoverPanel=false;	// Ŀ�� ȭ�鿡�� ������Ƿ�
		}
	}

	private class SettingListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			card.show(mainPanel, "���� ȭ��");
			timer.stop();
			timerStarted=false;
			timerStartButton.setText("����");	// ���� �� ���� ȭ������ ���� Ÿ�̸� ����
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
				card.show(mainPanel, "���� ȭ��");
			else {
				card.show(mainPanel, "���� ȭ��");
				displayGameCard.show(displayGameCardPanel, "���÷��� ���� ȭ��");	
			}
			// [����] ��ư�� ���� �� isCoverPanel�� true�� ����(cover) ȭ������, false�� ���� ȭ������ ���ư�		

		} 
	}

	private class MusicControlListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (bgmClip.isRunning()) {
				bgmClip.stop();
				musicControlButton.setText("BGM �ѱ�");	// BGM�� ���� ������ BGM ���� ��ư �ؽ�Ʈ �ٲٱ�
			}
			else {
				bgmClip.setMicrosecondPosition(0);	// BGM ó������ ������
				bgmClip.loop(Clip.LOOP_CONTINUOUSLY);	// BGM ���ѹݺ�
				bgmClip.start();	// BGM �ѱ�
				musicControlButton.setText("BGM ����");	// BGM�� ���� ������ BGM �Ѱ� ��ư �ؽ�Ʈ �ٲٱ�
			} 

		}	
	}


	private class TimerStartListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {

			displayCard.show(displayCardPanel, "���� ���� ȭ��");

			if (timerStarted==false) {
				timer.start();
				timerStarted=true;
				timerStartButton.setText("����");	// ���� ���� �϶� �����ϰ� ��ư �ؽ�Ʈ '����'�� �ٲٱ�
				hintButton.setVisible(true);

				displayGameCard.show(displayGameCardPanel, "���÷��� ���� ȭ��");
			}	
			else {
				timer.stop();
				timerStarted=false;
				timerStartButton.setText("����");	// ���� ������ �϶� ���� ��ư �ؽ�Ʈ '����'���� �ٲٱ�
				hintButton.setVisible(false);

				displayGameCard.show(displayGameCardPanel, "���÷��� ���� ȭ��");

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

							break loop;	// ���� Ż��
						}
						else if ((solution[r][c]==0)&&(square[r][c].getBackground()==Color.black)) {
							square[r][c].setBackground(Color.white);


							writeAnswerArray();

							isSucced();

							break loop;	// ���� Ż��
						}
					}
				}
		} 
	}


	class SettingButton extends Button{

		public SettingButton(int h_size, int v_size, int font_size) {
			super("����", h_size, v_size, font_size);
			this.addActionListener(new SettingListener());
		}
	}

	class EndButton extends Button{

		public EndButton(int h_size, int v_size, int font_size) {
			super("����", h_size, v_size, font_size);
			this.addActionListener(new EndListener());
		}
	}

	public void editDisplayGamePanel(JComponent c, int x, int y, double wx, double wy) {

		gbc.gridx = x;	// ������Ʈ ����ġ
		gbc.gridy = y;	// ������Ʈ ����ġ

		gbc.weightx = wx;	// �׸����� ũ�Ⱑ ���� ��� ������Ʈ�� ���� ũ�� ��ȭ�� ��Ÿ���� ���� (0�� ��� ����) 
		gbc.weighty = wy;	// �׸����� ũ�Ⱑ ���� ��� ������Ʈ�� ���� ũ�� ��ȭ�� ��Ÿ���� ���� (0�� ��� ����)

		gbc.fill = GridBagConstraints.BOTH;	// ������Ʈ�� ���ں��� ���� ��� ó��
		// NONE: ������Ʈ ũ�� ����
		// BOTH: ���� ũ�⿡ ����
		// HORIZONTAL/VERTICAL: ����/������ ����

		gbc.insets = new Insets(0, 5, 3, 5);	// ������Ʈ ������ ���� (��, ����, �Ʒ�, ������)

		gridBag.setConstraints(c, gbc);

	}	// GridBagLayout�� GridBagConstraints ����


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

			if (!succed) {	// ���������� �׸��� ��ư �� ������ �ʰ�
				gridClicked=true;

				if(gridClicked) {

					if (e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK){
						if (firstPressedButtonColor==Color.white)
							colorGridButton(e, Color.black);	// ��ư�� �Ͼ���̰� ���� ���콺 Ŭ������ �� -> ������
						else if (firstPressedButtonColor==Color.black)
							colorGridButton(e, Color.white);	// ��ư�� �������̰� ���� ���콺 Ŭ������ �� -> �Ͼ��
					}

					else if (e.getModifiersEx()==MouseEvent.BUTTON3_DOWN_MASK) {
						if (firstPressedButtonColor==Color.white)
							xGridButton(e);	// ��ư�� �Ͼ���̰� ������ ���콺 Ŭ������ �� -> X
						else if (firstPressedButtonColor==Color.yellow)
							colorGridButton(e, Color.white);	// ��ư�� X�̰� ������ ���콺 Ŭ������ �� -> �Ͼ��
					}

				}
			}
		}	// ���콺 ��ư Ŭ���� ���·�(gridClicked=true�� ��) �巡���� �� ù ��° ��ư ĥ�ϱ� ����

		public void mouseReleased(MouseEvent e){
			
			if (!succed) {	// ���������� �׸��� ��ư �� ������ �ʰ�
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

			if (!succed) {	// ���������� �׸��� ��ư �� ������ �ʰ�
			if(gridClicked) {

				Color EnteredButtonColor =getGridButtonColor(e);

				if (e.getModifiersEx()==MouseEvent.BUTTON1_DOWN_MASK){
					if (changedButtonColor == Color.white) {
						if (EnteredButtonColor == Color.black)
							colorGridButton(e, Color.white);
					}	// ó�� Press�� ��ư�� �Ͼ������ �ٲ���� ��, ���� ��ư�� ���� �������̸� �Ͼ������ ����
					else if (changedButtonColor == Color.black) {
						if (EnteredButtonColor == Color.white)
							colorGridButton(e, Color.black);
					}	// ó�� Press�� ��ư�� ���������� �ٲ���� ��, ���� ��ư�� ���� �Ͼ���̸� ���������� ����
				}	// ���� ���콺 Ŭ������ ��

				else if (e.getModifiersEx()==MouseEvent.BUTTON3_DOWN_MASK) {
					if (changedButtonColor == Color.white) {
						if (EnteredButtonColor == Color.yellow)
							colorGridButton(e, Color.white);
					}	// ó�� Press�� ��ư�� �Ͼ������ �ٲ���� ��, ���� ��ư�� ���� ������̸� �Ͼ������ ����
					else if (changedButtonColor == Color.yellow) {
						if (EnteredButtonColor == Color.white)
							xGridButton(e);
					}	// ó�� Press�� ��ư�� X�� �ٲ���� ��, ���� ��ư�� ���� �Ͼ��̸� X�� ����
				}	// ������ ���콺 Ŭ������ ��


			}
			}
		}	// ���콺 ��ư Ŭ���� ���·�(gridClicked=true�� ��) �巡���� �� ������ ��ư �ѹ��� ĥ�ϱ� ����

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
		} // 10�� �������� �� Ǯ������ 1������ ���ư�

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

	}	// ������ ������� ����

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
				}	// JLabel �ȿ����� \n�� ���� ����!
			}
			rowLabelElement[i].setText(rowLabelElement[i].getText()+"</html>");

			if (isNull==true) {
				rowLabelElement[i].setText("0");
			}

			gridRowLabelPanel.add(rowLabelElement[i]);

		}	// gridRowLabelPanel ä���

		int [][]colLabel = new int[COLUMN][(int)(ROW/2+1)];
		ElementLabel[] colLabelElement = new ElementLabel[ROW];

		for (int r=0;r<ROW;r++) {
			int i=0;
			boolean isOne=false;	// 1�� �ѹ��̶� ���� ��� �迭�� ���� �߰��ϱ� ����
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

		} // gridColLabelPanel ä���

		displayGamePanel.add(gridRowLabelPanel);
		displayGamePanel.add(gridColLabelPanel);
		displayGamePanel.add(gridPanel);

		displayGamePanel.setLayout(gridBag);
		editDisplayGamePanel(gridPanel, 1, 1, 1, 1);
		editDisplayGamePanel(gridRowLabelPanel, 1, 0, 0, 0);
		editDisplayGamePanel(gridColLabelPanel, 0, 1, 0, 0);	// displayGamePanel ���̾ƿ� ����
	}

	void fontSetting(JComponent c, int default_font_size, int custom_font_size) {
		if (fontSettingFailed) 
			c.setFont(new Font("���ʷҵ���", Font.PLAIN, default_font_size));
		else
			c.setFont((customFont.deriveFont(Font.PLAIN, custom_font_size)));
	} // -> ��Ʈ ����. Ŀ���� ��Ʈ �ҷ����� �������� ��� ���ʷҵ������� ����

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


		} // deepEquals() -> ������ �迭 ��. solution�� answer�� ������ ���� ����!

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
	}	// �׸��� ��ư�� �������̸� 1, �Ͼ���̸� 0�� answer �迭�� ����

}
