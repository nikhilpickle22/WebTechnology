import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PageCounterServlet")
public class PageCounterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Thread-safe counter
    private AtomicInteger visitCounter;

    @Override
    public void init() throws ServletException {
        super.init();
        // Initialize counter
        visitCounter = new AtomicInteger(0);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Increment counter
        int currentCount = visitCounter.incrementAndGet();

        // Set response content type
        response.setContentType("text/html");
        
        // Write response
        response.getWriter().println("<html><body>");
        response.getWriter().println("<h1>Welcome to the Page Counter!</h1>");
        response.getWriter().println("<p>Page was visited " + currentCount + " times.</p>");
        response.getWriter().println("</body></html>");
    }

    @Override
    public void destroy() {
        // Clean up resources if necessary
        super.destroy();
    }
}