package drawboard.serviceimpl;

import drawboard.context.DefaultContext;
import drawboard.entity.PictureEntity;
import drawboard.entity.RectEntity;
import drawboard.entity.TrailEntity;
import drawboard.service.PictureService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 21:27 2018/9/5
 */

public class PictureServiceImpl extends PubServiceImpl implements PictureService {

    private static final Logger log = LoggerFactory.getLogger(PictureServiceImpl.class);

    /**
     * @param id 图片id
     * @author:pis
     * @description: 根据图片id得到画痕
     * @date: 15:44 2018/9/8
     */
    @Override
    public Set<TrailEntity> getTrails(Integer id) {
        Set<TrailEntity> trailEntities = new HashSet<>();
        Path path = Paths.get(DefaultContext.TRAILPATH);
        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String str = bufferedReader.readLine();
            while (str != null && str.length() > 0) {
                JSONObject jsonObject = new JSONObject(str);
                TrailEntity trailEntity = new TrailEntity();
                trailEntity.setId(jsonObject.getInt(DefaultContext.ID));
                trailEntity.setPoints(jsonObject.getString(DefaultContext.POINTS));
                trailEntity.setPictureTrail(jsonObject.getInt(DefaultContext.PICTURETRAIL));
                if (id.equals(trailEntity.getPictureTrail()))
                    trailEntities.add(trailEntity);
                str = bufferedReader.readLine();
            }
        } catch (IOException e) {
            log.error(DefaultContext.FILE_NOT_FOUND);
        }
        return trailEntities;
    }


    /**
     * @author:pis
     * @description: 找到所有图片
     * @date: 22:11 2018/9/8
     */
    @Override
    public List<PictureEntity> findAll() {
        List<PictureEntity> pictureEntities = new ArrayList<>();
        Path path = Paths.get(DefaultContext.PICTUREPATH);
        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String str = bufferedReader.readLine();
            while (str != null && str.length() > 0) {
                JSONObject jsonObject = new JSONObject(str);
                PictureEntity pictureEntity = new PictureEntity();
                pictureEntity.setId(jsonObject.getInt(DefaultContext.ID));
                pictureEntity.setName(jsonObject.getString(DefaultContext.NAME));
                pictureEntities.add(pictureEntity);
                str = bufferedReader.readLine();
            }
        } catch (IOException e) {
            log.error(DefaultContext.FILE_NOT_FOUND);
        }
        return pictureEntities;
    }


    /**
     * @param id 图片id
     * @author:pis
     * @description: 得到所有矩形框
     * @date: 22:12 2018/9/8
     */
    @Override
    public Set<RectEntity> getRects(Integer id) {
        Set<RectEntity> rectEntities = new HashSet<>();
        Path path = Paths.get(DefaultContext.RECTPATH);
        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String str = bufferedReader.readLine();
            while (str != null && str.length() > 0) {
                JSONObject jsonObject = new JSONObject(str);
                RectEntity rectEntity = new RectEntity();
                rectEntity.setId(jsonObject.getInt(DefaultContext.ID));
                rectEntity.setRectPicture(jsonObject.getInt(DefaultContext.RECTPICTURE));
                rectEntity.setX0(jsonObject.getDouble(DefaultContext.X0));
                rectEntity.setX1(jsonObject.getDouble(DefaultContext.X1));
                rectEntity.setY0(jsonObject.getDouble(DefaultContext.Y0));
                rectEntity.setY1(jsonObject.getDouble(DefaultContext.Y1));
                rectEntity.setType(jsonObject.getString(DefaultContext.TYPE));
                rectEntity.setMark(jsonObject.getString(DefaultContext.MARK));
                if (id.equals(rectEntity.getRectPicture()))
                    rectEntities.add(rectEntity);
                str = bufferedReader.readLine();
            }
        } catch (IOException e) {
            log.error(DefaultContext.FILE_NOT_FOUND);
        }
        return rectEntities;
    }


    /**
     * @param id 图片id
     * @author:pis
     * @description: 根据id找图片
     * @date: 22:31 2018/9/8
     */
    @Override
    public PictureEntity findByID(Integer id) {
        Path path = Paths.get(DefaultContext.PICTUREPATH);
        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String str = bufferedReader.readLine();
            while (str != null && str.length() > 0) {
                JSONObject jsonObject = new JSONObject(str);
                PictureEntity pictureEntity = new PictureEntity();
                pictureEntity.setId(jsonObject.getInt(DefaultContext.ID));
                pictureEntity.setName(jsonObject.getString(DefaultContext.NAME));
                if (pictureEntity.getId().equals(id)) {
                    return pictureEntity;
                }
                str = bufferedReader.readLine();
            }
        } catch (IOException e) {
            log.error(DefaultContext.FILE_NOT_FOUND);
        }
        return null;
    }
}
