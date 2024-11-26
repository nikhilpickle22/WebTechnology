

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/hiddenform")
public class HiddenFormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        response.setContentType("text/html");
        response.getWriter().println("<form action='hiddenform' method='post'>");
        response.getWriter().println("<input type='hidden' name='username' value='" + username + "'>");
        response.getWriter().println("<h1>Welcome, " + username + "!</h1>");
        response.getWriter().println("<button type='submit'>Submit Again</button>");
        response.getWriter().println("</form>");
    }
}
