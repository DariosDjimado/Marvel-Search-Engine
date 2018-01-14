package fr.tse.fise2.heapoverflow.marvelapi;

/**
 * ComicPrice POJO
 *
 * @author Darios DJIMADO
 */
public class ComicPrice {

    private String price;

    private String type;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ComicPrice{" +
                "price='" + price + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
