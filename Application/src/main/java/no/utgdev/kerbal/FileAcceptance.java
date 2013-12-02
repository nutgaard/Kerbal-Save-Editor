/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class FileAcceptance {

    public static void verify(File file1, File file2) {
        try (BufferedReader br1 = new BufferedReader(new FileReader(file1)); BufferedReader br2 = new BufferedReader(new FileReader(file2))) {
            String line1 = null, line2 = null;
            boolean cont = true;
            int lineno = 1;
            
            while (cont) {
                line1 = br1.readLine();
                line2 = br2.readLine();
                
                //not same length
                if (line1 == null && line2 != null) {
                    System.out.println("Error at lineNO: "+lineno);
                    cont = false;
                }else if (line1 != null && line2 == null){
                    System.out.println("Error at lineNO: "+lineno);
                    cont = false;
                }else if (line1 == null && line2 == null) {
                    System.out.println("Reached EOF: "+lineno);
                    cont = false;
                }else if (!line1.equals(line2)) {
                    System.out.println("Discrepancy at line: "+lineno);
                    System.out.println("File1: "+line1);
                    System.out.println("    Len: "+line1.length());
                    System.out.println("File2: "+line2);
                    System.out.println("    Len: "+line2.length());
                    cont = false;
                }
                lineno++;
            }
        } catch (Exception ex) {
            Logger.getLogger(FileAcceptance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
