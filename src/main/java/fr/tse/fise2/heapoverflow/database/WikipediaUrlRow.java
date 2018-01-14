package fr.tse.fise2.heapoverflow.database;

/**
 * @author Darios DJIMADO
 */
class WikipediaUrlRow {

    private String characterName;
    private String characterLabel;
    private String characterUrl;
    private String characterAlias;
    private String characterDescription;


    public WikipediaUrlRow(String characterName, String characterLabel, String characterUrl, String characterAlias, String characterDescription) {
        this.characterName = characterName;
        this.characterLabel = characterLabel;
        this.characterUrl = characterUrl;
        this.characterAlias = characterAlias;
        this.characterDescription = characterDescription;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }

    public String getCharacterLabel() {
        return characterLabel;
    }

    public void setCharacterLabel(String characterLabel) {
        this.characterLabel = characterLabel;
    }

    public String getCharacterUrl() {
        return characterUrl;
    }

    public void setCharacterUrl(String characterUrl) {
        this.characterUrl = characterUrl;
    }

    public String getCharacterAlias() {
        return characterAlias;
    }

    public void setCharacterAlias(String characterAlias) {
        this.characterAlias = characterAlias;
    }

    public String getCharacterDescription() {
        return characterDescription;
    }

    public void setCharacterDescription(String characterDescription) {
        this.characterDescription = characterDescription;
    }


    @Override
    public String toString() {
        return "WikipediaUrlRow{" +
                "characterName='" + characterName + '\'' +
                ", characterLabel='" + characterLabel + '\'' +
                ", characterUrl='" + characterUrl + '\'' +
                ", characterAlias='" + characterAlias + '\'' +
                ", characterDescription='" + characterDescription + '\'' +
                '}';
    }
}
