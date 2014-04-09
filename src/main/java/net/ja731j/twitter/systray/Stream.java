package net.ja731j.twitter.systray;

import net.ja731j.twitter.systray.event.AuthenticationEvent;
import net.ja731j.twitter.systray.event.AuthenticationEventListener;
import net.ja731j.twitter.systray.event.ExitEvent;
import net.ja731j.twitter.systray.event.ExitEventListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

/**
 *
 * @author ja731j <jetkiwi@gmail.com>
 */
public class Stream implements ExitEventListener, AuthenticationEventListener {

    private static Stream instance = null;
    private TwitterStream twitterStream = null;
    private Thread thread = null;

    private Stream() {
        SysTray.getInstance().addExitListener(this);
        Config.addAuthenticationEventListener(this);
    }

    public static Stream getInstance() {
        if (instance == null) {
            instance = new Stream();
        }
        return instance;
    }

    @Override
    public void onApplicationExit(ExitEvent ex) {
        if (thread != null) {
            twitterStream.shutdown();
            twitterStream = null;
            thread = null;
        }
    }

    @Override
    public void onAuthentication(AuthenticationEvent e) {
        init();
    }

    public void init() {
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
