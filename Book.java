public class Book {
    private String title;
    private String author;
    private String isbn;
    private String publicationDate;
    private int availableCopies;

    public Book(String title, String author, String isbn, String publicationDate, int availableCopies) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.availableCopies = availableCopies;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getPublicationdate() {
        return publicationDate;
    }

    public void setPublicationdate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }





}
