package tetris.audio;

import com.goxr3plus.streamplayer.stream.StreamPlayer;
import com.goxr3plus.streamplayer.stream.StreamPlayerEvent;
import com.goxr3plus.streamplayer.stream.StreamPlayerException;
import com.goxr3plus.streamplayer.stream.StreamPlayerListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class GameAudioPlayer extends StreamPlayer implements StreamPlayerListener {

    private static final String PATH_PREFIX = "resources/";
    private static final String MENU_BGM_PATH = "bgm1.mp3";
    private static final String PLAY_BGM_PATH = "bgm2.mp3";
    private static final String LEADERBOARD_BGM_PATH = "bgm3.mp3";
    private static final String _BGM_PATH = "bgm4.mp3";
    private static final ExecutorService parentService1;
    private static final ExecutorService parentService2;
    private static final ScheduledExecutorService loopService;
    private static final Logger logger; // disabled logger
    private static ScheduledFuture<?> future;
    private static GameAudioPlayer instance;

    static {
        parentService1 = Executors.newSingleThreadScheduledExecutor();
        parentService2 = Executors.newSingleThreadScheduledExecutor();
        loopService = Executors.newSingleThreadScheduledExecutor();
        logger = Logger.getLogger("audioLogger");
        logger.setUseParentHandlers(false);
        Arrays.stream(logger.getHandlers())
            .forEach(logger::removeHandler);
    }

    private String currentLoop = "";

    private GameAudioPlayer() {
        super(logger, parentService1, parentService2);
    }

    public static GameAudioPlayer getInstance() {
        if (instance == null) {
            instance = new GameAudioPlayer();
        }
        return instance;
    }

    public void playMenuBgm() {
        startLoop(MENU_BGM_PATH);
    }

    public void playInGameBgm() {
        startLoop(PLAY_BGM_PATH);
    }

    public void shutDown() {
        stop();
        call();
        // stream player bug
        parentService1.shutdownNow();
        parentService2.shutdownNow();
        loopService.shutdownNow();
        shutDownLoop();
    }

    private void startLoop(String path) {
        if (isSourceInLoop(path)) {
            return;
        }
        if (isPlaying()) {
            stop();
        }
        shutDownLoop();
        try {
            open(openResource(path));
            play();
            future = loopService.schedule(task(path), getDurationInMilliseconds(),
                TimeUnit.MILLISECONDS);
        } catch (StreamPlayerException e) {
            e.printStackTrace();
        }
        currentLoop = path;
    }

    private boolean isSourceInLoop(String path) {
        return isPlaying() && currentLoop.equals(path);
    }

    private void shutDownLoop() {
        if (future != null && !future.isDone()) {
            future.cancel(true);
        }
    }

    public Runnable task(String name) {
        return () -> {
            stop();
            call();
            startLoop(name);
        };
    }

    private String getAbsolutePath(String path) {
        return openResource(path).toURI().toString();
    }

    private File openResource(String name) {
        String currentDir = null;
        try {
            currentDir = new File(new File(".").getAbsolutePath()).getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File(currentDir, PATH_PREFIX + name);
    }

    @Override
    public void opened(Object dataSource, Map<String, Object> properties) {
    }

    @Override
    public void progress(int nEncodedBytes, long microsecondPosition, byte[] pcmData,
        Map<String, Object> properties) {
    }

    @Override
    public void statusUpdated(StreamPlayerEvent event) {
    }
}
