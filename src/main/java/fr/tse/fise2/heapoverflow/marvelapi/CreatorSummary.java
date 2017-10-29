package fr.tse.fise2.heapoverflow.marvelapi;

public class CreatorSummary extends TemplateSummary {

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "CreatorSummary{" +
                "role='" + role + '\'' +
                ", resourceURI='" + resourceURI + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
