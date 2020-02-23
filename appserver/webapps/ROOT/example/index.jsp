<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html; charset=utf-8"%>
<%@ page import="java.sql.*"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
    <title>Apache Tomcat</title>
</head>

<body>
    <p><a href="http://xxxxxx:8080/index.jsp">home</a></p>
    <table width="100" border="1">

        <tr>
            <th>no</th>
            <th>data</th>
        </tr>
        <%
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try{
        // 1. 드라이버 로딩
        Class.forName("com.mysql.jdbc.Driver");

        // 2. 연결하기
        String url = "jdbc:mysql://xxxxxxxx/xxxxxx";
        conn = DriverManager.getConnection(url, "xxxx", "xxxxxx");

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
            //out.println(no + " -> " + context + "<br>");
    %>
        <tr>
            <td><%=no%></td>
            <td><%=context%></td>
        </tr>
        <%
        }
    } 
    catch( ClassNotFoundException e){
        out.println("드라이버 로딩 실패");
    }
    catch( SQLException e){
        out.println("에러 " + e);
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
    %>
    </table>

</body>

</html>