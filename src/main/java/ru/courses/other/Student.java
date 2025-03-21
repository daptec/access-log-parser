package ru.courses.other;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private final String name;
    private List<Integer> grades;

    public Student(String name, List<Integer> grades) {
        this.name = name;
        this.grades = new ArrayList<>();

        for (int grade: grades) {
            addGrades(grade);
        }
    }

    public Student(String name) {
        this(name, new ArrayList<>());
    }

    public String getName() {
        return name;
    }

    public void addGrades (int grade) {
        if (grade >= 2 && grade <= 5) {
            grades.add(grade);
        }
    }

    public List<Integer> getGrades() {
        return new ArrayList<>(grades);
    }

    @Override
    public String toString() {
        return name + ": [" + grades + "]";
    }
}
