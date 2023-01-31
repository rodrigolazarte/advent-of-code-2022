package day07;

import java.util.ArrayList;
import java.util.List;

public class Folder {

    private String name;
    private List<Folder> folders;
    private List<File> files;

    public Folder(String name) {
        this.name = name;
        folders = new ArrayList<>();
        files = new ArrayList<>();
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFolderSizeBelowHundredThousand() {
        int totalFilesSize = 0;
        for (File file : files) {
            totalFilesSize += file.size();
        }
        //if (totalSize <= 100000)
//            System.out.println("Folder with name " + name + " has a size of " + totalSize);
        if (folders.isEmpty()) {
            if (totalFilesSize <= 100000)
                System.out.println("Total del peso de archivos de la carpeta: " + name + ": " + totalFilesSize);
        } else {
            for (Folder folder : folders) {
                totalFilesSize += folder.getFolderSizeBelowHundredThousand();
            }
            if (totalFilesSize <= 100000)
                System.out.println("Total del peso de archivos de la carpeta: " + name + ": " + totalFilesSize);
        }
        return totalFilesSize;
    }

    public int getFolderSize() {
        int totalFilesSize = 0;
        for (File file : files) {
            totalFilesSize += file.size();
        }

        if (!folders.isEmpty()){
            for (Folder folder : folders) {
                totalFilesSize += folder.getFolderSize();
            }
        }

        //System.out.println("Total del peso de archivos de la carpeta: " + name + ": " + totalFilesSize);

        return totalFilesSize;
    }
}
