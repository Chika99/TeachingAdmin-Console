package com.chika.courses;

import com.chika.systems.TeachingSystem;
import com.chika.users.Teacher;
import com.chika.utils.ConsoleFormatter;
import com.chika.utils.FormatterConfig;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cheng Liu
 */
public class Course implements Serializable {

    private int id;
    private String name;
    private String requirement;
    private List<Teacher> teachers = new ArrayList<>();

    public Course(String name, String requirement) {
        this.name = name;
        this.requirement = requirement;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
        TeachingSystem.getInstance().save();
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return String.format("%d %s %s", id, name, requirement);
    }

    public static void printList(List<Course> courses, boolean showTeacher) {
        FormatterConfig config = new FormatterConfig.Builder()
                .setTitle("Course Id")
                .setPlaceholder(15)
                .setContents(courses.stream().map(Course::getId).collect(Collectors.toList()))
                .build();
        FormatterConfig config1 = new FormatterConfig.Builder()
                .setTitle("Course Name")
                .setPlaceholder(20)
                .setContents(courses.stream().map(Course::getName).collect(Collectors.toList()))
                .build();
        FormatterConfig config2 = new FormatterConfig.Builder()
                .setTitle("Course Requirement")
                .setPlaceholder(40)
                .setContents(courses.stream().map(Course::getRequirement).collect(Collectors.toList()))
                .build();
        if (showTeacher) {
            FormatterConfig config3 = new FormatterConfig.Builder()
                    .setTitle("Teachers[id username training-state]")
                    .setPlaceholder(40)
                    .setContents(courses.stream().map(Course::getTeachers).collect(Collectors.toList()))
                    .build();
            ConsoleFormatter.print(new FormatterConfig[]{config, config1, config2, config3});
        } else {
            ConsoleFormatter.print(new FormatterConfig[]{config, config1, config2});
        }
    }
}
