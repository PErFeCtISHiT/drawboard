import drawboard.context.DefaultContext;
import drawboard.entity.RectEntity;
import drawboard.entity.TrailEntity;
import drawboard.service.PictureService;
import drawboard.service.PubService;
import drawboard.service.RectangleService;
import drawboard.service.TrailService;
import drawboard.serviceimpl.PictureServiceImpl;
import drawboard.serviceimpl.RectangleServiceImpl;
import drawboard.serviceimpl.TrailServiceImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 12:13 2018/9/9
 */

public class FunctionalTest {

    //resources
    private PictureService pictureService = new PictureServiceImpl();
    private RectangleService rectangleService = new RectangleServiceImpl();
    private PubService pubService = new PictureServiceImpl();
    private TrailService trailService = new TrailServiceImpl();

    @Test
    public void testPictureService(){

        //测试图片的数据加载方法
        assertEquals(10,pictureService.getTrails(1).size());
        assertEquals(2,pictureService.findAll().size());
        assertEquals(4,pictureService.getRects(1).size());
        assertEquals("test1",pictureService.findByID(1).getName());
    }

    @Test
    public void testPubService(){

        //测试id自增
        assertEquals(2,pubService.findLastID(DefaultContext.PICTUREPATH).intValue());
    }
    @Test
    public void testRectangleService(){
        RectEntity rectEntity = new RectEntity();
        rectEntity.setX0(0.0);
        rectEntity.setX1(10.0);
        rectEntity.setY0(0.0);
        rectEntity.setY1(10.0);
        Set<TrailEntity> trailEntities = new HashSet<>();
        TrailEntity trailEntity = new TrailEntity();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(DefaultContext.X,1.0);
        jsonObject.put(DefaultContext.Y,1.0);
        jsonArray.put(jsonObject);
        trailEntity.setPoints(jsonArray.toString());
        trailEntities.add(trailEntity);
        trailEntity = new TrailEntity();
        jsonObject = new JSONObject();
        jsonArray = new JSONArray();
        jsonObject.put(DefaultContext.X,1.0);
        jsonObject.put(DefaultContext.Y,11.0);
        jsonArray.put(jsonObject);
        trailEntity.setPoints(jsonArray.toString());
        trailEntities.add(trailEntity);


        //测试确定类型方法
        assertEquals("circle",rectangleService.defineType(rectEntity,trailEntities));
        Set<RectEntity> rectEntities = new HashSet<>();
        rectEntities.add(rectEntity);

        //测试选框方法
        assertEquals(rectEntity.getId(),rectangleService.judge(1.0,1.1,rectEntities).getId());
        assertNull(rectangleService.judge(11.0, 1.1, rectEntities));
    }

    @Test
    public void testTrailService(){

    }
}
