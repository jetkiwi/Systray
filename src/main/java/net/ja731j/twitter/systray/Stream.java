package net.ja731j.twitter.systray;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class Stream {

    private static Stream instance = null;
    private TwitterStream twitterStream = null;
    private Thread thread = null;

    private Stream() {
    }

    public static Stream getInstance() {
        if(!Config.isAuthNotNull()){
            return null;
        }
        
        if (instance == null) {
            instance = new Stream();
        }
        return instance;
    }

    public void init() {
        if (thread == null) {
            thread = new Thread() {
                @Override
                public void run() {
                    TwitterStreamFactory twitterStreamFactory = new TwitterStreamFactory(Config.getConfiguration());
                    TwitterStream stream = twitterStreamFactory.getInstance();
                    stream.addListener(new StreamListener());
                    twitterStream = stream;
                    stream.user();
                }
            };
            thread.start();
        }
    }

    public void quit() {
        if (thread != null) {
            twitterStream.shutdown();
            twitterStream = null;
            thread = null;
        }
    }
}
