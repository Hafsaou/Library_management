package com.library;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.service.BookService;
import com.library.service.BorrowService;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class BorrowServiceTest {
    private BorrowService borrowService;
    private BookDAO bookDAO;
    private StudentDAO studentDAO;

    private Date borrowDate;
    private Date returnDate;
    private Book book1;
    private Book book2;
    private BorrowDAO borrowDAO = new BorrowDAO();
    private BookService bookService;
    private Student student1;
    private Student student2;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentDAO = new  StudentDAO();
        bookService = new BookService();
        studentService = new StudentService();
        borrowService = new BorrowService(borrowDAO);
        borrowService.deleteAllBorrows();
        bookService.deleteAllBooks();
        studentService.deleteAllStudents();

        student1 = new Student(StudentDAO.getLastInsertedStudentId()+1, "Alice");
        student2 = new Student(StudentDAO.getLastInsertedStudentId()+2, "Bob");

        // Ajouter un étudiant
        studentService.addStudent(student1);
        studentService.addStudent(student2);

        book1 =new Book( BookDAO.getLastInsertedBookId()+1,"Java Programming", "John Doe", "isbn",2024);
        book2 = new Book(BookDAO.getLastInsertedBookId()+2,"Java Programming2", "John Doe2", "isbn2",2024);
        bookService.addBook( book1);
        bookService.addBook(book2);


        borrowDate = new Date(System.currentTimeMillis());
        returnDate = new Date(System.currentTimeMillis() + 10000);
    }

    @Test
    void testBorrowBook() {
        System.out.println(  "id st "+studentService.findStudentById(student2.getId()));
        Borrow borrow = new Borrow(BorrowDAO.getLastInsertedBorrowId()+1,student2, book2, borrowDate, returnDate);
        assertEquals("Livre emprunté avec succès!", borrowService.borrowBook(borrow));
     }

    @Test
    void testReturnBook() {
         assertEquals("Livre retourné avec succès!", borrowService.deleteBorrow(BorrowDAO.getLastInsertedBorrowId()));
     }

    @Test
    void testBorrowBookNotAvailable() {

        assertEquals("Étudiant ou livre non trouvé.", borrowService.borrowBook(new Borrow(student1, book2, borrowDate, returnDate)));
    }

    @Test
    void testBorrowBookStudentNotFound() {
        Borrow borrow = new Borrow(BorrowDAO.getLastInsertedBorrowId(),new Student("saad"), book1, borrowDate, returnDate);
        assertEquals("Étudiant ou livre non trouvé.", borrowService.borrowBook(borrow));    }
    @Test
    void testUpdateBorrow() {
        Borrow borrow = new Borrow(BorrowDAO.getLastInsertedBorrowId(),student2, book2, borrowDate, returnDate);
        borrowService.borrowBook(borrow);
        borrow.setReturnDate(new Date(System.currentTimeMillis()));
        assertEquals("Emprunt mis à jour avec succès!", borrowService.updateBorrow(borrow));

    }

    @Test
    void testDeleteAllBorrows() {
        Borrow borrow = new Borrow(BorrowDAO.getLastInsertedBorrowId(),student2, book2, borrowDate, returnDate);
        borrowService.borrowBook(borrow);
        assertEquals("Tous les livres ont été retournés avec succès!", borrowService.deleteAllBorrows());
        assertEquals(0, borrowService.displayBorrows().size());
    }

}
