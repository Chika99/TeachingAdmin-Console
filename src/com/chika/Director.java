package com.chika;

/**
 * @author Cheng Liu
 */
public class Director extends User {
    public Director(String username, String password, Roles role) {
        super(username, password, role);
    }

    @Function(command = "addCourse [courseName] [requirement]", message = "add a course", permissions = {Roles.CLASS_DIRECTOR})
    public void addCourse(String name, String requirement) {
        TeachingSystem.getInstance().addCourse(new Course(name, requirement));
        System.out.println("success");
    }

    @Function(command = "editCourse [courseName] [requirement]", message = "edit a course", permissions = {Roles.CLASS_DIRECTOR})
    public void editCourse(String name, String requirement) {
        TeachingSystem.getInstance().getCourse(name).setRequirement(requirement);
        System.out.println("success");
    }
}
