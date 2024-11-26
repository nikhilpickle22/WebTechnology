

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cookie")
public class CookieServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        Cookie userCookie = new Cookie("username", username);
        userCookie.setMaxAge(60 * 60); // 1 hour
        response.addCookie(userCookie);

        response.setContentType("text/html");
        response.getWriter().println("<h1>Welcome, " + username + "!</h1>");
        response.getWriter().println("<a href='cookie.html'>Go Back</a>");
    }
}
