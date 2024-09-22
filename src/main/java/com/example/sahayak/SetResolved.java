package com.example.sahayak;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/SetResolved")
public class SetResolved extends HttpServlet {
    static Connect cn;

    public SetResolved() {
        cn = new Connect();
        cn.connectdb();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head><title>Set Problem as Resolved</title></head>");
        out.println("<body>");
        out.println("<h2>Set Problem as Resolved</h2>");
        out.println("<form method='post'>");
        out.println("Issue ID: <input type='text' name='issue_id'><br>");
        out.println("<input type='submit' value='Set as Resolved'>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String issueIdStr = req.getParameter("issue_id");
        int issueId = Integer.parseInt(issueIdStr);

        try {
            Connection con = cn.getConnection();
            String sql = "UPDATE issues SET issue_status = 'resolved' WHERE issue_id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, issueId);
            int rowsUpdated = pstmt.executeUpdate();

            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.println("<html>");
            out.println("<head><title>Set Problem as Resolved</title></head>");
            out.println("<body>");
            if (rowsUpdated > 0) {
                out.println("<h2>Issue ID " + issueId + " has been set to resolved.</h2>");
            } else {
                out.println("<h2>Issue ID " + issueId + " not found.</h2>");
            }
            out.println("</body>");
            out.println("</html>");

            pstmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
