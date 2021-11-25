import model.*;
import service.AuthorizationService;
import service.PostService;
import utils.DBConnector;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException, ParseException {
        Connection connection = DBConnector.connect();
        AuthorizationService authorizationService = new AuthorizationService(connection);
        PostService postService = new PostService(connection);
        Scanner in = new Scanner(System.in);
        System.out.println("\n Выберите действие: ");
        System.out.println("1 - Создать новую учётную запись пользователя");
        System.out.println("2 - Войти в существующую учётную запись");
        if (Integer.parseInt(in.nextLine()) == 1) {
            System.out.println("Введите логин пользователя: ");
            String username = in.nextLine();
            System.out.println("Введите пароль пользователя: ");
            String password = in.nextLine();
            User user = new User(username, password);
            authorizationService.addUser(connection, user);
        }
        System.out.println("Введите логин пользователя: ");
        String username = in.nextLine();
        System.out.println("Введите пароль пользователя: ");
        String password = in.nextLine();
        Role loginResult = authorizationService.login(username, password);
        if (loginResult != null) {
            System.out.println("\n Добро пожаловать, " + username + "!");
            boolean running = true;
            while (running) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
                System.out.println("\n Выберите действие: ");
                if (loginResult == Role.ADMIN) {
                    Admin admin = new Admin(username, password);
                    System.out.println("1 - Создать новую учётную запись администратора");
                    System.out.println("2 - Удалить учётную запись");
                    System.out.println("3 - Добавить новый пост");
                    System.out.println("4 - Удалить пост");
                    System.out.println("5 - Завершить программу \n");
                    switch (Integer.parseInt(in.nextLine())) {
                        case 1 -> {
                            System.out.println("Введите логин администратора: ");
                            String newUsername = in.nextLine();
                            System.out.println("Введите пароль: ");
                            String newPassword = in.nextLine();
                            Admin newAdmin = new Admin(username, password);
                            authorizationService.addUser(connection, newAdmin);
                        }
                        case 2 -> {
                            System.out.println("Введите логин аккаунта, который хотите удалить: ");
                            String newUsername = in.nextLine();
                            authorizationService.removeUser(connection, newUsername);
                        }
                        case 3 -> {
                            System.out.println("Введите заголовок: ");
                            String title = in.nextLine();
                            System.out.println("Введите текст: ");
                            String text = in.nextLine();
                            Date date = Calendar.getInstance().getTime();
                            Post post = new Post(title, text, date);
                            postService.createPost(admin, post);
                        }
                        case 4 -> {
                            System.out.println("Введите логин, у которого вы хотите удалить пост: ");
                            String newUsername = in.nextLine();
                            System.out.println("Введите время создания поста: ");
                            Date date = formatter.parse(in.nextLine());
                            postService.deletePost(newUsername, date);
                        }
                        case 5 -> running = false;
                    }
                } else if (loginResult == Role.USER) {
                    User user = new User(username, password);
                    System.out.println("1 - Удалить свою учётную запись");
                    System.out.println("2 - Добавить новый пост");
                    System.out.println("3 - Удалить свой пост");
                    System.out.println("4 - Завершить программу \n");
                    switch (Integer.parseInt(in.nextLine())) {
                        case 1 -> {
                            authorizationService.removeUser(connection, user.username);
                        }
                        case 2 -> {
                            System.out.println("Введите заголовок: ");
                            String title = in.nextLine();
                            System.out.println("Введите текст: ");
                            String text = in.nextLine();
                            Date date = Calendar.getInstance().getTime();
                            Post post = new Post(title, text, date);
                            postService.createPost(user, post);
                        }
                        case 3 -> {
                            System.out.println("Введите время создания поста: ");
                            Date date = formatter.parse(in.nextLine());
                            postService.deletePost(user.username, date);
                        }
                        case 4 -> running = false;
                    }
                }
            }
        } else {
            System.err.println("Введены неверные данные!");
        }
    }
}
