public class Insects extends Creature
{
    private boolean canMove;

    public Insects(int lifespan, boolean canMove){
        super(lifespan);
        this.canMove = canMove;
    }

    public void setCanMove(boolean canMove)
    {
        this.canMove = canMove;
    }

    public boolean getCanMove(){
        return canMove;
    }

    public boolean Moves(){
        return true;
    }

    private void HowDisgusting(float d){
        System.out.println("This is " + d*10 + " out of 10, disgusting");
    }
}
