/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.utgdev.kerbal.common.utils;

/**
 *
 * @author Nicklas
 */
public class OutputHelper {

    public static String createIndentation(int num) {
        if (num == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num; i++) {
            sb.append("\t");
        }
        return sb.toString();
    }
}
