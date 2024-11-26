

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/urlrewriting")
public class URLRewritingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        response.setContentType("text/html");
        response.getWriter().println("<h1>Welcome, " + username + "!</h1>");
        response.getWriter().println("<a href='urlrewriting?username=" + username + "'>Continue</a>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");

        response.setContentType("text/html");
        response.getWriter().println("<h1>Thanks for visiting, " + username + "!</h1>");
    }
}
