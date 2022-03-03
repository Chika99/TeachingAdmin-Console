package com.chika;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Cheng Liu
 */
public class CommandSystem {
    public static void start() {
        TeachingSystem.getInstance().read();
        System.out.println("ENTER help or commands");
        while (true) {
            try {
                System.out.print("system>>>");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                Scanner stringScanner = new Scanner(line);
                String command = stringScanner.nextLine();
                dispatch(command);
            } catch (NoSuchElementException ignored) {
            }
        }
    }

    public static void help() {
        StringBuilder s = new StringBuilder();
        User currentUser = LoginSystem.getInstance().getCurrentUser();
        Method[] methods = currentUser.getClass().getMethods();
        Arrays.stream(methods).forEach(method -> {
            Function annotation = method.getAnnotation(Function.class);
            if (annotation != null) {
                List<Roles> permissions = Arrays.stream(annotation.permissions()).collect(Collectors.toList());
                if (permissions.contains(Roles.ALL) || permissions.contains(currentUser.getRole())) {
                    s.append(String.format("%s:%s\n", annotation.command(), annotation.message()));
                }

            }
        });
        System.out.print(s);
    }

    public static void dispatch(String command) {
        String[] args = command.split(" ");
        User currentUser = LoginSystem.getInstance().getCurrentUser();
        Method[] methods = currentUser.getClass().getMethods();
        Map<String, Method> methodMap = new HashMap<>();
        Arrays.stream(methods).forEach(method -> {
            Function annotation = method.getAnnotation(Function.class);
            if (annotation != null) {
                List<Roles> permissions = Arrays.stream(annotation.permissions()).collect(Collectors.toList());
                // System.out.println(permissions);
                // System.out.println(permissions.contains(Roles.ALL) || permissions.contains(currentUser.getRole()));
                if (permissions.contains(Roles.ALL) || permissions.contains(currentUser.getRole())) {
                    methodMap.put(method.getName(), method);
                }
            }
        });
        if (args.length <= 0) {
            System.out.println("invalid input");
            return;
        }
        Method callMethod = methodMap.get(args[0]);
        if (callMethod == null) {
            System.out.printf("no [%s] command found%n", args[0]);
            return;
        }
        try {
            String[] passinArgs = Arrays.copyOfRange(args, 1, args.length);
            callMethod.invoke(currentUser, passinArgs);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.out.println("args not correct");
            Function annotation = callMethod.getAnnotation(Function.class);
            if (annotation != null) {
                System.out.println("this command is " + annotation.command());
            }
        }

    }
}
