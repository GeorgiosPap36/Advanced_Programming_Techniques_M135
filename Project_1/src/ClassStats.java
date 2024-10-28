import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ClassStats
{
    private String myName;

    private Field[] myFields;
    private List<String> inheritedFields = new ArrayList<String>();

    private Method[] myMethods;
    private List<Method> beforeOverriddenMethods = new ArrayList<Method>();
    private List<String> inheritedMethods = new ArrayList<String>();

    private List<String> superclasses = new ArrayList<String>();
    private List<String> interfaces = new ArrayList<String>();
    private List<String> allSupertypes = new ArrayList<String>();
    private List<String> subtypes = new ArrayList<String>();


    public ClassStats(String typeName, List<String> inputs)
    {
        setMyName(typeName);

        SuperAndSubtypeStats typeStats = new SuperAndSubtypeStats(inputs);

        try {
            Class me = Class.forName(myName);
            MethodFieldsStats mfStats = new MethodFieldsStats(me.getPackageName());

            typeStats.FindSuperclasses(myName, superclasses);
            typeStats.FindInterfaces(myName, interfaces);
            typeStats.FindExtraInterfaces(superclasses, interfaces);

            typeStats.FindClassSubtypes(myName, subtypes);
            typeStats.FindInterfaceSubtypes(myName, subtypes);

            //remove duplicates
            superclasses = OnceEveryItem(superclasses);
            interfaces = OnceEveryItem(interfaces);
            subtypes = OnceEveryItem(subtypes);
            //Superclasses + interfaces
            allSupertypes.addAll(superclasses);
            allSupertypes.addAll(interfaces);

            myFields = mfStats.FindMyFields(myName);
            mfStats.FindInheritedFields(superclasses, inheritedFields);
            mfStats.FindInheritedFields(interfaces, inheritedFields);

            myMethods = mfStats.FindMyMethods(myName);
            mfStats.FindInheritedMethods(superclasses, beforeOverriddenMethods);
            mfStats.FindInheritedMethods(interfaces, beforeOverriddenMethods);

            mfStats.ClearOverriddenMethods(beforeOverriddenMethods, inheritedMethods, myMethods);


            //Prints
            /*System.out.println(typeName + ": has these declared fields " + "[" + FieldsToString(myFields) + "]");
            System.out.println(typeName + ": has these inherited fields " + inheritedFields);
            System.out.println(typeName + ": has these declared methods" + "[" + MethodsToString(myMethods) + "]");
            System.out.println(typeName + ": has these inherited methods " + inheritedMethods);
            System.out.println(typeName + ": has these superclasses" + superclasses);
            System.out.println(typeName + ": has these interfaces" + interfaces);
            System.out.println(typeName + ": has these subtypes" + subtypes);*/
        }
        catch(ClassNotFoundException cnfe){
            System.out.println("Class not found");
        }
    }

    //Getters - setters
    public void setMyName(String myName)
    {
        this.myName = myName;
    }

    public String getMyName()
    {
        return myName;
    }

    public Field[] getMyFields()
    {
        return myFields;
    }

    public List<String> getInheritedFields()
    {
        return inheritedFields;
    }

    public Method[] getMyMethods()
    {
        return myMethods;
    }

    public List<String> getInheritedMethods()
    {
        return inheritedMethods;
    }

    public List<String> getAllSupertypes()
    {
        return allSupertypes;
    }

    public List<String> getSubtypes(){return subtypes;}

    //Utility
    private List<String> OnceEveryItem(List<String> list){
        List<String> nList = new ArrayList<String>();

        for (String s : list){
            if (!nList.contains(s)){
                nList.add(s);
            }
        }
        return nList;
    }


    //Prints
    private String FieldsToString(Field[] fields) {
        List<String> fieldsStrings = new ArrayList<>();
        for (Field f: fields) {
            String methodStr = FieldsToString(f);
            fieldsStrings.add(methodStr);
        }
        return String.join(", ", fieldsStrings);
    }

    private String FieldsToString(Field f) {
        return f.getName();
    }

    private String MethodsToString(Method[] methods) {
        List<String> methodStrings = new ArrayList<>();
        for (Method m : methods) {
            String methodStr = MethodsToString(m);
            methodStrings.add(methodStr);
        }
        return String.join(", ", methodStrings);
    }

    private String MethodsToString(Method m) {
        return m.getName();
    }
}
