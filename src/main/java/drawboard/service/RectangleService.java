package drawboard.service;

import drawboard.entity.RectEntity;
import drawboard.entity.TrailEntity;

import java.util.Set;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 21:26 2018/9/5
 */
public interface RectangleService extends PubService {

    String defineType(RectEntity rect, Set<TrailEntity> trails);

    RectEntity judge(Double x0, Double y0, Set<RectEntity> rectEntities);
}
