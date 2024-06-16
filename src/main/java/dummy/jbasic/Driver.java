package dummy.jbasic;

public class Driver {
    public static void main(String[] args) {
       /* Child1 obj = new Child1();
        obj.method1();
*/

        /*
        // COMPLILE-TIIME POLYMORPHISIM -> METHOD OVERLOADING
        // OVERLOADING BY CHANGING TYPE OF ARGUMENTS
        Arithm arithm1 = new Arithm();
        System.out.println(arithm1.multiply(2,6));
        System.out.println(arithm1.multiply(45.98,78.36));
*/

/*
        // COMPLILE-TIIME POLYMORPHISIM -> METHOD OVERLOADING
        // OVERLOADING BY CHANGING NUMBER OF ARGUMENTS
        Arithm1 arithm1 = new Arithm1();
        System.out.println(arithm1.multiply(2,6));
        System.out.println(arithm1.multiply(6,7,18));
*/

/*

        //RUNTIME POLYMORPHISM -> METHOD OVERRIDING
        RunTimePolymorphParent a;

        a = new RunTimePolymorphChild1();
        a.method1();

        a = new RunTimePolymorphChild2();
        a.method1();

*/
/*

        // SUPER KEYWORD with VARIABLES
        SuperWithVariablesChild a = new SuperWithVariablesChild();
        a.method1();
*/
/*

        //SUPER KEYWORD with METHOD
        SuperWithMethodChild a =new SuperWithMethodChild();
        a.display();
*/

        //SUPER KEYWORD WITH CONSTRUCTOR
        SuperWithConstructorChild a = new SuperWithConstructorChild();
    }
}
