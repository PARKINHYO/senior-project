package org.eclipse.californium.examples;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;


public class InsertDB{

    public static void insert(String data){
        Connection conn = null;
        PreparedStatement pstmt = null;
        String lat = data.substring(0, 9);
        String lon = data.substring(11, 21);

        System.out.println("lat : " + lat);
        System.out.println("lon : " + lon);
       

        try{
            // 1. 드라이버 로딩
            Class.forName("com.mysql.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mysql://xxx/seniorProject?characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "sssss", "sssss");

            // 3. SQL 쿼리 준비
            // 추가하려는 데이터의 값은 전달된 인자를 통해 동적으로 할당되는 값이다.
            // 즉 어떤 값이 전달될지 모르므로 Select 할 때와 달리
            // stmt = conn.createStatement(); 를 작성하지 않고
            // pstmt = conn.prepareStatement(sql); 로 작성하여 데이터를 추가할 것임을 알립니다.
            // 물론 sql 쿼리 내에서 + 연산자로 한 줄로 작성할 수 있지만 가독성이 너무 떨어지게 되므로
            // 이 방법을 권합니다.
            String sql = "insert into GPS(lat, lon) values(?, ?)";

            pstmt = conn.prepareStatement(sql);


            // 4. 데이터 binding
            pstmt.setString(1, lat);
            pstmt.setString(2, lon);




            // 5. 쿼리 실행 및 결과 처리
            // SELECT와 달리 INSERT는 반환되는 데이터들이 없으므로
            // ResultSet 객체가 필요 없고, 바로 pstmt.executeUpdate()메서드를 호출하면 됩니다.
            // INSERT, UPDATE, DELETE 쿼리는 이와 같이 메서드를 호출하며
            // SELECT에서는 stmt.executeQuery(sql); 메서드를 사용했었습니다.
            // @return     int - 몇 개의 row가 영향을 미쳤는지를 반환
            int count = pstmt.executeUpdate();
            if( count == 0 ){
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

    public static void select() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try{
            // 1. 드라이버 로딩
            Class.forName("com.mysql.jdbc.Driver");

            // 2. 연결하기
            String url = "jdbc:mysql://xxxx/seniorProject?characterEncoding=UTF-8&serverTimezone=UTC";
            conn = DriverManager.getConnection(url, "izzzz", "zzzzz");


            // 3. 쿼리 수행을 위한 Statement 객체 생성
            stmt = conn.createStatement();

            // 4. SQL 쿼리 작성
            // 주의사항
            // 1) JDBC에서 쿼리를 작성할 때는 세미콜론(;)을 빼고 작성한다.
            // 2) SELECT 할 때 * 으로 모든 칼럼을 가져오는 것보다
            //   가져와야 할 칼럼을 직접 명시해주는 것이 좋다.
            // 3) 원하는 결과는 쿼리로써 마무리 짓고, java 코드로 후작업 하는 것은 권하지 않음
            // 4) 쿼리를 한 줄로 쓰기 어려운 경우 들여쓰기를 사용해도 되지만 띄어쓰기에 유의 !!
            String sql = "SELECT no, context FROM coap_data";


            // 5. 쿼리 수행
            // 레코드들은 ResultSet 객체에 추가된다.
            rs = stmt.executeQuery(sql);

            // 6. 실행결과 출력하기
            while(rs.next()){
                // 레코드의 칼럼은 배열과 달리 0부터 시작하지 않고 1부터 시작한다.
                // 데이터베이스에서 가져오는 데이터의 타입에 맞게 getString 또는 getInt 등을 호출한다.
                String no = rs.getString(1);
                String context = rs.getString(2);
                
                System.out.println(no + " -> " + context);
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
                if( stmt != null && !stmt.isClosed()){
                    stmt.close();
                }
                if( rs != null && !rs.isClosed()){
                    rs.close();
                }
            }
            catch( SQLException e){
                e.printStackTrace();
            }
        }
    }
}
