package Domain;

public class Image {
    private String id;
    private String filename;
    private String title;
    private String description;
    private String keyWords;
    private String author;
    private String creator;
    private String captureDate;
    private String storageDate;
    private String imageBase64;
    
    public Image() {}
    
    public Image(String id, String filename, String title, String description, String keyWords,
            String author, String creator, String captureDate,
            String storageDate) {
        this.id = id;
        this.filename = filename;
        this.title = title;
        this.description = description;
        this.keyWords = keyWords;
        this.author = author;
        this.creator = creator;
        this.captureDate = captureDate;
        this.storageDate = storageDate;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription() {
        this.description = description;
    }
    
    public String getKeyWords() {
        return keyWords;
    }
    public void setKeyWords(String keywords) {
        this.keyWords = keywords;
    }
    
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    
    public String getCaptureDate() {
        return captureDate;
    }
    public void setCaptureDate(String captureDate) {
        this.captureDate = captureDate;
    }
    
    public String getStorageDate() {
        return storageDate;
    }
    public void setStorageDate(String storageDate) {
        this.storageDate = storageDate;
    }
    
    public String getImageBase64() {
        return imageBase64;
    }
    public void setImageBase64(String imageBase64) {
        this.imageBase64 = imageBase64;
    }
    
}