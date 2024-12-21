package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import Domain.Image;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebServlet(name = "searchImg", urlPatterns = {"/searchImg"})
public class searchImg extends HttpServlet {

    // returns true if parameters form a complex query, false otherwise
    // a complex query is a query where > 1 parameters are of length > 0 or
    // description.length() > 0 or creator.length() > 0 or storageDate > 0
    private boolean isComplexQuery(String id, String title, String description, String keyWords,
                                String author, String creator, String creationDate, String storageDate) {
        int nVars = 0;
        if (id.length() > 0) ++nVars;
        if (title.length() > 0) ++nVars;
        if (description.length() > 0) {
            ++nVars;
            return true;
        }
        if (keyWords.length() > 0) ++nVars;
        if (author.length() > 0) ++nVars;
        if (creator.length() > 0) {
            ++nVars;
            return true;
        }
        if (creationDate.length() > 0) ++nVars;
        if (storageDate.length() > 0) {
            ++nVars;
            return true;
        }
        
        return nVars > 1;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String id = request.getParameter("idValue");
        String title = request.getParameter("titleValue");
        String description = request.getParameter("descriptionValue");
        String keyWords = request.getParameter("keyWordsValue");
        String author = request.getParameter("authorValue");
        String creator = request.getParameter("creatorValue");
        String creationDate = request.getParameter("captureDateValue");
        String storageDate = request.getParameter("storageDateValue");
        
        URL url;
        String pathAPI = "http://localhost:8080/ServerP4/resources/jakartaee9/";
        if (isComplexQuery(id,title,description,keyWords,author,creator,creationDate,storageDate)) {
            // Call to API complexQuery
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
            String encodedDescription = URLEncoder.encode(description, StandardCharsets.UTF_8);
            String encodedKeywords = URLEncoder.encode(keyWords, StandardCharsets.UTF_8);
            String encodedAuthor = URLEncoder.encode(author, StandardCharsets.UTF_8);
            String encodedCreator = URLEncoder.encode(creator, StandardCharsets.UTF_8);
            String encodedCapture = URLEncoder.encode(creationDate, StandardCharsets.UTF_8);
            url = new URL(pathAPI + "complexSearch" +
                    "?id=" + id +
                    "&title=" + encodedTitle +
                    "&description=" + encodedDescription +
                    "&keywords=" + encodedKeywords +
                    "&author=" + encodedAuthor +
                    "&creator=" + encodedCreator +
                    "&capture=" + encodedCapture);
        }
        else if (id.length() > 0) 
            url = new URL(pathAPI + "searchID/" + Integer.valueOf(id));
        else if (title.length() > 0) 
            url = new URL(pathAPI + "searchTitle/" + title);
        else if (creationDate.length() > 0) 
            url = new URL(pathAPI + "searchCreationDate/" + creationDate);
        else if (author.length() > 0) 
            url = new URL(pathAPI + "searchAuthor/" + author);
        else if (keyWords.length() > 0)
            url = new URL(pathAPI + "searchKeywords/" + keyWords);
        else {
            String encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8);
            String encodedDescription = URLEncoder.encode(description, StandardCharsets.UTF_8);
            String encodedKeywords = URLEncoder.encode(keyWords, StandardCharsets.UTF_8);
            String encodedAuthor = URLEncoder.encode(author, StandardCharsets.UTF_8);
            String encodedCreator = URLEncoder.encode(creator, StandardCharsets.UTF_8);
            String encodedCapture = URLEncoder.encode(creationDate, StandardCharsets.UTF_8);
            url = new URL(pathAPI + "complexSearch" +
                    "?id=" + id +
                    "&title=" + encodedTitle +
                    "&description=" + encodedDescription +
                    "&keywords=" + encodedKeywords +
                    "&author=" + encodedAuthor +
                    "&creator=" + encodedCreator +
                    "&capture=" + encodedCapture);
        }
            
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        int responseCode = conn.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = null;
            ObjectMapper objectMapper = new ObjectMapper();
        
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder res = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                res.append(line);
            }
            ArrayList<Image> images = objectMapper.readValue(res.toString(), new TypeReference<ArrayList<Image>>(){});
            request.setAttribute("numImages",images.size());
            int i = 0;
            for (Image img : images) {
                request.setAttribute("img" + Integer.toString(i), img);
                ++i;
            }
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("listImages.jsp");
        dispatcher.forward(request,response);
    }
}
