import java.util.*;
import java.io.*;

public class Review {
    private String username;
    private String text;

    public Review(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return username + " says '" + text + "'\n";
    }
}
