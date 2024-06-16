package dummy.jbasic;

public class SuperWithVariablesChild extends SuperWithVariablesParent {

    String name = "Mark Antony";

    public void method1(){
        System.out.println("Even though the name in this class is Mark it will return John only, " +
                "because i am using super keyword with variables \n");
        System.out.println("returned name: "+super.name);
    }

}
