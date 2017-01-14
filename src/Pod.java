import java.util.ArrayList;

/**
 * Created by Matthew on 1/13/2017.
 */
public class Pod {
    private String title, body;
    public Pod(String title, String body) {
        this.title = title;
        this.body = body;
    }
    public String toString() {
        return "*" + title + "*\n" + body;
    }
    boolean isInput() {
        return title.startsWith("Input");
    }
    public String getTitle() {
        return title;
    }
}
