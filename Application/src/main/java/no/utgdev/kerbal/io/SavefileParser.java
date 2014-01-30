/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.io;

import java.util.LinkedList;
import java.util.List;
import no.utgdev.kerbal.common.treemodel.MarshallingObjectFactory;
import no.utgdev.kerbal.common.treemodel.Property;
import no.utgdev.kerbal.common.treemodel.PropertyMap;
import no.utgdev.kerbal.common.treemodel.marshallingObjects.Savefile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nicklas
 */
public class SavefileParser {

    public static Logger logger = LoggerFactory.getLogger(SavefileParser.class);

    public enum ParserEvents {

        TAG, OPENINGBRACKET, CLOSINGBRACKET, KEYVALUE;
    }
    private String name;
    private LinkedList<String> content;
    private Savefile savefile;
    private String currentTag;
    private LinkedList<PropertyMap> currentPropertyMap;

    public SavefileParser(String name, List<String> content) {
        logger.trace("Initializing SavefileParser for {}", name);
        this.name = name;
        this.savefile = new Savefile(name);
        this.currentPropertyMap = new LinkedList<>();
        this.currentPropertyMap.push(savefile);
        logger.trace("Wrapping content in new datastructure to avoid messing up original");
        this.content = new LinkedList<>(content);
    }

    public Savefile parse() {
        logger.debug("Starting parsing of {}", this.name);
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
        logger.debug("Parsing of {} completed.", this.name);
        return savefile;
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
                this.currentPropertyMap.push(MarshallingObjectFactory.create(currentTag));
                currentTag = null;
                break;
            case CLOSINGBRACKET:
                PropertyMap pm = this.currentPropertyMap.pop();
                this.currentPropertyMap.peek().addChild(pm);
                logger.trace("{}: {}", this.currentPropertyMap.peek().getName(), pm.getName());
                break;
            case KEYVALUE:
                logger.trace("{}: {}->{}", this.currentPropertyMap.peek().getName(), strings[0], strings[1]);
                this.currentPropertyMap.peek().addChild(new Property(strings[0].trim(), strings[1].trim()));
                break;
            default:
                throw new IllegalArgumentException("Fuck");
        }
    }
}
