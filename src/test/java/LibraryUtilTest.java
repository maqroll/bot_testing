import com.opencsv.exceptions.CsvValidationException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LibraryUtilTest {
  private static List<Library> libraries;

  @BeforeAll
  static void load() throws IOException, CsvValidationException {
    libraries = LoadLibrary.get();
  }

  @Test
  void distanceTest() {
    List<Library> libs = LibraryUtil.nearFrom(this.libraries, new BigDecimal("-8.3548675"), new BigDecimal("43.3043319"), 3.0f);
    assertThat(libs.size()).as("check libraries size").isEqualTo(6);

    assertThat(libs.get(0).getCode()).as("check library at 0 position").isEqualTo("1500113-129"); // branch:CBM018
    assertThat(libs.get(1).getCode()).as("check library at 1 position").isEqualTo("1500113-178"); // branch:CBM020
    assertThat(libs.get(2).getCode()).as("check library at 2 position").isEqualTo("1500113-143"); // branch:CBM019
    assertThat(libs.get(3).getCode()).as("check library at 3 position").isEqualTo("1501072-347"); // branch:OLE6
    assertThat(libs.get(4).getCode()).as("check library at 4 position").isEqualTo("1500648-161"); // branch:CBM040
    assertThat(libs.get(5).getCode()).as("check library at 5 position").isEqualTo("1500648-119"); // branch:CAM017
  }
}
