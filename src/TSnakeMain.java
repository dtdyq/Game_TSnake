import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class TSnakeMain {
	
	private static final int MainWH=500;
	private JFrame Sboard;
	private SDraw SMainArea;
	Timer timer;
	private LinkedList<pointers> Snake;
	
	private int keyAction=KeyEvent.VK_RIGHT;
	private boolean hasFood=false;
	private pointers food;
	
	private int timeCounter=500;
	
	public TSnakeMain(){
		Sboard=new JFrame("T-Snake");
		Sboard.setBounds(300, 100, MainWH, MainWH);
		
		Snake=new LinkedList<>();
		Snake.add(new pointers(0,0));
	
		SMainArea=new SDraw();
		SMainArea.setBackground(new Color(0x555555));
		Sboard.add(SMainArea);
		
		food=new pointers(200,200);
		init();
		Sboard.setVisible(true);
	}
	private void init(){
		
		Sboard.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				int temp=e.getKeyCode();
				if(keyAction==temp){
					return;
				}
				else if(keyAction==KeyEvent.VK_UP && temp==KeyEvent.VK_DOWN  || keyAction==KeyEvent.VK_DOWN && temp==KeyEvent.VK_UP){
					return;
				}
				else if(keyAction==KeyEvent.VK_LEFT && temp==KeyEvent.VK_RIGHT ||keyAction==KeyEvent.VK_RIGHT && temp==KeyEvent.VK_LEFT){
					return;
				}
				keyAction=temp;
			}
		});
		ActionListener Smove=new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
					if(keyAction==KeyEvent.VK_UP){
						pointers head=new pointers(Snake.getFirst().getXs(),Snake.getFirst().getYs()-10);
						nextStep(head);
					}else
					if(keyAction==KeyEvent.VK_DOWN){
						pointers head=new pointers(Snake.getFirst().getXs(),Snake.getFirst().getYs()+10);
						nextStep(head);
					}
					if(keyAction==KeyEvent.VK_LEFT){
						pointers head=new pointers(Snake.getFirst().getXs()-10,Snake.getFirst().getYs());
						nextStep(head);
					}else
					if(keyAction==KeyEvent.VK_RIGHT){
						pointers head=new pointers(Snake.getFirst().getXs()+10,Snake.getFirst().getYs());
						nextStep(head);
					}
			}
			
		};
		
		timer=new Timer(timeCounter,Smove);
		timer.start();
	}
	private boolean knockSelf(pointers head){
		if(Snake.contains(head)){
			return true;
		}
		return false;
	}
	private boolean knockBorder(pointers head){
		if(head.getXs()<0 || head.getXs()>SMainArea.getWidth() ||head.getYs()<0 || head.getYs()>SMainArea.getHeight()){
			return true;
		}
		return false;
	}
	private void gameEnd(){
		JDialog end=new JDialog(Sboard,"tip!",true);
		end.setBounds(350, 250, 200, 100);
		JButton exit=new JButton("È·¶¨");
		JLabel message=new JLabel("    Game over!!!");
		message.setFont(new Font("Arial",Font.PLAIN,24));
		message.setForeground(new Color(0xff0000));
		end.setLayout(new GridLayout(2,1,5,10));
		end.add(message);
		end.add(exit);
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		end.setVisible(true);
	}
	private void nextStep(pointers head){
		if(knockSelf(head) || knockBorder(head)){
			timer.stop();
			gameEnd();
		}
		Snake.offerFirst(head);
		if(head.equals(food)){
			hasFood=false;
			timeCounter--;
			SMainArea.repaint();
			return;
		}
		Snake.removeLast();
		SMainArea.repaint();
	}
	public static void main(String[] args){
		new TSnakeMain();
	}
	private class SDraw extends Canvas{
		private static final long serialVersionUID = 1L;
		public void paint(Graphics g){
			Iterator<pointers> it=Snake.iterator();
			g.setColor(new Color(0xff00ff));
			it.hasNext();
			pointers head=it.next();
			g.fillOval(head.getXs(), head.getYs(), 10, 10);
			g.setColor(new Color(0x00ff00));
			while(it.hasNext()){
				pointers temp=it.next();
				g.fillOval(temp.getXs(), temp.getYs(), 10, 10);
			}
			if(!hasFood){
				Random temp=new Random();
				int x=temp.nextInt(SMainArea.getWidth());
				int y=temp.nextInt(SMainArea.getHeight());
				food.setXs(x-x%10);
				food.setYs(y-y%10);
				hasFood=true;
			}
			g.setColor(new Color(0xff0000));
			g.fill3DRect(food.getXs(), food.getYs(), 10, 10,true);
		}
	}
}
class pointers{
	int x;
	int y;
	public pointers(int x,int y){
		this.x=x;
		this.y=y;
	}
	public void setXs(int x){
		this.x=x;
	}
	public int getXs(){
		return x;
	}
	public void setYs(int y){
		this.y=y;
	}
	public int getYs(){
		return y;
	}
	@Override
	public boolean equals(Object anObject){
		pointers temp=(pointers)anObject;
		if(this.x==temp.getXs() && this.y==temp.getYs()){
			return true;
		}
		return false;
	}
}













