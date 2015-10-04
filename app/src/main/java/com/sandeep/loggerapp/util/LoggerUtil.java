package com.sandeep.loggerapp.util;

import android.os.Environment;
import android.util.Log;

import com.sandeep.loggerapp.util.network.NetworkServiceGenerator;
import com.sandeep.loggerapp.util.network.networkservice.UploadServices;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by sandeep on 04-10-2015.
 */
public class LoggerUtil {

    /**
     * Min Log Level which is to be logged
     * (Note: For security purpose Log Level is kept to Info during production builds)
     * VERBOSE = 2;
     * DEBUG = 3;
     * INFO = 4;
     * WARN = 5;
     * ERROR = 6;
     */
    private static final int MIN_LOG_LEVEL = Log.INFO;

    /**
     * Max File Size in Bytes to be uploaded to server.
     * The Log file generated will be uploaded to server after the size
     * of the file exceeds this size
     */
    private static final int MAX_FILE_SIZE = 1024; // in Bytes

    /**
     * Send a {@link Log#VERBOSE} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param log The message you would like logged.
     */
    public static void v(String tag, String log) {
        if (MIN_LOG_LEVEL <= Log.VERBOSE) {
            writeToFile("v", tag, log);
            Log.v(tag, log);
        }
    }

    /**
     * Send a {@link Log#DEBUG} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param log The message you would like logged.
     */
    public static void d(String tag, String log) {
        if (MIN_LOG_LEVEL <= Log.DEBUG) {
            writeToFile("d", tag, log);
            Log.d(tag, log);
        }
    }

    /**
     * Send a {@link Log#INFO} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param log The message you would like logged.
     */
    public static void i(String tag, String log) {
        if (MIN_LOG_LEVEL <= Log.INFO) {
            writeToFile("i", tag, log);
            Log.i(tag, log);
        }
    }

    /**
     * Send a {@link Log#WARN} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param log The message you would like logged.
     */
    public static void w(String tag, String log) {
        if (MIN_LOG_LEVEL <= Log.WARN) {
            writeToFile("w", tag, log);
            Log.w(tag, log);
        }
    }

    /**
     * Send a {@link Log#ERROR} log message.
     *
     * @param tag Used to identify the source of a log message.  It usually identifies
     *            the class or activity where the log call occurs.
     * @param log The message you would like logged.
     */
    public static void e(String tag, String log) {
        if (MIN_LOG_LEVEL <= Log.ERROR) {
            writeToFile("e", tag, log);
            Log.e(tag, log);
        }
    }

    /**
     * Write the Logs to file
     *
     * @param level : Log Level Tag to differentiate between the level of logs
     * @param tag   Used to identify the source of a log message.  It usually identifies
     *              the class or activity where the log call occurs.
     * @param log   The message you would like logged.
     */
    private static void writeToFile(String level, String tag, String log) {

        //Creation of Text to stored in the Log File
        String logData = "\n" + new Date() + " " + level + "// " + tag + " : " + log;

        File sdCard = Environment.getExternalStorageDirectory();
        File dir = new File(sdCard.getAbsolutePath() + "/Logger");

        if (!dir.exists()) {
            //Create the Logger Folder if folder does not exist
            dir.mkdirs();
        }

        File file = new File(dir, "log.log");

        /*
         * if file size exceeds Max File Size Upload the file to server
         * and save the new data to a new file
         */
        if (file.length() > MAX_FILE_SIZE) {
            String destFileName = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date()) + ".log";
            File destFile = new File(dir, destFileName);
            try {
                FileUtils.moveFile(file, destFile);
                uploadLogFile(destFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //Writing the data to file
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file, true);
            out.write(logData.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Uploads the log file to the server after it has reached the max size
     *
     * @param file : Log File to be uploaded ot the server
     */
    public static void uploadLogFile(final File file) {
        UploadServices uploadService = NetworkServiceGenerator.createService(UploadServices.class, "http://baseurl");
        uploadService.uploadFile(new TypedFile("text/plain", file), new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                //if the file is successfully uploaded then delete the file
                if ("Y".equalsIgnoreCase(s)) {
                    FileUtils.deleteQuietly(file);
                }
                /**
                 * TODO: Write The case for handling the upload failure case.
                 * One of few possible solutions is to try to upload the file after some more time with some upload services.
                 */
            }

            @Override
            public void failure(RetrofitError error) {
                /**
                 *  TODO: Write The case for handling the upload failure case.
                 *  One of few possible solutions is to try to upload the file after some more time with some upload services.
                 */
            }
        });
    }
}
