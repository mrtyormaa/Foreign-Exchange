import data.prep.ProcessData;

import java.io.File;
import java.util.ArrayList;

public class Main {

    //update the offset, duration and directory location as per requirement.
    public static void main(String[] args) {
        int offset = 1;
        String duration = "m";
        final String testDir = "/Users/mrtyormaa/Downloads/test";

        ProcessData pd = new ProcessData(testDir, offset, duration);
        ArrayList<File> files;
        files = pd.getFileNames();
        for (File f : files) pd.readAndProcessFile(f);
        System.out.println(files);
    }
}