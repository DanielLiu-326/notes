package demo2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WorkerManager {
    HashMap<String,Workers> workers;
    WorkerManager(){
        workers = new HashMap<>();
    }
    void goToWork(String id){
        this.workers.get(id).goToWork();
    }
    void leaveWork(String id){
        this.workers.get(id).leaveWork();
    }
    void showWorkerState(Workers workers){
        workers.work();
    }
    Map<String ,Workers> getWorkerSet(){
        return this.workers;
    }
    void showAllWorkersState(){
        for(Workers x: workers.values()){
            x.work();
        }
    }
}
