package fr.tse.fise2.heapoverflow.database;

public final class CacheUrlsRow {
    private final String shortenUrl;
    private final String completeUrl;

    public CacheUrlsRow(String shortenUrl, String completeUrl) {
        this.shortenUrl = shortenUrl;
        this.completeUrl = completeUrl;
    }

    public String getShortenUrl() {
        return shortenUrl;
    }

    public String getCompleteUrl() {
        return completeUrl;
    }
}
