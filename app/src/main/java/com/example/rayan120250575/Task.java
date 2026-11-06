package com.example.rayan120250575;


public class Task {
    private int id;
    private String title;
    private double grade;
    private boolean submitted;

    public Task(int id, String title, double grade, boolean submitted) {
        this.id = id;
        this.title = title;
        this.grade = grade;
        this.submitted = submitted;
    }

    public int getId() {
        return id; }
    public String getTitle() {
        return title; }
    public double getGrade() {
        return grade; }
    public boolean isSubmitted() {
        return submitted; }
}
