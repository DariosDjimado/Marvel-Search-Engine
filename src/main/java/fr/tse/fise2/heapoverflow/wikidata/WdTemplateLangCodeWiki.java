package fr.tse.fise2.heapoverflow.wikidata;

import java.util.Arrays;

public class WdTemplateLangCodeWiki {
    private String site;

    private String[] badges;

    private String title;

    private String url;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String[] getBadges() {
        return badges;
    }

    public void setBadges(String[] badges) {
        this.badges = badges;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Tlwiki{" +
                "site='" + site + '\'' +
                ", badges=" + Arrays.toString(badges) +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
