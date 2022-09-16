package demo3;

public class Department {
    int id;
    String  name;
    int stuNum;
    int classNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStuNum() {
        return stuNum;
    }

    public void setStuNum(Integer stuNum) {
        this.stuNum = stuNum;
    }

    public Integer getClassNum() {
        return classNum;
    }

    public void setClassNum(Integer classNum) {
        this.classNum = classNum;
    }

    void classBegin(){
        System.out.println("正在上课...");
    }
    void addClass(int stuNum){
        this.classNum +=1;
        this.stuNum+= stuNum;
    }
    void addClass(int stuNum1,int stuNum2){
        this.addClass(stuNum1);
        this.addClass(stuNum2);
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", stuNum=" + stuNum +
                ", classNum=" + classNum +
                '}';
    }
}
