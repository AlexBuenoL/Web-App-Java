package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


@WebServlet(name = "eliminateImage", urlPatterns = {"/eliminateImage"})
public class eliminateImage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("imageId");
        
        HttpSession session = request.getSession();
        String creator = (String) session.getAttribute("nameUsr");
        
        URL url = new URL("http://localhost:8080/ServerP4/resources/jakartaee9/delete");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setDoOutput(true);

        String postData = "id=" + URLEncoder.encode(id, StandardCharsets.UTF_8) +
                           "&creator=" + URLEncoder.encode(creator, StandardCharsets.UTF_8);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(postData.getBytes(StandardCharsets.UTF_8));
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
        }

        int responseCode = conn.getResponseCode();
        System.out.println(responseCode + " this is the response code for eliminate");
        if (responseCode == HttpURLConnection.HTTP_OK) {
             response.sendRedirect("eliminatedSuccess.jsp");
        }
        else {
            response.sendRedirect("error.jsp?orig=eliminate");
         }
    }
}
