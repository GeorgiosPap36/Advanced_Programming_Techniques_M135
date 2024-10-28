public class AppleTrees extends Plants
{

    public AppleTrees(int lifespan, int height){
        super(lifespan, false);
        this.height = 10;
        this.bearFruit = true;
    }

    public void FruitColor(){
        System.out.println("Apples are red");
    }
}
