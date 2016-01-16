package socket;

import java.io.DataInputStream;  
import java.io.DataOutputStream;    
import java.io.FileInputStream;    
import java.io.IOException;    
import java.net.InetSocketAddress;    
import java.net.ServerSocket;    
import java.net.Socket;    
import java.util.ArrayList;
  
public class Server {    
	//����һ�����Ա�����װ�½��õ�socket
    public static ArrayList<Socket> socketList = new ArrayList<Socket>();
     
    //����һ�����Ա�����װ�½��õ��ļ���
    public static ArrayList<String> fileList = new ArrayList<String>();
     
    //�ļ������
    public static int no=0;
     
    public static void main(String[] args) throws IOException {
         
        ServerSocket ss = new ServerSocket(40000);
         
        while(true){
            Socket s = ss.accept();
            socketList.add(s);
            new Thread(new ServerThread(s)).start();
        }
    }    
}    