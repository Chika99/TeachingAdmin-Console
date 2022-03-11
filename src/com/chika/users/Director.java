package com.chika.users;

import com.chika.courses.Course;
import com.chika.utils.Function;
import com.chika.enums.Roles;
import com.chika.systems.TeachingSystem;

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
