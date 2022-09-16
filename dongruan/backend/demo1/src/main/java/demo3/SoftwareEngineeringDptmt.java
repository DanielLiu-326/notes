package demo3;

public class SoftwareEngineeringDptmt extends Department {
    public SoftwareEngineeringDptmt() {
        this.setName("SoftwareEngineeringDepartment");
    }

    @Override
    void classBegin() {
        System.out.println("在读软件工程课程....");
    }
    void practice(){
        System.out.println("正在实习....");
    }
}
