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
	private int[] yy=new int[50];   //障碍物个数，坐标
	
	private int x,y;        //物体运动坐标，称为目标
	private Button button;    //目标
	private	Button reset;     
	
	private boolean kDown;
	private boolean kRight;
	private boolean kLeft;
	private boolean kUp;
	
	private int step=15;         //目标移动步长
	
	private long firsttime;
	private long lasttime;
	
	private JLabel jLabel;


	public GamePanel(){
		super();
		this.setSize(PANEL_WIDTH, PAINT_HEIGHT);
		this.setBackground(Color.BLACK);
		this.setLayout(null);
	
		
		
		
		//初始化jLabel
		jLabel=new JLabel();
		button=new Button();
		button.addKeyListener(new KeyListener(){ 
			
		
			
		
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO 自动生成的方法存根
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO 自动生成的方法存根
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
				// TODO 自动生成的方法存根
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
	
	
	//初始化目标和障碍物
	private void initialize(){
	
		
		//初始化目标坐标
		x=PANEL_WIDTH/2;
		y=PAINT_HEIGHT/2;
		button.setSize(15, 15);

		//初始化障碍物坐标
				for(int i=0;i<50;i++){
					xx[i]=new Random().nextInt(PANEL_WIDTH);
					yy[i]=new Random().nextInt(PAINT_HEIGHT);
					while(true){	
						//障碍物不能在目标的一个区域内
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
		// TODO 自动生成的方法存根
		super.paint(g);
		drawPoint(g);		
	}


	//画障碍物和目标
	private void drawPoint(Graphics g){
		
		g.setColor(Color.WHITE);		
		//画点		
		for(int i=0;i<50;i++ ){
			g.fillRoundRect(xx[i], yy[i], 5, 5, 5, 5);
		}	
//		//画目标
//		g.fill3DRect(x, y, 10, 10, true);
		button.setBounds(new Rectangle(x, y, 15, 15));
	}
	
	

	public void start() {
		// TODO 自动生成的方法存根
			for(int i=3;i>0;i--){
				jLabel.setText("剩余"+i+"s开始");	
				repaint();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
				}
			}
			jLabel.setVisible(false);
			firsttime=new Date().getTime();
			pointMove();
			System.out.println("主线程");
			
	
	}


	public void reset() {
		// TODO 自动生成的方法存根
		initialize();
		jLabel.setVisible(true);
		start();
		
	}
	private void pointMove(){
		//创建匿名新线程，移动障碍物
		new Thread(){    
			public void run(){	
				int lt=0;     //判断游戏等级用的参数
				int [] speedx=new int[50];   //50个障碍物每个的移动速度
				int [] speedy=new int[50];
				for(int i=0;i<50;i++ ){	
					speedx[i]=(x-xx[i])/ ( Game.LEVEL * 5 );
					speedy[i]=(y-yy[i])/ (Game.LEVEL * 5 );
				}
					while(true){
						//靠近目标直线移动					
						for(int i=0;i<50;i++ ){	
								xx[i]=speedx[i]+xx[i];
								yy[i]=speedy[i]+yy[i];
							
								//判断是否超出边界
								if(xx[i]<0  ||  xx[i]>PANEL_WIDTH   ||  yy[i]<0  ||  yy[i]>PAINT_HEIGHT ){
									xx[i]=new Random().nextInt(PANEL_WIDTH);	
									yy[i]=new Random().nextInt(PAINT_HEIGHT);
									while(true){	
										//障碍物不能在目标的一个区域内
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
	
						//目标移动
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
						//检测游戏是否结束
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
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						}
					}				
			}
			
		}.start();
	
	}
	private boolean isGameOver() {
		// TODO 自动生成的方法存根
		for(int i=0;i<50;i++ ){
			if(
					(xx[i]>=x)  &&  (xx[i]<=x+15)     &&
					(yy[i]>=y)  &&  (yy[i]<=y+15)  
					){
				
				Game.gameOver=true;
				if(reset==null){				
					reset=new Button("确定");
					reset.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO 自动生成的方法存根
							if(e.getActionCommand().equals("确定")){	
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
					
					jLabel.setText(sec+"s:	秒射男");
				}
				if(sec>=10 && sec<20){
					jLabel.setText(sec+"s:	废材");
					
				}
				if(sec>=20 && sec<25){
					jLabel.setText(sec+"s:	肾虚吗");
					
				}
				if(sec>=25 && sec<30){
					jLabel.setText(sec+"s:	are you man?");
					
				}
				if(sec>=30 && sec<40){
					jLabel.setText(sec+"s:垃圾	");
					
				}
				if(sec>=40 && sec<45){
					jLabel.setText(sec+"s:	no can no man");
					
				}
				if(sec>=45 && sec<60){
					jLabel.setText(sec+"s:	小男人");
					
				}
				if(sec>=60){
					
					jLabel.setText(sec+"s:	男儿本色");
				}
				return true;
			}		
		}
		return false;
	}

}
