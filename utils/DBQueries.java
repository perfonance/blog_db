package utils;

public class DBQueries {
    public static String login() {
        return "select ur.role from users_roles ur where id = (select u.id from users u where u.login = ? and u.password = ?)";
    }

    public static String addUser() {
        return "insert into users (username, password) values (?, ?); insert into user_roles (role) values (?)";
    }

    public static String removeUser() {
        return "delete from users_roles ur where ur.id = (select u.id from users u where u.username = ?); delete from users u where u.username = ?";
    }

    public static String createPost() {
        return "insert into posts (user_id, title, text, date) values ((select u.id from users u where u.username = ?), ?, ?, to_date(?, 'DD.MM.YYYY 24h:mm'))";
    }

    public static String deletePost() {
        return "delete from posts p where p.user_id = (select u.id from users u where u.username = ?), date = to_date(?, 'DD.MM.YYYY 24h:mm')";
    }
}
