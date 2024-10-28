public class Pebbles extends SmallRocks
{
    public int field1;
    private boolean field2;

    public Pebbles(int diameter, String color, boolean throwable){
        super(diameter, color, throwable);
    }

    public boolean GoodForThrow()
    {
        if (getThrowable() && (diameter <= 5)) {
            System.out.println("Yes, pebble good for throw!");
            return true;
        }
        return false;
    }

    public void privateMethod1(){

    }

    public void Method1(int a, String b){

    }
}
