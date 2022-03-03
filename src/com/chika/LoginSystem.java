package com.chika;

/**
 * @author Cheng Liu
 */
public class LoginSystem {
    private static LoginSystem loginSystem = new LoginSystem();
    private User currentUser = new User("", "", Roles.ANONYMOUS);

    private LoginSystem() {
    }

    public static LoginSystem getInstance() {
        return loginSystem;
    }

    public void login(String username, String password) {
        User loginUser = TeachingSystem.getInstance().getUser(username, password);
        if (loginUser == null) {
            System.out.println("wrong username or password");
        } else {
            currentUser = loginUser;
            System.out.printf("login success, welcome %s!%n", currentUser.getUsername());
        }
    }

    public void logout() {
        currentUser = new User("", "", Roles.ANONYMOUS);
        System.out.println("logout success");
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
