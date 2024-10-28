public class FindBestN
{
    int bestN;


    public FindBestN(int n)
    {
        this.bestN = n;
    }

    public ClassStats[] FirstA(ClassStats[] stats)
    {
        ClassStats[] array = stats;
        ClassStats temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < (array.length - i); j++) {
                if (array[j - 1].getMyFields().length <= array[j].getMyFields().length) {
                    temp = array[j - 1];
                    array[j - 1] = stats[j];
                    array[j] = temp;
                }
            }
        }
        return GetFirstNElements(array);
    }

    public ClassStats[] FirstB(ClassStats[] stats)
    {
        ClassStats[] array = stats;
        ClassStats temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < (array.length - i); j++) {
                if (array[j - 1].getInheritedFields().size() + array[j - 1].getMyFields().length <= array[j].getInheritedFields().size() + array[j].getMyFields().length) {
                    temp = array[j - 1];
                    array[j - 1] = stats[j];
                    array[j] = temp;
                }
            }
        }
        return GetFirstNElements(array);
    }

    public ClassStats[] SecondA(ClassStats[] stats)
    {
        ClassStats[] array = stats;
        ClassStats temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < (array.length - i); j++) {
                if (array[j - 1].getMyMethods().length <= array[j].getMyMethods().length) {
                    temp = array[j - 1];
                    array[j - 1] = stats[j];
                    array[j] = temp;
                }
            }
        }
        return GetFirstNElements(array);
    }

    public ClassStats[] SecondB(ClassStats[] stats)
    {
        ClassStats[] array = stats;
        ClassStats temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < (array.length - i); j++) {
                if (array[j - 1].getInheritedMethods().size() <= array[j].getInheritedMethods().size()) {
                    temp = array[j - 1];
                    array[j - 1] = stats[j];
                    array[j] = temp;
                }
            }
        }
        return GetFirstNElements(array);
    }

    public ClassStats[] Third(ClassStats[] stats)
    {
        ClassStats[] array = stats;
        ClassStats temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < (array.length - i); j++) {
                if (array[j - 1].getSubtypes().size() <= array[j].getSubtypes().size()) {
                    temp = array[j - 1];
                    array[j - 1] = stats[j];
                    array[j] = temp;
                }
            }
        }
        return GetFirstNElements(array);
    }

    public ClassStats[] Fourth(ClassStats[] stats)
    {
        ClassStats[] array = stats;
        ClassStats temp;
        for (int i = 0; i < array.length; i++) {
            for (int j = 1; j < (array.length - i); j++) {
                if (array[j - 1].getAllSupertypes().size() <= array[j].getAllSupertypes().size()) {
                    temp = array[j - 1];
                    array[j - 1] = stats[j];
                    array[j] = temp;
                }
            }
        }
        return GetFirstNElements(array);
    }

    ClassStats[] GetFirstNElements(ClassStats[] array)
    {
        if (array.length > bestN) {
            ClassStats[] temp = new ClassStats[bestN];
            for (int i = 0; i < bestN; i++) {
                temp[i] = array[i];
            }
            return temp;
        }
        else{
            return array;
        }
    }
}
