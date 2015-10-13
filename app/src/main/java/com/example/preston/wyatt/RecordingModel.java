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
    private static String LOG_TAG = "RECORDING_MODEL";
    private boolean recording;
    private boolean playing;
    private String fileName = null;

    private static RecordingModel model = null;

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    private RecordingModel(boolean recording) {
        this.recording = recording;
        playing = false;
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/audiorecordtest.3gp";
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

    public void changeRecordingState(){
        this.recording = !recording;
        if (recording)
            startRecording();
        else
            stopRecording();
    }
    public void changePlayingState(){
        this.playing = !playing;
        if (playing)
            startPlaying();
        else
            stopPlaying();
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
            Log.e(LOG_TAG, "tried to store in : " + fileName);
        }

        mRecorder.start();
    }
    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

    }
    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "starting playing failed");
        }

    }
    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;

    }

    //TODO: should we do something with onPause?
    public void hardStop() {
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

}
