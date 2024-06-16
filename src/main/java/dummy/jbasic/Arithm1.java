package dummy.jbasic;
// COMPLILE-TIIME POLYMORPHISIM -> METHOD OVERLOADING
// OVERLOADING BY CHANGING NUMBER OF ARGUMENTS
public class Arithm1 {

    int multiply(int a, int b)
    {
        int c = a*b;
        return c;
    }

    int multiply(int a, int b, int c)
    {
        int d = a*b*c;
        return d;
    }
}
