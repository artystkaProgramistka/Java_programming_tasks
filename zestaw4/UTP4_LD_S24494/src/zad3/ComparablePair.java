package zad3;

class ComparablePair implements Comparable<ComparablePair> {
    int x;
    String y;

    public ComparablePair(int x, String y)
    {
        this.x = x;
        this.y = y;
    }

    public String toString()
    {
        return "(" + x + "," + y + ")";
    }

    @Override public int compareTo(ComparablePair a)
    {
        // if the string are not equal
        if (x != a.x) {
            return x - a.x;
        }
        else {
            // we compare int values
            // if the strings are equal
            return y.compareTo(a.y);
        }
    }
}