package fr.tse.fise2.heapoverflow.main;

import okhttp3.Cache;

import java.io.File;

public class AppConfig {
    //private final
    private static AppConfig instance;
    private final Cache cacheUrls;
    private String tmpDir;


    private AppConfig() {
        this.cacheUrls = new Cache(new File("CacheResponse.tmp"), 10 * 1024 * 1024);
        this.tmpDir = "CacheImage.tmp/";

       /* try {
            tmpDir = Files.createTempDirectory("CacheImage.tmp") + "/";
        } catch (IOException e) {
            AppErrorHandler.onError(e);
        }*/
    }

    public static AppConfig getInstance() {
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    public String getTmpDir() {
        return tmpDir;
    }

    public Cache getCacheUrls() {
        return cacheUrls;
    }
}
