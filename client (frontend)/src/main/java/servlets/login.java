package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("valueUsername").trim();
        String password = request.getParameter("valuePassword").trim();
        
        URL url = new URL("http://localhost:8080/ServerP4/resources/jakartaee9/login");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);
        
        String postData = "username=" + URLEncoder.encode(name, StandardCharsets.UTF_8) +
                          "&password=" + URLEncoder.encode(password, StandardCharsets.UTF_8);
        
        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.getBytes(StandardCharsets.UTF_8));
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
        }
        
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            HttpSession session = request.getSession();
            session.setAttribute("nameUsr", name);
            session.setMaxInactiveInterval(300); // 5'
            response.sendRedirect("menu.jsp");
        }
        else {
            response.sendRedirect("error.jsp?orig=login");
        }
    }
}
