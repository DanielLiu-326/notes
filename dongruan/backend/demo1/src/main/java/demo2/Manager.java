package demo2;

import javax.xml.crypto.Data;
import java.sql.Time;
import java.util.Date;

public class Manager extends Workers{

    @Override
    void work() {
        Date date = new Date(System.currentTimeMillis());
        ;
        if(date.getHours()>=18){
            System.out.print("休息");
            return;
        }
        if(this.getState()){
            //在岗
            System.out.print("经理现场监督");
        }else{
            //不在岗
            System.out.println("经理线上管理");
        }
    }

}
