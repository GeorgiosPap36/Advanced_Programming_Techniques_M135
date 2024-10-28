import java.util.Arrays;
import java.util.List;

public class SuperAndSubtypeStats
{

    private List<String> inputs;

    public SuperAndSubtypeStats(List<String> inputs){
        this.inputs = inputs;
    }

    public void FindSuperclasses(String typeName, List<String> superclasses) throws ClassNotFoundException {
        if (typeName != null) {
            Class c = Class.forName(typeName);
            Class temp = c.getSuperclass();
            if (temp != null && ShouldICheckType(temp.getName())){
                superclasses.add(temp.getName());
                FindSuperclasses(temp.getName(), superclasses);
            }
        }
    }

    public void FindInterfaces(String typeName, List<String> interfaces) throws ClassNotFoundException {
        if (typeName != null && ShouldICheckType(typeName)) {
            Class c = Class.forName(typeName);
            Class[] temp = c.getInterfaces();
            if (temp.length > 0) {
                for (Class aClass : temp) {
                    if (ShouldICheckType(aClass.getName())) {
                        interfaces.add(aClass.getName());
                        FindInterfaces(aClass.getName(), interfaces);
                    }
                }
            }
        }
    }

    public void FindExtraInterfaces(List<String> list, List<String> interfaces){
        for (String s : list){
            try {
                FindInterfaces(s, interfaces);
            }
            catch (ClassNotFoundException cnfe){
                System.out.print("Not found");
            }
        }
    }

    public void FindClassSubtypes(String typeName, List<String> subtypes) throws ClassNotFoundException{
        String sub;
        Class possibleChild, parent;
        for (String input : inputs) {
            sub = input;
            possibleChild = Class.forName(sub);
            parent = possibleChild.getSuperclass();
            if (parent != null) {
                if (typeName.equals(parent.getName())) {
                    subtypes.add(sub);
                    FindClassSubtypes(sub, subtypes);
                }
            }
        }
    }

    public void FindInterfaceSubtypes(String typeName, List<String> subtypes) throws ClassNotFoundException{
        String sub;
        Class c;
        Class[] temp;
        List<Class> inters;

        for (String input : inputs) {
            sub = input;
            c = Class.forName(sub);
            temp = c.getInterfaces();
            inters = Arrays.asList(temp);
            if (inters.contains(Class.forName(typeName))) {
                subtypes.add(sub);
                FindInterfaceSubtypes(sub, subtypes);
            }
        }
        FindClassSubtypes(typeName, subtypes);
    }


    private boolean ShouldICheckType(String typeName){
        return inputs.contains(typeName) || typeName.startsWith("java.") || typeName.startsWith("jdk.");
    }

}
