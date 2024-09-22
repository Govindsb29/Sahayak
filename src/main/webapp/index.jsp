<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Citizen Engagement Platform</title>
    <link rel="stylesheet" href="styles.css">
    <script>
        let slideIndex = 0;
        function showSlides() {
            let i;
            const slides = document.getElementsByClassName("slides");
            for (i = 0; i < slides.length; i++) {
                slides[i].style.display = "none";
            }
            slideIndex++;
            if (slideIndex > slides.length) {slideIndex = 1}
            slides[slideIndex-1].style.display = "block";
            setTimeout(showSlides, 2000); // Change image every 2 seconds
        }
        function changeSlide(n) {
            showSlides(slideIndex += n);
        }
    </script>
</head>
<body onload="showSlides()">
<header>
    <div class="logo">
        <img src="logo.jpeg" alt="City Logo">
    </div>
    <nav>
        <ul>
            <li><a href="index.jsp">Home</a></li>
            <li><a href="About.html">About City</a></li>
            <li><a href="ReportIssue.jsp">Report Problem</a></li>
            <%
                Cookie[] cookies = request.getCookies();
                String email = null;
                boolean loggedIn = false;

                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().equals("EmailID")) {
                            email = cookie.getValue();
                            loggedIn = true;
                            break;
                        }
                    }
                }
            %>
            <% if (loggedIn) { %>
            <li><b><%= email %></b></li>
            <li><a href="logout.jsp">Logout</a></li>
            <% } else { %>
            <li><a href="Sign.html">Sign Up</a></li>
            <li><a href="Login.html"><b>Log In</b></a></li>
            <% } %>
        </ul>
    </nav>
</header>

<div class="slider">
    <div class="slides">
        <img src="photo1.jpeg" alt="Photo 1">
        <img src="photo2.png" alt="Photo 2">
        <img src="photo3.png" alt="Photo 3">
        <img src="photo1.jpeg" alt="Photo 4">
    </div>
    <a class="prev" onclick="changeSlide(-1)">&#10094;</a>
    <a class="next" onclick="changeSlide(1)">&#10095;</a>
</div>
<main>
    <h1 class="headline">Report a Problem</h1>
    <div class="problem-section">
        <a href="ReportIssue.jsp" class="problem-item">Road Issue</a>
        <a href="ReportIssue.jsp" class="problem-item">Electricity</a>
        <a href="ReportIssue.jsp" class="problem-item">Garbage</a>
        <a href="ReportIssue.jsp" class="problem-item">Public Places</a>
        <a href="ReportIssue.jsp" class="problem-item">Other Problem</a>
    </div>
</main>
<footer>
    <div class="footer-content">
        <p class="caption">Thank you for visiting our platform. We value your feedback!</p>
        <p class="contact-info">
            <span>Email: <a href="mailto:en21cs306024@medicaps.ac.in">apkasahak@city.in</a></span><br>
            <span>Contact: 11111111111</span>
        </p>
    </div>
</footer>
<script src="script.js"></script>
</body>
</html>
