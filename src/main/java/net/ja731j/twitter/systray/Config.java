package net.ja731j.twitter.systray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class Config {

    private static Config instance = null;
    private final Configuration config;
    private boolean accessTokenNotNull = true;

    private Config() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        Yaml yaml = new Yaml();

        File settings = new File(new File("."), "settings.yml");
        if (!settings.exists()) {

            try {
                Files.copy(Class.class.getResourceAsStream("/default.yml"), settings.toPath());
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
                throw new RuntimeException(ex);
            }
        }

        Map<String, String> accessMap;
        Map<String, String> consumerMap;
        

        try {
            accessMap = (Map<String, String>) yaml.load(new FileInputStream(settings));
            consumerMap = (Map<String, String>) yaml.load(Class.class.getResourceAsStream("/consumerAuth.yml"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        }

        String accessToken = accessMap.get("accessToken");
        String accessTokenSecret = accessMap.get("accessTokenSecret");
        String consumerKey = consumerMap.get("key");
        String consumerSecret = consumerMap.get("secret");

        if (accessToken == null || accessTokenSecret == null) {
            accessTokenNotNull = false;
        }
        cb.setOAuthAccessToken(accessToken);
        cb.setOAuthAccessTokenSecret(accessTokenSecret);
        cb.setOAuthConsumerKey(consumerKey);
        cb.setOAuthConsumerSecret(consumerSecret);
        config = cb.build();
    }

    public static boolean isAuthNotNull() {
        if (instance == null) {
            instance = new Config();
        }
        return instance.accessTokenNotNull;
    }

    public static Configuration getConfiguration() {
        if (instance == null) {
            instance = new Config();
        }
        return instance.config;
    }
}
