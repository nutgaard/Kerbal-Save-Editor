/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nicklas
 */
public class SavefileReader {
    public static Logger logger = LoggerFactory.getLogger(SavefileReader.class);

    public static LinkedList<String> read(File file) {
        logger.debug("Reading raw data from {}", file);
        LinkedList<String> list = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception ex) {
            logger.error("An error accured when reading raw data.", ex);
        }
        logger.debug("Reading completed, found a total of {} lines in file.", list.size());
        return list;
    }
}
