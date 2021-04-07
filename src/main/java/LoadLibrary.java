import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoadLibrary {
  private static final String DELIMITER = ",";

  public static List<Library> get() throws IOException {
    List<Library> libraries = new ArrayList<>();

    try (BufferedReader br = Files.newBufferedReader(Paths.get("libraries.csv"))) {
      String line;
      boolean headerLoaded = false;

      while ((line = br.readLine()) != null) {
        if (headerLoaded) {
          String[] columns = line.split(DELIMITER);
          String desc = columns[0];
          Double lon = new Double(columns[1]);
          Double lat = new Double(columns[2]);
          String code = columns[3];

          Library lib = new Library(desc, new BigDecimal(lon), new BigDecimal(lat), code);
          libraries.add(lib);
        } else {
          headerLoaded = true;
        }
      }
    }

    return libraries;
  }
}
