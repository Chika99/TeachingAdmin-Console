package com.chika;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * @author Cheng Liu
 */
public class TeachingSystem implements Serializable {
    private List<User> users = new ArrayList<>();
    private List<Course> courseList = new ArrayList<>();
    private static TeachingSystem teachingSystem = new TeachingSystem();
    private static final String path = System.getProperty("user.dir") + "/teaching_system.ser";

    private TeachingSystem() {
    }

    public static TeachingSystem getInstance() {
        return teachingSystem;
    }

    public void printCourseList() {
        System.out.println("Id\tName\tRequirement\tTeachers");
        StringJoiner s = new StringJoiner("\n");
        courseList.forEach(course -> s.add(course.toTableString()));
        System.out.println(s);
    }

    public void printTeacherList() {
        System.out.println("Id\tName\tTrainingState\tCourses");
        StringJoiner s = new StringJoiner("\n");
        users.stream().filter(user -> user.getRole() == Roles.TEACHER).forEach(user -> s.add(user.toString()));
        System.out.println(s);
    }

    public void save() {
        try {
            FileOutputStream f = new FileOutputStream(path);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(this);
            o.close();
            f.close();
            System.out.println("teaching system has saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void read() {
        try {
            FileInputStream f = new FileInputStream(path);
            ObjectInputStream o = new ObjectInputStream(f);
            teachingSystem = (TeachingSystem) o.readObject();
            System.out.println("teaching system loaded");
        } catch (IOException | ClassNotFoundException e) {
            if (e instanceof FileNotFoundException) {
                System.out.println("no history record found, try to create a new one");
                save();
            } else {
                e.printStackTrace();
            }
        }
    }

    public void addUser(User user) {
        user.setId(users.size() + 1);
        users.add(user);
        save();
    }

    public User getUser(String username, String password) {
        return users.stream().filter(user -> user.identify(username, password)).findFirst().orElse(null);
    }

    public void addCourse(Course course) {
        course.setId(courseList.size() + 1);
        courseList.add(course);
        save();
    }

    public Course getCourse(String name) {
        return courseList.stream().filter(course -> Objects.equals(course.getName(), name)).findFirst().orElse(null);
    }

    public User getUserById(String userId) {
        try {
            int index = Integer.parseInt(userId);
            return users.get(index - 1);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            return null;
        }
    }

    public Course getCourseById(String courseId) {
        try {
            int index = Integer.parseInt(courseId);
            return courseList.get(index - 1);
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            return null;
        }
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public List<User> getUsers() {
        return users;
    }
}
