package demo2;

public class Member extends Workers{
    @Override
    void work() {
        if(this.getState()){
            //在岗
            System.out.print("正在工作...");
        }else{
            //不在岗
            System.out.println("已经下班");
        }
    }
}
