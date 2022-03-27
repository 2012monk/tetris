package tetris.ui.message;

import tetris.constants.AudioStatus;

public class AudioMessage extends Post<AudioStatus>{

    public AudioMessage(AudioStatus payload) {
        super(payload);
    }
}
