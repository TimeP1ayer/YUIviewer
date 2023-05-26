package controller;

import javax.swing.JFrame;

import model.Video;

public class MyRun implements Runnable {
	
	public Video video;
	public JFrame frame;

	public MyRun(Video video,JFrame frame) {
		this.video = video;
		this.frame = frame;
	}
	
	@Override
	public void run() {
		// TODO 自动生成的方法存根

		video.Cplay(frame);
		System.out.println("vedio playing");
	
	}
}
