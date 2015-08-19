package tcp;
import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
 
 
public class Client 
{
	//Define constant
	final static int PORT = 5000;
	final static String serverIP = "192.168.42.1";
	
    public static void main(String[] args) 
    {
        try 
        {
            System.out.println("Connecting. Server IP : " + serverIP);
             
            // socket Created and connect request
            Socket socket = new Socket(serverIP, PORT);
            System.out.println(Time.getTime() + "Connect"); 
            
            FileSender fs = new FileSender(socket);
            fs.start();
        } 
        catch (ConnectException ce) 
        {
            ce.printStackTrace();
        } 
        catch (IOException ie) 
        {
            ie.printStackTrace();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        } 
    } 
} 

class Time
{
	static String getTime() {
	    SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
	    return f.format(new Date());
	} 
}

class FileSender extends Thread
{
	Socket socket;
	DataOutputStream dos;
	FileInputStream fis;
	BufferedInputStream bis;
	
	public FileSender(Socket socket)
	{
		this.socket = socket;
		try
		{
			//data send stream create
			dos = new DataOutputStream(socket.getOutputStream());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try
		{
			System.out.println(Time.getTime() + "file transfer start");
			
			//file name transfer
			String fName = "~/media/RACK001201508132000.mp4";
			dos.writeUTF(fName);
			System.out.print(Time.getTime() + "File name (%s) Sended\n");
			
			//file read & send
			File f = new File(fName);
			fis = new FileInputStream(f);
			bis = new BufferedInputStream(fis);
			
			int len;
			int size = 4096;
			byte[] data = new byte[size];
			while ( (len = bis.read(data)) != -1 )
			{
				dos.write(data,0,len);
			}
			
			dos.flush();
			dos.close();
			bis.close();
			fis.close();
			
			System.out.println(Time.getTime() + "file Transfering finish");
			System.out.println(Time.getTime() + "file size : " + f.length());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}






