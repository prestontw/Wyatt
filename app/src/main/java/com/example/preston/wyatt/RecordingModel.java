package com.example.preston.wyatt;

/**
 * Created by preston on 10/6/15.
 * Represents a recorder
 *
 * Maybe make this a singleton
 */
public class RecordingModel {
    private boolean recording;
    private static RecordingModel model = null;

    private RecordingModel(boolean recording) {
        this.recording = recording;
    }

    public static RecordingModel getInstance(){
        if (model == null)
            model = new RecordingModel(false);
        return model;
    }

    public boolean isRecording() {
        return recording;
    }

    public void setRecording(boolean recording) {
        this.recording = recording;
    }

    public void changeState(){
        this.recording = !recording;
    }
}
