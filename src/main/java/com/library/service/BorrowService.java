
package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.StudentDAO;
import com.library.model.Book;
import com.library.model.Student;
import com.library.dao.BorrowDAO;
import com.library.model.Borrow;

import java.util.List;

public class BorrowService {

    private BorrowDAO borrowDAO;

    // Constructeur avec BorrowDAO
    public BorrowService(BorrowDAO borrowDAO) {
        this.borrowDAO = borrowDAO;
    }

    // Méthode pour emprunter un livre
    public String borrowBook(Borrow borrow) {
        // Sauvegarde de l'emprunt dans la base de données
        return borrowDAO.addBorrow(borrow);
    }

    public String deleteBorrow(int id) {
        return borrowDAO.deleteBorrow(id);
    }

    public  List<Borrow> displayBorrows() {
        List<Borrow> borrows = borrowDAO.getAllBorrows();
        if(borrows != null)
        {
            for (Borrow borrow : borrows) {
                System.out.println(borrow.getId());
            }
        }
        return borrows;
    }
    public String deleteAllBorrows() {
        return borrowDAO.deleteAll();
    }

    public String updateBorrow(Borrow borrow) {
        return borrowDAO.save(borrow);
    }
}
