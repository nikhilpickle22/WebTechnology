import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class MovieServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/movies_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM movies";
            ResultSet rs = statement.executeQuery(query);

            out.println("<html><head><title>Movies List</title></head><body>");
            out.println("<h1>Movies List</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>ID</th><th>Title</th><th>Genre</th><th>Year</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("id") + "</td>");
                out.println("<td>" + rs.getString("title") + "</td>");
                out.println("<td>" + rs.getString("genre") + "</td>");
                out.println("<td>" + rs.getInt("year") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<br><a href='addMovie.html'>Add a New Movie</a>");
            out.println("</body></html>");

        } catch (SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title");
        String genre = request.getParameter("genre");
        String year = request.getParameter("year");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = connection.prepareStatement("INSERT INTO movies (title, genre, year) VALUES (?, ?, ?)")) {

            ps.setString(1, title);
            ps.setString(2, genre);
            ps.setInt(3, Integer.parseInt(year));
            ps.executeUpdate();

            out.println("<html><head><title>Movie Added</title></head><body>");
            out.println("<h1>Movie Added Successfully</h1>");
            out.println("<a href='movies'>View Movies</a>");
            out.println("</body></html>");

        } catch (SQLException e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
    }
}
