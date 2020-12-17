package pojo;



// this is  a pojo class
// used to  represent spartan data in  response json
// it has 4  fields : id, name ,  gender , phone

public class SpartanRead {
    private int id;
    private String name;
    private String gender;
    private long phone;

    public SpartanRead() {
    }

    public SpartanRead(int id, String name, String gender, long phone) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "SpartanRead{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public long getPhone() {
        return phone;
    }
}
