package fr.tse.fise2.heapoverflow.marvelapi;

/**
 *
 */
public class Authentication {
    public Authentication(String privateKey, String publicKey, int rateLimit, int numberOfRequest) {
        Authentication.privateKey = privateKey;
        Authentication.publicKey = publicKey;
        Authentication.rateLimit = rateLimit;
        Authentication.numberOfRequest = numberOfRequest;
    }

    private static String privateKey = "8964bac1f6de8a7580e2cbf587674497ffb17e87";
    private static String publicKey = "20ed82c345b223a930d1332c37058bc2";
    private static int rateLimit = 3000;
    // we need to abort request if the limit is reached
    private static int numberOfRequest = 0;

    public static String getPrivateKey() {
        return privateKey;
    }

    public static void setPrivateKey(String privateKey) {
        Authentication.privateKey = privateKey;
    }

    public static String getPublicKey() {
        return publicKey;
    }

    public static void setPublicKey(String publicKey) {
        Authentication.publicKey = publicKey;
    }

    public static int getRateLimit() {
        return rateLimit;
    }

    public static void setRateLimit(int rateLimit) {
        Authentication.rateLimit = rateLimit;
    }

    public static int getNumberOfRequest() {
        return numberOfRequest;
    }

    public static void setNumberOfRequest(int numberOfRequest) {
        Authentication.numberOfRequest = numberOfRequest;
    }
}
