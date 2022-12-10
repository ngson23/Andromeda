package vn.edu.poly.andromeda.model;

public class FavoriteModel {
    String Favcast;
    String Favcover;
    String Favdes;
    String Favlink;
    String Favthumb;
    String Favtitle;
    String Favtlink;
    String UserId;
    String Favtime;

    public FavoriteModel() {
    }

    public FavoriteModel(String favcast, String favcover, String favdes, String favlink, String favthumb, String favtitle, String favtlink, String favtime) {
        Favcast = favcast;
        Favcover = favcover;
        Favdes = favdes;
        Favlink = favlink;
        Favthumb = favthumb;
        Favtitle = favtitle;
        Favtlink = favtlink;
        Favtime = favtime;
    }

    public String getFavcast() {
        return Favcast;
    }

    public void setFavcast(String favcast) {
        Favcast = favcast;
    }

    public String getFavcover() {
        return Favcover;
    }

    public void setFavcover(String favcover) {
        Favcover = favcover;
    }

    public String getFavdes() {
        return Favdes;
    }

    public void setFavdes(String favdes) {
        Favdes = favdes;
    }

    public String getFavlink() {
        return Favlink;
    }

    public void setFavlink(String favlink) {
        Favlink = favlink;
    }

    public String getFavthumb() {
        return Favthumb;
    }

    public void setFavthumb(String favthumb) {
        Favthumb = favthumb;
    }

    public String getFavtitle() {
        return Favtitle;
    }

    public void setFavtitle(String favtitle) {
        Favtitle = favtitle;
    }

    public String getFavtlink() {
        return Favtlink;
    }

    public void setFavtlink(String favtlink) {
        Favtlink = favtlink;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getFavtime() {
        return Favtime;
    }

    public void setFavtime(String favtime) {
        Favtime = favtime;
    }
}
