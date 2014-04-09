package net.ja731j.twitter.systray;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        SysTray.getInstance().init();
        Stream s = Stream.getInstance();
        if (Config.isAuthNotNull()) {
            s.init();
        }
    }

}
