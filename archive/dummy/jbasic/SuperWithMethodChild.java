package dummy.jbasic;

public class SuperWithMethodChild extends SuperWithMethodParent{
    void person()
    {
        System.out.println("This person is Child");
    }

    void display()
    {
        person();
        super.person();
    }
}
