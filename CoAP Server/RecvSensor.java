package org.eclipse.californium.examples; 

import java.io.*;  
import java.net.*; 
import java.lang.*;

public class RecvSensor {

	public static String function() {
		String str = "";

		try{      
			Socket socket=new Socket("127.0.0.1",9999);  

			DataOutputStream dout=new DataOutputStream(socket.getOutputStream());  
			DataInputStream din=new DataInputStream(socket.getInputStream());


			dout.writeUTF("Hello");
			dout.flush();

			str = din.readUTF();//in.readLine();

			System.out.println("receive: " + str);


			dout.close();  
			din.close();
			socket.close();
		}

		catch(Exception e){
			e.printStackTrace();}   
		return str;
	}  

}
