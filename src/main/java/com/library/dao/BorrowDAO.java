
package com.library.dao;

import com.library.model.Book;
import com.library.model.Borrow;
import com.library.model.Student;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BorrowDAO {
    private static final Logger LOGGER = Logger.getLogger(BorrowDAO.class.getName());

    public List<Borrow> getAllBorrows() {
        StudentDAO studentDAO = new StudentDAO();
        BookDAO bookDAO = new BookDAO();
        List<Borrow> borrows = new ArrayList<>();
        String query = "SELECT * FROM borrows";
         Student student;
        Book book;
        try (Connection connection = DbConnection.getConnection();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                student = studentDAO.getStudentById(rs.getInt("member"));
                book = bookDAO.getBookById(rs.getInt("book"));
                Borrow borrow = new Borrow(
                        rs.getInt("id"),
                        student,
                        book,
                        rs.getDate("borrow_date"),
                        rs.getDate("return_date")
                );
                borrows.add(borrow);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return borrows;
    }

public void save(Borrow borrow) {

    String query = "UPDATE borrows SET member = ?, book = ?, borrow_date = ?, return_date = ? WHERE id = ?";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, borrow.getId());
            stmt.setInt(2, borrow.getStudent().getId());
            stmt.setInt(3, borrow.getBook().getId());
            stmt.setDate(4, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(5, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    

}



    public void addBorrow(Borrow borrow) {
        String query = "INSERT INTO borrows (id, member, book, borrow_date, return_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DbConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(0, borrow.getId());
            stmt.setInt(1, borrow.getStudent().getId());
            stmt.setInt(2, borrow.getBook().getId());
            stmt.setDate(3, new java.sql.Date(borrow.getBorrowDate().getTime()));
            stmt.setDate(4, new java.sql.Date(borrow.getReturnDate().getTime()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public Borrow getById(int id) {
        StudentDAO studentDAO = new StudentDAO();
        BookDAO bookDAO = new BookDAO();
        String query = "SELECT * FROM borrows WHERE id = ?";
        Student student;
        Book book;
        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    student = studentDAO.getStudentById(rs.getInt("member"));
                    book = bookDAO.getBookById(rs.getInt("book"));
                    return new Borrow(
                            rs.getInt("id"),
                            student,
                            book,
                            rs.getDate("borrow_date"),
                            rs.getDate("return_date"));
                }
            }
        } catch (SQLException e) {
         }
        return null;
    }

    public String deleteBorrow(int id) {
        String query = "DELETE FROM borrows WHERE id = ?";
        try (PreparedStatement stmt = DbConnection.getConnection().prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return "book returned";
        } catch (SQLException e) {
             return "returning error";
        }
    }

    public String deleteAll() {
        String query = "DELETE FROM borrows";
        try (Statement stm = DbConnection.getConnection().createStatement()) {
               stm.executeUpdate(query);
               return "books returned";
        } catch (SQLException e) {
             return "books not returned";
        }
    }
}
