package com.sandeep.loggerapp.util.network;

import android.support.annotation.NonNull;

import com.squareup.okhttp.OkHttpClient;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by sandeep on 04-10-2015.
 */
public class NetworkServiceGenerator {

    private static OkClient okClient;

    private NetworkServiceGenerator() {
    }

    /**
     * @param serviceClass
     * @param baseUrl
     * @param <S>
     * @return
     */
    public static <S> S createService(Class<S> serviceClass, String baseUrl) {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(baseUrl)
                .setClient(getClient());

        RestAdapter adapter = builder.build();

        return adapter.create(serviceClass);
    }

    @NonNull
    private static OkClient getClient() {
        if (okClient == null) {
            okClient = new OkClient(new OkHttpClient());
        }
        return okClient;
    }
}
