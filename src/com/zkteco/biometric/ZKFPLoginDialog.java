package com.zkteco.biometric;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class ZKFPLoginDialog extends JFrame{
	public ZKFPLoginDialog() {
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	JButton btnOpen = null;
	JButton btnEnroll = null;
	JButton btnVerify = null;
	JButton btnIdentify = null;
	JButton btnRegImg = null;
	JButton btnIdentImg = null;
	JButton btnClose = null;
	JButton btnImg = null;
	JRadioButton radioISO = null;
	JRadioButton radioANSI = null;
	JRadioButton radioZK = null;
	
	
	private JTextArea textArea;
	
	//the width of fingerprint image
	int fpWidth = 0;
	//the height of fingerprint image
	int fpHeight = 0;
	//for verify test
	private byte[] lastRegTemp = new byte[2048];
	//the length of lastRegTemp
	private int cbRegTemp = 0;
	//pre-register template
	private byte[][] regtemparray = new byte[3][2048];
	//Register
	private boolean bRegister = false;
	//Identify
	private boolean bIdentify = true;
	//finger id
	private int iFid = 1;
	
	private int nFakeFunOn = 1;
	//must be 3
	static final int enroll_cnt = 3;
	//the index of pre-register function
	private int enroll_idx = 0;
	
	private byte[] imgbuf = null;
	private byte[] template = new byte[2048];
	private int[] templateLen = new int[1];
	
	
	private boolean mbStop = true;
	private long mhDevice = 0;
	private long mhDB = 0;
	private WorkThread workThread = null;
	
	public void launchFrame(){
		getContentPane().setLayout (null);
		btnOpen = new JButton("Open");  
		getContentPane().add(btnOpen);  
		int nRsize = 20;
		btnOpen.setBounds(30, 10 + nRsize, 140, 30);
		
		btnEnroll = new JButton("Enroll");  
		getContentPane().add(btnEnroll);  
		btnEnroll.setBounds(30, 60 + nRsize, 140, 30);
		
		btnVerify = new JButton("Verify");  
		getContentPane().add(btnVerify);  
		btnVerify.setBounds(30, 110 + nRsize, 140, 30);
		
		btnIdentify = new JButton("Identify");  
		getContentPane().add(btnIdentify);  
		btnIdentify.setBounds(30, 160 + nRsize, 140, 30);
		
		btnRegImg = new JButton("Register By Image");  
		getContentPane().add(btnRegImg);  
		btnRegImg.setBounds(30, 210 + nRsize, 140, 30);
		
		btnIdentImg = new JButton("Verify By Image");  
		getContentPane().add(btnIdentImg);  
		btnIdentImg.setBounds(30, 260 + nRsize, 140, 30);
		
		
		btnClose = new JButton("Close");  
		getContentPane().add(btnClose);  
		btnClose.setBounds(30, 310 + nRsize, 140, 30);
		
		
		//For ISO/Ansi/ZK
		radioANSI = new JRadioButton("ANSI", true);
		getContentPane().add(radioANSI);  
		radioANSI.setBounds(30, 360 + nRsize, 60, 30);
		
		radioISO = new JRadioButton("ISO");
		getContentPane().add(radioISO);  
		radioISO.setBounds(120, 360 + nRsize, 60, 30);
		
		radioZK = new JRadioButton("ZK");
		getContentPane().add(radioZK);
		radioZK.setBounds(210, 360 + nRsize, 60, 30);
        
        ButtonGroup group = new ButtonGroup();
        group = new ButtonGroup();
        group.add(radioANSI);
        group.add(radioISO);
        group.add(radioZK);
        //For End
        
		btnImg = new JButton();
		btnImg.setBounds(200, 5, 288, 375);
		btnImg.setDefaultCapable(false);
		getContentPane().add(btnImg); 
		
		textArea = new JTextArea();
		getContentPane().add(textArea);  
		textArea.setBounds(10, 420, 490, 150);
		textArea.setLineWrap(true);
		textArea.setSelectedTextColor(Color.RED);
		
		this.setSize(520, 620);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("ZKFingerSDK Demo");
		this.setResizable(false);
		
		btnOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (0 != mhDevice)
				{
					//already inited
					textArea.setText("Please close device first!\n");
					return;
				}
				int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
				//Initialize
				cbRegTemp = 0;
				bRegister = false;
				bIdentify = false;
				iFid = 1;
				enroll_idx = 0;
				if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init())
				{
					textArea.setText("Init failed!\n");
					return;
				}
				ret = FingerprintSensorEx.GetDeviceCount();
				if (ret < 0)
				{
					textArea.setText("No devices connected!\n");
					FreeSensor();
					return;
				}
				if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0)))
				{
					textArea.setText("Open device fail, ret = " + ret + "!\n");
					FreeSensor();
					return;
				}
				if (0 == (mhDB = FingerprintSensorEx.DBInit()))
				{
					textArea.setText("Init DB fail, ret = " + ret + "!\n");
					FreeSensor();
					return;
				}
				
				//For ISO/Ansi
				int nFmt = 0;	//Ansi
				if (radioISO.isSelected())
				{
					nFmt = 1;	//ISO
				}
				FingerprintSensorEx.DBSetParameter(mhDB,  5010, nFmt);				
				//For ISO/Ansi End
				
				//set fakefun off
				//FingerprintSensorEx.SetParameter(mhDevice, 2002, changeByte(nFakeFunOn), 4);
				
				byte[] paramValue = new byte[4];
				int[] size = new int[1];
				//GetFakeOn
				//size[0] = 4;
				//FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue, size);
				//nFakeFunOn = byteArrayToInt(paramValue);
				
				size[0] = 4;
				FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
				fpWidth = byteArrayToInt(paramValue);
				size[0] = 4;
				FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
				fpHeight = byteArrayToInt(paramValue);
								
				imgbuf = new byte[fpWidth*fpHeight];
				//btnImg.resize(fpWidth, fpHeight);
				mbStop = false;
				workThread = new WorkThread();
			    workThread.start();// 线程�?�动
				textArea.setText("Open succ! Finger Image Width:" + fpWidth + ",Height:" + fpHeight +"\n");
			}
		});
		
		
		
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FreeSensor();
				
				textArea.setText("Close succ!\n");
			}
		});
		
		btnEnroll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(0 == mhDevice)
				{
					textArea.setText("Please Open device first!\n");
					return;
				}
				if(!bRegister)
				{
					enroll_idx = 0;
					bRegister = true;
					textArea.setText("Please your finger 3 times!\n");
				}
			}
			});
		
		btnVerify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(0 == mhDevice)
				{
					textArea.setText("Please Open device first!\n");
					return;
				}
				if(bRegister)
				{
					enroll_idx = 0;
					bRegister = false;
				}
				if(bIdentify)
				{
					bIdentify = false;
				}
			}
			});
		
		btnIdentify.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(0 == mhDevice)
				{
					textArea.setText("Please Open device first!\n");
					return;
				}
				if(bRegister)
				{
					enroll_idx = 0;
					bRegister = false;
				}
				if(!bIdentify)
				{
					bIdentify = true;
				}
			}
			});
		
		
		btnRegImg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(0 == mhDB)
				{
					textArea.setText("Please open device first!\n");
				}
				//String path = "d:\\test\\fingerprint.bmp";
				//String path = ".\\fingerprint.bmp";
				String path = ".\\FPID_6_2018_09_19_08_05_06.jpg";
				byte[] fpTemplate = new byte[2048];
				int[] sizeFPTemp = new int[1];
				sizeFPTemp[0] = 2048;
				int ret = FingerprintSensorEx.ExtractFromImage( mhDB, path, 500, fpTemplate, sizeFPTemp);		
				if (0 == ret)
				{
					ret = FingerprintSensorEx.DBAdd( mhDB, iFid, fpTemplate);
					if (0 == ret)
					{
						//String base64 = fingerprintSensor.BlobToBase64(fpTemplate, sizeFPTemp[0]);		
						iFid++;
                    	cbRegTemp = sizeFPTemp[0];
                        System.arraycopy(fpTemplate, 0, lastRegTemp, 0, cbRegTemp);
                        //Base64 Template
                        //String strBase64 = Base64.encodeToString(regTemp, 0, ret, Base64.NO_WRAP);
                        textArea.setText("enroll succ\n");
					}
					else
					{
						textArea.setText("DBAdd fail, ret=" + ret + "\n");
					}
				}
				else
				{
					textArea.setText("ExtractFromImage fail, ret=" + ret + "\n");
				}
			}
			});
		
		
		btnIdentImg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(0 ==  mhDB)
				{
					textArea.setText("Please open device first!\n");
				}
				//String path = "d:\\test\\fingerprint.bmp";
				String path = ".\\fingerprint.bmp";
				byte[] fpTemplate = new byte[2048];
				int[] sizeFPTemp = new int[1];
				sizeFPTemp[0] = 2048;
				int ret = FingerprintSensorEx.ExtractFromImage(mhDB, path, 500, fpTemplate, sizeFPTemp);
				if (0 == ret)
				{
					if (bIdentify)
					{
						int[] fid = new int[1];
						int[] score = new int [1];
						ret = FingerprintSensorEx.DBIdentify(mhDB, fpTemplate, fid, score);
                        if (ret == 0)
                        {
                        	textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0] + "\n");
                        }
                        else
                        {
                        	textArea.setText("Identify fail, errcode=" + ret +"\n");
                        }
                            
					}
					else
					{
						if(cbRegTemp <= 0)
						{
							textArea.setText("Please register first!\n");
						}
						else
						{
							ret = FingerprintSensorEx.DBMatch(mhDB, lastRegTemp, fpTemplate);
							if(ret > 0)
							{
								textArea.setText("Verify succ, score=" + ret + "\n");
							}
							else
							{
								textArea.setText("Verify fail, ret=" + ret +"\n");
							}
						}
					}
				}
				else
				{
					textArea.setText("ExtractFromImage fail, ret=" + ret + "\n");
				}
			}
		});
	
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){

            @Override
            public void windowClosing(WindowEvent e) {
                // TODO Auto-generated method stub
            	System.out.println("#############Closing window###########");
            	int status=0;
            	try {
            	FreeSensor();
            	}catch (Exception ex) {
            		status =-1;
            	}
            	System.exit(status);
            }
		});
	}
	
	private void FreeSensor()
	{
		mbStop = true;
		try {		//wait for thread stopping
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (0 != mhDB)
		{
			FingerprintSensorEx.DBFree(mhDB);
			mhDB = 0;
		}
		if (0 != mhDevice)
		{
			FingerprintSensorEx.CloseDevice(mhDevice);
			mhDevice = 0;
		}
		FingerprintSensorEx.Terminate();
	}
	
	public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight,
			String path) throws IOException {
		java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
		java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

		int w = (((nWidth+3)/4)*4);
		int bfType = 0x424d; // �?图文件类型（0—1字节）
		int bfSize = 54 + 1024 + w * nHeight;// bmp文件的大�?（2—5字节）
		int bfReserved1 = 0;// �?图文件�?留字，必须为0（6-7字节）
		int bfReserved2 = 0;// �?图文件�?留字，必须为0（8-9字节）
		int bfOffBits = 54 + 1024;// 文件头开始到�?图实际数�?�之间的字节的�??移�?（10-13字节）

		dos.writeShort(bfType); // 输入�?图文件类型'BM'
		dos.write(changeByte(bfSize), 0, 4); // 输入�?图文件大�?
		dos.write(changeByte(bfReserved1), 0, 2);// 输入�?图文件�?留字
		dos.write(changeByte(bfReserved2), 0, 2);// 输入�?图文件�?留字
		dos.write(changeByte(bfOffBits), 0, 4);// 输入�?图文件�??移�?

		int biSize = 40;// 信�?�头所需的字节数（14-17字节）
		int biWidth = nWidth;// �?图的宽（18-21字节）
		int biHeight = nHeight;// �?图的高（22-25字节）
		int biPlanes = 1; // 目标设备的级别，必须是1（26-27字节）
		int biBitcount = 8;// �?个�?素所需的�?数（28-29字节），必须是1�?（�?�色）�?4�?（16色）�?8�?（256色）或者24�?（真彩色）之一。
		int biCompression = 0;// �?图压缩类型，必须是0（�?压缩）（30-33字节）�?1（BI_RLEB压缩类型）或2（BI_RLE4压缩类型）之一。
		int biSizeImage = w * nHeight;// 实际�?图图�?的大�?，�?�整个实际绘制的图�?大�?（34-37字节）
		int biXPelsPerMeter = 0;// �?图水平分辨率，�?米�?素数（38-41字节）这个数是系统默认值
		int biYPelsPerMeter = 0;// �?图垂直分辨率，�?米�?素数（42-45字节）这个数是系统默认值
		int biClrUsed = 0;// �?图实际使用的颜色表中的颜色数（46-49字节），如果为0的�?，说明全部使用了
		int biClrImportant = 0;// �?图显示过程中�?�?的颜色数(50-53字节)，如果为0的�?，说明全部�?�?

		dos.write(changeByte(biSize), 0, 4);// 输入信�?�头数�?�的总字节数
		dos.write(changeByte(biWidth), 0, 4);// 输入�?图的宽
		dos.write(changeByte(biHeight), 0, 4);// 输入�?图的高
		dos.write(changeByte(biPlanes), 0, 2);// 输入�?图的目标设备级别
		dos.write(changeByte(biBitcount), 0, 2);// 输入�?个�?素�?��?�的字节数
		dos.write(changeByte(biCompression), 0, 4);// 输入�?图的压缩类型
		dos.write(changeByte(biSizeImage), 0, 4);// 输入�?图的实际大�?
		dos.write(changeByte(biXPelsPerMeter), 0, 4);// 输入�?图的水平分辨率
		dos.write(changeByte(biYPelsPerMeter), 0, 4);// 输入�?图的垂直分辨率
		dos.write(changeByte(biClrUsed), 0, 4);// 输入�?图使用的总颜色数
		dos.write(changeByte(biClrImportant), 0, 4);// 输入�?图使用过程中�?�?的颜色数

		for (int i = 0; i < 256; i++) {
			dos.writeByte(i);
			dos.writeByte(i);
			dos.writeByte(i);
			dos.writeByte(0);
		}

		byte[] filter = null;
		if (w > nWidth)
		{
			filter = new byte[w-nWidth];
		}
		
		for(int i=0;i<nHeight;i++)
		{
			dos.write(imageBuf, (nHeight-1-i)*nWidth, nWidth);
			if (w > nWidth)
				dos.write(filter, 0, w-nWidth);
		}
		dos.flush();
		dos.close();
		fos.close();
	}

	public static byte[] changeByte(int data) {
		return intToByteArray(data);
	}
	
	public static byte[] intToByteArray (final int number) {
		byte[] abyte = new byte[4];  
	    // "&" 与（AND），对两个整型�?作数中对应�?执行布尔代数，两个�?都为1时输出1，�?�则0。  
	    abyte[0] = (byte) (0xff & number);  
	    // ">>"�?�移�?，若为正数则高�?补0，若为负数则高�?补1  
	    abyte[1] = (byte) ((0xff00 & number) >> 8);  
	    abyte[2] = (byte) ((0xff0000 & number) >> 16);  
	    abyte[3] = (byte) ((0xff000000 & number) >> 24);  
	    return abyte; 
	}	 
		 
		public static int byteArrayToInt(byte[] bytes) {
			int number = bytes[0] & 0xFF;  
		    // "|="按�?或赋值。  
		    number |= ((bytes[1] << 8) & 0xFF00);  
		    number |= ((bytes[2] << 16) & 0xFF0000);  
		    number |= ((bytes[3] << 24) & 0xFF000000);  
		    return number;  
		 }
	
		private class WorkThread extends Thread {
	        @Override
	        public void run() {
	            super.run();
	            int ret = 0;
	            while (!mbStop) {
	            	templateLen[0] = 2048;
	            	if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen)))
	            	{
	            		if (nFakeFunOn == 1)
                    	{
                    		byte[] paramValue = new byte[4];
            				int[] size = new int[1];
            				size[0] = 4;
            				int nFakeStatus = 0;
            				//GetFakeStatus
            				ret = FingerprintSensorEx.GetParameters(mhDevice, 2004, paramValue, size);
            				nFakeStatus = byteArrayToInt(paramValue);
            				System.out.println("ret = "+ ret +",nFakeStatus=" + nFakeStatus);
            				if (0 == ret && (byte)(nFakeStatus & 31) != 31)
            				{
            					textArea.setText("Is a fake finger?\n");
            					return;
            				}
                    	}
                    	OnCatpureOK(imgbuf);
                    	OnExtractOK(template, templateLen[0]);
	            	}
	                try {
	                    Thread.sleep(500);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }

	            }
	        }
	    }
		
		private void OnCatpureOK(byte[] imgBuf)
		{
			try {
				writeBitmap(imgBuf, fpWidth, fpHeight, "fingerprint.bmp");
				btnImg.setIcon(new ImageIcon(ImageIO.read(new File("fingerprint.bmp"))));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void OnExtractOK(byte[] template, int len)
		{
			if(bRegister)
			{
				int[] fid = new int[1];
				int[] score = new int [1];
                int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
                if (ret == 0)
                {
                    textArea.setText("the finger already enroll by " + fid[0] + ",cancel enroll\n");
                    bRegister = false;
                    enroll_idx = 0;
                    return;
                }
                if (enroll_idx > 0 && FingerprintSensorEx.DBMatch(mhDB, regtemparray[enroll_idx-1], template) <= 0)
                {
                	textArea.setText("please press the same finger 3 times for the enrollment\n");
                    return;
                }
                System.arraycopy(template, 0, regtemparray[enroll_idx], 0, 2048);
                enroll_idx++;
                if (enroll_idx == 3) {
                	int[] _retLen = new int[1];
                    _retLen[0] = 2048;
                    byte[] regTemp = new byte[_retLen[0]];
                    
                    if (0 == (ret = FingerprintSensorEx.DBMerge(mhDB, regtemparray[0], regtemparray[1], regtemparray[2], regTemp, _retLen)) &&
                    		0 == (ret = FingerprintSensorEx.DBAdd(mhDB, iFid, regTemp))) {
                    	iFid++;
                    	cbRegTemp = _retLen[0];
                        System.arraycopy(regTemp, 0, lastRegTemp, 0, cbRegTemp);
                        //Base64 Template
                        textArea.setText("enroll succ:\n");
                    } else {
                    	textArea.setText("enroll fail, error code=" + ret + "\n");
                    }
                    bRegister = false;
                } else {
                	textArea.setText("You need to press the " + (3 - enroll_idx) + " times fingerprint\n");
                }
			}
			else
			{
				if (bIdentify)
				{
					int[] fid = new int[1];
					int[] score = new int [1];
					int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
                    if (ret == 0)
                    {
                    	textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0] +"\n");
                    }
                    else
                    {
                    	textArea.setText("Identify fail, errcode=" + ret + "\n");
                    }
                        
				}
				else
				{
					if(cbRegTemp <= 0)
					{
						textArea.setText("Please register first!\n");
					}
					else
					{
						int ret = FingerprintSensorEx.DBMatch(mhDB, lastRegTemp, template);
						if(ret > 0)
						{
							textArea.setText("Verify succ, score=" + ret + "\n");
						}
						else
						{
							textArea.setText("Verify fail, ret=" + ret + "\n");
						}
					}
				}
			}
		}
		
		public static void main(String[] args) {
			new ZKFPLoginDialog().launchFrame();
		}
		
		
		/**
		 * load reqister 
		 * @param path1
		 */
		public void loadTemplate(String path1) {
			if(0 == mhDB)
			{
				textArea.setText("Please open device first!\n");
				return ;
			}
			//String path = "d:\\test\\fingerprint.bmp";
			//String path = ".\\fingerprint.bmp";
			String path = ".\\FPID_6_2018_09_19_08_05_06.jpg";
			byte[] fpTemplate = new byte[2048];
			int[] sizeFPTemp = new int[1];
			sizeFPTemp[0] = 2048;
//			int ret = FingerprintSensorEx.ExtractFromImage( mhDB, path, 500, fpTemplate, sizeFPTemp);	
//			String path1="G:\\rsync\\RESEARCHS\\moodle\\eclipse\\javaws\\moodle_att_https\\tpl\\TPL9_6.tpl";
//			String 
			
			path1="G:\\rsync\\RESEARCHS\\moodle\\eclipse\\javaws\\moodle_att_https\\tpl\\TPL10_6.tpl";
			File nf =new File(path1);
			try {
//				fpTemplate=Files.readAllBytes(nf.toPath());
				new BufferedInputStream(new FileInputStream(nf)).read(fpTemplate);
				System.out.println("tpl length:"+nf.length());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//			f.re
			int ret=0; //fake extract
			if (0 == ret)
			{
				ret = FingerprintSensorEx.DBAdd( mhDB, iFid, fpTemplate);
				if (0 == ret)
				{
					//String base64 = fingerprintSensor.BlobToBase64(fpTemplate, sizeFPTemp[0]);		
					iFid++;
                	cbRegTemp = sizeFPTemp[0];
                    System.arraycopy(fpTemplate, 0, lastRegTemp, 0, cbRegTemp);
                    //Base64 Template
                    //String strBase64 = Base64.encodeToString(regTemp, 0, ret, Base64.NO_WRAP);
                    textArea.setText("enroll succ\n");
				}
				else
				{
					textArea.setText("DBAdd fail, ret=" + ret + "\n");
				}
			}
			else
			{
				textArea.setText("ExtractFromImage fail, ret=" + ret + "\n");
			}
		}
		
		/**
		 * load all templates inside all folders
		 * @param folder
		 */
		public void loadFolders(String []folder){
			
		}

		
		
}
