abstract public class Animals extends Creature
{
    boolean canMove;

    public Animals(int lifespan, boolean canMove){
        super(lifespan);
        this.canMove = canMove;
    }

    public boolean Moves()
    {
        return canMove;
    }

    abstract public void MMM();
}
