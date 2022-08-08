package loader;

public class MenuLoader {
    public String[] parseStringArray(String line) {
        String[] strings = line.split(",");
        return strings;
    }
}
