import ch.qos.logback.classic.Logger;
import com.sun.tools.javac.Main;
import in.InputReader;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        InputReader instance = InputReader.getInstance();
        instance.readLines();
    }

}
