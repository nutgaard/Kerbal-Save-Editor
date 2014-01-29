/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Nicklas
 */
public class FileAcceptance {

    public static Logger logger = LoggerFactory.getLogger(FileAcceptance.class);

    public static void verify(File file1, File file2) {
        logger.debug("Starting verification process between {} and {}", file1, file2);
        int errors = 0;
        try (BufferedReader br1 = new BufferedReader(new FileReader(file1)); BufferedReader br2 = new BufferedReader(new FileReader(file2))) {
            String line1 = null, line2 = null;
            boolean cont = true;
            int lineno = 1;
            
            while (cont) {
                line1 = br1.readLine();
                line2 = br2.readLine();

                //not same length
                if (line1 == null && line2 != null) {
                    logger.warn("Error at lineNO: {}", lineno);
                    cont = false;
                    errors++;
                } else if (line1 != null && line2 == null) {
                    logger.warn("Error at lineNO: {}", lineno);
                    cont = false;
                    errors++;
                } else if (line1 == null && line2 == null) {
                    logger.warn("Reached EOF: {}", lineno);
                    cont = false;
                    errors++;
                } else if (!line1.equals(line2)) {
                    logger.warn("Discrepancy at line: {}", lineno);
                    logger.warn("File1: {}", line1);
                    logger.warn("    Len: {}", line1.length());
                    logger.warn("File2: {}", line2);
                    logger.warn("    Len: {}", line2.length());
                    errors++;
                    cont = false;
                }
                lineno++;
            }
        } catch (Exception ex) {
            logger.error("An error accured while validating files.", ex);
        }
        logger.debug("Verification prosess completed with {} errors.", errors);
    }
}
