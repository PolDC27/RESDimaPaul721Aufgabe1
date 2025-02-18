
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class MyObject{

    public int Id;
    public String Charaktername;
    public String Stufe;
    public String Beschreibung;
    public LocalDate Datum;
    public double Kraftpunkte;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getKraftpunkte() {
        return Kraftpunkte;
    }

    public void setKraftpunkte(double kraftpunkte) {
        Kraftpunkte = kraftpunkte;
    }

    public String getCharaktername() {
        return Charaktername;
    }

    public void setCharaktername(String charaktername) {
        Charaktername = charaktername;
    }

    public String getStufe() {
        return Stufe;
    }

    public void setStufe(String stufe) {
        Stufe = stufe;
    }

    public String getBeschreibung() {
        return Beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        Beschreibung = beschreibung;
    }

    public LocalDate getDatum() {
        return Datum;
    }

    public void setDatum(LocalDate datum) {
        Datum = datum;
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "Id=" + Id +
                ", Mitgliedsname='" + Charaktername + '\'' +
                ", Haus='" + Stufe + '\'' +
                ", Ereignis='" + Beschreibung + '\'' +
                ", Datum='" + Datum + '\'' +
                '}';
    }
}

public class ReadCsv {
    public static void main(String[] args) {
        String filePath = "src/main/resources/ninja_events.tsv";

        // Read CSV and map to MyObject
        List<MyObject> objects = readCsvToObjects(filePath);

        List<String> ninjasAbove = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        double kraftpunkte = scanner.nextDouble();
        scanner.nextLine();
        for (MyObject obj : objects) {
            if (obj.Kraftpunkte > kraftpunkte && !ninjasAbove.contains(obj.Charaktername)) {
                ninjasAbove.add(obj.Charaktername);
            }
            System.out.println(obj);
        }

        for (String obj : ninjasAbove) {
            System.out.println(obj);
        }

    }

    /**
     * Reads from a CSV type file with a special delimiter and returns a list of myObject instances.
     * The method expects the file to have a header row that contains the names of the columns
     * @param filePath
     * @return
     */
    public static List<MyObject> readCsvToObjects(String filePath) {
        List<MyObject> objects = new ArrayList<>();

        try (FileReader reader = new FileReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter('\t').withFirstRecordAsHeader().parse(reader); // Adjust CSV format to Tab-Separated
            for (CSVRecord record : records) {
                MyObject obj = new MyObject();
                obj.setId(Integer.parseInt(record.get("Id")));
                obj.setCharaktername(record.get("Charaktername"));
                obj.setStufe(record.get("Stufe"));
                obj.setBeschreibung(record.get("Beschreibung"));
                obj.setDatum(LocalDate.parse(record.get("Datum")));
                obj.setKraftpunkte(Double.parseDouble(record.get("Kraftpunkte")));
                objects.add(obj);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }
}
