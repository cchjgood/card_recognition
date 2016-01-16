package socket;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.net.ssl.SSLContext;

import KNN.KnnTest;
import convert.convert;
 
public class ServerThread implements Runnable {
     
    Socket s=null;
     
    public ServerThread(Socket socket){
        this.s=socket;
        System.out.println("һ���߳�����");
    }
     
    public void run() {
        DataInputStream dis =null;
        FileOutputStream fos=null;
        DataOutputStream dos=null;
        DataOutputStream DOS =null;
         
        //ÿ��ȡһ�����Կͻ��˵���Ϣ�����½�һ���ļ����ļ�����1
        Server.no++;
        //��������ͻ��˷�������Ϣ���ļ������������뵽˳�������ȥ
        String fileName="image.jpg";
        Server.fileList.add(fileName);
         
        File file = new File(fileName);
         
        try{
            //���ӿͻ��˵�socket��Ϊ��ȡ�ͻ���������׼��
            dis =new DataInputStream(s.getInputStream());
            DOS = new DataOutputStream(s.getOutputStream()); 
             
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] inputByte = new byte[1024];
            int length = 0;
            //���ܿͻ��˵����룬���뵽inputByte������뵽bytestream��
            while ((length = dis.read(inputByte, 0, inputByte.length)) > 0) {
                bytestream.write(inputByte, 0, length);
            }
             
            fos=new FileOutputStream(file);
            //��bytestream���������뵽jpg�ļ�����
            dos = new DataOutputStream(fos);
            dos.write(bytestream.toByteArray());
            dos.flush();
     
            System.out.println("���������Ƭ��");
            String resultreturn="";
            try {
            	Process proc = Runtime.getRuntime().exec("python seg2.py");
            	proc.waitFor();
            	convert demo = new convert();  
                demo.conimg();
                System.out.println("Convert successful!");
                KnnTest knntest=new KnnTest();
                String trainpath = "train\\txt";
                for(int i=0 ; i<=17 ; i++){
        			String testFileName = "test\\"+i+".jpg.txt";
        			int result = knntest.useKnn(trainpath, testFileName);
        			resultreturn=resultreturn+result;
        		}
                System.out.println(resultreturn);
			} catch (Exception e) {
				// TODO: handle exception
			}
            PrintWriter mout=new PrintWriter(s.getOutputStream(),true);
            mout.println(resultreturn);
            System.out.println("Accept!");
                
        }catch(IOException e){
             
        }
    }
}
