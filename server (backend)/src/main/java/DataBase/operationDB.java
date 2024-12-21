package DataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Domain.Image;
import java.util.ArrayList;


public class operationDB {
    
    public static boolean existsUser(String name) {
        boolean exists = false;
        Connection c = initDB.getConnection();
        try {
            String select = "SELECT * FROM USUARIOS WHERE ID_USUARIO = ?";
            PreparedStatement ps = c.prepareStatement(select);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            exists = rs.next();      
        } 
        catch (SQLException e) {
            System.err.println(e.getMessage());
        } 
        initDB.closeConnection(c);
        return exists;
    }
    
    public static void insertUser(String name, String pwd) {
        Connection c = initDB.getConnection();
        try {
            // Insertion of new user
            String update = "INSERT INTO USUARIOS (ID_USUARIO, PASSWORD) VALUES (?,?)";
            PreparedStatement ps = c.prepareStatement(update);
            ps.setString(1, name);
            ps.setString(2, pwd);
            ps.executeUpdate();
        } 
        catch (Exception e) {
            System.err.println(e.getMessage());
        } 
        initDB.closeConnection(c);
    }
    
    public static boolean isValidUser(String name, String pwd_introduced) {
        if (!existsUser(name)) {
            return false;
        }
        else {
            Connection c = initDB.getConnection();
            try {
                String select = "SELECT PASSWORD FROM USUARIOS WHERE "
                        + "ID_USUARIO = ?";
                PreparedStatement ps = c.prepareStatement(select);
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                rs.next();
                String pwd = rs.getString("PASSWORD"); 
                initDB.closeConnection(c);
                return pwd.equals(pwd_introduced);
            } 
            catch (SQLException e) {
                System.err.println(e.getMessage());
                initDB.closeConnection(c);
                return false;
            } 
        }
    }
    
    public static boolean saveImage(String title, String description, String keyWords, String author, String creator, String captureDate, String storageDate, String filename, int encrypted) {
        Connection c = initDB.getConnection();
        try {
            // Insertion of new image
            String update = "INSERT INTO IMAGE (TITLE, DESCRIPTION, KEYWORDS, AUTHOR, CREATOR, CAPTURE_DATE, STORAGE_DATE, FILENAME, ENCRYPTED) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(update);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, keyWords);
            ps.setString(4, author);
            ps.setString(5, creator);
            ps.setString(6, captureDate);
            ps.setString(7, storageDate);
            ps.setString(8, filename);
            ps.setInt(9, encrypted);
            ps.executeUpdate();
            initDB.closeConnection(c);
            return true;
        } 
        catch (Exception e) {
            System.err.println(e.getMessage());
            initDB.closeConnection(c);
            return false;
        } 
    }
    
    public static ArrayList<Image> getImages(Integer imageId, String title, String description, 
            String keyWords, String author, String creator, String captureDate,
            String storageDate) {
        
        String select = "SELECT * FROM image WHERE 1=1";
        if (imageId != null) 
            select = "SELECT * FROM image WHERE ID=?";
        if (title != null && !title.isEmpty()) 
            select += " AND title LIKE ?";
        if (description != null && !description.isEmpty()) 
            select += " AND description LIKE ?";
        if (keyWords != null && !keyWords.isEmpty()) 
            select += " AND keywords LIKE ?";
        if (author != null && !author.isEmpty()) 
            select += " AND author LIKE ?";
        if (creator != null && !creator.isEmpty()) 
            select += " AND creator LIKE ?";
        if (captureDate != null && !captureDate.isEmpty()) {
            select += " AND capture_date = ?";
            captureDate = captureDate.replace("-", "/");
        }
        if (storageDate != null && !storageDate.isEmpty()) {
            select += " AND storage_Date = ?";
            storageDate = storageDate.replace("-", "/");
        }
        
        ArrayList<Image> images = new ArrayList<Image>();
        
        Connection c = initDB.getConnection();
        try {
            PreparedStatement ps = c.prepareStatement(select);
            int i = 1;
            if (imageId != null) {
               ps.setInt(i, imageId);
                i++; 
            }
            if (title != null && !title.isEmpty()) {
                ps.setString(i, "%" + title + "%");
                i++;
            }
            if (description != null && !description.isEmpty()) {
                ps.setString(i, "%" + description + "%");
                i++;
            }
            if (keyWords != null && !keyWords.isEmpty()) {
                ps.setString(i, "%" + keyWords + "%");
                i++;
            }
            if (author != null && !author.isEmpty()) {
                ps.setString(i, "%" + author + "%");
                i++;
            }
            if (creator != null && !creator.isEmpty()) {
                ps.setString(i, "%" + creator + "%");
                i++;
            }
            if (captureDate != null && !captureDate.isEmpty()) {
                ps.setString(i, captureDate);
                i++;
            }
            if (storageDate != null && !storageDate.isEmpty()) {
                ps.setString(i, storageDate);
                i++;
            }
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = Integer.toString(rs.getInt("id"));
                String filename = rs.getString("filename");
                String title2 = rs.getString("title");
                String description2 = rs.getString("description");
                String keyWords2 = rs.getString("keywords");
                String author2 = rs.getString("author");
                String creator2 = rs.getString("creator");
                String captureDate2 = rs.getString("capture_date");
                String storageDate2 = rs.getString("storage_date");
                
                Image img = new Image(id, filename, title2, description2, keyWords2,
                        author2, creator2, captureDate2, storageDate2);
                images.add(img);
            }
        } 
        catch (SQLException e) {
            System.err.println(e.getMessage());
            return null;
        }
        
        initDB.closeConnection(c);
        return images;
    }
    
    public static String getFilenameFromImageId(Integer id) {
        Connection c = initDB.getConnection();
         try {
           String select = "SELECT FILENAME FROM image WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(select);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            String filename = null;
            if (rs.next()) {
               filename = rs.getString("FILENAME");
            }
            initDB.closeConnection(c);
            return filename;
        } 
        catch (Exception e) {
            System.err.println(e.getMessage());
            initDB.closeConnection(c);
            return null;
        } 
    }
    
    public static String getImageCreator(Integer id) {
        Connection c = initDB.getConnection();
         try {
           String select = "SELECT CREATOR FROM image WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(select);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            String creator = null;
            if (rs.next()) {
               creator = rs.getString("CREATOR");
            }
            initDB.closeConnection(c);
            return creator;
        } 
        catch (Exception e) {
            System.err.println(e.getMessage());
            initDB.closeConnection(c);
            return null;
        } 
    }
    
    public static boolean modifyImage(Integer id, String title, String description, 
            String keyWords, String author, String captureDate, String filename) {
        
        Connection c = initDB.getConnection();
        try {
            String update = "UPDATE image SET title=?, description=?, keywords=?, "
                    + "author=?, capture_date=? WHERE id=?";
            PreparedStatement ps = c.prepareStatement(update);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, keyWords);
            ps.setString(4, author);
            ps.setString(5, captureDate);
            ps.setInt(6, id);
            ps.executeUpdate();
            
            if (filename != null) {
                update = "UPDATE image SET filename=? WHERE id=?";
                ps = c.prepareStatement(update);
                ps.setString(1, filename);
                ps.setInt(2, id);
                ps.executeUpdate(); 
            }
            
            initDB.closeConnection(c);
            return true;
        } 
        catch (Exception e) {
            System.err.println(e.getMessage());
            initDB.closeConnection(c);
            return false;
        } 
    }
    
    public static boolean eliminateImage(String id) {
        Connection c = initDB.getConnection();
        try {
            String delete = "DELETE FROM image WHERE id=?";
            PreparedStatement ps = c.prepareStatement(delete);
            ps.setString(1, id);
            ps.executeUpdate();
            initDB.closeConnection(c);
            return true;
        } 
        catch (Exception e) {
            System.err.println(e.getMessage());
            initDB.closeConnection(c);
            return false;
        } 
    }
}
