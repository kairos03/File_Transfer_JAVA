package tcp;
// Server.java
 
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server 
{
	
	//Define constant
	final static int PORT = 5000;
	
    @SuppressWarnings("resource")
	public static void main(String[] args) 
    {
        ServerSocket serverSocket = null;
         
        try 
        {
            // server Socket create and bind
            serverSocket = new ServerSocket(PORT);
            System.out.println(Time.getTime() + " Listening...");
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        } // try - catch
         
        while (true) 
        {
            try 
            {
                // accept
                Socket socket = serverSocket.accept();
                System.out.println(Time.getTime() + "Incoming conntect" + socket.getInetAddress() );
                 
                // Send data to remote socket
                FileReceiver fr = new FileReceiver(socket);
                fr.start();
                 
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            } 
        } 
    } 
} 
 
class FileReceiver extends Thread
{
	Socket socket;
	DataInputStream dis;
	FileOutputStream fos;
	BufferedOutputStream bos;
	
	public FileReceiver(Socket socket)
	{
		this.socket = socket;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try 
		{
			System.out.println(Time.getTime() + "File Transfering start");
			dis = new DataInputStream(socket.getInputStream());
			
			//file name send
			String fName = dis.readUTF();
			System.out.println(Time.getTime() + "File Name : " + fName + "receved");
			fName = fName.replaceAll("a", "b");
			
			//create file
			File f = new File(fName);
			fos = new FileOutputStream(f);
			bos = new BufferedOutputStream(fos);
			System.out.println(Time.getTime() + fName + "file created");
			
			//byte data read & write
			int len;
			int size = 4096;
			byte[] data = new byte[size];
			while( (len = dis.read(data)) != -1 )
			{
				bos.write(data,0,len);
			}
			
			bos.flush();
			bos.close();
			fos.close();
			dis.close();
			
			System.out.println(Time.getTime() + "ALL File Received");
			System.out.println(Time.getTime() + "file size : " + f.length());
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}