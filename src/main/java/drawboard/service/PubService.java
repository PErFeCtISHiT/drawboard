package drawboard.service;

/**
 * @author: pis
 * @description: service的共有接口
 * @date: create in 21:24 2018/9/5
 */
public interface PubService {

    boolean add(String path,String data);


    Integer findLastID(String path);
}
