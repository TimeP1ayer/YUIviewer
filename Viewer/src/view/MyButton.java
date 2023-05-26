package view;

import java.awt.Insets;

import javax.swing.JButton;
/*
 * 继承JButton，添加设置位置的参数
 * 
 * 
 */
public class MyButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1214128623951037783L;

	public MyButton(String tittle,int x ,int y){
		//调整边界与位置
		this.setText(tittle);
		this.setBounds(x, y, 65, 25);
		this.setMargin(new Insets(0,0,0,0));
	}
	
	
}
