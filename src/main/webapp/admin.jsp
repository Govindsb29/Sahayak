<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);

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
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Centered Boxes Layout</title>
    <link rel="stylesheet" href="admin.css">
</head>
<body>
<header>
    <div class="logo">
        <a href="index.jsp"><img src="logo.jpeg" alt="City Logo"></a>
    </div>
    <nav>
        <ul>
            <li><a href="index.jsp">Home</a></li>
            <li><a href="About.html">About City</a></li>
            <li><a href="admin.jsp">Report Problem</a></li>
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
<main>
    <div class="container">
        <div class="row">
            <div class="box">
                <form action="Report" method="get">
                    <button type="submit">All Reports</button>
                </form>
            </div>
            <div class="box">
                <form action="SetResolved" method="get">
                    <button type="submit">Set Problem as Resolved</button>
                </form>
            </div>
        </div>
        <div class="coloumn">
            <div class="box">
                <form action="Report_r" method="get">
                    <button type="submit">Resolved Problems</button>
                </form>
            </div>
            <div class="box">
                <form action="Report_u" method="get">
                    <button type="submit">Unresolved Problems</button>
                </form>
            </div>
        </div>
    </div>
</main>
</body>
</html>
