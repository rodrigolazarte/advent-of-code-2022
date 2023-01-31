package day07;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SizeCalculator {

    static Folder rootFolder = new Folder("/");
    static int inputDataSize;
    static Pattern pattern = Pattern.compile("^\\d+ \\D+");
    static int totalSize = 0;

    final static int neededFreeSpace = 30_000_000;
    final static int totalDiskSpace = 70_000_000;

    public static void main(String[] args) throws FileNotFoundException {
        List<String> inputData = readInputData("src/day07/input.txt");
        inputDataSize = inputData.size();
        setFolderStructure(inputData, rootFolder, 1);
        printFolderSize(rootFolder);
        System.out.println("The total Size is: " + totalSize);

        getFoldersWithEnoughSpace(rootFolder);
    }

    private static List<String> readInputData(String inputUrl) throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader(inputUrl)).useDelimiter("\n");
        List<String> inputData = new ArrayList<>();

        while (scanner.hasNext()) {
            inputData.add(scanner.next().trim());
        }

        return inputData;
    }

    private static void setFolderStructure(List<String> inputData, Folder folder, int dataIndex) {

        for (int i = dataIndex; i < inputDataSize; i++) {
            String data = inputData.get(i);
            Matcher matcher = pattern.matcher(data);

            if (data.contains("dir ")) {
                String newFolderName = data.replace("dir ", "");
                var newFolder = new Folder(newFolderName);
                folder.getFolders().add(newFolder);
                int indexCdFolder = inputData.indexOf("$ cd " + newFolderName);
                inputData.set(i, "");
                if (indexCdFolder > 1){
                    inputData.set(indexCdFolder, "");
                    setFolderStructure(inputData, newFolder, indexCdFolder + 1);
                }
                continue;
            }

            if (matcher.matches()) {
                int fileSize = Integer.parseInt(data.replaceAll("\\D", ""));
                folder.getFiles().add(new File(fileSize));
                inputData.set(i, "");
            }

            if(data.contains("$ cd ..")) {
                inputData.set(i, "");
                break;
            }
        }
    }

    private static void printFolderSize(Folder folder) {
        totalSize = folder.getFolderSizeBelowHundredThousand();
    }

    private static void getFoldersWithEnoughSpace(Folder folder){
        int availableSpace = totalDiskSpace - totalSize;
        int spaceToBeFree = neededFreeSpace - availableSpace;

        List<Folder> folders = folder.getFolders();

        if (availableSpace < neededFreeSpace) {
            for (Folder f: folders) {
                if (!f.getFolders().isEmpty()){
                    getFoldersWithEnoughSpace(f);
                }
                if (f.getFolderSize() >= spaceToBeFree) {
                    System.out.println("Folder with name: " + f.getName() +
                            " can be deleted with space of: " + f.getFolderSize());
                }
            }
        }
    }
}
