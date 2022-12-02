package vn.edu.poly.andromeda.model;

public class CommentModel {
    String Username;
    String Comment;

    public CommentModel() {
    }

    public CommentModel(String username, String comment) {
        Username = username;
        Comment = comment;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
