package com.chika.users;

import com.chika.enums.Roles;
import com.chika.systems.CommandSystem;
import com.chika.systems.LoginSystem;
import com.chika.systems.TeachingSystem;
import com.chika.utils.ConsoleFormatter;
import com.chika.utils.FormatterConfig;
import com.chika.utils.Function;

import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

import static com.chika.enums.Roles.*;

/**
 * @author Cheng Liu
 */
public class User implements Serializable {
    private int id;
    private String username;
    private String password;
    private Roles role;

    public User(String username, String password, Roles role) {
        if (username == null || password == null || role == null) {
            throw new RuntimeException("failed to create user");
        }
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public boolean identify(String username, String password) {
        return Objects.equals(this.username, username) && Objects.equals(this.password, password);
    }

    @Function(command = "register [username] [password] [role]", message = "register a user", permissions = {ALL})
    public void register(String username, String password, String roleStr) {
        Roles role;
        if (Objects.equals(roleStr, "teacher")) {
            role = TEACHER;
        } else if (Objects.equals(roleStr, "administrator")) {
            role = ADMINISTRATOR;
        } else if (Objects.equals(roleStr, "director")) {
            role = CLASS_DIRECTOR;
        } else {
            System.out.println("role should within [teacher, administrator, director]");
            return;
        }
        User user;
        switch (role) {
            case TEACHER:
                user = new Teacher(username, password, role);
                break;
            case ADMINISTRATOR:
                user = new Administrator(username, password, role);
                break;
            case CLASS_DIRECTOR:
                user = new Director(username, password, role);
                break;
            default:
                throw new RuntimeException("invalid role");
        }
        if (TeachingSystem.getInstance().getUsers().stream().anyMatch(user1 -> Objects.equals(user1.getUsername(), user.getUsername()))) {
            System.out.println("username already exist");
            return;
        }
        TeachingSystem.getInstance().addUser(user);
        System.out.println("register success, please login");
    }

    @Function(command = "login [username] [password]", message = "login", permissions = {ANONYMOUS})
    public void login(String username, String password) {
        LoginSystem.getInstance().login(username, password);
    }

    @Function(command = "logout", message = "logout", permissions = {ADMINISTRATOR, CLASS_DIRECTOR, TEACHER})
    public void logout() {
        LoginSystem.getInstance().logout();

    }

    @Function(command = "userinfo", message = "show user info", permissions = {ALL})
    public void userinfo() {
        FormatterConfig config = new FormatterConfig.Builder()
                .setTitle("Id")
                .setPlaceholder(5)
                .setContents(id)
                .build();
        FormatterConfig config1 = new FormatterConfig.Builder()
                .setTitle("Username")
                .setPlaceholder(20)
                .setContents(username)
                .build();
        FormatterConfig config2 = new FormatterConfig.Builder()
                .setTitle("Role")
                .setPlaceholder(15)
                .setContents(role.toString())
                .build();
        ConsoleFormatter.print(new FormatterConfig[]{config, config1, config2});
    }

    @Function(command = "help", message = "show help menu", permissions = {ALL})
    public void help() {
        CommandSystem.help();
    }

    public String getUsername() {
        return username;
    }

    @Function(command = "course", message = "show all courses", permissions = {Roles.CLASS_DIRECTOR, Roles.ADMINISTRATOR})
    public void course() {
        TeachingSystem.getInstance().printCourseList();
    }

    @Function(command = "teacher", message = "show all teachers", permissions = {Roles.CLASS_DIRECTOR, Roles.ADMINISTRATOR})
    public void teacher() {
        TeachingSystem.getInstance().printTeacherList();
    }

    @Function(command = "quit", message = "exit system", permissions = {ALL})
    public void quit() {
        System.exit(0);
    }

    public Roles getRole() {
        return role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("username='" + username + "'")
                .add("role=" + role)
                .toString();
    }
}
