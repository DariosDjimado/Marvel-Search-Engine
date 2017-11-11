package fr.tse.fise2.heapoverflow.wikidata;

import java.util.Arrays;

class WdAliases {
    private WdTemplateLangCode[] ca;

    private WdTemplateLangCode[] it;

    public WdTemplateLangCode[] getCa() {
        return ca;
    }

    public void setCa(WdTemplateLangCode[] ca) {
        this.ca = ca;
    }

    public WdTemplateLangCode[] getIt() {
        return it;
    }

    public void setIt(WdTemplateLangCode[] it) {
        this.it = it;
    }

    @Override
    public String toString() {
        return "WdAliases{" +
                "ca=" + Arrays.toString(ca) +
                ", it=" + Arrays.toString(it) +
                '}';
    }
}
