import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LibraryUtil {
  public static List<Library> nearFrom(List<Library> libraries, BigDecimal lon, BigDecimal lat, float distanceKms) {
    List<DistanceToLibrary> librariesNearby = new ArrayList<>();

    libraries.parallelStream().forEach(library -> {
      double distMeters = distFrom(library.getLat().doubleValue(), library.getLon().doubleValue(), lat.doubleValue(), lon.doubleValue());
      if (distMeters <= distanceKms*1000) {
        librariesNearby.add(new DistanceToLibrary(library, distMeters));
      }
    });

    librariesNearby.sort((o1, o2) -> (int) (o1.getDistMeters() - o2.getDistMeters()));

    return librariesNearby.stream().map(distanceToLibrary -> distanceToLibrary.getLibrary()).collect(Collectors.toList());
  }

  // https://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
  private static double distFrom(double lat1, double lng1, double lat2, double lng2) {
    double earthRadius = 6371000; //meters
    double dLat = Math.toRadians(lat2-lat1);
    double dLng = Math.toRadians(lng2-lng1);
    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
        Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
            Math.sin(dLng/2) * Math.sin(dLng/2);
    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
    double dist = earthRadius * c;

    return dist;
  }
}
