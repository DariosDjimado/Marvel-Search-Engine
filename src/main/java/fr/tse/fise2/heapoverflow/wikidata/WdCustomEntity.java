package fr.tse.fise2.heapoverflow.wikidata;

class WdCustomEntity {

    private String id;

    private String title;

    private String ns;

    private WdLabels labels;

    private String type;

    private WdAliases aliases;

    private String lastrevid;

    private WdDescriptions descriptions;

    private WdSitelinks sitelinks;

    private String modified;

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

    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
    }

    public WdLabels getLabels() {
        return labels;
    }

    public void setLabels(WdLabels wdLabels) {
        this.labels = wdLabels;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public WdAliases getAliases() {
        return aliases;
    }

    public void setAliases(WdAliases wdAliases) {
        this.aliases = wdAliases;
    }

    public String getLastrevid() {
        return lastrevid;
    }

    public void setLastrevid(String lastrevid) {
        this.lastrevid = lastrevid;
    }

    public WdDescriptions getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(WdDescriptions wdDescriptions) {
        this.descriptions = wdDescriptions;
    }

    public WdSitelinks getSitelinks() {
        return sitelinks;
    }

    public void setSitelinks(WdSitelinks wdSitelinks) {
        this.sitelinks = wdSitelinks;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getPageid() {
        return pageid;
    }

    public void setPageid(String pageid) {
        this.pageid = pageid;
    }

    @Override
    public String toString() {
        return "Q2511584{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ns='" + ns + '\'' +
                ", labels=" + labels +
                ", type='" + type + '\'' +
                ", aliases=" + aliases +
                ", lastrevid='" + lastrevid + '\'' +
                ", descriptions=" + descriptions +
                ", sitelinks=" + sitelinks +
                ", modified='" + modified + '\'' +
                ", pageid='" + pageid + '\'' +
                '}';
    }
}
