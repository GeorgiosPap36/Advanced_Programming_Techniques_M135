abstract public class Creature implements Thing
{
    public int lifespan;

    public Creature(int lifespan){
        this.lifespan = lifespan;
    }

    public boolean Living(){
        return true;
    }

    public void Breed(){

    }
}
