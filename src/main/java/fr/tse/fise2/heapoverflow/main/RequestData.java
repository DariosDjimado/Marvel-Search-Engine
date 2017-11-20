package fr.tse.fise2.heapoverflow.main;

import okhttp3.OkHttpClient;

public class RequestData {

    private static final OkHttpClient client = new OkHttpClient();

    public static OkHttpClient getClient() {
        return client;
    }
}
