package com.chika;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import static com.chika.Roles.ALL;

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

    public void setTrainingState(TrainingStates trainingState) {
        this.trainingState = trainingState;
    }


    @Function(command = "userinfo", message = "show user info", permissions = {ALL})
    @Override
    public void userinfo() {
        System.out.println(new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("username='" + getUsername() + "'")
                .add("role=" + getRole())
                .add("trainingState=" + trainingState)
                .add("courseList=" + courseList));
    }

    public String toSimpleString() {
        return new StringJoiner(", ", Teacher.class.getSimpleName() + "[", "]")
                .add("id=" + getId())
                .add("username='" + getUsername() + "'")
                .add("trainingState=" + trainingState)
                .toString();
    }

    @Override
    public String toString() {
        return String.format("%d\t%s\t%s\t%s", getId(), getUsername(), trainingState, courseList);
    }
}
