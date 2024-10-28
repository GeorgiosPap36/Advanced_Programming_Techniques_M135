public class Bush extends Plants
{
    public Bush(int lifespan, int height){
        super(lifespan, false);
        this.height = 2;
        this.bearFruit = false;
    }

    public void BushAction(){
        System.out.println("You can hide in me!");
    }
}
