package fr.tse.fise2.heapoverflow.wikidata;

class WdDescriptions {
    private WdTemplateLangCode de;

    private WdTemplateLangCode ca;

    private WdTemplateLangCode it;

    private WdTemplateLangCode fr;

    private WdTemplateLangCode es;

    public WdTemplateLangCode getDe() {
        return de;
    }

    public void setDe(WdTemplateLangCode de) {
        this.de = de;
    }

    public WdTemplateLangCode getCa() {
        return ca;
    }

    public void setCa(WdTemplateLangCode ca) {
        this.ca = ca;
    }

    public WdTemplateLangCode getIt() {
        return it;
    }

    public void setIt(WdTemplateLangCode it) {
        this.it = it;
    }

    public WdTemplateLangCode getFr() {
        return fr;
    }

    public void setFr(WdTemplateLangCode fr) {
        this.fr = fr;
    }

    public WdTemplateLangCode getEs() {
        return es;
    }

    public void setEs(WdTemplateLangCode es) {
        this.es = es;
    }

    @Override
    public String toString() {
        return "WdDescriptions{" +
                "de=" + de +
                ", ca=" + ca +
                ", it=" + it +
                ", fr=" + fr +
                ", es=" + es +
                '}';
    }
}
