package com.example.sahayak;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;

@WebServlet("/ReportIssue")
@MultipartConfig(maxFileSize = 16177216) // 16MB
public class ReportIssue extends HttpServlet {
    static Connect cn;

    public ReportIssue() {
        cn = new Connect();
        cn.connectdb();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        try {
            String location = req.getParameter("location");
            String landmark = req.getParameter("landmark");
            String details = req.getParameter("details");

            Part filePart = req.getPart("imageUpload");
            InputStream inputStream = null;

            if (filePart != null) {
                // prints out some information for debugging
                System.out.println(filePart.getName());
                System.out.println(filePart.getSize());
                System.out.println(filePart.getContentType());

                // obtains input stream of the upload file
                inputStream = filePart.getInputStream();
            }

            Part filePart2 = req.getPart("imageUpload2");
            InputStream inputStream2 = null;

            if (filePart2 != null) {
                System.out.println(filePart2.getName());
                System.out.println(filePart2.getSize());
                System.out.println(filePart2.getContentType());

                inputStream2 = filePart2.getInputStream();
            }

            // Retrieve email from cookie
            String reportedBy = "Anonymous"; // default value
            Cookie[] cookies = req.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("EmailID")) {
                        reportedBy = cookie.getValue();
                        break;
                    }
                }
            }

            out.println("<h2>Processing your request...</h2>");
            out.println("<p>Location: " + location + "</p>");
            out.println("<p>Landmark: " + landmark + "</p>");
            out.println("<p>Details: " + details + "</p>");
            out.println("<p>Reported By: " + reportedBy + "</p>");

            Connection connection = cn.getConnection();
            if (connection == null) {
                out.println("<h2>Database connection is null</h2>");
                return;
            }

            String sql = "INSERT INTO issues (date, time, reported_by, location, landmark, details, img1, img2, issue_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 'Unresolved')";
            PreparedStatement pstmt = connection.prepareStatement(sql);

            // Get the current date and time
            LocalDate currentDate = LocalDate.now();
            LocalTime currentTime = LocalTime.now();

            pstmt.setString(1, currentDate.toString());
            pstmt.setString(2, currentTime.toString());
            pstmt.setString(3, reportedBy);
            pstmt.setString(4, location);
            pstmt.setString(5, landmark);
            pstmt.setString(6, details);

            if (inputStream != null) {
                pstmt.setBlob(7, inputStream);
            } else {
                pstmt.setNull(7, java.sql.Types.BLOB);
            }

            if (inputStream2 != null) {
                pstmt.setBlob(8, inputStream2);
            } else {
                pstmt.setNull(8, java.sql.Types.BLOB);
            }

            int result = pstmt.executeUpdate();

            if (result > 0) {
                out.println("<h2>Record Entered Successfully</h2>");
                System.out.println("File Part 2 Size: " + (filePart2 != null ? filePart2.getSize() : "null"));

                RequestDispatcher rd = req.getRequestDispatcher("/ReportIssue.jsp");
                rd.include(req, resp);
            } else {
                out.println("<h2>Error in Record Insertion</h2>");
                RequestDispatcher rd = req.getRequestDispatcher("/ReportIssue.jsp");
                rd.include(req, resp);
            }
        } catch (Exception e) {
            out.println("<h2>Exception: " + e.getMessage() + "</h2>");
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }
}
