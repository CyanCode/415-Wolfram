package com.walkerchristie.content;
import org.jsoup.nodes.Element;

import java.util.ArrayList;

public class AssumptionParser {
    private String statement;
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<String> urls = new ArrayList<>();
    
    public AssumptionParser(Element assumptionElement) {
        for (Element value : assumptionElement.children()) {
            names.add(value.attr("desc"));
            urls.add(value.attr("input"));
        }
        
        statement = assumptionElement.attr("template");
        statement = statement.substring(0, statement.indexOf('.') + 1);
        String word = assumptionElement.attr("word");
        statement = statement.replace("${word}", word);
        statement = statement.replace("${desc1}", names.get(0));
    }

    public String[][] getAssumptionOptions() {
        String[][] assumptions = new String[2][names.size()];
        for (int i = 0; i < names.size(); i++) {
            assumptions[0][i] = names.get(i);
            assumptions[1][i] = urls.get(i);
        }
        return assumptions;
    }
    
    public String getStatement() {
        return statement;
    }
}
