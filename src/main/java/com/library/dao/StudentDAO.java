package com.library.dao;

import com.library.model.Student;
import com.library.util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDAO {

    private  Connection connection;
    private static final Logger LOGGER = Logger.getLogger(StudentDAO.class.getName());



    public StudentDAO() {
        try {
            this.connection = DbConnection.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la connexion à la base de données", e);
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }


    public void addStudent(Student student) {
        String query = "INSERT INTO students (id, name) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de l'ajout de l'étudiant", e);
        }
    }
    public static int getLastInsertedStudentId() {
        String query = "SELECT id FROM students ORDER BY id DESC LIMIT 1";
        try (Connection connection = DbConnection.getConnection()) {
            PreparedStatement stmt = connection.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println(rs.getInt("id"));
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error: sql exception "+e.getMessage());
        }
        return -1;  // Retourne -1 si aucune entrée n'est trouvée
    }


    public Student getStudentById(int id) {
        String query = "SELECT * FROM students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Student(resultSet.getInt("id"), resultSet.getString("name"));
                } else {
                    LOGGER.log(Level.WARNING, "Aucun étudiant trouvé avec l'ID : " + id);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération de l'étudiant", e);
        }
        return null;
    }

    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                students.add(new Student(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la récupération des étudiants", e);
        }
        return students;
    }

    public void updateStudent(Student student) {
        String query = "UPDATE students SET name = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, student.getName());
            statement.setInt(2, student.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la mise à jour de l'étudiant", e);
        }
    }

    public void deleteStudent(int id) {
        String query = "DELETE FROM students WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la suppression de l'étudiant", e);
        }
    }
    public void deleteAllStudents() {
        String query = "DELETE FROM students";
        try (PreparedStatement statement = DbConnection.getConnection().prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erreur lors de la suppression de tous les étudiants", e);
        }
    }
}
