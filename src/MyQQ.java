import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.JDialog;
import java.net.*;
import java.io.*;
public class MyQQ
{
	static MyQQ QQ=new MyQQ();
	private static JTextArea area1=null;
	private static JTextField nafield=null;
	private static JTextArea area2=null;
	private static JButton button1=null;
	private static JButton button2=null;
	private static JButton button3=null;
	static String str1="";
	static String str2="";
	static String str3="";
	static String str4="";
	static String str5="";
	static String fileName="";
	static String filePath="";
	static String flag="";
	FileInputStream indata=null;
	int fileSize=0;
	static JPanel panel=null;
	public MyQQ()
	{
		
	}
	public static void main(String[] args)
	{
		try{
			File file=new File("Portinfo.dat");
			BufferedReader  input=new BufferedReader(new FileReader(file));        
			String ss=input.readLine();
			String[] Array = ss.split("#");
			str1=Array[0];
			str2=Array[1];
			str3=Array[2];
			str4=Array[3];
			str5=Array[4];
			input.close();
		}
		catch(Exception e)
		{
			try{
				byte oo=(Byte) null;
				FileOutputStream outfile=new FileOutputStream("Portinfo.dat");
				outfile.write(oo);
				outfile.close();
			}
			catch(Exception e1){}
		}
		final JFrame frame=new JFrame();
		Toolkit kit=Toolkit.getDefaultToolkit();
		Dimension screenSize=kit.getScreenSize();
		int x=(screenSize.width-400)/3;
		int y=(screenSize.height-510)/3;
		frame.setLocation(x,y);
		frame.setIconImage(new ImageIcon("qq.jpg").getImage());
		frame.setSize(410,510);
		frame.setTitle("QQ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		JMenuBar menubar=new JMenuBar();
		frame.setJMenuBar(menubar);
		JMenu SysMenu=new JMenu("系统");
		menubar.add(SysMenu);
		SysMenu.add( new AbstractAction("设置")
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event)
			{
				final JDialog dialog=new JDialog(frame,"设置",true);
				Container cc = dialog.getContentPane();
				cc.setLayout(new FlowLayout(FlowLayout.LEFT));
				JLabel label1=new JLabel("文本接收端口:");
				JLabel label2=new JLabel("文件接收端口:");
				JLabel label3=new JLabel("目标接收地址:");
				JLabel label4=new JLabel("目标文本端口:");
				JLabel label5=new JLabel("目标文件端口:");
				final JTextField text1=new JTextField(15);
				final JTextField text2=new JTextField(15);
				final JTextField text3=new JTextField(15);
				final JTextField text4=new JTextField(15);
				final JTextField text5=new JTextField(15);
				text1.setText(str1);
				text2.setText(str2);
				text3.setText(str3);
				text4.setText(str4);
				text5.setText(str5);
				cc.setLayout(new FlowLayout(FlowLayout.CENTER));
				JButton btn1=new JButton("确定");
				btn1.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{     
						str1=text1.getText().trim();
						str2=text2.getText().trim();
						str3=text3.getText().trim();
						str4=text4.getText().trim();
						str5=text5.getText().trim();
						try{
							File f=new File("Portinfo.dat");
							FileOutputStream putfile=new FileOutputStream(f);
							String str=str1+"#"+str2+"#"+str3+"#"+str4+"#"+str5;
							byte b[]=str.getBytes();
							putfile.write(b);
							putfile.close();
						}catch(Exception e2){}
						dialog.dispose();
					}
				}
			);
				JButton btn2=new JButton("取消");
				btn2.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent event)
					{
						dialog.dispose();
					}
				});
				cc.add(label1);
				cc.add(text1);
				cc.add(label2);
				cc.add(text2);
				cc.add(label3);
				cc.add(text3);
				cc.add(label4);
				cc.add(text4);
				cc.add(label5);
				cc.add(text5);
				cc.add(btn1);
				cc.add(btn2);
				dialog.setResizable(false);
				dialog.setSize(300,220);
				dialog.setVisible(true);
			}
		});
		SysMenu.addSeparator();
		SysMenu.add(new AbstractAction("退出")
		{
			private static final long serialVersionUID = 1L;
			public void actionPerformed(ActionEvent event)
			{
				System.exit(0);
			}
		});
		menubar.add(SysMenu);
		panel=new JPanel();
		panel.setLayout(new BorderLayout());
		area1=new JTextArea(13,34);
		area1.setLineWrap(true);
		JScrollPane scroll=new JScrollPane(area1);
		JPanel panel1=new JPanel();
		panel1.add(scroll);  
		panel.add(panel1,BorderLayout.NORTH);
		JPanel panel2=new JPanel();
		button1=new JButton("传送文件");
		button2=new JButton("接收文件");
		button2.setEnabled(false);
		button3=new JButton("发送");
		JPanel panel3=new JPanel();
		JLabel nalabel=new JLabel("昵称:");
		nafield=new JTextField(31);
		area2=new JTextArea(6,34);
		area2.setLineWrap(true);
		JScrollPane scroll2=new JScrollPane(area2);
		panel3.add(nalabel);
		panel3.add(nafield);
		panel3.add(scroll2);
		panel2.add(button1);
		panel2.add(button2);
		panel2.add(button3);
		panel.add(panel2,BorderLayout.SOUTH);
		panel.add(panel3,BorderLayout.CENTER);
		frame.add(panel);
		frame.setVisible(true);
		button3.addActionListener(abtn3Listener);
		button1.addActionListener(abtn1Listener);
		button2.addActionListener(abtn2Listener);
		wordthread.start();
	}
	private class getWords implements Runnable
	{
		public void run()
		{
			DatagramPacket pack=null;
			DatagramSocket mail_data=null;
			int no1=0;
			while(true)
			{  
				byte data[]=new byte[8192];
				try
				{
					no1=Integer.parseInt(str1);
					pack=new DatagramPacket(data,data.length);
					mail_data=new DatagramSocket(no1);
				}
				catch(Exception e){}  
				try
				{
					mail_data.receive(pack);
					int length=pack.getLength();
					String message=new String(pack.getData(),0,length);
					if(message.substring(0,1).equals("*"))
					{
						button2.setEnabled(true);
					}
					if(message.substring(0,2).equals("##"))
					{         
						int no5=Integer.parseInt(str5);
						byte buffer[]=new byte[1024];

						InetAddress address=InetAddress.getByName(str3);
						DatagramPacket data_p=null;
						DatagramSocket mail_out=new DatagramSocket();
						if(indata.read(buffer)==-1)
						{
							indata.close();
						}
						if(fileSize>1024)
						{
							data_p=new DatagramPacket(buffer,buffer.length,address,no5);
							fileSize=fileSize-1024;
						}

						else if(fileSize<=1024&&fileSize>0)
						{
							data_p=new DatagramPacket(buffer,0,fileSize,address,no5);
							area1.append("文件传送结束。\n");
							fileSize=0;
						}

						mail_out.send(data_p);
					}
					if(!message.equals("####"))
						area1.append(message+"\n");
				}
				catch(Exception e){}
			}
		}
	}
	static getWords agetWords=QQ.new getWords();
	static Thread wordthread=new Thread(agetWords);
	private class button3Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String data="昵称 :"+nafield.getText().trim()+"   "+area2.getText().trim();
			byte buffer[]=data.getBytes();
			int no4=Integer.parseInt(str4);
			try
			{
				InetAddress address=InetAddress.getByName(str3);
				DatagramPacket data_pack=new DatagramPacket(buffer,buffer.length,address,no4);
				DatagramSocket mail_data=new DatagramSocket();
				mail_data.send(data_pack);
				area1.append(data+"\n");
				area2.setText("");
			}
			catch(Exception e){}
		}
	}
	static button3Listener abtn3Listener=QQ.new button3Listener();
	private class button1Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{

			JFileChooser fileChooser=new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			fileChooser.setCurrentDirectory(new File("."));
			int result=fileChooser.showOpenDialog(panel);
			if(result==JFileChooser.APPROVE_OPTION)
			{
				fileName=fileChooser.getSelectedFile().getName();
				filePath=fileChooser.getSelectedFile().getPath();
				fileSize=(int)fileChooser.getSelectedFile().length();
				try {
					indata= new FileInputStream(filePath);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				}
				int pd=JOptionPane.showConfirmDialog(null, "确认传送 "+fileName,"提示",JOptionPane.YES_NO_OPTION);
				if(pd==JOptionPane.YES_OPTION)
				{ 
					try
					{
						String ss="我要传文件:"+fileName;
						String data="**昵称 :"+nafield.getText().trim()+"   "+ss;
						byte buffer[]=data.getBytes();
						int no4=Integer.parseInt(str4);
						InetAddress address=InetAddress.getByName(str3);
						DatagramPacket data_pack=new DatagramPacket(buffer,buffer.length,address,no4);
						DatagramSocket mail_data=new DatagramSocket();
						mail_data.send(data_pack);
						area1.append(data+"\n");
						area2.setText("");
					}
					catch(Exception e){}
				}
			}
		}

	}
	static button1Listener abtn1Listener=QQ.new button1Listener();
	private class button2Listener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{

			button2.setEnabled(false);
			String data="##昵称 :"+nafield.getText().trim()+"   "+"接收文件";
			area1.append(data+"\n");
			byte buffer[]=data.getBytes();
			int no4=Integer.parseInt(str4);
			try
			{
				InetAddress address=InetAddress.getByName(str3);
				DatagramPacket data_pack=new DatagramPacket(buffer,buffer.length,address,no4);
				DatagramSocket mail_data=new DatagramSocket();
				mail_data.send(data_pack);
			}catch(Exception e){}
			filethread.start();
		}
	}
	static button2Listener abtn2Listener=QQ.new button2Listener();
	private class getFile implements Runnable
	{
		public void run()
		{
			int no4=Integer.parseInt(str4);
			DatagramPacket pack=null;
			DatagramPacket reportpack=null;
			DatagramSocket mail_data=null;
			DatagramSocket report_data=null;
			int no2=Integer.parseInt(str2);
			try {
				mail_data=new DatagramSocket(no2);
			} catch (SocketException e2) {
				e2.printStackTrace();
			}
			FileOutputStream out = null;
			try {
				out = new FileOutputStream("D:\\MyAcept.dat");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
			while(true)
			{  
				byte data[]=new byte[1024];
				try
				{

					pack=new DatagramPacket(data,data.length);
					mail_data.receive(pack); 
					out.write(pack.getData(),0,pack.getLength());
					if(pack.getLength()<1024)
					{
						area1.append("文件传输结束.");
						break;
					}
					String report="####";
					byte reporter[]=report.getBytes();
					InetAddress address=InetAddress.getByName(str3);
					reportpack=new DatagramPacket(reporter,reporter.length,address,no4);
					report_data=new DatagramSocket();
					report_data.send(reportpack);
				}
				catch(Exception e){}
			}
		}
	}
	static getFile agetFile=QQ.new getFile();
	static Thread filethread=new Thread(agetFile);
}
