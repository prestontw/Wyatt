package com.example.preston.wyatt;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

/**
 * Created by preston on 10/6/15.
 * Represents a recorder
 *
 * Maybe make this a singleton
 */
public class RecordingModel {
    private boolean recording;
    private boolean playing;
    private static RecordingModel model = null;
    private MediaRecorder mediaRecorder = null;
    private MediaPlayer mediaPlayer = null;
    private String filename = null;

    private RecordingModel(boolean recording) {
        this.recording = recording;
        playing = false;
        filename =
                Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/wyattrecording.3gp";

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
        if (recording)
            startRecording();
        else
            stopRecording();
    }

    public void changePlaying() {
        playing = !playing;

        if (playing)
            startPlaying();
        else
            stopPlaying();
    }

    private void startPlaying() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filename);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e) {
            Log.e("RecordingApp", "can't load file " + filename);
        }
    }

    private void stopPlaying() {
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private void startRecording(){
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setOutputFile(filename);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mediaRecorder.prepare();
        }
        catch (IOException e) {
            Log.e("RecordingApp", "could not prepare");
        }

        mediaRecorder.start();
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
    }




}
