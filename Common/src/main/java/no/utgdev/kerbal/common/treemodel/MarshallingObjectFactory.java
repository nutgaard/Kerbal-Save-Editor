/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.treemodel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nicklas
 */
public class MarshallingObjectFactory {

    public static final Logger logger = LoggerFactory.getLogger(MarshallingObjectFactory.class);

    public static PropertyMap create(String name) {
        logger.trace("Factory trying to create marshalling object from {}.",name);
        try {
            String packagePrefix = MarshallingObjectFactory.class.getPackage().getName();
            Class<PropertyMap> pmClass = (Class<PropertyMap>) Class.forName(packagePrefix + ".marshallingObjects." + capitalize(name));
            logger.trace("Factory found class: {}.", pmClass);
            return pmClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            logger.trace("An error accured when finding or instantating class for {}. This is to be expected as not all PropertyMaps have MarshallingObjects.", name);
        }
        logger.trace("Did not find MarshallingClass, returning default PropertyMap.");
        return new PropertyMap(name);
    }
    private static String capitalize(String s){
        return s.substring(0, 1).toUpperCase()+s.substring(1).toLowerCase();
    }
    
    public static void main(String[] args) {
        String[] ss = new String[]{
            "Game", "GAME", "GaMe", //Return Game
            "Scenario", "SCENARIO", //Return Scenario
            "FirstLaunch",          //Return PropertyMap
            "Vessel", "VESSEL",     //Return Vessel
            "SPACECENTER", "SpaceCenter",
            "TrackingStation", "Trackingstation",
            "FlightState", "FLIGHTSTATE"
        };
        for (String s : ss){
            PropertyMap pm = MarshallingObjectFactory.create(s);
            logger.info("Search: {}. Result: {}.", s, pm.getClass().getSimpleName());
        }
    }
}
