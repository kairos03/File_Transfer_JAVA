package tcp;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.Socket;
 
 
public class Client 
{
	final static String serverIP = "192.168.42.1";
	
    public static void main(String[] args) 
    {
        try 
        {
            System.out.println("Connecting. Server IP : " + serverIP);
             
            // socket Created and connect request
            Socket socket = new Socket(serverIP, 5000);
             
            // get sockte's output stream
            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);  // sub stream
             
            // data output
            System.out.println("Message from server : " + dis.readUTF());
            System.out.println("Connect close");
             
            // close socket, stream
            dis.close();
            socket.close();
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