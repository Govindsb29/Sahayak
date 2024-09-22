<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    String email = null;
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("EmailID")) {
                email = cookie.getValue();
                break;
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Report an Issue</title>
    <link rel="stylesheet" href="ReportIssue.css">
</head>
<body>
<header>
    <div class="logo">
        <a href="index.jsp"><img src="logo.jpeg" alt="City Logo"></a>
    </div>
    <nav>
        <ul style="list-style-type: none; padding: 0; margin: 0; display: flex;">
            <li><a href="index.jsp">Home</a></li>
            <li><a href="About.html">About City</a></li>
            <li><a href="ReportIssue.jsp">Report Problem</a></li>
            <li style="margin-left: auto;"><b>
                <%= email != null ? email : "" %>
            </b></li>
            <li>
                <a href="<%= email != null ? "logout.jsp" : "Login.html" %>">
                    <%= email != null ? "Logout" : "" %>
                </a>
            </li>
            <li>
                <a href="<%= email == null ? "Sign.html" : "" %>" style="display:<%= email == null ? "inline" : "none" %>;">
                    Sign Up
                </a>
            </li>
            <li>
                <a href="<%= email == null ? "Login.html" : "" %>" style="display:<%= email == null ? "inline" : "none" %>;">
                    Log In
                </a>
            </li>
        </ul>
    </nav>
</header>

<div class="container">
    <div class="form-container">
        <form action="ReportIssue" method="post" enctype="multipart/form-data">
            <label for="location">Location:</label>
            <input type="text" id="location" name="location" required>

            <label for="landmark">Landmark:</label>
            <input type="text" id="landmark" name="landmark" required>

            <label for="details">Details about the Problem:</label>
            <textarea id="details" name="details" required></textarea>

            <label for="imageUpload">Upload Image 1:</label>
            <input type="file" id="imageUpload" name="imageUpload" accept="image/*">

            <label for="imageUpload2">Upload Image 2:</label>
            <input type="file" id="imageUpload2" name="imageUpload2" accept="image/*">

            <button type="submit">Submit</button>
        </form>
    </div>
    <div class="images-container">
        <div class="image-layer">
            <img class="image1" src="rajwada.jpeg" alt="Image 1">
            <img class="image2" src="rajwada.jpeg" alt="Image 2">
        </div>
    </div>
</div>
</body>
</html>
