/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatSockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import java.security.*;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import crypt.*;
import java.math.BigInteger;
import java.util.List;


/**
 *
 * @author mounir
 */
public class Serveur implements Runnable {

    BigInteger p = new BigInteger("5700734181645378434561188374130529072194886062117");
    BigInteger q = new BigInteger("35894562752016259689151502540913447503526083241413");
    BigInteger e = new BigInteger("33445843524692047286771520482406772494816708076993");
    RSAImpl rsa =new RSAImpl(p,q,e);
    Socket socket;
    ServerSocket socketserver;
    SSLServerSocketFactory factory;
    JLabel recent_message_1;
    JLabel recent_message_2;
    JLabel recent_message_3;
    JLabel recent_message_4;
    private ObjectOutputStream output;
    /**
     * @param args the command line arguments
     */
    public Serveur(Socket socket, JLabel recent_message_1, JLabel recent_message_2, JLabel recent_message_3, JLabel recent_message_4){
                this.socket=socket;
                this.recent_message_1=recent_message_1;
                this.recent_message_2=recent_message_2;
                this.recent_message_3=recent_message_3;
                this.recent_message_4=recent_message_4;
                
    }
    public void send(String message){
    try {
          PrintWriter out;
        output=new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(rsa.encryptMessage("the server : "+message));
      

    } catch (IOException ex) {
        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
    }
        
    }
    public void run(){
        try{
            //factory=(SSLServerSocketFactory)SSLServerSocketFactory.getDefault();
            //socketserver =(SSLServerSocket)factory.createServerSocket(5001);
            socketserver = new ServerSocket(5001);
            socket = socketserver.accept();    
            System.out.println("ici le serveur");
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
        }catch(IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Serveur.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        // TODO code application logic here
        ServerSocket socketserver;
        Socket socket;
        BufferedReader in,in1=null;
        try{
            socketserver =new ServerSocket(5001);
            socket = socketserver.accept();
            System.out.println("ici le serveur");
            Scanner sc =new Scanner(System.in);
            
            PrintWriter out;
            out = new PrintWriter(socket.getOutputStream());
            for(;;){
                in =new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if(in!=in1){
                in1=in;
                String message =in.readLine();
                System.out.println(message);
                }
            String message=sc.next();
            out.println(message);
            
            out.flush();
            
            }
            
            
            
            
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
