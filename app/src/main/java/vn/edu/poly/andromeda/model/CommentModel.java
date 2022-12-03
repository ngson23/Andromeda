package vn.edu.poly.andromeda.model;

public class CommentModel {
    String Username;
    String Comment;
    String Url;
    String Child;
    public CommentModel() {
    }

    public CommentModel(String username, String comment, String url) {
        Username = username;
        Comment = comment;
        Url = url;
    }

    public CommentModel(String username, String comment, String url, String child) {
        Username = username;
        Comment = comment;
        Url = url;
        Child = child;
    }

    public String getChild() {
        return Child;
    }

    public void setChild(String child) {
        Child = child;
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
