package view;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import controller.MyListener;
import controller.MyRun;
import model.Video;


public class ViewFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3630387485195125563L;

	//视频地址
	public String URL;

	//按钮位置参数
	int btn_left = 400;//左派按钮位置
	int btn_right = btn_left+70;//右排按钮位置	
	
	//视频默认尺寸
	public int width = 176;
	public int heigh = 144;
	
	public Video video;
	public JFrame video_frame;
	
	//视频线程
	public Thread thread;
	
	public MyRun myrun;
	
	//帧率
	public int f_rate = 5;

	//监听器
	public MyListener mylistener = new MyListener();
	
	//按钮
	public MyButton openfile;
	public MyButton play;
	public MyButton close;
	public MyButton Quit;
	public MyButton Back;
	public MyButton Next;
	public MyButton Next5;
	public MyButton Previous;
	public MyButton Revious;
	public MyButton Backward;
	
	//起始与结束帧
	public int start_f=0;
	public int end_f;

	//输入框
	public JTextField In_from;
	public JTextField In_to;
	public JTextField Width;
	public JTextField Heigh;

	//圆形按钮
	public JRadioButton Other;
	public JRadioButton CIF;
	public JRadioButton QCIF;
	public JRadioButton Zoom;

	//文本框
	public JLabel T_Heigh;
	public JLabel T_Width;
	
	//拉选框
	public JComboBox<String> list;
	
	//容器
	public JPanel P_P;
	public JPanel root;
	
	//有参构造
	public ViewFrame(String tittle) {
		
		super(tittle);
		//禁止重新设置窗口大小
		setResizable(false);
		
		//565,185
		root= new JPanel();
		root.setLayout(null);
		this.setContentPane(root);
		
		
		//open file按钮
		/*
		 * 文件选中器
		 * 先获取每个按钮的设置
		 * 在最后负责给每个按钮重新载入对象
		 */
		openfile = new MyButton("openfile",btn_right,10);		
		root.add(openfile);
		


		/*/play按钮
		 * 按下一次后变为pause
		 * 
		 */
		play = new MyButton("play",btn_right,35);		
		root.add(play);
		
		
		/*Close All
		 * 关闭窗口并释放资源
		 */
		close = new MyButton("Close All",btn_right,60);
		root.add(close);
		
		
		/*Quit
		 * 关闭播放器
		 */
		Quit = new MyButton("Quit", btn_right, 85);
		root.add(Quit);
		
		
		/*BackTo0
		 * 归零
		 */
		Back = new MyButton("BackTo0", btn_right, 85+25);
		root.add(Back);
		
		/*Next
		 * 跳转到1帧后
		 */
		Next = new MyButton("Next", btn_left, 10);
		root.add(Next);
		
		/*Revious
		 * 跳转到5帧后
		 */
		Next5 = new MyButton("Next 5", btn_left, 35);
		root.add(Next5);
		
		/*Previous
		 * 跳转到1帧前
		 */
		
		Previous = new MyButton("Previous", btn_left, 60);
		root.add(Previous);
		
		/*Revious
		 * 跳转到5帧前
		 * 
		 */
		Revious = new MyButton("Revious !", btn_left, 85);
		root.add(Revious);
		
		
		/*Backward
		 * 正放与倒放
		 */
		
		Backward = new MyButton("backward", btn_left, 110);	
		root.add(Backward);
		
		
		
		/*
		 * Frame Size边框
		 */
		
		//画面放大两倍
        Zoom = new JRadioButton("Zoom 2x2");
        Zoom.setBounds(10, 90, 90, 20);
		
		
		JPanel F_size = new JPanel();
		F_size.setLayout(null);
		F_size.setBorder(BorderFactory.createTitledBorder("Frame Size"));
		F_size.setBounds(20, 10, 200,120);
		

		//Width文字
		T_Width = new JLabel("Width");
		T_Width.setBounds(90, 65, 40, 15);
		T_Width.setEnabled(false);
		F_size.add(T_Width);
		//Width输入框
		Width = new JTextField(); // 创建一个单行输入框
		Width.setEditable(false); // 设置输入框允许编辑
		Width.setColumns(5); // 设置输入框的长度为11个字符
		Width.setBounds(90, 85, 40, 15);
		
		Width.setText("176");
		F_size.add(Width); // 在面板上添加单行输入框
		
		
		//Height文字
		T_Heigh = new JLabel("Height");
		T_Heigh.setBounds(140, 65, 40, 15);
		T_Heigh.setEnabled(false);
		F_size.add(T_Heigh);
		//Height输入框
		Heigh = new JTextField(); // 创建一个单行输入框
		Heigh.setEditable(false); // 设置输入框允许编辑
		Heigh.setColumns(5); // 设置输入框的长度为5个字符
		Heigh.setBounds(140, 85, 40, 15);
		
		Heigh.setText("144");
		F_size.add(Heigh); // 在面板上添加单行输入框
		
	
		int R_b = 28;
		
		//CIF
		CIF = new JRadioButton("CIF");
		CIF.setBounds(10, R_b, 50, 15);
		F_size.add(CIF);

		
		//QCIF
		QCIF = new JRadioButton("QCIF");
		QCIF.setBounds(10, R_b+25, 70, 15);
		QCIF.setSelected(true); //默认选中	
		F_size.add(QCIF);
		
		
		
		//Other
		Other = new JRadioButton("Other");
		Other.setBounds(10, R_b+50, 70, 15);
		F_size.add(Other);
		
		//按钮组，每次只有一个被选中
		ButtonGroup group = new ButtonGroup();
		group.add(CIF);
		group.add(QCIF);
		group.add(Other);
		
	
        root.add(F_size);
      
		//Play Parameters边框
        /*
         * 
         * Frame rate拉选框
         * 设置帧速度
         * 
         * From to
         * 播放从x帧到y帧
         * 
         * Zoom 2x2
         * 画面放大两倍，窗口放大两倍
         */

        P_P = new JPanel();//创建面板Play Parameters
        P_P.setLayout(null);
        P_P.setBorder(BorderFactory.createTitledBorder("Play Parameters"));
        P_P.setBounds(230, 10, 160,120);

        
        JLabel F_rate = new JLabel("Frame Rate");      
        JLabel F_from = new JLabel("From");
        JLabel F_to = new JLabel("To");
        
        F_rate.setBounds(10, 20, 80, 20);
        F_from.setBounds(10, 60, 40, 20);
        F_to.setBounds(90, 60, 40, 20);
        
        
        /*下拉列表
         * 调整帧率
         */
        list=new JComboBox<>();
        
        //添加帧率
        for(int i = 5;i<=30;i+=5)
        list.addItem(String.valueOf(i));
        list.addItem("999");

        list.setEditable(true);//帧率可编辑
        list.setBounds(80,20,70,20);
        

        
        
        /*
         * 播放从起始帧到结束帧
         */
        
        //起始帧
        In_from = new JTextField(); // 创建一个单行输入框from
        In_from.setEditable(true); // 设置输入框允许编辑
        In_from.setColumns(20); // 设置输入框的长度为20个字符
        In_from.setText("0");
        In_from.setBounds(45, 60, 40, 20);
        
        //结束帧
        In_to = new JTextField(); // 创建一个单行输入框to
        In_to.setEditable(true); // 设置输入框允许编辑
        In_to.setColumns(20); // 设置输入框的长度为20个字符
        In_to.setText("0");
        In_to.setBounds(110, 60, 40, 20);
        
     
        //添加至面板
        P_P.add(list);    
        P_P.add(In_from);
        P_P.add(In_to);     
        P_P.add(F_rate);
        P_P.add(F_from);
        P_P.add(F_to);       
        P_P.add(Zoom);
        root.add(P_P);
		
	}

		}
	
