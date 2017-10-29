package fr.tse.fise2.heapoverflow.marvelapi;

public class CharacterSummary extends TemplateSummary {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CharacterSummary{" +
                "role='" + role + '\'' +
                ", resourceURI='" + resourceURI + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
