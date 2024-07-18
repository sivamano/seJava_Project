package dummy.jbasic;

public class Child extends Parent {

    public Child() {
        double pi = super.pi;
    }

    public static void main(String[] args) {
        calcCir();
    }

    public static double calcCir()
    {
        int r=9;
        double cirOCir = 2*(pi)*r;
        System.out.println(cirOCir);
        return cirOCir;
    }
}
