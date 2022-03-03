package com.chika;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

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

    public String getTeachersString() {
        StringJoiner s = new StringJoiner(", ", "[", "]");
        teachers.forEach(teacher -> s.add(teacher.toSimpleString()));
        return s.toString();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Course.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("requirement='" + requirement + "'")
                .add("teachers=" + getTeachersString())
                .toString();
    }

    public String toTableString() {
        return String.format("%d\t%s\t%s\t%s", id, name, requirement, getTeachersString());
    }
}
