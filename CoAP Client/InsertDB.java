package org.eclipse.californium.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
// import java.sql.*;


public class InsertDB{
        public static void insert(String data){
                Connection conn = null;
                PreparedStatement pstmt = null;

                String lat = data.substring(0, 9);
                String lon = data.substring(11, 21);

                System.out.println("lat : " + lat);
                System.out.println("lon : " + lon);

                try{
                        Class.forName("com.mysql.jdbc.Driver");

                        String url = "jdbc:mysql://52.78.141.155:3306/seniorProject?characterEncoding=UTF-8&serverTimezone=UTC";
                        conn = DriverManager.getConnection(url, "ihp001", "qkrdlsgy001");

                        String sql = "insert into location(lat, lon) values(?, ?)";

                        pstmt = conn.prepareStatement(sql);

                        pstmt.setString(1, lat);
                        pstmt.setString(2, lon);

                        int count = pstmt.executeUpdate();

                        if(count == 0){
                                System.out.println("데이터 입력 실패");
                        }
                        else{
                                System.out.println("데이터 입력 성공");
                        }
                }
                catch( ClassNotFoundException e){
                        System.out.println("드라이버 로딩 실패");
                }
                catch( SQLException e){
                        System.out.println("에러 " + e);
                }
                finally{
                        try{
                                if( conn != null && !conn.isClosed()){
                                        conn.close();
                                }
                                if( pstmt != null && !pstmt.isClosed()){
                                        pstmt.close();
                                }
                        }
                        catch( SQLException e){
                                e.printStackTrace();
                        }
                }
        }
        public static void update(String data){
                Connection conn = null;
                PreparedStatement pstmt = null;

                String gyro = data.substring(23, data.length());

                System.out.println("gyro : " + gyro);

                try{
                        Class.forName("com.mysql.jdbc.Driver");

                        String url = "jdbc:mysql://52.78.141.155:3306/seniorProject?characterEncoding=UTF-8&serverTimezone=UTC";
                        conn = DriverManager.getConnection(url, "ihp001", "qkrdlsgy001");

                        String sql = "update gyro set count=? where device_name=?";

                        pstmt = conn.prepareStatement(sql);

                        pstmt.setString(1, gyro);
                        pstmt.setString(2, "b8:27:eb:ba:0a:34");

                        int count = pstmt.executeUpdate();

                        if(count == 0){
                                System.out.println("데이터 입력 실패");
                        }
                        else{
                                System.out.println("데이터 입력 성공");
                        }
                }
                catch( ClassNotFoundException e){
                        System.out.println("드라이버 로딩 실패");
                }
                catch( SQLException e){
                        System.out.println("에러 " + e);
                }
                finally{
                        try{
                                if( conn != null && !conn.isClosed()){
                                        conn.close();
                                }
                                if( pstmt != null && !pstmt.isClosed()){
                                        pstmt.close();
                                }
                        }
                        catch( SQLException e){
                                e.printStackTrace();
                        }
                }
        }
}
