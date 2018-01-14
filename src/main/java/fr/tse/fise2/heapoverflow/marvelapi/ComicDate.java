package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * ComicDate POJO
 *
 * @author Darios DJIMADO
 */
public class ComicDate {

    private String type;

    private String date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ComicDate{" +
                "type='" + type + '\'' +
                ", date=" + date +
                '}';
    }
}
