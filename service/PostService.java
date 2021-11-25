package service;

import model.Person;
import model.Post;
import utils.DBQueries;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PostService {
    private final Connection connection;

    public PostService(Connection connection) {
        this.connection = connection;
    }
    public void createPost(Person person, Post post) throws SQLException {
        PreparedStatement addPost = connection.prepareStatement(DBQueries.createPost());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 24h:mm");
        addPost.setString(1, person.username);
        addPost.setString(2, post.title);
        addPost.setString(3, post.text);
        addPost.setString(4, formatter.format(post.date));
        addPost.executeQuery();
    }

    public void deletePost(String username, Date date) throws SQLException { // ???????????
        PreparedStatement deletePost = connection.prepareStatement(DBQueries.deletePost());
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy 24h:mm");
        deletePost.setString(1, username);
        deletePost.setString(2, formatter.format(date));
        deletePost.executeUpdate();
    }
}
