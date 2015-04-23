package biz.glieunou.meteo;

/**
 * Created by sumbang on 08/04/15.
 */

public class Ville {

    private int Id;

    private String Name;

    private String Temp;

    private String Vent;

    private String Climat;

    private String Des;

    private int Position;

    private String Mini;


    public Ville(){

        this.Id=0; this.Climat=""; this.Temp=""; this.Vent=""; this.Des=""; this.Position=0; this.Mini="";
    }

    public Ville(int id, String name, String temp, String vent, String climat, String des, int position, String mini){

        this.Id=id; this.Climat=climat; this.Temp=temp; this.Vent=vent; this.Des=des; this.Position=position; this.Mini=mini;
    }

    public void setId(int id) {
        Id = id;
    }

    public void setClimat(String climat) {
        Climat = climat;
    }

    public void setDes(String des) {
        Des = des;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

    public void setVent(String vent) {
        Vent = vent;
    }

    public int getId() {
        return Id;
    }

    public String getClimat() {
        return Climat;
    }

    public String getDes() {
        return Des;
    }

    public String getName() {
        return Name;
    }

    public String getTemp() {
        return Temp;
    }

    public String getVent() {
        return Vent;
    }

    public void setPosition(int position) {
        Position = position;
    }

    public void setMini(String mini) {
        Mini = mini;
    }

    public String getMini() {
        return Mini;
    }

    public int getPosition() {
        return Position;
    }
}
