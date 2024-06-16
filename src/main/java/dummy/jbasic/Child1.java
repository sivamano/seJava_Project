package dummy.jbasic;
//for method overriding
public class Child1 extends Parent1 {

    @Override
    public void method1() {
        super.method1();
        System.out.println("I am being invoked from child class");
        super.method2(14, 14);
        int a = 10, b= 15;
        super.method1();
    }
}
