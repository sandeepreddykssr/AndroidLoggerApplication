package com.sandeep.loggerapp.util.network.networkservice;

import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by sandeep on 04-10-2015.
 */
public interface UploadServices {

    @Multipart
    @POST("/upload/log")
    void uploadFile(@Part("LogFile") TypedFile logFile, Callback<String> callBack);
}
