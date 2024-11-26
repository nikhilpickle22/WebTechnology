

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/httpsession")
public class HttpSessionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        HttpSession session = request.getSession();
        session.setAttribute("username", username);

        response.setContentType("text/html");
        response.getWriter().println("<h1>Session Set for " + username + "!</h1>");
        response.getWriter().println("<a href='httpsession.html'>Go Back</a>");
    }
}
