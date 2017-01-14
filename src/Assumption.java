import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class Assumption {
    private String description;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> urls = new ArrayList<>();
    public Assumption(Element assumptionElement) {
        for (Element value : assumptionElement.children()) {
            names.add(value.attr("desc"));
            urls.add(value.attr("input"));
        }
        description = assumptionElement.attr("template");
        description = description.substring(0, description.indexOf('.') + 1);
        CharSequence word = Pattern.quote("word");
        description = description.replace(word, "test");
    }

    public String getURL(int numOfAssumption) {
        return getURL(numOfAssumption);
    }
    public String toString() {
        return description;
    }
}
