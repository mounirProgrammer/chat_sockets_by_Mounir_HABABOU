/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatSockets;

import java.io.*;
import java.net.*;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import chatsockets.Log_in;
import javax.net.ssl.HandshakeCompletedEvent;
import javax.net.ssl.HandshakeCompletedListener;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import crypt.*;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author mounir
 */
public class Client implements Runnable {
    BigInteger p = new BigInteger("5700734181645378434561188374130529072194886062117");
    BigInteger q = new BigInteger("35894562752016259689151502540913447503526083241413");
    BigInteger e = new BigInteger("33445843524692047286771520482406772494816708076993");
    RSAImpl rsa =new RSAImpl(p,q,e);
Socket socket;
SSLSocketFactory factory;
        BufferedReader in,in1;
        JLabel recent_message_1,recent_message_2,recent_message_3,recent_message_4;
        String user_name;
    /**
     * @param args the command line arguments
     */
    public Client(Socket socket,JLabel recent_message_1,JLabel recent_message_2,JLabel recent_message_3,JLabel recent_message_4, String user_name,BigInteger p,BigInteger q,BigInteger e){
        this.socket=socket;
        this.recent_message_1=recent_message_1;
        this.recent_message_2=recent_message_2;
        this.recent_message_3=recent_message_3;
        this.recent_message_4=recent_message_4;
        this.user_name=user_name;
        this.p=p;
        this.q=q;
        this.e=e;
        
            
    }
    public void send(String message){
        ObjectOutputStream output;
    try {
        PrintWriter out;
        output=new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(rsa.encryptMessage(user_name+" "+message));
        
    } catch (IOException ex) {
        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }
    
    public void run(){
        try{
            //factory=(SSLSocketFactory)SSLSocketFactory.getDefault();
            //socket=(SSLSocket)factory.createSocket("127.0.0.1",5001);
            socket=new Socket("127.0.0.1",5001);
            System.out.println("demmande de connxion");
            send(" with the ip adress : "+socket.getInetAddress().toString()+" is online");
            ObjectInputStream input;
            Object object;
            for(;;){
                input=new ObjectInputStream(socket.getInputStream());
                object =input.readObject();
                String message = null;
                //if(object.getClass().equals(String.class))
                //    message=object.toString();
                message=Utils.bigIntegerToString(rsa.decrypt((List<BigInteger>)object));
                recent_message_4.setText(recent_message_3.getText());
                recent_message_3.setText(recent_message_2.getText());
                recent_message_2.setText(recent_message_1.getText());
                recent_message_1.setText(message);
                System.out.println(message);
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    }
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        Socket socket;
        BufferedReader in;
        Scanner sc;
        try{
            socket=new Socket("127.0.0.1",5001);
            System.out.println("demmande de connxion");
            in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
           // String sc.nextLine(Socket.);
            System.out.println();
            String message =in.readLine();
            System.out.println(message);
            //socket=new Socket("192.168.1.1",3600);
            socket.close();
        }catch(UnknownHostException e){
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
