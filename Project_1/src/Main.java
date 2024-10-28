import java.util.ArrayList;
import java.util.List;

public class Main
{

    public static void main(String[] args)
    {
        if (args != null && args.length == 3){

            int bestN;

            try {
                bestN = Integer.parseInt(args[2]);
                if (bestN < 0){
                    System.out.println("Number should be >= 0");
                    return;
                }
            }
            catch (NumberFormatException nfe){
                System.out.println("Third argument should be a number!");
                return;
            }

            FindBestN findBest = new FindBestN(bestN);

            FileInteract fileObj = new FileInteract(args[0], args[1]);

            List<String> inputTypes = fileObj.ReadFile();

            fileObj.CreateOutputFile();
            fileObj.WriteFile("", false); //Clear output file

            ClassStats[] stats;

            if(inputTypes.size() > 0){
                stats = new ClassStats[inputTypes.size()];
                for (int i = 0; i < inputTypes.size(); i++){
                    //System.out.println();
                    //System.out.println("---------------" + inputTypes.get(i) + "---------------");
                    //System.out.println();
                    ClassStats typeStats = new ClassStats(inputTypes.get(i), inputTypes);
                    stats[i] = typeStats;
                }
            }
            else{
                System.out.println("Empty input file");
                return;
            }

            //System.out.println();
            //System.out.println("--------------ANSWERS----------------");

            int finalBestN = Math.min(bestN, inputTypes.size());

            try {
                fileObj.WriteFile("1a: " + ArrayToString(findBest.FirstA(stats)) + "\n", true);
                //Prints("1a", findBest.FirstA(stats), finalBestN);

                fileObj.WriteFile("1b: " + ArrayToString(findBest.FirstB(stats)) + "\n", true);
                //Prints("1b", findBest.FirstB(stats), finalBestN);

                fileObj.WriteFile("2a: " + ArrayToString(findBest.SecondA(stats)) + "\n", true);
                //Prints("2a", findBest.SecondA(stats), finalBestN);

                fileObj.WriteFile("2b: " + ArrayToString(findBest.SecondB(stats)) + "\n", true);
                //Prints("2a", findBest.SecondB(stats), finalBestN);

                fileObj.WriteFile("3: " + ArrayToString(findBest.Third(stats)) + "\n", true);
                //Prints("3", findBest.Third(stats), finalBestN);

                fileObj.WriteFile("4: " + ArrayToString(findBest.Fourth(stats)), true);
                //Prints("4", findBest.Fourth(stats), finalBestN);
            }
            catch (NullPointerException npE){
                System.out.println("At least one of the types in the input file does not exist!");
            }
        }
        else{
            System.out.println("Not enough arguments");
        }
    }

    private static String ArrayToString(ClassStats[] array){
        List<String> strings = new ArrayList<>();
        for (ClassStats stat: array) {
            String methodStr = stat.getMyName();
            strings.add(methodStr);
        }
        return String.join(", ", strings);
    }

    private static void Prints(String s, ClassStats[] temp, int finalBestN){
        System.out.println();
        System.out.println("------------------------------------------------");
        System.out.println();
        for (int i = 0; i < finalBestN; i++){
            System.out.println("Type with most " + s + ": " + temp[i].getMyName());
        }
    }

}