public class SmallRocks extends Rocks
{
    private final boolean throwable;
    public int field5;
    public Human steve;

    public SmallRocks(int diameter, String color, boolean throwable ){
        super(diameter, color);
        this.throwable = throwable;
    }

    public boolean getThrowable(){
        return throwable;
    }

    public void Belittle(){
        System.out.println("You are the smallest rock I have ever seen");
    }

    private void ThrowArch(){
        if (throwable){
            System.out.println("It can be thrown, but I don't know");
        }
        else{
            System.out.println(("Can't be thrown!"));
        }
    }

    public void privateMethod1(){

    }

    public void Method1(int a, String b){

    }
}
