package com.library.model;

public class Book {
    private int id;
    private String title;
    private String author;
    private int publisherYear;
    private String isbn;
    // Constructeur complet
    public Book(int id, String title, String author, String isbn, int publishedYear) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisherYear=publishedYear;
    }

    public Book(String title, String author, String isbn, int publishedYear) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publisherYear=publishedYear;
    }


    // Constructeur par défaut
    public Book() {
    }

    // Constructeur complet


    // Constructeur additionnel si nécessaire
    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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


    public int getPublishedYear() {
        return this.publisherYear;
    }

    public String getIsbn() {
       return  this.isbn;
    }

    public void setPublishedYear(int year) {
        this.publisherYear = year;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
