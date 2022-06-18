import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
    private String name;
    private String lastname;
    private String surname;
    private String mobileH;
    private String mobileW;
    private String adress;
    private LocalDate bday;
    private String comment;
    private String mobiles;

    public Person(String name, String lastname, String surname, String mobileH, String mobileW, String adress, LocalDate bday, String comment) {
        this.name = name;
        this.lastname = lastname;
        this.surname = surname;
        this.mobileH = mobileH;
        this.mobileW = mobileW;
        this.adress = adress;
        this.bday = bday;
        this.comment = comment;
        if(mobileH.equals("") || mobileW.equals(""))
            mobiles = mobileH + mobileW;
        else
            mobiles = mobileH + " / " + mobileW;
    }
   //Находил ошибку в этом методе 2 часа а потом заметил, что не юзаю входные аргументы !!!!!!!!!!!!!!!!!!!
   //public void changeMobiles(String h, String w)
   //{
   //    if(mobileH.equals("") || mobileW.equals(""))
   //        mobiles = mobileH + mobileW;
   //    else
   //        mobiles = mobileH + " / " + mobileW;
   //}
    //соединяет телефоны для таблицы
   public void setMobiles(String mobileH, String mobileW) {
       if(mobileH.equals("") || mobileW.equals(""))
           mobiles = mobileH + mobileW;
       else
           mobiles = mobileH + " / " + mobileW; }

   public String getName() {
       return name;
    }

    public String getLastname() {
        return lastname;
    }

    public String getSurname() {
        return surname;
    }

    public String getMobileH() {
        return mobileH;
    }

    public String getMobileW() {
        return mobileW;
    }

    public String getAdress() {
        return adress;
    }

    public String getComment() {
        return comment;
    }

    public String getMobiles() { return mobiles; }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setSurname(String surname) { this.surname = surname; }

    public void setMobileH(String mobileH) {
        this.mobileH = mobileH;
    }

    public void setMobileW(String mobileW) {
        this.mobileW = mobileW;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getBday() { return bday; }

    public void setBday(LocalDate bday) { this.bday = bday; }
}