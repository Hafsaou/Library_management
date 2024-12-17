package com.library;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookServiceTest {
    private BookService bookService;
    private BookDAO bookDAO;

    @BeforeEach
    void setUp() {
        // Initialiser DAO et Service
        bookDAO = new BookDAO();
        bookService = new BookService();
        bookService.deleteAllBooks(); // Nettoyer les données avant chaque test
    }

    @Test
    void testAddBook() {
        // Ajouter un livre
        Book book = new Book(BookDAO.getLastInsertedBookId()+1, "Java Programming", "John Doe", "isbn", 2024);
        String result = bookService.addBook(book);

        // Vérification des résultats
        assertEquals("Livre inséré avec succès !", result, "Le message d'ajout devrait correspondre.");
        List<Book> books = bookDAO.getAllBooks();
        assertEquals(1, books.size(), "La taille de la liste devrait être 1.");
        assertEquals("Java Programming", books.get(0).getTitle(), "Le titre du livre doit être 'Java Programming'.");
    }

    @Test
    void testUpdateBook() {
        // Ajouter un livre
        Book book = new Book(BookDAO.getLastInsertedBookId()+1, "Java Programming", "John Doe", "isbn", 2024);
        bookService.addBook(book);

        // Mettre à jour le livre
        Book updatedBook = new Book(BookDAO.getLastInsertedBookId(), "Advanced Java", "John Doe", "isbn", 2024);
        String result = bookService.updateBook(updatedBook);

        // Vérification des résultats
        assertEquals("Livre mis à jour avec succès !", result, "Le message de mise à jour devrait correspondre.");
        Book fetchedBook = bookDAO.getBookById(BookDAO.getLastInsertedBookId());
        assertEquals("Advanced Java", fetchedBook.getTitle(), "Le titre devrait être 'Advanced Java'.");
    }

    @Test
    void testDeleteBook() {
        // Ajouter un livre
        Book book = new Book(BookDAO.getLastInsertedBookId(), "Java Programming", "John Doe", "isbn", 2024);
        bookService.addBook(book);

        // Supprimer le livre
        String result = bookService.deleteBook(BookDAO.getLastInsertedBookId());

        // Vérification des résultats
        assertEquals("Book successfully deleted!", result, "Le message de suppression devrait correspondre.");
        assertNull(bookDAO.getBookById(BookDAO.getLastInsertedBookId()), "Le livre ne devrait plus exister après suppression.");
    }

    @Test
    void testDeleteAllBooks() {
        // Ajouter plusieurs livres
        bookService.addBook(new Book(BookDAO.getLastInsertedBookId()+1, "Book 1", "Author 1", "isbn1", 2023));
        bookService.addBook(new Book(BookDAO.getLastInsertedBookId()+2, "Book 2", "Author 2", "isbn2", 2024));

        // Supprimer tous les livres
        String result = bookService.deleteAllBooks();

        // Vérification des résultats
        assertEquals("All books deleted successfully!", result, "Le message de suppression devrait correspondre.");
        assertTrue(bookDAO.getAllBooks().isEmpty(), "La liste des livres devrait être vide.");
    }

    @Test
    void testFindBookById() {
        // Ajouter un livre
        Book book = new Book(BookDAO.getLastInsertedBookId()+1, "Java Programming", "John Doe", "isbn", 2024);
        bookService.addBook(book);

        // Récupérer le livre par ID
        Book fetchedBook = bookService.findBookById(BookDAO.getLastInsertedBookId());

        // Vérification
        assertNotNull(fetchedBook, "Le livre récupéré ne devrait pas être null.");
        assertEquals("Java Programming", fetchedBook.getTitle(), "Le titre du livre devrait être 'Java Programming'.");
        assertEquals("John Doe", fetchedBook.getAuthor(), "L'auteur du livre devrait être 'John Doe'.");
    }
}
