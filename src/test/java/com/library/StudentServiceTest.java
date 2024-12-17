package com.library;

import com.library.dao.StudentDAO;
import com.library.model.Student;
import com.library.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {
    private StudentService studentService;
    private StudentDAO studentDAO;

    @BeforeEach
    void setUp() {
        // Initialiser DAO et Service
        studentDAO = new StudentDAO();
        studentService = new StudentService(studentDAO);
        studentService.deleteAllStudents(); // Nettoyer la base de données avant chaque test
    }

    @Test
    void testAddStudent() {
        // Ajouter un étudiant
        Student student = new Student("Hafsa");
        studentService.addStudent(student);

        // Vérifier que l'étudiant est bien ajouté
        List<Student> students = studentDAO.getAllStudents();
        assertEquals(1, students.size(), "La taille de la liste devrait être 1.");
        assertEquals("Hafsa", students.get(0).getName(), "Le nom de l'étudiant devrait être 'Hafsa'.");
    }

    @Test
    void testUpdateStudent() {
        // Ajouter un étudiant et mettre à jour son nom
        Student student = new Student("Alice");
        studentService.addStudent(student);

        // Récupérer l'étudiant ajouté
        Student existingStudent = studentDAO.getAllStudents().get(0);
        existingStudent.setName("Alice Smith");

        // Mise à jour
        studentService.updateStudent(existingStudent);

        // Vérification
        Student updatedStudent = studentDAO.getStudentById(existingStudent.getId());
        assertEquals("Alice Smith", updatedStudent.getName(), "Le nom de l'étudiant devrait être 'Alice Smith'.");
    }

    @Test
    void testDeleteStudent() {
        // Ajouter un étudiant et le supprimer
        Student student = new Student("Alice");
        studentService.addStudent(student);

        // Récupérer l'ID du premier étudiant ajouté
        int studentId = studentDAO.getAllStudents().get(0).getId();

        // Suppression
        studentService.deleteStudent(studentId);

        // Vérification que l'étudiant est supprimé
        assertNull(studentDAO.getStudentById(studentId), "L'étudiant devrait être supprimé.");
    }

    @Test
    void testGetAllStudents() {
        // Ajouter deux étudiants
        studentService.addStudent(new Student("Alice"));
        studentService.addStudent(new Student("Bob"));

        // Vérification que les deux étudiants existent
        List<Student> students = studentDAO.getAllStudents();
        assertEquals(2, students.size(), "La taille de la liste devrait être 2.");
    }

    @Test
    void testFindStudentById() {
        // Ajouter un étudiant
        Student student = new Student("Charlie");
        studentService.addStudent(student);

        // Récupérer l'étudiant ajouté
        int studentId = studentDAO.getAllStudents().get(0).getId();
        Student foundStudent = studentService.findStudentById(studentId);

        // Vérification
        assertNotNull(foundStudent, "L'étudiant ne devrait pas être null.");
        assertEquals("Charlie", foundStudent.getName(), "Le nom de l'étudiant devrait être 'Charlie'.");
    }
}
