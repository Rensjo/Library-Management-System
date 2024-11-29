import java.util.*;

public class Member extends Person {
    private String memberID;
    private List<Book> borrowedBooks;

    public Member(String name, String memberID) {
        super(name);
        this.memberID = memberID;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberID() {
        return memberID;
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public List<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void borrowBook(Book book) {
        borrowedBooks.add(book);
        book.setAvailableCopies(book.getAvailableCopies() - 1);
    }

    public void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.setAvailableCopies(book.getAvailableCopies() + 1);
    }
}
