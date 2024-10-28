public class BigRocks extends Rocks
{
    private final boolean pushable;

    public BigRocks(int diameter, String color, boolean pushable){
        super(diameter, color);
        this.pushable = pushable;
    }

    public void Praise(){
        System.out.println("You are the biggest rock I have ever seen");
    }

    private void PowerToPush(){
        if (pushable){
            System.out.println("It can be pushed, but I don't know");
        }
        else{
            System.out.println("Can't be pushed");
        }
    }
}
