package issuetracker.sanitycheck;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/TestJDBCServlet")
public class TestJDBCServlet extends javax.servlet.http.HttpServlet {
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String user = "springstudent";
        String pass = "springstudent";

        String jdbcUrl = "jdbc:mysql://localhost:3306/issue-tracker?useSSL=false";
        String driver = "com.mysql.jdbc.Driver";

        //connection to database
        try{
            PrintWriter out = response.getWriter();
            out.println("Connection to database: "+jdbcUrl);
            Class.forName(driver);
            Connection myConnection = DriverManager.getConnection(jdbcUrl, user, pass);
            out.println("Connection successful");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
