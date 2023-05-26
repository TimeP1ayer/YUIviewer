package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URI;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import model.Video;
import view.ViewFrame;


public class ControlFrame extends ViewFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3729941892765462550L;

	public ControlFrame(String tittle) {
		super(tittle);
		// TODO 自动生成的构造函数存根
		
		play.addActionListener(mylistener.new play());
		
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				video_frame.dispose();
				//video_frame.setVisible(false);
				video = null;
			}
		});
		
		Back.addActionListener(mylistener.new backto0());
		
		Next.addActionListener(mylistener.new next());
		
		Next5.addActionListener(mylistener.new next5());
		
		Previous.addActionListener(mylistener.new previous());
		
		Revious.addActionListener(mylistener.new revious());
		
		Backward.addActionListener(mylistener.new backward());
		
		Quit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				dispose();
			}
		});
		
		CIF.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(CIF.isSelected()) {
					
					Width.setEditable(false);
					Heigh.setEditable(false);
					
					T_Width.setEnabled(false);
					T_Heigh.setEnabled(false);
					
					
					Width.setText("352");
					Heigh.setText("288");


				}
			}
		});
				
		QCIF.addActionListener(new ActionListener() {

			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(QCIF.isSelected()) {

					Width.setEditable(false);
					Heigh.setEditable(false);
					
					T_Width.setEnabled(false);
					T_Heigh.setEnabled(false);

					Width.setText("176");
  					Heigh.setText("144");

				}
			}
		});
		
		Other.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(Other.isSelected()) {

					Width.setEditable(true);
					Heigh.setEditable(true);
					
					T_Width.setEnabled(true);
					T_Heigh.setEnabled(true);
					
				}else {					
					Width.setEditable(false);
					Heigh.setEditable(false);
					
					T_Width.setEnabled(false);
					T_Heigh.setEnabled(false);
					
				}
			}
		});
		
        list.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				f_rate = Integer.parseInt((String)list.getSelectedItem());
				System.out.println(f_rate);
				
			}
		});
		
		openfile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				
				
				try {
		        	//获取用户填写尺寸
		        	width = Integer.parseInt(Width.getText());		        
		        	heigh = Integer.parseInt(Heigh.getText());
		        	
		        	
		        	//获取起始帧与结束帧
			        start_f = Integer.parseInt(In_from.getText());
			        end_f  = Integer.parseInt(In_to.getText());
		        	
		        }catch(NumberFormatException et) {
		        	System.out.println("类型错误");
		        }
		        
				
		        System.out.println("playing width:heigh = " + width +" : " +heigh);
		        		        	
	        	System.out.println("Rate: " + f_rate);
	
				
				JFileChooser flc = new JFileChooser();
				flc.setDialogTitle("请选择视频文件");
				int result = flc.showOpenDialog(null);
				
				
				if(result == JFileChooser.APPROVE_OPTION) {
					
					//获取文件路径
					URL = flc.getSelectedFile().getAbsolutePath();
					URI uri = new File(URL).toURI();
					
					System.out.println("已选定文件:  "+uri.toString());
					
					//创建视频对象，设定起始帧
			        video = new Video(URL, width, heigh, start_f); 
			        video.setF_rate(f_rate);
			        
			        
					if(end_f!=0&&end_f>start_f) {
						video.setEndrate(end_f);
						System.out.println("Start:to = " + start_f +" : " +end_f);
					}else {
						System.out.println("Start:to = " + start_f +" : end");
					}
					
			        		        
					//创建视频窗口
			        video_frame = new JFrame("YUV  Player of GDUT");
			        video_frame.setResizable(false);
        
        
			        //添加视频到窗口,不可重新设置大小
			        video_frame.add("Center", video);
			        video_frame.pack();
			        video_frame.setVisible(true);			        
			        video_frame.addWindowListener(new WindowAdapter() {
			            public void windowClosing(WindowEvent e) {System.exit(0);}
			        });
			        
			        //对视频进行监听
			        mylistener.setVideo(video);

			        //视频进入线程
			        myrun = new MyRun(video,video_frame);
					thread = new Thread(myrun);
							
					thread.start();
				
				}
			}
		});
		
		
		
	}

}
