package com.parthjadav.passwordmanager.seekbar;

public interface OnSeekBarChangeListener {
    void onProgressChanged(SeekBar seekBar, int progress);

    void onStartTrackingTouch(SeekBar seekBar);

    void onStopTrackingTouch(SeekBar seekBar);
}
