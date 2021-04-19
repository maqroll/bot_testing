import com.opencsv.CSVIterator;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LoadLibrary {
  private static Logger LOGGER = LoggerFactory.getLogger(LoadLibrary.class);

  private static final String DELIMITER = ";";

  public static List<Library> get() throws IOException, CsvValidationException {
    List<Library> libraries = new ArrayList<>();

    InputStream is = LoadLibrary.class.getResourceAsStream("DirectorioBibliotecasPublicasGalicia.csv");
    InputStreamReader isr = new InputStreamReader(is);

    final CSVParser parser = new CSVParserBuilder().withSeparator(';').withQuoteChar('"').build();
    final CSVReader reader = new CSVReaderBuilder(isr).withSkipLines(1).withCSVParser(parser).build();

    CSVIterator iterator = new CSVIterator(reader);

    for (CSVIterator it = iterator; it.hasNext(); ) {
      String[] nextLine = it.next();

        String acceso = nextLine[19]; // acceso
        String estado = nextLine[20]; // estado

        if ("Público".equals(acceso) && "Aberta".equals(estado)) {
          String desc = nextLine[2];
          String code = nextLine[13]; // id
          String[] coordinates = nextLine[9].split(","); // coordenadas
          if (coordinates.length == 2) {
            Double lat = new Double(coordinates[0]);
            Double lon = new Double(coordinates[1]);

            Library lib = new Library(desc, new BigDecimal(lon), new BigDecimal(lat), code);
            libraries.add(lib);
          } else {
            LOGGER.warn("Missing coordinates for {}. Skipping!!",desc);
          }
        }
    }

    return libraries;
  }
}
