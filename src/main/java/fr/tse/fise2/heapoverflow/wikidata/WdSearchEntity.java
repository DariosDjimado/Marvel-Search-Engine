package fr.tse.fise2.heapoverflow.wikidata;


import java.util.Arrays;

public class WdSearchEntity {
    private String id;

    private String title;

    private String concepturi;

    private String repository;

    private String description;

    private String label;

    private WdMatch match;

    private String[] aliases;

    private String url;

    private String pageid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConcepturi() {
        return concepturi;
    }

    public void setConcepturi(String concepturi) {
        this.concepturi = concepturi;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public WdMatch getMatch() {
        return match;
    }

    public void setMatch(WdMatch match) {
        this.match = match;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    @Override
    public String toString() {
        return "WdSearchEntity{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", concepturi='" + concepturi + '\'' +
                ", repository='" + repository + '\'' +
                ", description='" + description + '\'' +
                ", label='" + label + '\'' +
                ", match=" + match +
                ", aliases=" + Arrays.toString(aliases) +
                ", url='" + url + '\'' +
                ", pageid='" + pageid + '\'' +
                '}';
    }
}
