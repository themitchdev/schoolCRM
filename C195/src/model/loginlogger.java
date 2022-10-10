package model;

import java.io.FileWriter;
import java.io.IOException;

public abstract class loginlogger{

    public static void log(String lineToAppend) throws IOException {
        String filePath = "..\\schoolCRM\\login_activity.txt";
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(lineToAppend);
        fw.close();
    }

}
