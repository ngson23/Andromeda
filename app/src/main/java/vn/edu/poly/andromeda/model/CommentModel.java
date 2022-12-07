package vn.edu.poly.andromeda.model;

public class CommentModel {
    String Username;
    String Comment;
    String Url;
    String Child;
    String Id;
    String Time;
    public CommentModel() {
    }

    public CommentModel(String username, String comment, String url) {
        Username = username;
        Comment = comment;
        Url = url;
    }

    public CommentModel(String username, String comment, String url, String id) {
        Username = username;
        Comment = comment;
        Url = url;
        Id = id;
    }

    public CommentModel(String username, String comment, String url, String id, String time) {
        Username = username;
        Comment = comment;
        Url = url;
        Id = id;
        Time = time;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
