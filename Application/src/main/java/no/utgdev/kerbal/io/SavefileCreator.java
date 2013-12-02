/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.utgdev.kerbal.common.treemodel.IProperty;
import no.utgdev.kerbal.common.treemodel.PropertyMap;

/**
 *
 * @author Nicklas
 */
public class SavefileCreator {

    public static void write(File file, PropertyMap root) {
        String filecontent = createContent(root);
        try (FileWriter fw = new FileWriter(file)) {
            fw.write(filecontent);
        } catch (IOException ex) {
            Logger.getLogger(SavefileCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static String createContent(PropertyMap root) {
        StringBuilder sb = new StringBuilder();

        for (IProperty property : root.getChildren()) {
            sb.append(property.print(0));
        }

        return sb.toString();
    }

}
