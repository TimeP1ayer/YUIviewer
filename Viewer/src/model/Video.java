package model;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class Video  extends Component {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2683012758444279593L;
	
	DataInputStream data_in;
	private byte[] yuv_array;//数据缓冲区
	private int[] u_array, v_array;
	private int[] rgb_array;
	private BufferedImage img;
	private int width, height;
	private int frame_number;
    private int frame_size, yuv_frame_size;
    
    //视频播放状态
	private boolean isPlaying;
	private boolean isForward = true;
	private String filename;
	private FileInputStream f_in;
	private int backzero = 0;
	private int endrate;
    private int start_rate;
	private int f_rate;
	private boolean adjust = false;

	
	
	
    //输入文件名，长宽，起始帧
    public Video(String filename, int width, int height, int frame_number) {
    	
    	this.filename = filename;
    	this.width = width;
    	this.height = height;
    	frame_size = width * height;
    	this.frame_number = frame_number;
    	yuv_frame_size = (width * height * 3)>>1;
    	//申请Heap内存空间
    	img = new BufferedImage(width, height, 1);//1:TYPE_INT_RGB
    	yuv_array = new byte[yuv_frame_size];
		u_array = new int[frame_size];
    	v_array = new int[frame_size];
    	rgb_array = new int[frame_size];
    	
    	start_rate = frame_number;
    	
    	try {
    		f_in = new FileInputStream(new File(filename));
    		f_in.skip(frame_number * yuv_frame_size);
    		data_in = new DataInputStream(f_in);
    		data_in.read(yuv_array, 0, yuv_frame_size);
    		this.frame_number++;
    	} catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }

    	//绘制第一帧
    	yuv2rgb();
    	img.setRGB(0, 0, width, height, rgb_array, 0, width);
    }
    
    //将yuv文件数据转型为img可读数组
    private void yuv2rgb()
    {
    	int h;
    	int h2;
    	int frame_size2 = frame_size + (frame_size>>2);
    	int width2 = width<<1;
    	int i2, j2;
    	
    	h = 0;
    	h2 = 0;
    	for (int j = 0; j < (height>>1); j++)
    	{
    		for (int i = 0; i < (width>>1); i++)
    		{
    			i2 = i<<1;
    			//int a, b;
    			u_array[h2 + i2]   = yuv_array[frame_size + h + i]&0xff;
    			v_array[h2 + i2]   = yuv_array[frame_size2 + h + i]&0xff;
    		}
    		h += width>>1;
    		h2 += width2;
    	}
    	//执行双线性插值，将4:1:1的YUV格式转为4:4:4的YUV格式
    	h2 = 0;
    	for (j2 = 0; j2 < height - 2; j2 += 2)
    	{
    		for (i2 = 0; i2 < width - 2; i2 += 2)
    		{
    			int a, b, ab;
    			
    			a = u_array[h2 + i2] + u_array[h2 + i2 + 2];//ˮƽ
    			b = u_array[h2 + i2] + u_array[h2 + i2 + width2];//��ֱ
    			ab = u_array[h2 + i2] + u_array[h2 + i2 + 2] + u_array[h2 + i2 + width2] + u_array[h2 + i2 + width2 + 2];//�Խ���
    			u_array[h2 + i2 + 1] = (a + 1)>>1;
    			u_array[h2 + i2 + width] = (b + 1)>>1;
    			u_array[h2 + i2 + width + 1] = (ab + 2)>>2;
    			
    			a = v_array[h2 + i2] + v_array[h2 + i2 + 2];//ˮƽ
    			b = v_array[h2 + i2] + v_array[h2 + i2 + width2];//��ֱ
    			ab = v_array[h2 + i2] + v_array[h2 + i2 + 2] + v_array[h2 + i2 + width2] + v_array[h2 + i2 + width2 + 2];//�Խ���
    			v_array[h2 + i2 + 1] = (a + 1)>>1;
    			v_array[h2 + i2 + width] = (b + 1)>>1;
    			v_array[h2 + i2 + width + 1] = (ab + 2)>>2;
    		}
			u_array[h2 + i2 + 1] = u_array[h2 + i2];
			u_array[h2 + i2 + width] = 
			u_array[h2 + i2 + width + 1] = (u_array[h2 + i2] + u_array[h2 + i2 + width2] + 1)>>1;
			
			v_array[h2 + i2 + 1] = v_array[h2 + i2];
			v_array[h2 + i2 + width] = 
			v_array[h2 + i2 + width + 1] = (v_array[h2 + i2] + v_array[h2 + i2 + width2] + 1)>>1;
			
			h2 += width2;
    	}
		for (i2 = 0; i2 < width - 2; i2 += 2)
		{
			//int a, b, ab;
			
			u_array[h2 + i2 + 1] = 
			u_array[h2 + i2 + width + 1] = (u_array[h2 + i2] + u_array[h2 + i2 + 2] + 1)>>1;
			u_array[h2 + i2 + width] = u_array[h2 + i2];
			
			v_array[h2 + i2 + 1] = 
			v_array[h2 + i2 + width + 1] = (v_array[h2 + i2] + v_array[h2 + i2 + 2] + 1)>>1;
			v_array[h2 + i2 + width] = v_array[h2 + i2];
		}
		u_array[h2 + i2 + 1] =
		u_array[h2 + i2 + width] = 
		u_array[h2 + i2 + width + 1] = u_array[h2 + i2];   	
    	
		v_array[h2 + i2 + 1] =
		v_array[h2 + i2 + width] = 
		v_array[h2 + i2 + width + 1] = v_array[h2 + i2];
		
		//执行YUV转RGB
		for (int i = 0; i < frame_size; i++)
		{	
			int pixel_r, pixel_g, pixel_b;
			int pixel_y = yuv_array[i]&0xff;
			int pixel_u = u_array[i] - 128;
			int pixel_v = v_array[i] - 128;
			
			double R = pixel_y - 0.001 * pixel_u + 1.402 * pixel_v;
			double G = pixel_y - 0.344 * pixel_u - 0.714 * pixel_v;
			double B = pixel_y + 1.772 * pixel_u + 0.001 * pixel_v;
			
			if (R > 255) pixel_r = 255;
			else if (R < 0) pixel_r = 0;
			else pixel_r = (int)R;
			if (G > 255) pixel_g = 255;
			else if (G < 0) pixel_g = 0;
			else pixel_g = (int)G;
			if (B > 255) pixel_b = 255;
			else if (B < 0) pixel_b = 0;
			else pixel_b = (int)B;
			rgb_array[i] = (pixel_r<<16) | (pixel_g<<8) | pixel_b;
		}
    }
    
    //获取窗口信息
    public Dimension getPreferredSize() {
        if (img == null) {
             return new Dimension(width, height);
        } else {
           return new Dimension(img.getWidth(null), img.getHeight(null));
       }
    }
    
    
    public void writeFile(String formatName, String filename) {
        try {
            ImageIO.write(img, formatName, new File(filename));
        } catch (IOException e) {
            e.printStackTrace();  
        }
    }
    
    public void paint(Graphics g) {
    	g.drawImage(img, 0, 0, null);
    }
       
    //重头播放
    public void backto0() {
		backzero =1;
	}
    
    //暂停播放
    public void pause(){
        isPlaying = false;
    }

    //继续播放
    public void resume(){
        isPlaying = true;
    }

    //正放
    public void forward(){
        isForward = true;
    }

    //倒放
    public void backward(){
        isForward = false;
    }
    
    //调整帧数
    public void adjustFrame_number(int num) {
    	this.frame_number = frame_number+num;
    	this.adjust = true;   
    	this.isPlaying = false;
    }
    
    //设置结束帧
	public void setEndrate(int endrate) {
		// TODO 自动生成的方法存根
		this.endrate = endrate;
	}

	//设置帧率
	public void setF_rate(int f_rate) {
		this.f_rate = f_rate;
	}

	//获取播放状态
	public boolean getisPlaying() {
		// TODO 自动生成的方法存根
		return isPlaying;
	}
	
    //播放方法
    public void Cplay(JFrame f) {
        

    	
    	while (true) {
    		f.setTitle("YUV Player of GDUT   #" + frame_number + " frames");
        	
        	if(isPlaying){     		
        		try {
                    data_in.read(yuv_array, 0, yuv_frame_size);
                } catch (IOException e) {
                    break;
                }
        		
                yuv2rgb();
                img.setRGB(0, 0, width, height, rgb_array, 0, width);       
                repaint();
            }

            //判断是否停止播放
            if(!isPlaying){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    break;
                }
               continue;
            }

            //判断正放还是倒放
            if(isForward){
                frame_number++;
            }else{
                frame_number--;
            }

            //播放完毕时循环播放,或到达结束帧结束,或调整帧数时调用,暂停播放
            if (frame_number == 0 || frame_number >= 300||backzero==1||frame_number==endrate||adjust) {
                
            	//循环播放调整
            	if(!adjust) {
                	frame_number = start_rate;
                }
            	
                try {
                	//重新加载文件
                    data_in.close();
                    FileInputStream f_in = new FileInputStream(new File(filename));
                                      
                    
                    f_in.skip(frame_number * yuv_frame_size);
                    data_in = new DataInputStream(f_in);
                    data_in.read(yuv_array, 0, yuv_frame_size);
                    

                    //重新设置状态
                    backzero=0;
                    isPlaying = false;
                    adjust = false;
                    
                    
                } catch (IOException e) {
                    break;
                }
            }
            
            
            /*设置播放帧率
             */
            try {
                Thread.sleep(1000/f_rate);
            } catch (InterruptedException e) {
                break;
            }
            
            
        }
    }
    
}
