package com.sandeep.loggerapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sandeep.loggerapp.util.LoggerUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoggerActivityFragment extends Fragment {

    private static final String LOG_TAG = LoggerActivityFragment.class.getSimpleName();
    @Bind(R.id.log_text)
    EditText mLogText;

    public LoggerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_logger, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.btn_verbose)
    void verboseLogClicked() {
        if (isValidMessage()) {
            LoggerUtil.v(LOG_TAG, mLogText.getText().toString());
        }
    }

    @OnClick(R.id.btn_debug)
    void debugLogClicked() {
        if (isValidMessage()) {
            LoggerUtil.d(LOG_TAG, mLogText.getText().toString());
        }
    }

    @OnClick(R.id.btn_info)
    void infoLogClicked() {
        if (isValidMessage()) {
            LoggerUtil.i(LOG_TAG, mLogText.getText().toString());
        }
    }

    @OnClick(R.id.btn_warn)
    void warnLogClicked() {
        if (isValidMessage()) {
            LoggerUtil.w(LOG_TAG, mLogText.getText().toString());
        }
    }

    @OnClick(R.id.btn_error)
    void errorLogClicked() {
        if (isValidMessage()) {
            LoggerUtil.e(LOG_TAG, mLogText.getText().toString());
        }
    }

    private boolean isValidMessage() {
        if (mLogText.getText().toString().isEmpty()) {
            mLogText.setError("Please Enter Text to bo logged");
            return false;
        }
        return true;
    }

}
