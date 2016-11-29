package cn.itcast.myself;

import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Date;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel{
	

	
	private static final int PANEL_WIDTH = 695;
	private static final int PAINT_HEIGHT = 695;
	
	private int[] xx=new int[50];
	private int[] yy=new int[50];   //�ϰ������������
	
	private int x,y;        //�����˶����꣬��ΪĿ��
	private Button button;    //Ŀ��
	private	Button reset;     
	
	private boolean kDown;
	private boolean kRight;
	private boolean kLeft;
	private boolean kUp;
	
	private int step=15;         //Ŀ���ƶ�����
	
	private long firsttime;
	private long lasttime;
	
	private JLabel jLabel;


	public GamePanel(){
		super();
		this.setSize(PANEL_WIDTH, PAINT_HEIGHT);
		this.setBackground(Color.BLACK);
		this.setLayout(null);
	
		
		
		
		//��ʼ��jLabel
		jLabel=new JLabel();
		button=new Button();
		button.addKeyListener(new KeyListener(){ 
			
		
			
		
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO �Զ����ɵķ������
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO �Զ����ɵķ������
				if(KeyEvent.VK_DOWN==e.getKeyCode()){
					kDown=false;
				}
				if(KeyEvent.VK_UP==e.getKeyCode()){
					kUp=false;		
				}
				if(KeyEvent.VK_LEFT==e.getKeyCode()){
					kLeft=false;				
				}
				if(KeyEvent.VK_RIGHT==e.getKeyCode()){
					kRight=false;				
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO �Զ����ɵķ������
				if(KeyEvent.VK_DOWN==e.getKeyCode()){
					kDown=true;			
				}
				if(KeyEvent.VK_UP==e.getKeyCode()){
					kUp=true;						
				}
				if(KeyEvent.VK_LEFT==e.getKeyCode()){
					kLeft=true;	
				}
				if(KeyEvent.VK_RIGHT==e.getKeyCode()){
					kRight=true;					
				}
			}
		});
		
		jLabel.setBounds(new Rectangle(PANEL_WIDTH/2-50, 50, 100, 50));
		
		
		this.add(button);		
		this.add(jLabel);
		initialize();
		
	}
	
	
	//��ʼ��Ŀ����ϰ���
	private void initialize(){
	
		
		//��ʼ��Ŀ������
		x=PANEL_WIDTH/2;
		y=PAINT_HEIGHT/2;
		button.setSize(15, 15);

		//��ʼ���ϰ�������
				for(int i=0;i<50;i++){
					xx[i]=new Random().nextInt(PANEL_WIDTH);
					yy[i]=new Random().nextInt(PAINT_HEIGHT);
					while(true){	
						//�ϰ��ﲻ����Ŀ���һ��������
						if(
								(xx[i]>=x-300)  &&  (xx[i]<=x+300)     &&
								(yy[i]>=y-300)  &&  (yy[i]<=y+300)  
								){
							xx[i]=new Random().nextInt(PANEL_WIDTH);
							yy[i]=new Random().nextInt(PAINT_HEIGHT);
						}
						else
							break;
					}
				}
				
				
	
	}

	
	@Override
	public void paint(Graphics g) {
		// TODO �Զ����ɵķ������
		super.paint(g);
		drawPoint(g);		
	}


	//���ϰ����Ŀ��
	private void drawPoint(Graphics g){
		
		g.setColor(Color.WHITE);		
		//����		
		for(int i=0;i<50;i++ ){
			g.fillRoundRect(xx[i], yy[i], 5, 5, 5, 5);
		}	
//		//��Ŀ��
//		g.fill3DRect(x, y, 10, 10, true);
		button.setBounds(new Rectangle(x, y, 15, 15));
	}
	
	

	public void start() {
		// TODO �Զ����ɵķ������
			for(int i=3;i>0;i--){
				jLabel.setText("ʣ��"+i+"s��ʼ");	
				repaint();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
				}
			}
			jLabel.setVisible(false);
			firsttime=new Date().getTime();
			pointMove();
			System.out.println("���߳�");
			
	
	}


	public void reset() {
		// TODO �Զ����ɵķ������
		initialize();
		jLabel.setVisible(true);
		start();
		
	}
	private void pointMove(){
		//�����������̣߳��ƶ��ϰ���
		new Thread(){    
			public void run(){	
				int lt=0;     //�ж���Ϸ�ȼ��õĲ���
				int [] speedx=new int[50];   //50���ϰ���ÿ�����ƶ��ٶ�
				int [] speedy=new int[50];
				for(int i=0;i<50;i++ ){	
					speedx[i]=(x-xx[i])/ ( Game.LEVEL * 5 );
					speedy[i]=(y-yy[i])/ (Game.LEVEL * 5 );
				}
					while(true){
						//����Ŀ��ֱ���ƶ�					
						for(int i=0;i<50;i++ ){	
								xx[i]=speedx[i]+xx[i];
								yy[i]=speedy[i]+yy[i];
							
								//�ж��Ƿ񳬳��߽�
								if(xx[i]<0  ||  xx[i]>PANEL_WIDTH   ||  yy[i]<0  ||  yy[i]>PAINT_HEIGHT ){
									xx[i]=new Random().nextInt(PANEL_WIDTH);	
									yy[i]=new Random().nextInt(PAINT_HEIGHT);
									while(true){	
										//�ϰ��ﲻ����Ŀ���һ��������
										if(
												(xx[i]>=10)  &&  (xx[i]<=690)     &&
												(yy[i]>=10)  &&  (yy[i]<=690)  
												){
											xx[i]=new Random().nextInt(PANEL_WIDTH);
											yy[i]=new Random().nextInt(PAINT_HEIGHT);
										}
										else
											break;
									}
									speedx[i]=(x-xx[i])/ ( Game.LEVEL * 5 );
									speedy[i]=(y-yy[i])/ (Game.LEVEL * 5 );
								}
								
						}
	
						//Ŀ���ƶ�
						if(kUp){
							y=y-step;			
						}
						if(kDown){
							y=y+step;				
						}
						if(kRight){
							x=x+step;		
						}
						if(kLeft){
							x=x-step;
						}
						button.setBounds(new Rectangle(x, y, 15, 15));
						repaint();
						//�����Ϸ�Ƿ����
						if(isGameOver()){
							break;
						}
						try {						
								
							lt=lt+1;
							if(lt==150){
								Game.LEVEL--;
								System.out.println(Game.LEVEL);
							}
							if(lt==300){
								Game.LEVEL--;
								System.out.println(Game.LEVEL);
							}
							if(lt==450){
								Game.LEVEL--;
								System.out.println(Game.LEVEL);
							}
							Thread.sleep(100);
						} catch (InterruptedException e) {
							// TODO �Զ����ɵ� catch ��
							e.printStackTrace();
						}
					}				
			}
			
		}.start();
	
	}
	private boolean isGameOver() {
		// TODO �Զ����ɵķ������
		for(int i=0;i<50;i++ ){
			if(
					(xx[i]>=x)  &&  (xx[i]<=x+15)     &&
					(yy[i]>=y)  &&  (yy[i]<=y+15)  
					){
				
				Game.gameOver=true;
				if(reset==null){				
					reset=new Button("ȷ��");
					reset.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO �Զ����ɵķ������
							if(e.getActionCommand().equals("ȷ��")){	
								reset.setVisible(false);
//								repaint();
								reset();
							}
						}
					});
					reset.setSize(100,50);
					reset.setBounds(new  Rectangle(PANEL_WIDTH/2, PAINT_HEIGHT/2,100 ,50 ));
					this.add(reset);
				}
				reset.setVisible(true);
				lasttime=new Date().getTime();
				Date gametime = new Date(lasttime-firsttime);
				int min =gametime.getMinutes();
				int sec =gametime.getSeconds();
	
				jLabel.setVisible(true);
				if(sec<10){
					
					jLabel.setText(sec+"s:	������");
				}
				if(sec>=10 && sec<20){
					jLabel.setText(sec+"s:	�ϲ�");
					
				}
				if(sec>=20 && sec<25){
					jLabel.setText(sec+"s:	������");
					
				}
				if(sec>=25 && sec<30){
					jLabel.setText(sec+"s:	are you man?");
					
				}
				if(sec>=30 && sec<40){
					jLabel.setText(sec+"s:����	");
					
				}
				if(sec>=40 && sec<45){
					jLabel.setText(sec+"s:	no can no man");
					
				}
				if(sec>=45 && sec<60){
					jLabel.setText(sec+"s:	С����");
					
				}
				if(sec>=60){
					
					jLabel.setText(sec+"s:	�ж���ɫ");
				}
				return true;
			}		
		}
		return false;
	}

}
