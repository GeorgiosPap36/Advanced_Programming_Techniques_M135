public class Human extends Mammals implements Interface1
{
    protected int limbs;

    public Human(){
        super(90);
        this.limbs = 4;
    }

    public void MMM(){
        System.out.println("Humans walk");
    }
}

class BodyPart extends Human{

}
