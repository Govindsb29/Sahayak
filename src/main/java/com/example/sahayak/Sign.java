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

@WebServlet("/Sign")
public class Sign extends HttpServlet
{
    static Connect cn;
    public Sign()
    {
        cn = new Connect();
        cn.connectdb();
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        String Name = req.getParameter("Name");
        String id = req.getParameter("EmailID");
        String password = req.getParameter("password");
        try
        {
            String sql = "insert into user_details values('" + id + "','" + Name + "','" + password + "');";
            int result = cn.stmt.executeUpdate(sql);

            if (result>0)
            {
                out.println("<h2>Account Created Successfully</h2>");
                out.println("<h2>Please Login again</h2>");
                RequestDispatcher rd = req.getRequestDispatcher("/Login.html");
                rd.include(req,resp);
            }
            else
            {
                out.println("<h2>Email already exists</h2>");
                RequestDispatcher rd = req.getRequestDispatcher("/InsertRecord.html");
                rd.include(req,resp);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
