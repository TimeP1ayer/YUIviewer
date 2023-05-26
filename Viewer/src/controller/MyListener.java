package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Video;
import view.MyButton;

public class MyListener{

	private Video video;
	
	public void setVideo(Video video) {
		this.video = video;
	}

	public class play implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			
			MyButton play = (MyButton) e.getSource();
			
			//play.getText().equals("play")
			
			if(video.getisPlaying()==false) {
				//播放视频，并将按钮改为暂停
				play.setText("pause");
				video.resume();
				
			}else {
				//暂停视频，并将按钮改为播放
				play.setText("play");
				video.pause();
				
			}
			
		}
	}
	
	public class next implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			video.adjustFrame_number(1);
			System.out.println("next!");
		}
	
	}
	
	public class next5 implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			video.adjustFrame_number(5);
			System.out.println("next5!");
		}
	
	}

	public class backto0 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			video.backto0();
		}
		
		
	}
	
	public class previous implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			video.adjustFrame_number(-1);
		}
	}
		
	public class revious implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			video.adjustFrame_number(-5);
		}
	}
		
	public class backward implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
			MyButton backward = (MyButton) e.getSource();
			if(backward.getText().equals("backward")) {
				backward.setText("forward");
				video.forward();
			}else{
				backward.setText("backward");
				video.backward();
		}
		}
	}
	

}
