package demo2;

import java.util.Map;

public class Main {
    public static Workers createManager(String id, String name, String department){
        Workers worker = new Manager();
        worker.setName(name);
        worker.setId(id);
        worker.setDepartment(department);
        return worker;
    }
    public static Workers createMember(String id, String name, String department){
        Workers worker = new Member();
        worker.setName(name);
        worker.setId(id);
        worker.setDepartment(department);
        return worker;
    }
    public static void main(String[] args) {
        WorkerManager workerManager= new WorkerManager();
        Map<String , Workers>  workers = workerManager.getWorkerSet();
        {
            Workers worker =
                    createManager("123456","danny","adsf");
            workers.put(worker.getId(),worker);
        }
        {
            Workers worker =
                    createManager("123457","tom","dsfa");
            workers.put(worker.getId(),worker);
        }
    ////////////////memebers
        {
            Workers worker =
                    createMember("123458","test","adsf");
            workers.put(worker.getId(),worker);
        }
        {
            Workers worker =
                    createMember("123459","dafs","adsf");
            workers.put(worker.getId(),worker);
        }
        {
            Workers worker =
                    createMember("123460","dfsf","dsfa");
            workers.put(worker.getId(),worker);
        }
        {
            Workers worker =
                    createMember("123461","weruiow","dsfa");
            workers.put(worker.getId(),worker);
        }
        {
            Workers worker =
                    createMember("123462","werdsf","dsfa");
            workers.put(worker.getId(),worker);
            worker.getState();
        }
        workerManager.goToWork("123462");
        
    }
}
