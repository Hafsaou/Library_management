package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.StudentDAO;
import com.library.model.Student;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class StudentService {
    private final Logger logger = Logger.getLogger(StudentService.class.getName());

    private StudentDAO studentDAO;


    public StudentService() {
    this.studentDAO = new StudentDAO();
}

    // Constructeur
    public StudentService(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }


    // Supprimer un étudiant par ID
    public void deleteStudent(int id) {
        try {
            studentDAO.deleteStudent(id);
        } catch (Exception e) {
            logger.severe("Erreur lors de la suppression de l'étudiant : " + e.getMessage());
        }
    }
    // Ajouter un étudiant
    public void addStudent(Student student) {
        try {
            studentDAO.addStudent(student);
        } catch (Exception e) {
            logger.severe("adding student error : " + e.getMessage());
        }
    }

    // Afficher tous les étudiants
    public void displayStudents() {
        try {
            List<Student> students = studentDAO.getAllStudents();
            for (Student student : students) {
                System.out.println("ID: " + student.getId() + " | Nom: " + student.getName());
            }
        } catch (Exception e) {
            logger.severe("display student error  : " + e.getMessage());
        }
    }

    // Trouver un étudiant par ID
    public Student findStudentById(int id) {
        try {
            return studentDAO.getStudentById(id);
        } catch (Exception e) {
            logger.severe("Erreur lors de la recherche de l'étudiant par ID  : " + e.getMessage());
        }
        return null;
    }
    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

    // delete all students
    public void deleteAllStudents() {
        try {
            studentDAO.deleteAllStudents();
        } catch (Exception e) {
            logger.severe("Erreur lors de la suppression de tous les étudiants  : " + e.getMessage());
        }
    }
}
