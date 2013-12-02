/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nicklas
 */
public class SavefileReader {

    public static LinkedList<String> read(File file) {
        LinkedList<String> list = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = null;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (Exception ex) {
            Logger.getLogger(SavefileReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
    public static String readString(File file) {
        List<String> data = read(file);
        StringBuilder sb = new StringBuilder();
        for (String s : data){
            sb.append(s).append("\n");
        }
        System.out.println("Length: "+data.size());
        return sb.toString();
    }
}
