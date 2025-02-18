import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String filePath = "output.txt";
        String text = "Paul";

        try (FileWriter fileWriter = new FileWriter(filePath, true)) {
            fileWriter.write(text + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
        }
}