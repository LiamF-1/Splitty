package client;


import java.io.*;
import java.util.List;

/**
 * Interacts with the config file and stores the settings
 * Follows singleton pattern
 */
public class ConfigParser {

    private static ConfigParser parser;
    private final String url;

    /**
     * The constructor is private so multiple instances can't be created
     */
    private ConfigParser(String url) {
        this.url = url;
    }


    /**
     * Creates
     *
     * @return the config parser singleton instance
     */
    public static ConfigParser createInstance() throws IOException {
        if(parser == null) {
            List<String> settings = readConfig();
            parser = new ConfigParser(settings.getFirst());
        }
        return parser;
    }


    /**
     * Reads the settings of the config and returns them in a list
     *
     * @return the list of settings
     * @throws IOException if config file can not be accessed
     */
    private static List<String> readConfig() throws IOException {
//        File file = new File(Objects.requireNonNull(ConfigParser.class.getClassLoader()
//                .getResource("client/config.properties")).getPath());
//
//        // try with resources auto-closes the resource
//        try(FileReader fileReader = new FileReader(file)) {
//            BufferedReader reader = new BufferedReader(fileReader);
//            return reader.lines().toList();
//        }
        // Just as test
        return List.of("http://localhost:8080/");
    }

    /**
     * Returns the server URL from the config
     *
     * @return the server URL
     */
    public String getUrl() {
        return url;
    }
}
