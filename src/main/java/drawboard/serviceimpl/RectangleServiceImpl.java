package drawboard.serviceimpl;

import drawboard.context.DefaultContext;
import drawboard.entity.RectEntity;
import drawboard.entity.TrailEntity;
import drawboard.service.RectangleService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Set;

/**
 * @author: pis
 * @description: good good study
 * @date: create in 21:27 2018/9/5
 */

public class RectangleServiceImpl extends PubServiceImpl implements RectangleService {

    /**
     * @param rect   矩形框
     * @param trails 墨迹
     * @author:pis
     * @description: 根据笔画数判断类型
     * @date: 22:08 2018/9/8
     */
    @Override
    public String defineType(RectEntity rect, Set<TrailEntity> trails) {
        int count = 0;
        for (TrailEntity trail : trails) {
            JSONArray jsonArray = new JSONArray(trail.getPoints());
            for (Object object : jsonArray) {
                JSONObject jsonObject = (JSONObject) object;
                Double x = jsonObject.getDouble(DefaultContext.X);
                Double y = jsonObject.getDouble(DefaultContext.Y);
                if (x <= rect.getX1() && x >= rect.getX0() && y <= rect.getY1() && y >= rect.getY0()) {
                    count++;
                    break;
                }
            }
        }
        switch (count) {
            case 1:
                return "circle";
            case 2:
                return "triangle";
            case 3:
                return "rectangle";
            case 4:
                return "square";
            default:
                return "unknown";
        }
    }


    /**
     * @param x0           坐标
     * @param y0           坐标
     * @param rectEntities 矩形框
     * @author:pis
     * @description: 判断是否选中矩形框(坐标是否在一个矩形框内)
     * @date: 22:09 2018/9/8
     */
    @Override
    public RectEntity judge(Double x0, Double y0, Set<RectEntity> rectEntities) {
        for (RectEntity rectEntity : rectEntities) {
            if (x0 >= rectEntity.getX0() && x0 <= rectEntity.getX1() &&
                    y0 >= rectEntity.getY0() && y0 <= rectEntity.getY1())
                return rectEntity;
        }
        return null;
    }
}
