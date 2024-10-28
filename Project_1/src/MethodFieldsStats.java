import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MethodFieldsStats
{

    private String myPackageName;

    public MethodFieldsStats(String myPackageName){
        this.myPackageName = myPackageName;
    }

    //Fields
    public Field[] FindMyFields(String typeName) throws ClassNotFoundException {
        if (typeName != null) {
            Class c = Class.forName(typeName);
            return c.getDeclaredFields();
        }
        return null;
    }

    public void FindInheritedFields(List<String> list, List<String> inheritedFields) throws ClassNotFoundException {

        Field[] fs;
        for (String str : list){
            fs = FindMyFields(str);
            for (Field f : fs){
                String temp = f.toString();
                if (temp.startsWith("public ") || temp.startsWith("protected ") || IsPackagePublicMethod(temp, str)){
                    inheritedFields.add(temp);
                }
            }
        }
    }

    //Methods
    public Method[] FindMyMethods(String typeName) throws ClassNotFoundException {
        if (typeName != null) {
            Class c = Class.forName(typeName);
            return c.getDeclaredMethods();
        }
        return null;
    }

    public void FindInheritedMethods(List<String> list, List<Method> beforeOverriddenMethods) throws ClassNotFoundException {
        Method[] ms;
        for (String str : list){
            ms = FindMyMethods(str);
            for (Method m : ms){
                String temp = m.toString();
                if (temp.startsWith("public ") || temp.startsWith("protected ") || IsPackagePublicMethod(temp, str)){
                    beforeOverriddenMethods.add(m);
                }
            }
        }
    }

    public void ClearOverriddenMethods(List<Method> beforeOverriddenMethods, List<String> inheritedMethods, Method[] myMethods)
    {
        Collections.addAll(beforeOverriddenMethods, myMethods);
        boolean overridden;
        for (int i = 0; i < beforeOverriddenMethods.size(); i++) {
            Method m1 = beforeOverriddenMethods.get(i);
            overridden = false;
            for (int j = i + 1; j < beforeOverriddenMethods.size(); j++) {
                Method m2 = beforeOverriddenMethods.get(j);
                overridden = MethodIsOverridden(m1, m2) || overridden;
            }
            if (!overridden) {
                inheritedMethods.add(m1.getName());
            }
        }
    }

    private boolean MethodIsOverridden(Method m1, Method m2){
        Class type1 = m1.getReturnType();
        Parameter[] paramTypes1 = m1.getParameters();

        Class type2 = m2.getReturnType();
        Parameter[] paramTypes2 = m2.getParameters();

        if(m1.getName().equals(m2.getName())) {
            if (type1 == type2) {
                if (paramTypes1.length == paramTypes2.length) {
                    if (AllTypesAreEqual(paramTypes1, paramTypes2)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean AllTypesAreEqual(Parameter[] paramTypes1, Parameter[] paramTypes2){
        for (int i = 0; i < paramTypes1.length; i++){
            if (paramTypes1[i].getType() != paramTypes2[i].getType()){
                return false;
            }
        }
        return true;
    }

    private boolean IsPackagePublicMethod(String typeName, String className) throws ClassNotFoundException{
        Class c = Class.forName(className);
        if (Objects.equals(myPackageName, c.getPackageName())){
            if (!typeName.startsWith("private ")) {
                return true;
            }
        }
        return false;
    }
}
