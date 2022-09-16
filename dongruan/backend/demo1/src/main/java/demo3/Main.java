package demo3;

public class Main {
    public static void main(String[] args) {
        SoftwareEngineeringDptmt sfDptmt = new SoftwareEngineeringDptmt();
        sfDptmt.addClass(123);
        System.out.println(sfDptmt);
        sfDptmt.addClass(456);
        System.out.println(sfDptmt);
        sfDptmt.classBegin();
        sfDptmt.practice();
    }
}
