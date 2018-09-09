package drawboard.entity;

/**
 * @author: pis
 * @description: 画痕
 * @date: create in 21:17 2018/9/5
 */

public class TrailEntity {
    private Integer id;
    //画痕归属的图片
    private Integer pictureTrail;
    //点迹
    private String points;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPictureTrail() {
        return pictureTrail;
    }

    public void setPictureTrail(Integer pictureTrail) {
        this.pictureTrail = pictureTrail;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}
