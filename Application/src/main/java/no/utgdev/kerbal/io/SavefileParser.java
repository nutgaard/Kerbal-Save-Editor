/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.io;

import java.util.LinkedList;
import java.util.List;
import no.utgdev.kerbal.common.treemodel.Property;
import no.utgdev.kerbal.common.treemodel.PropertyMap;

/**
 *
 * @author Nicklas
 */
public class SavefileParser {

    public enum ParserEvents {

        TAG, OPENINGBRACKET, CLOSINGBRACKET, KEYVALUE;
    }
    private LinkedList<String> content;
    private PropertyMap root;
    private String currentTag;
    private LinkedList<PropertyMap> currentPropertyMap;

    public SavefileParser(String name, List<String> content) {
        this.root = new PropertyMap(name);
        this.currentPropertyMap = new LinkedList<>();
        this.currentPropertyMap.push(root);
        this.content = new LinkedList<>(content);
    }

    public PropertyMap parse() {
        String current = null;

        for (int i = 0, l = content.size(); i < l; i++) {
            current = content.poll().trim();

            if (content.peek() != null && content.peek().trim().equals("{")) {
                process(ParserEvents.TAG, current);
            } else if (current.equals("{")) {
                process(ParserEvents.OPENINGBRACKET, current);
            } else if (current.equals("}")) {
                process(ParserEvents.CLOSINGBRACKET, current);
            } else {
                String[] keyvalue = current.split("=");
                String[] pkv = new String[2];
                pkv[0] = (keyvalue.length >= 1 && keyvalue[0] != null) ? keyvalue[0] : "";
                pkv[1] = (keyvalue.length == 2 && keyvalue[1] != null) ? keyvalue[1] : "";
                process(ParserEvents.KEYVALUE, pkv[0], pkv[1]);
            }
        }
        return root;
    }

    private void process(ParserEvents event, String... strings) {
        switch (event) {
            case TAG:
                if (strings.length == 0 || strings[0] == null) {
                    throw new IllegalArgumentException("Tag string null");
                }
                this.currentTag = strings[0].trim();
                break;
            case OPENINGBRACKET:
                this.currentPropertyMap.push(new PropertyMap(currentTag));
                currentTag = null;
                break;
            case CLOSINGBRACKET:
                PropertyMap pm = this.currentPropertyMap.pop();
                this.currentPropertyMap.peek().addChild(pm);
                output(this.currentPropertyMap.peek().getName() + ": " + pm.getName());
                break;
            case KEYVALUE:
                output(this.currentPropertyMap.peek().getName() + ": " + strings[0] + "->" + strings[1]);
                this.currentPropertyMap.peek().addChild(new Property(strings[0].trim(), strings[1].trim()));
                break;
            default:
                throw new IllegalArgumentException("Fuck");
        }
    }
    private static void output(String s) {
        if (false) {
            System.out.println(s);
        }
    }
}
