package drawboard.service;

import drawboard.entity.PictureEntity;
import drawboard.entity.RectEntity;
import drawboard.entity.TrailEntity;

import java.util.List;
import java.util.Set;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 21:25 2018/9/5
 */
public interface PictureService extends PubService {
    Set<TrailEntity> getTrails(Integer id);

    List<PictureEntity> findAll();

    Set<RectEntity> getRects(Integer id);

    PictureEntity findByID(Integer id);
}
