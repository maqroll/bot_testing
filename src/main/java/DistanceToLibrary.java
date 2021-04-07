public class DistanceToLibrary {
  private final Library library;
  private final double distMeters;

  public DistanceToLibrary(Library library, double distMeters) {
    this.library = library;
    this.distMeters = distMeters;
  }

  public Library getLibrary() {
    return library;
  }

  public double getDistMeters() {
    return distMeters;
  }
}
