import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LoadLibrary {
  private static final String DELIMITER = ",";

  public static List<Library> get() throws IOException {
    List<Library> libraries = new ArrayList<>();

    InputStream is = LoadLibrary.class.getResourceAsStream("libraries.csv");
    InputStreamReader isr = new InputStreamReader(is);

    try (BufferedReader br = new BufferedReader(isr)) {
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
