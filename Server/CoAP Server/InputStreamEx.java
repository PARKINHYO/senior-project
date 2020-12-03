package org.eclipse.californium.examples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class InputStreamEx {
        public static String function(){
                String line="";
                try{
                        //파일 객체 생성
                        File file = new File("/home/ubuntu/sensor.txt");
                        //입력 스트림 생성
                        FileReader filereader = new FileReader(file);
                        //입력 버퍼 생성
                        BufferedReader bufReader = new BufferedReader(filereader);
                        line = bufReader.readLine();
                        System.out.println("inputStreamEx :" + line);
                        //.readLine()은 끝에 개행문자를 읽지 않는다.
                        bufReader.close();
                }catch (FileNotFoundException e) {
                }catch(IOException e){
                        System.out.println(e);
                }
                return line;
        }
}
