package Ex2;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        if (args.length > 0) {
            Ex2.runGUI(args[0]);
        } else {
            System.out.println("Wrong Format");
            System.out.println("Enter 'java -jar <name of jar file> <src of json file>'");
        }
    }
}
