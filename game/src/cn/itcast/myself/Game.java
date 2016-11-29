package cn.itcast.myself;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Date;

public class Game extends JFrame {
	
	
	
	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HEIGHT = 700;
	
	
	public static  int LEVEL = 5;
	

	public static boolean gameOver;
	//��Ϸ���
	public GamePanel gamePanel;

		//������
		public static void main(String[] args) {
			Game game = new Game();
			game.gamePanel.start();
		}
		




		//����
		public Game() {
			super("1����������");
			gamePanel=new GamePanel();
		
			
			
			initialize();
			
		}
		
		
		//��ʼ��
		private void initialize() {
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);	
		//�˳�
//		this.addWindowListener(new java.awt.event.WindowAdapter() {
//			public void windowClosing(java.awt.event.WindowEvent e) {
//				System.exit(1);
//			}
//		});
		//�˳�
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setContentPane(gamePanel);
		this.setVisible(true);
	}



} 
