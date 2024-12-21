package test.restad.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.MediaType;
import org.glassfish.jersey.media.multipart.FormDataParam;
import java.io.FileOutputStream;
import java.io.IOException;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.Base64;
import java.io.InputStream;
import java.nio.file.Files;
//import java.nio.file.Path;
import java.io.File;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


import DataBase.*;
import Domain.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

/**
 *
 * @author 
 */
@Path("jakartaee9")
public class JakartaEE91Resource {
    
    @GET
    public Response ping(){
        return Response
                .ok("ping Jakarta EE")
                .build();
    }
    /**
        * POST method to login in the application
        * @param username
        * @param password
        * @return
    */ 
    @Path("login")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username,
                            @FormParam("password") String password) {
        Connection con = initDB.getConnection();
        if (operationDB.isValidUser(username, password)) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "Login successful");
            return Response
                    .status(Response.Status.OK)
                    .entity(jsonResponse)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "fail");
            errorResponse.put("message", "Invalid username or password");
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(errorResponse)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
    
    @Path("registerUser")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@FormParam("username") String username,
                            @FormParam("password") String password) {
        Connection con = initDB.getConnection();
        if (!operationDB.existsUser(username)) {
            operationDB.insertUser(username, password);
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("status", "success");
            jsonResponse.put("message", "Register successful");
            return Response
                    .status(Response.Status.OK)
                    .entity(jsonResponse)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
        else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "fail");
            errorResponse.put("message", "User already exists");
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(errorResponse)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
    
     /**
     * POST method to register a new image – File is not uploaded
     * @param title
     * @param description
     * @param keyWords
     * @param author
     * @param creator
     * @param captDate
     * @param fileName
     * @param fileInputStream
     * @param fileData
     * @return
     */
     @Path("register")
     @POST
     @Consumes(MediaType.MULTIPART_FORM_DATA)
     @Produces(MediaType.APPLICATION_JSON)
     public Response registerImage (@FormDataParam("title") String title,
                                    @FormDataParam("description") String description,
                                    @FormDataParam("keyWords") String keyWords,
                                    @FormDataParam("author") String author,
                                    @FormDataParam("creator") String creator,
                                    @FormDataParam("captureDate") String captDate,
                                    @FormDataParam("fileName") String fileName,
                                    @FormDataParam("fileImgValue") InputStream fileInputStream,
                                    @FormDataParam("fileImgValue") FormDataContentDisposition fileData) {
        
        // Obtain storage date
        LocalDateTime dateAct = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String storageDate = dateAct.format(format);
        
        // Create the directory if it does not exists
        String UPLOAD_DIR = "/var/webapp/uploads";
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        
        // Obtain image extension
        String origFileName = fileData.getFileName();
        String fileExtension = origFileName.substring(origFileName.lastIndexOf('.') + 1);
        
        // Construct final file name
        String fileStoredName = creator + fileName + "." + fileExtension;
        java.nio.file.Path filePathToImg = new File(uploadDir, fileStoredName).toPath();
        
        // Check that file does not exist
        if (Files.exists(filePathToImg)) {
            Map<String, Object> jsonResponse = new HashMap<>();
            jsonResponse.put("status", "fail");
            jsonResponse.put("message", "File already exist");
            return Response
                .status(Response.Status.CONFLICT)
                .entity(jsonResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }

        try {
            Files.copy(fileInputStream, filePathToImg, StandardCopyOption.REPLACE_EXISTING);
            boolean imgSaved = operationDB.saveImage(title, description, keyWords, author, creator, captDate, storageDate, fileStoredName,0);
            if (imgSaved) {
                Map<String, Object> jsonResponse = new HashMap<>();
                jsonResponse.put("status", "success");
                jsonResponse.put("message", "Register image successful");
                return Response
                        .status(Response.Status.OK)
                        .entity(jsonResponse)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
            }
            else {
                Map<String, String> jsonResponse = new HashMap<>();
                jsonResponse.put("status", "fail");
                jsonResponse.put("message", "Could not register image");
                return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(jsonResponse)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
            Map<String, String> jsonResponse = new HashMap<>();
            jsonResponse.put("status", "fail");
            jsonResponse.put("message", "Could not register image");
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(jsonResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
    }
     
     
     /**
    * POST method to modify an existing image
    * @param id
    * @param title
    * @param description
    * @param keywords
    * @param author
    * @param creator, used for checking image ownership
    * @param capt_date
    * @return
    */
    @Path("modify")
    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyImage (@FormDataParam("id") String id,
                                @FormDataParam("title") String title,
                                @FormDataParam("description") String description,
                                @FormDataParam("keywords") String keywords,
                                @FormDataParam("author") String author,
                                @FormDataParam("creator") String creator,
                                @FormDataParam("capture") String capt_date,
                                @FormDataParam("fileName") String fileName,
                                @FormDataParam("fileImgValue") InputStream fileInputStream,
                                @FormDataParam("fileImgValue") FormDataContentDisposition fileData) {
        
        String imageCreator = operationDB.getImageCreator(Integer.valueOf(id));
        if (imageCreator.equals(creator)) {
                String filename = null;     // it updates if an image file has been specified
                // if new image file is specified
                if (fileInputStream != null && fileData != null &&  fileData.getFileName().length() > 0) {
                    String origFileName = fileData.getFileName();
                    String fileExtension = origFileName.substring(origFileName.lastIndexOf('.') + 1);
                    String UPLOAD_DIR = "/var/webapp/uploads";
                    // Construct final file name, if not specified keep the original
                    System.out.println("length of filename: " + fileName.length());
                    if (fileName.equals("") || fileName == null) {
                        filename = operationDB.getFilenameFromImageId(Integer.valueOf(id));
                    }
                    else {
                        filename = creator + fileName + "." + fileExtension;
                        java.nio.file.Path filePathToImg = new File(UPLOAD_DIR, filename).toPath();
                        if (Files.exists(filePathToImg)) {
                            Map<String, Object> jsonResponse = new HashMap<>();
                            jsonResponse.put("status", "fail");
                            jsonResponse.put("message", "File already exist");
                            return Response
                                .status(Response.Status.CONFLICT)
                                .entity(jsonResponse)
                                .type(MediaType.APPLICATION_JSON)
                                .build();
                        }
                    }
                    try {
                        java.nio.file.Path filePathToImg = new File(UPLOAD_DIR, filename).toPath();
                        Files.copy(fileInputStream, filePathToImg, StandardCopyOption.REPLACE_EXISTING);
                    } 
                    catch (Exception e) {
                        e.printStackTrace();
                        Map<String, String> jsonResponse = new HashMap<>();
                        jsonResponse.put("status", "fail");
                        jsonResponse.put("message", "Could not modify image");
                        return Response
                            .status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity(jsonResponse)
                            .type(MediaType.APPLICATION_JSON)
                            .build();
                    }  
                }
                else filename = null;
                boolean modified = operationDB.modifyImage(Integer.valueOf(id), title, description, keywords, author, capt_date, filename);
                if (modified) {
                    Map<String, Object> jsonResponse = new HashMap<>();
                    jsonResponse.put("status", "success");
                    jsonResponse.put("message", "Modify image successful");
                    return Response
                        .status(Response.Status.OK)
                        .entity(jsonResponse)
                        .type(MediaType.APPLICATION_JSON)
                        .build();    
                }
                else {
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("status", "fail");
                    errorResponse.put("message", "Could not modify image");
                    return Response
                        .status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(errorResponse)
                        .type(MediaType.APPLICATION_JSON)
                        .build();
                }
            }
        
        else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "fail");
            errorResponse.put("message", "Unauthorized to modify image");
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(errorResponse)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
    
    
        /**
     * POST method to delete an existing image
     * @param id
     * @return
     */
     @Path("delete")
     @POST
     @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
     @Produces(MediaType.APPLICATION_JSON)
     public Response deleteImage (@FormParam("id") String id,
                                  @FormParam("creator") String creator) {
         
         String imageCreator = operationDB.getImageCreator(Integer.valueOf(id));
         if (imageCreator.equals(creator)) {
             Image img = operationDB.getImages(Integer.valueOf(id),null,null,null,null,null,null,null).get(0);
             String filename = img.getFilename();
             boolean isDeleted = operationDB.eliminateImage(id);
            if (isDeleted) {
               String UPLOAD_DIR = "/var/webapp/uploads";
               File imgFile = new File(UPLOAD_DIR, filename);
               if (imgFile.exists()) {
                   imgFile.delete();
               } 
               Map<String, Object> jsonResponse = new HashMap<>();
               jsonResponse.put("status", "success");
               jsonResponse.put("message", "Deleted image successfully");
               return Response
                  .status(Response.Status.OK)
                  .entity(jsonResponse)
                  .type(MediaType.APPLICATION_JSON)
                  .build();
            }
            else {
                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("status", "fail");
                errorResponse.put("message", "Unable to delete image");
                return Response
                     .status(Response.Status.INTERNAL_SERVER_ERROR)
                     .entity(errorResponse)
                     .type(MediaType.APPLICATION_JSON)
                     .build();
            }
         }
         else {
             Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("status", "fail");
                errorResponse.put("message", "Unauthorized to delete image");
                return Response
                     .status(Response.Status.UNAUTHORIZED)
                     .entity(errorResponse)
                     .type(MediaType.APPLICATION_JSON)
                     .build();
         }
     }
     
        /**
    * GET method to search images by id
    * @param id
    * @return
    */
    @Path("searchID/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByID (@PathParam("id") int id) {
        ArrayList<Image> images = operationDB.getImages(id, null, null, null, null, null, null, null);
        if (images != null) {
            String UPLOAD_DIR = "/var/webapp/uploads";
            for (Image image : images) {
                String imageName = image.getFilename();
                java.nio.file.Path filePath = new File(UPLOAD_DIR,imageName).toPath();
                try {
                    byte[] fileContent = Files.readAllBytes(filePath);
                    String base64Image = Base64.getEncoder().encodeToString(fileContent);
                    image.setImageBase64(base64Image);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    image.setImageBase64(null);
                }
            }
            return Response
                .status(Response.Status.OK)
                .entity(images)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "fail");
            errorResponse.put("message", "Error in retrieving images");
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
    }
    
        /**
     * GET method to search images by title
     * @param title
     * @return
     */
     @Path("searchTitle/{title}")
     @GET
     @Produces(MediaType.APPLICATION_JSON)
     public Response searchByTitle (@PathParam("title") String title) {
        ArrayList<Image> images = operationDB.getImages(null, title, null, null, null, null, null, null);
        if (images != null) {
            String UPLOAD_DIR = "/var/webapp/uploads";
            for (Image image : images) {
                String imageName = image.getFilename();
                java.nio.file.Path filePath = new File(UPLOAD_DIR,imageName).toPath();
                try {
                    byte[] fileContent = Files.readAllBytes(filePath);
                    String base64Image = Base64.getEncoder().encodeToString(fileContent);
                    image.setImageBase64(base64Image);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    image.setImageBase64(null);
                }
            }
            
            return Response
                .status(Response.Status.OK)
                .entity(images)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "fail");
            errorResponse.put("message", "Error in retrieving images");
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
     }
     
        /**
     * GET method to search images by creation date. Date format should be
     * yyyy-mm-dd
     * @param date
     * @return
     */
     @Path("searchCreationDate/{date}")
     @GET
     @Produces(MediaType.APPLICATION_JSON)
     public Response searchByCreationDate (@PathParam("date") String date) {
        ArrayList<Image> images = operationDB.getImages(null, null, null, null, null, null, date, null);
        if (images != null) {
            String UPLOAD_DIR = "/var/webapp/uploads";
            for (Image image : images) {
                String imageName = image.getFilename();
                java.nio.file.Path filePath = new File(UPLOAD_DIR,imageName).toPath();
                try {
                    byte[] fileContent = Files.readAllBytes(filePath);
                    String base64Image = Base64.getEncoder().encodeToString(fileContent);
                    image.setImageBase64(base64Image);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    image.setImageBase64(null);
                }
            }
            
            return Response
                .status(Response.Status.OK)
                .entity(images)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "fail");
            errorResponse.put("message", "Error in retrieving images");
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
     }
     
        /**
    * GET method to search images by author
    * @param author
    * @return
    */
    @Path("searchAuthor/{author}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchByAuthor (@PathParam("author") String author) {
        ArrayList<Image> images = operationDB.getImages(null, null, null, null, author, null, null, null);
        if (images != null) {
            String UPLOAD_DIR = "/var/webapp/uploads";
            for (Image image : images) {
                String imageName = image.getFilename();
                java.nio.file.Path filePath = new File(UPLOAD_DIR,imageName).toPath();
                try {
                    byte[] fileContent = Files.readAllBytes(filePath);
                    String base64Image = Base64.getEncoder().encodeToString(fileContent);
                    image.setImageBase64(base64Image);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    image.setImageBase64(null);
                }
            }
            
            return Response
                .status(Response.Status.OK)
                .entity(images)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "fail");
            errorResponse.put("message", "Error in retrieving images");
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
    }
    
        /**
     * GET method to search images by keyword
     * @param keywords
     * @return
     */
     @Path("searchKeywords/{keywords}")
     @GET
     @Produces(MediaType.APPLICATION_JSON)
     public Response searchByKeywords (@PathParam("keywords") String keywords) {
        ArrayList<Image> images = operationDB.getImages(null, null, null, keywords, null, null, null, null);
        if (images != null) {
            String UPLOAD_DIR = "/var/webapp/uploads";
            for (Image image : images) {
                String imageName = image.getFilename();
                java.nio.file.Path filePath = new File(UPLOAD_DIR,imageName).toPath();
                try {
                    byte[] fileContent = Files.readAllBytes(filePath);
                    String base64Image = Base64.getEncoder().encodeToString(fileContent);
                    image.setImageBase64(base64Image);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    image.setImageBase64(null);
                }
            }
            
            return Response
                .status(Response.Status.OK)
                .entity(images)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "fail");
            errorResponse.put("message", "Error in retrieving images");
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
     }
     
      /**
     * GET method to search images by multiple parameters
     * @param id
     * @param title
     * @param description
     * @param keywords
     * @param author
     * @param creator
     * @param capt_date
     * @return
     */
     @Path("complexSearch")
     @GET
     @Produces(MediaType.APPLICATION_JSON)
     public Response searchByParams (@QueryParam("id") String id,
                                     @QueryParam("title") String title,
                                     @QueryParam("description") String description,
                                     @QueryParam("keywords") String keywords,
                                     @QueryParam("author") String author,
                                     @QueryParam("creator") String creator,
                                     @QueryParam("capture") String capt_date) {
        Integer idInt = null;
        if (!id.isEmpty()) 
            idInt = Integer.valueOf(id);
        
        ArrayList<Image> images = operationDB.getImages(idInt, title, description, keywords, author, creator, capt_date, null);
        if (images != null) {
            String UPLOAD_DIR = "/var/webapp/uploads";
            for (Image image : images) {
                String imageName = image.getFilename();
                java.nio.file.Path filePath = new File(UPLOAD_DIR,imageName).toPath();
                try {
                    byte[] fileContent = Files.readAllBytes(filePath);
                    String base64Image = Base64.getEncoder().encodeToString(fileContent);
                    image.setImageBase64(base64Image);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    image.setImageBase64(null);
                }
            }
            
            return Response
                .status(Response.Status.OK)
                .entity(images)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
        else {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("status", "fail");
            errorResponse.put("message", "Error in retrieving images");
            return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
     }
}
