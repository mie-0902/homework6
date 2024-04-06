package Model;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditorModel {
    public void saveToFile(File file, String content) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            writer.newLine();

        }
    }

    public String loadFromFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public List<File> listFilesRecursive(File folder) {
        List<File> fileList = new ArrayList<>();
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    fileList.add(file);
                    fileList.addAll(listFilesRecursive(file));
                }
            }
        }
        return fileList;
    }
}
