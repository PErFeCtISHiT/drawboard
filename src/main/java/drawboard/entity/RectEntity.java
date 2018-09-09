package drawboard.entity;

/**
 * @author: pis
 * @description: 矩形框
 * @date: create in 21:21 2018/9/5
 */

public class RectEntity {
    private Integer id;


    //对应图片
    private Integer rectPicture;

    public Integer getRectPicture() {
        return rectPicture;
    }

    public void setRectPicture(Integer rectPicture) {
        this.rectPicture = rectPicture;
    }

    //记录下左上角和右下角的点
    private Double x0;
    private Double y0;
    private Double x1;
    private Double y1;

    //类型
    private String type;
    //标注
    private String mark;


    public Double getX0() {
        return x0;
    }

    public void setX0(Double x0) {
        this.x0 = x0;
    }

    public Double getY0() {
        return y0;
    }

    public void setY0(Double y0) {
        this.y0 = y0;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }


    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
