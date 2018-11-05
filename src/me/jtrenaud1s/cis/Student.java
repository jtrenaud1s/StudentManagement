package me.jtrenaud1s.cis;

import java.util.HashMap;

public class Student {
    private String firstname;
    private String lastname;
    private String middlename;

    private HashMap<Integer, Grade> grades;

    public Student(String firstname, String lastname, String middlename) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.middlename = middlename;
        this.grades = new HashMap<>();
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public HashMap<Integer, Grade> getGrades() {
        return grades;
    }
}
