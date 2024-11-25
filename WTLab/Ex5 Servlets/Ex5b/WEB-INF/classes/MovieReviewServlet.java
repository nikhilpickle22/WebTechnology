package com.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/MovieReviewServlet")
public class MovieReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private List<String> reviews = new ArrayList<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String review = request.getParameter("review");
        String rating = request.getParameter("rating");

        // Store the review in memory (could use a database for persistent storage)
        String reviewEntry = "<div class='review'><strong>" + username + ":</strong> " + review
                + " <br><em>Rating: " + rating + "/5</em></div>";
        reviews.add(reviewEntry);

        // Redirect back to the main page to show the reviews
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head><title>Movie Review & Rating Platform</title></head>");
        out.println("<body>");
        out.println("<h1>Movie Review & Rating Platform</h1>");
        out.println("<form action='MovieReviewServlet' method='POST'>");
        out.println("<input type='text' name='username' placeholder='Your Name' required/><br>");
        out.println("<textarea name='review' placeholder='Write your review here...' rows='5' required></textarea><br>");
        out.println("<select name='rating'>");
        out.println("<option value='1'>1 - Poor</option>");
        out.println("<option value='2'>2 - Fair</option>");
        out.println("<option value='3'>3 - Good</option>");
        out.println("<option value='4'>4 - Very Good</option>");
        out.println("<option value='5'>5 - Excellent</option>");
        out.println("</select><br>");
        out.println("<button type='submit'>Submit Review</button>");
        out.println("</form>");

        out.println("<div class='reviews'>");
        out.println("<h2>Recent Reviews</h2>");
        for (String review : reviews) {
            out.println(review);
        }
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
