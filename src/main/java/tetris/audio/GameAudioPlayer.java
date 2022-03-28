package tetris.audio;

import static tetris.constants.AudioStatus.MUTE;
import static tetris.constants.AudioStatus.STOP;

import com.goxr3plus.streamplayer.stream.StreamPlayer;
import com.goxr3plus.streamplayer.stream.StreamPlayerEvent;
import com.goxr3plus.streamplayer.stream.StreamPlayerException;
import com.goxr3plus.streamplayer.stream.StreamPlayerListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import tetris.constants.AudioStatus;
import tetris.system.MessageBroker;
import tetris.ui.annotations.OnMessage;
import tetris.ui.message.AudioMessage;

public class GameAudioPlayer extends StreamPlayer implements StreamPlayerListener {

    private static final String PATH_PREFIX = "resources/";
    private static final String MENU_BGM_PATH = "bgm1.mp3";
    private static final String MENU_BGM2_PATH = "bgm3.mp3";
    private static final String MENU_BGM3_PATH = "bgm4.mp3";
    private static final String PLAY_BGM_PATH = "bgm2.mp3";
    private static final ExecutorService parentService1;
    private static final ExecutorService parentService2;
    private static final ScheduledExecutorService loopService;
    // disabled logger
    private static final Logger logger;
    private static final List<String> menuBgmList = new ArrayList<>();
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
        menuBgmList.add(MENU_BGM2_PATH);
        menuBgmList.add(MENU_BGM3_PATH);
        menuBgmList.add(MENU_BGM_PATH);
    }

    private String currentLoop = "";

    private GameAudioPlayer() {
        super(logger, parentService1, parentService2);
        MessageBroker.subscribe(AudioMessage.class, this);
    }

    public static GameAudioPlayer getInstance() {
        if (instance == null) {
            instance = new GameAudioPlayer();
        }
        return instance;
    }

    @OnMessage
    private void onMessage(AudioMessage message) {
        AudioStatus status = message.getPayload();
        if (status == MUTE) {
            toggleMute();
        }
        if (status == STOP) {
            stop();
            call();
        }
    }

    public void playMenuBgm() {
        Random random = new Random();
        startLoop(menuBgmList.get(random.nextInt(menuBgmList.size())));
    }

    public void playInGameBgm() {
        startLoop(PLAY_BGM_PATH);
    }

    public void toggleMute() {
        setMute(!getMute());
    }

    public void shutDown() {
        stop();
        call();
        // stream player bug
        parentService1.shutdownNow();
        parentService2.shutdownNow();
        shutDownLoop();
        loopService.shutdownNow();
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
