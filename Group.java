import java.util.List;

public class Group {
    private String groupCode; // Sequential group code
    private List<String> favoriteMovies; // List of favorite movies in the group

    // Constructor
    public Group(String groupCode, List<String> favoriteMovies) {
        this.groupCode = groupCode;
        this.favoriteMovies = favoriteMovies;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public List<String> getFavoriteMovies() {
        return favoriteMovies;
    }

    @Override
    public String toString() {
        return "Group code: " + groupCode + ", Favorite movies: " + favoriteMovies;
    }
}
