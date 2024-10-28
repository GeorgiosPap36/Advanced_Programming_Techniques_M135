public class SakuraTrees extends Plants
{
    public SakuraTrees(int lifespan, int height)
    {
        super(lifespan, false);
        this.height = 12;
        this.bearFruit = false;
    }

    public void SakuraTreeAction(){
        System.out.println("Beautiful");
    }
}
