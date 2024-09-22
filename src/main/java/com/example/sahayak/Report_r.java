package com.example.sahayak;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;

@WebServlet("/Report_r")
public class Report_r extends HttpServlet {
    static Connect cn;

    public Report_r() {
        cn = new Connect();
        cn.connectdb();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            Connection con = cn.getConnection();
            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM resolved";
            ResultSet rs = stmt.executeQuery(sql);

            out.println("<html>");
            out.println("<style>");
            out.println(".modal { display: none; position: fixed; z-index: 1; padding-top: 60px; left: 0; top: 0; width: 100%; height: 100%; overflow: auto; background-color: rgba(0,0,0,0.4); backdrop-filter: blur(5px); }");
            out.println(".modal-content { margin: auto; display: block; width: 80%; max-width: 700px; }");
            out.println(".close { position: absolute; top: 15px; right: 35px; color: #f1f1f1; font-size: 40px; font-weight: bold; transition: 0.3s; }");
            out.println(".close:hover, .close:focus { color: #bbb; text-decoration: none; cursor: pointer; }");
            out.println("th { color: white; }"); // Column headings in white color
            out.println(".blur { filter: blur(4px); }"); // If needed for future use
            out.println("h2 { font-weight: bold; text-decoration: underline; }"); // Title styling
            out.println("</style>");
            out.println("<head><title>Report Issues</title></head>");
            out.println("<body>");
            out.println("<h2 style='text-align:center;'>Reported Issues</h2>");
            out.println("<table style='border: 1.5px solid white; border-collapse: collapse; width: 100%;'>");
            out.println("<tr style='background-color: #275d7d;'><th style='border: 1px solid white; padding: 8px;'>Issue ID</th><th style='border: 1px solid white; padding: 8px;'>Date</th><th style='border: 1px solid white; padding: 8px;'>Time</th><th style='border: 1px solid white; padding: 8px;'>Reported By</th><th style='border: 1px solid white; padding: 8px;'>Location</th><th style='border: 1px solid white; padding: 8px;'>Landmark</th><th style='border: 1px solid white; padding: 8px;'>Details</th><th style='border: 1px solid white; padding: 8px;'>Image 1</th><th style='border: 1px solid white; padding: 8px;'>Image 2</th></tr>");

            while (rs.next()) {
                int issueId = rs.getInt("issue_id");
                String date = rs.getString("date");
                String time = rs.getString("time");
                String reportedBy = rs.getString("reported_by");
                String location = rs.getString("location");
                String landmark = rs.getString("landmark");
                String details = rs.getString("details");
                byte[] img1 = rs.getBytes("img1");
                byte[] img2 = rs.getBytes("img2");

                out.println("<tr>");
                out.println("<td style='border: 1.5px solid white; padding: 8px;'>" + issueId + "</td>");
                out.println("<td style='border: 1.5px solid white; padding: 8px;'>" + date + "</td>");
                out.println("<td style='border: 1.5px solid white; padding: 8px;'>" + time + "</td>");
                out.println("<td style='border: 1.5px solid white; padding: 8px;'>" + reportedBy + "</td>");
                out.println("<td style='border: 1.5px solid white; padding: 8px;'>" + location + "</td>");
                out.println("<td style='border: 1.5px solid white; padding: 8px;'>" + landmark + "</td>");
                out.println("<td style='border: 1.5px solid white; padding: 8px;'>" + details + "</td>");

                if (img1 != null) {
                    String img1Base64 = Base64.getEncoder().encodeToString(img1);
                    out.println("<td style='border: 1.5px solid white; padding: 8px;'><img src='data:image/jpeg;base64," + img1Base64 + "' width='100' height='100' onclick='openModal(\"" + img1Base64 + "\")'/></td>");
                } else {
                    out.println("<td style='border: 1.5px solid white; padding: 8px;'>No Image</td>");
                }

                if (img2 != null) {
                    String img2Base64 = Base64.getEncoder().encodeToString(img2);
                    out.println("<td style='border: 1.5px solid white; padding: 8px;'><img src='data:image/jpeg;base64," + img2Base64 + "' width='100' height='100' onclick='openModal(\"" + img2Base64 + "\")'/></td>");
                } else {
                    out.println("<td style='border: 1.5px solid white; padding: 8px;'>No Image</td>");
                }

                out.println("</tr>");
            }

            out.println("</table>");

            // Modal HTML structure
            out.println("<div id='myModal' class='modal'>");
            out.println("<span class='close' onclick='closeModal()'>&times;</span>");
            out.println("<img class='modal-content' id='modalImage'>");
            out.println("</div>");

            // JavaScript for modal functionality
            out.println("<script>");
            out.println("function openModal(imageBase64) {");
            out.println("  var modal = document.getElementById('myModal');");
            out.println("  var modalImg = document.getElementById('modalImage');");
            out.println("  modal.style.display = 'block';");
            out.println("  modalImg.src = 'data:image/jpeg;base64,' + imageBase64;");
            out.println("}");
            out.println("function closeModal() {");
            out.println("  var modal = document.getElementById('myModal');");
            out.println("  modal.style.display = 'none';");
            out.println("}");
            out.println("</script>");

            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
