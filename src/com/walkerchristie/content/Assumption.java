package com.walkerchristie.content;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

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
        
        String word = assumptionElement.attr("word");
        description = description.replace("${word}", word);
        description = description.replace("${desc1}", names.get(0));
    }

    public String getURL(int numOfAssumption) {
        return getURL(numOfAssumption);
    }
    
    public String toString() {
        return description;
    }
}
