package tcp;
// Server.java
 
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            System.out.println(getTime() + " Listening...");
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
                System.out.println(getTime() + "Incoming conntect" + socket.getInetAddress() );
                 
                // open socket output stream
                OutputStream out = socket.getOutputStream(); 
                DataOutputStream dos = new DataOutputStream(out); // 기본형 단위로 처리하는 보조스트림
                 
                // Send data to remote socket
                dos.writeUTF("Message from server.");
                System.out.println(getTime() + " Data Transfered");
                 
                // closet stream and socket
                dos.close();
                socket.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            } // try - catch
        } // while
    } // main
     
    static String getTime() {
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
        return f.format(new Date());
    } // getTime
} // TcpServerTest
 