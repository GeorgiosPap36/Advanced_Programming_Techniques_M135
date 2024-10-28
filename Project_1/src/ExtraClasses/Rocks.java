abstract public class Rocks implements Thing
{
    public int diameter;

    public String color;

    private boolean privateBool;
    private int privateInt;
    public double publicDouble;

    public Rocks(int diameter, String color){
        this.diameter = diameter;
        this.color = color;
    }

    public boolean Living(){
        System.out.println("Rocks need to touch grass");
        return false;
    }

    public boolean Moves(){
        System.out.println("Rocks can't move");
        return false;
    }

    public void setDiameter(int d){
        diameter = d;
    }

    public int getDiameter(){
        return diameter;
    }

    public void setColor(String c){
        color = c;
    }

    public String getColor(){
        return color;
    }

    public void Method1(){

    }

    public void Method1(int a, String b){

    }

    private String Method2(){
        return "Method2";
    }

    public void privateMethod1(){

    }
}

