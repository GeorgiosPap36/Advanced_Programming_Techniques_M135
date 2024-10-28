public class Plants extends Creature
{
    public boolean edible;
    public int height;
    public boolean bearFruit;

    public Plants(int lifespan, boolean edible){
        super(lifespan);
        this.edible = edible;
    }

    public boolean Moves()
    {
        return false;
    }
}
