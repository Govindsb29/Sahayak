package com.example.sahayak;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/login")
public class Login extends HttpServlet {
    static Connect cn;

    public Login() {
        cn = new Connect();
        cn.connectdb();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String id = req.getParameter("EmailID");
        String password = req.getParameter("password");

        try {
            String adminsql = "select * from admin_details where email = '" + id + "' AND password = '" + password + "';";
            String usersql = "select * from user_details where email = '" + id + "' AND password = '" + password + "';";
            ResultSet rs = cn.stmt.executeQuery(adminsql);

            if (rs.next()) {
                // Set the cookie for the admin user
                Cookie ck = new Cookie("EmailID", id);
                ck.setPath("/");
                resp.addCookie(ck);

                // Redirect to the admin page
                resp.sendRedirect(req.getContextPath() + "/admin.jsp");
            } else {
                ResultSet rs1 = cn.stmt.executeQuery(usersql);

                if (rs1.next()) {
                    // Set the cookie for the normal user
                    Cookie ck = new Cookie("EmailID", id);
                    ck.setPath("/");
                    resp.addCookie(ck);

                    // Redirect to the index page
                    resp.sendRedirect(req.getContextPath() + "/index.jsp");
                } else {
                    out.println("<h2>Wrong User ID or Password</h2>");
                    RequestDispatcher rd = req.getRequestDispatcher("/index.jsp");
                    rd.include(req, resp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
