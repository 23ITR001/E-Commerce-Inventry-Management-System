package admin;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
public class ThresholdAlert {
    public static String check(ArrayList<String[]> inventory, int threshold) {
        StringBuilder alertMessage = new StringBuilder();
        for (String[] product : inventory) {
            int quantity = Integer.parseInt(product[2]);
            if (quantity < threshold) {
                alertMessage.append(product[0]).append(" (Quantity: ").append(quantity).append(")\n");
            }
        }
        return alertMessage.length() > 0 ? "Threshold Alert: The following products have less than " + threshold + " in stock:\n" + alertMessage.toString() : "No products below threshold.";
    }
}
