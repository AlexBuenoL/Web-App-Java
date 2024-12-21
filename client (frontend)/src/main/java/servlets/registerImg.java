package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import jakarta.servlet.http.Part;


@WebServlet(name = "registerImg", urlPatterns = {"/registerImg"})
@MultipartConfig
public class registerImg extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtain data from user form
        String title = request.getParameter("titleValue");
        String description = request.getParameter("descriptionValue");
        String keyWords = request.getParameter("keyWordsValue");
        String author = request.getParameter("authorValue");
        String captureDate = request.getParameter("captureDateValue").replace('-','/');
        String fileName = request.getParameter("fileNameValue");
        Part imagePart = request.getPart("fileImgValue");
        
        // Obtain user that upload the image from session 
        HttpSession session = request.getSession();
        String creator = (String) session.getAttribute("nameUsr");
        
        URL url = new URL("http://localhost:8080/ServerP4/resources/jakartaee9/register");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=------WebKitFormBoundary");
        conn.setDoOutput(true);
        
        String bnd =  "------WebKitFormBoundary";
        try (OutputStream os = conn.getOutputStream()) {
            os.write(("--" + bnd + "\r\n").getBytes());
            os.write("Content-Disposition: form-data; name=\"title\"\r\n\r\n".getBytes());
            os.write((title + "\r\n").getBytes());
            
            os.write(("--" + bnd + "\r\n").getBytes());
            os.write("Content-Disposition: form-data; name=\"description\"\r\n\r\n".getBytes());
            os.write((description + "\r\n").getBytes());
            
            os.write(("--" + bnd + "\r\n").getBytes());
            os.write("Content-Disposition: form-data; name=\"keyWords\"\r\n\r\n".getBytes());
            os.write((keyWords + "\r\n").getBytes());
            
            os.write(("--" + bnd + "\r\n").getBytes());
            os.write("Content-Disposition: form-data; name=\"author\"\r\n\r\n".getBytes());
            os.write((author + "\r\n").getBytes());
            
            os.write(("--" + bnd + "\r\n").getBytes());
            os.write("Content-Disposition: form-data; name=\"creator\"\r\n\r\n".getBytes());
            os.write((creator + "\r\n").getBytes());
            
            os.write(("--" + bnd + "\r\n").getBytes());
            os.write("Content-Disposition: form-data; name=\"captureDate\"\r\n\r\n".getBytes());
            os.write((captureDate + "\r\n").getBytes());
            
            os.write(("--" + bnd + "\r\n").getBytes());
            os.write("Content-Disposition: form-data; name=\"fileName\"\r\n\r\n".getBytes());
            os.write((fileName + "\r\n").getBytes());
            
            os.write(("--" + bnd + "\r\n").getBytes());
            os.write(("Content-Disposition: form-data; name=\"fileImgValue\"; filename=\"" + imagePart.getSubmittedFileName() + "\"\r\n").getBytes());
            os.write("Content-Type: application/octet-stream\r\n\r\n".getBytes());
            imagePart.getInputStream().transferTo(os);
            os.write("\r\n".getBytes());

            os.write(("--" + bnd + "--\r\n").getBytes());
        }
        catch(IOException e) {
            System.err.println(e.getMessage());
        }

        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
             response.sendRedirect("registerImgSuccess.jsp");
        }
        else {
            response.sendRedirect("error.jsp?orig=registerImg");
        }
    }
}
