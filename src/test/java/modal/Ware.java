package modal;

import java.util.ArrayList;
import java.util.List;

public class Ware {
    private String name;
    private Integer price;
    private String brand;
    private Integer size;
    private final List<String> category = new ArrayList<>();


    public String getName() {
        return  name;
    } public Integer getPrice() {
        return  price;
    }
    public String getBrand() {
        return  brand;
    }

    public List<String> getCategory() {
        return category;
    }

    public Integer getSize() {
        return size;
    }
}
