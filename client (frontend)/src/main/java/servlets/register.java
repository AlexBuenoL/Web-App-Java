package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@WebServlet(name = "register", urlPatterns = {"/register"})
public class register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("valueUsername");
        String password = request.getParameter("valuePassword");
        String password2 = request.getParameter("valuePassword2");
        
        if (!password.equals(password2)) {
            response.sendRedirect("error.jsp?orig=register_pwds");
        }
        else {
             URL url = new URL("http://localhost:8080/ServerP4/resources/jakartaee9/registerUser");
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
                 response.sendRedirect("login.jsp");
            }
            else {
                response.sendRedirect("error.jsp?orig=register_usr_exists");
            }
        }   
    }
}
