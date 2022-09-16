package demo2;

public abstract class Workers {
    private String name;
    private String department;
    private String job;
    private String id;
    private boolean state;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    Workers(){
        state = false;
    }

    /*
    * {1,2,3,4}
    * */
    abstract void work();
    void goToWork(){
        this.setState(true);
    }
    void leaveWork(){
        this.setState(false);
    }

    @Override
    public String toString() {
        return "";
    }
    public static double getGrades(String job,String department,int absence_num,int finish_state){
        return 100-absence_num+(finish_state-100)*0.25;
    }
}
