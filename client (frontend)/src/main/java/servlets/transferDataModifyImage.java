package servlets;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "transferDataModifyImage", urlPatterns = {"/transferDataModifyImage"})
public class transferDataModifyImage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String imageId = (String)request.getParameter("imageId");
        String imageTitle = (String)request.getParameter("imageTitle");
        String imageDescription = (String)request.getParameter("imageDescription");
        String imageKeyWords = (String)request.getParameter("imageKeyWords");
        String imageAuthor = (String)request.getParameter("imageAuthor");
        String imageCaptureDate = (String)request.getParameter("imageCaptureDate");
        
        String url = "modifyImage.jsp";
        url += "?id=" + imageId;
        url += "&title=" + imageTitle;
        url += "&desc=" + imageDescription;
        url += "&kw=" + imageKeyWords;
        url += "&author=" + imageAuthor;
        url += "&captureDate=" + imageCaptureDate;
                
        
        response.sendRedirect(url);
    }
}
