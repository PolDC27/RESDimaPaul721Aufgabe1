
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


import java.io.FileReader;
import java.io.FileWriter;
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
        List<MyObject> erreignisseJonin = new ArrayList<>();
        List<String> stuffen = new ArrayList<>();
        List<Integer> anzahlEreignisse = new ArrayList<>();
        List<Double> totalPunkte = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        double kraftpunkte = scanner.nextDouble();
        scanner.nextLine();

        for (MyObject obj : objects) {
            if (obj.Kraftpunkte > kraftpunkte && !ninjasAbove.contains(obj.Charaktername)) {
                ninjasAbove.add(obj.Charaktername);
            }
            if (obj.Stufe.equals("Jonin")) {
                erreignisseJonin.add(obj);
            }

            if (!stuffen.contains(obj.Stufe)) {
                stuffen.add(obj.Stufe);
                anzahlEreignisse.add(1);
                totalPunkte.add(obj.Kraftpunkte);
            }
            else{
                for (int i = 0; i < stuffen.size(); i++) {
                    if (stuffen.get(i).equals(obj.Stufe)) {
                        anzahlEreignisse.set(i, anzahlEreignisse.get(i)+1);
                        totalPunkte.set(i, totalPunkte.get(i)+obj.Kraftpunkte);
                    }
                }
            }


            System.out.println(obj);
        }

        for (String obj : ninjasAbove) {
            System.out.println(obj);
        }

        for (int i = 0; i < erreignisseJonin.size() - 1; i++) {
            for (int j = i + 1; j < erreignisseJonin.size(); j++) {
                if (erreignisseJonin.get(i).getDatum().isAfter(erreignisseJonin.get(j).getDatum())){
                    MyObject temp = erreignisseJonin.get(i);
                    erreignisseJonin.set(i, erreignisseJonin.get(j));
                    erreignisseJonin.set(j, temp);
                }
            }
        }
        for (MyObject obj : erreignisseJonin) {
            System.out.println(obj.getDatum() + " : " +obj.Charaktername + " - " + obj.Beschreibung);
        }

        for (int i = 0; i < stuffen.size() - 1; i++) {
            for (int j = i + 1; j < stuffen.size(); j++) {
                if(anzahlEreignisse.get(i) < anzahlEreignisse.get(j)){
                    String temp = stuffen.get(i);
                    stuffen.set(i, stuffen.get(j));
                    stuffen.set(j, temp);
                    int temp1 = anzahlEreignisse.get(i);
                    anzahlEreignisse.set(i, anzahlEreignisse.get(j));
                    anzahlEreignisse.set(j, temp1);
                }
            }
        }

        String file = "gesamtzahl.txt";


        try (FileWriter fileWriter = new FileWriter(file, true)) {
            for (int i = 0; i < anzahlEreignisse.size(); i++) {
                fileWriter.write(stuffen.get(i) + "%" + anzahlEreignisse.get(i) + "#" + totalPunkte.get(i) + System.lineSeparator());
            }

        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
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
