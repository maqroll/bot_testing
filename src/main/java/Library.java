import java.math.BigDecimal;

public class Library {
  private final String name;
  private final BigDecimal lon;
  private final BigDecimal lat;
  private final String code;

  public Library(String name, BigDecimal lon, BigDecimal lat, String code) {
    this.name = name;
    this.lon = lon;
    this.lat = lat;
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public BigDecimal getLon() {
    return lon;
  }

  public BigDecimal getLat() {
    return lat;
  }

  public String getCode() {
    return code;
  }
}
