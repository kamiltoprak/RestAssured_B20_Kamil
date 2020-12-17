package pojo;



// A pojo (  plain old Java Object) class
// is used  to  create  object  that represent  data
//for example
//this is  an  java object that  contains  3  valkues for  3  fieldds
// Spartans sp1= new  spartan("b20 user", "MAle ", 1234567890
//A pojo class must have
//encapsulated fields
// public no  arguments  constructor
// everything else is optional

public class Spartan {

    private String name;
    private String gender;
    private long phone;


    public Spartan() {
    }

    public Spartan(String name, String gender, long phone) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Spartan{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", phone=" + phone +
                '}';
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
