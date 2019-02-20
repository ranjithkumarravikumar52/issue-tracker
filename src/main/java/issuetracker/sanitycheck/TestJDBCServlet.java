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
        String user = "issuetrackeradmin";
        String pass = "password123";

        String jdbcUrl = "jdbc:mysql://localhost:3306/issue-tracker?useSSL=false";
        String driver = "com.mysql.cj.jdbc.Driver";
	    PrintWriter out = response.getWriter();
	    Connection myConnection = null;

        //connection to database
        try{
            out.println("Connection to database: "+jdbcUrl);
            Class.forName(driver);
            myConnection = DriverManager.getConnection(jdbcUrl, user, pass);
            out.println("Connection successful");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            out.println(e.getMessage());
        }finally {
        	if(myConnection != null){
		        try {
			        myConnection.close();
		        } catch (SQLException e) {
			        e.printStackTrace();
			        out.println(e.getMessage());
		        }
	        }
        }
    }
}
