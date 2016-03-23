package addressbook.tests.utils;

public class TestFuncs {

    public static String cleanSpaces(String str) {
        return str.replaceAll("^\\s*", "").replaceAll("\\s*$", "").replaceAll("\\s+", " ");
    }

    public static String mergeSpaces(String str) {
        return str.replaceAll("\\s+", " ");
    }
}
