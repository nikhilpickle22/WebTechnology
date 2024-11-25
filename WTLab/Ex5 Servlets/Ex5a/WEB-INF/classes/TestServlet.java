import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class TestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>Test Servlet</title></head>");
        out.println("<body>");
        out.println("<h1>Welcome to Test Servlet</h1>");
        out.println("<p>This is a simple servlet example!</p>");
        out.println("</body>");
        out.println("</html>");
    }
}
