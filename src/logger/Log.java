package logger;

import java.sql.SQLOutput;

public class Log {
    private static boolean debug = true;

    public static void log(String message){
        System.out.println(message);
    }
    public static void log(String message, MessageType type){
        switch (type){
            case LOG -> log(message);
            case INFO -> log("INFO::"+message);
            case WARNING -> log("WARNING::"+message);
            case DEBUG -> {if(debug){log("DEBUG::"+message);}}
            case ERROR -> System.err.println("EROOR::"+message);
        }
    }
    public static void setDebug(boolean debug) {
        Log.debug = debug;
    }
    public static void activateDebug() {
        Log.debug = true;
    }
    public static void deactivateDebug() {
        Log.debug = false;
    }
}
