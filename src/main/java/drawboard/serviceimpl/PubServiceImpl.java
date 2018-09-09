package drawboard.serviceimpl;

import drawboard.context.DefaultContext;
import drawboard.service.PubService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 21:26 2018/9/5
 */

public class PubServiceImpl implements PubService {
    private static final Logger log = LoggerFactory.getLogger(PubServiceImpl.class);


    /**
     * @param path 接收添加的文件路径
     * @param data 添加的内容
     * @author:pis
     * @description: 添加一条记录
     * @date: 23:16 2018/9/8
     */
    @Override
    public boolean add(String path, String data) {
        File file = new File(path);
        try (FileWriter fileWriter = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)){
            bufferedWriter.write(data);
            return true;
        } catch (Exception e) {
            log.error(DefaultContext.FILE_NOT_FOUND);
            return false;
        }
    }


    /**
     * @param path 寻找的文件路径
     * @author:pis
     * @description: 找到最大的id
     * @date: 23:16 2018/9/8
     */
    @Override
    public Integer findLastID(String path) {
        Path path1 = Paths.get(path);
        try (BufferedReader bufferedReader = Files.newBufferedReader(path1, StandardCharsets.UTF_8)) {
            String str = bufferedReader.readLine();
            Integer ret = 0;
            while (str != null && str.length() > 0) {
                ret = Integer.valueOf(str.split(" ")[0]);
                str = bufferedReader.readLine();
            }
            return ret;
        } catch (Exception e) {
            log.error(DefaultContext.FILE_NOT_FOUND);
            return -1;
        }
    }
}
