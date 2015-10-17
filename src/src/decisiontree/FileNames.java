package decisiontree;// Created by mrtyormaa on 10/1/15.

import java.io.File;
import java.util.ArrayList;

public class FileNames {
    private ArrayList<File> fileNames = new ArrayList<>();
    public ArrayList<File> getAllFileNames(String path, String filter){
        listAllFiles(path, filter);
        return fileNames;
    }

    //    Returns list of all csv files found in the directory
    private void listAllFiles(String inputPath, String filter) {
        File directory = new File(inputPath);
        //get all the files from a directory
        File[] fList = directory.listFiles();
        assert fList != null;
        for (File file : fList) {
            if (file.isFile()) {
                String[] tokens = file.toString().split("\\.(?=[^\\.]+$)");
                if (tokens[1].equalsIgnoreCase("csv")) {
                    if ((tokens[0].toLowerCase().contains(filter)))
                        this.fileNames.add(file);
                    //System.out.println(file.getAbsolutePath());
                }
            } else if (file.isDirectory()) {
                listAllFiles(file.getAbsolutePath(), filter);
            }
        }
    }
}
