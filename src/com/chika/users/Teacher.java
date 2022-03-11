package com.chika.users;

import com.chika.courses.Course;
import com.chika.enums.Roles;
import com.chika.enums.TrainingStates;
import com.chika.utils.ConsoleFormatter;
import com.chika.utils.FormatterConfig;
import com.chika.utils.Function;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static com.chika.enums.Roles.ALL;

/**
 * @author Cheng Liu
 */
public class Teacher extends User {
    private TrainingStates trainingState = TrainingStates.UNTRAINED;
    private List<Course> courseList = new ArrayList<>();

    public Teacher(String username, String password, Roles role) {
        super(username, password, role);
    }

    public void assign(Course course) {
        courseList.add(course);
    }

    public TrainingStates getTrainingState() {
        return trainingState;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setTrainingState(TrainingStates trainingState) {
        this.trainingState = trainingState;
    }


    @Function(command = "userinfo", message = "show user info", permissions = {ALL})
    @Override
    public void userinfo() {
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(this);
        printList(teachers, false);
        Course.printList(courseList, false);
    }


    @Override
    public String toString() {
        return String.format("%d %s %s", getId(), getUsername(), trainingState);
    }

    public static void printList(List<Teacher> teachers, boolean showCourse) {
        FormatterConfig config = new FormatterConfig.Builder()
                .setTitle("Id")
                .setPlaceholder(5)
                .setContents(teachers.stream().map(Teacher::getId).collect(Collectors.toList()))
                .build();
        FormatterConfig config1 = new FormatterConfig.Builder()
                .setTitle("Username")
                .setPlaceholder(20)
                .setContents(teachers.stream().map(Teacher::getUsername).collect(Collectors.toList()))
                .build();
        FormatterConfig config2 = new FormatterConfig.Builder()
                .setTitle("Role")
                .setPlaceholder(15)
                .setContents(teachers.stream().map(Teacher::getRole).collect(Collectors.toList()))
                .build();
        FormatterConfig config3 = new FormatterConfig.Builder()
                .setTitle("Training State")
                .setPlaceholder(15)
                .setContents(teachers.stream().map(Teacher::getTrainingState).collect(Collectors.toList()))
                .build();
        if (showCourse) {
            FormatterConfig config4 = new FormatterConfig.Builder()
                    .setTitle("Courses[id name requirement]")
                    .setPlaceholder(50)
                    .setContents(teachers.stream().map(Teacher::getCourseList).collect(Collectors.toList()))
                    .build();
            ConsoleFormatter.print(new FormatterConfig[]{config, config1, config2, config3, config4});
        } else {
            ConsoleFormatter.print(new FormatterConfig[]{config, config1, config2, config3});
        }
    }
}
