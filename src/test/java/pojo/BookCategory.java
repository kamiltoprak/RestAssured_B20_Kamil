package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BookCategory {

// we  want  to be able to name the fields any name we want
// rather than  being limited to use same name as json object key

    @JsonProperty("id")
    private String category_id;
    @JsonProperty("name")
    private String category_name;


    public BookCategory() {
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getName() {
        return category_name;
    }

    public void setName(String name) {
        this.category_name = name;
    }

    @Override
    public String toString() {
        return "BookCategory{" +
                "id='" + category_id + '\'' +
                ", name='" + category_name + '\'' +
                '}';
    }
}
