package Helpers;

public class Logger {
    public Logger() {

    }

    public void log(String title) {
        System.out.println("--- " + title);
        System.out.println("");
    }
    
    public void log(String title, String message) {
        System.out.println("--- " + title);
        System.out.println(message);
        System.out.println("");
    }

    public void warn(String title) {
        System.out.println("!!! " + title);
        System.out.println("");
    }

    public void warn(String title, String message) {
        System.out.println("!!! " + title);
        System.out.println(message);
        System.out.println("");
    }
}
