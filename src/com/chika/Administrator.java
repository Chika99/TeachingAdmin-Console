package com.chika;

/**
 * @author Cheng Liu
 */
public class Administrator extends User {
    public Administrator(String username, String password, Roles role) {
        super(username, password, role);
    }

    @Function(command = "assign [courseId] [teacherId]", message = "assign a teacher to a course", permissions = {Roles.ADMINISTRATOR})
    public void assign(String courseId, String teacherId) {
        User user = TeachingSystem.getInstance().getUserById(teacherId);
        Course course = TeachingSystem.getInstance().getCourseById(courseId);
        if (user == null) {
            System.out.println("incorrect teacher id");
            return;
        }
        if (course == null) {
            System.out.println("incorrect course id");
            return;
        }
        if (user.getRole() != Roles.TEACHER) {
            System.out.println("this user is not a teacher");
            return;
        }
        Teacher teacher = (Teacher) user;
        if (course.getTeachers().stream().anyMatch(t -> t.getId() == teacher.getId())) {
            System.out.println("this teacher has already assigned to this course");
            return;
        }

        course.addTeacher(teacher);
        teacher.assign(course);
        System.out.println("success");
    }

    @Function(command = "train [teacherId]", message = "assign a teacher to train", permissions = {Roles.ADMINISTRATOR})
    public void train(String teacherId) {
        User user = TeachingSystem.getInstance().getUserById(teacherId);
        if (user == null || user.getRole() != Roles.TEACHER) {
            System.out.println("incorrect teacher id");
            return;
        }
        ((Teacher) user).setTrainingState(TrainingStates.TRAINING);
        System.out.println("success");
    }

    @Function(command = "endTrain [teacherId]", message = "end training of a teacher", permissions = {Roles.ADMINISTRATOR})
    public void endTrain(String teacherId) {
        User user = TeachingSystem.getInstance().getUserById(teacherId);
        if (user == null || user.getRole() != Roles.TEACHER) {
            System.out.println("incorrect teacher id");
            return;
        }
        Teacher teacher = (Teacher) user;
        if (teacher.getTrainingState() == TrainingStates.TRAINING) {
            teacher.setTrainingState(TrainingStates.TRAINED);
        }
        System.out.println("success");
    }
}
