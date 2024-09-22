<%@ page import="jakarta.servlet.http.Cookie" %>
<%@ page import="jakarta.servlet.http.HttpServletRequest" %>
<%@ page import="jakarta.servlet.http.HttpServletResponse" %>
<%
    // Retrieve cookies
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("EmailID")) {
                // Set the cookie's max age to 0 to delete it
                Cookie logoutCookie = new Cookie("EmailID", null);
                logoutCookie.setMaxAge(0);
                logoutCookie.setPath("/"); // Ensure the path matches the cookie's path
                response.addCookie(logoutCookie);
                break;
            }
        }
    }

    HttpSession sess = request.getSession(false);
    if (sess != null) {
        sess.invalidate();
    }

    // Redirect to homepage or login page
    response.sendRedirect("index.jsp");
%>
