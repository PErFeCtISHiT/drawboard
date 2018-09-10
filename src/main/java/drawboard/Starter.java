package drawboard;

import drawboard.context.DefaultContext;
import drawboard.entity.PictureEntity;
import drawboard.entity.RectEntity;
import drawboard.entity.TrailEntity;
import drawboard.service.PictureService;
import drawboard.service.RectangleService;
import drawboard.service.TrailService;
import drawboard.serviceimpl.PictureServiceImpl;
import drawboard.serviceimpl.RectangleServiceImpl;
import drawboard.serviceimpl.TrailServiceImpl;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author: pis
 * @description: 启动类
 * @date: create in 18:29 2018/9/5
 */


public class Starter extends Application {
    private static final Logger log = LoggerFactory.getLogger(Starter.class);

    //服务类实例
    private RectangleService rectangleService = new RectangleServiceImpl();
    private TrailService trailService = new TrailServiceImpl();
    private PictureService pictureService = new PictureServiceImpl();
    //方框
    private RectEntity rect;
    //绘制的图案
    private PictureEntity picture;

    //画框记录点
    private Double x0;
    private Double x1;
    private Double y0;
    private Double y1;

    //选框状态
    private RectEntity rectState;

    //画痕
    private TrailEntity trail;
    private JSONArray jsonArray;

    //文字显示
    private Label typeString;
    private TextField markString;

    //保存
    private Button saveMark;


    //画布
    private Canvas canvas;
    private GraphicsContext graphicsContext;


    /**
     * @author:pis
     * @description: 绘制背景
     * @date: 18:53 2018/9/5
     */

    private void drawBackground(Rectangle rect) {

        rect.setFill(new LinearGradient(0, 0, 1, 1, true,
                CycleMethod.REFLECT,
                new Stop(0, Color.RED),
                new Stop(1, Color.YELLOW)));
    }

    /**
     * @author:pis
     * @description: 重制画布
     * @date: 18:54 2018/9/5
     */

    private void reset(Canvas canvas) {

        typeString.setText("");
        markString.setText("");
        saveMark.setDisable(true);
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.GRAY);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    /**
     * @author:pis
     * @description: 初始化choiceBox组件
     * @date: 20:07 2018/9/10
     */
    private ChoiceBox<String> initChoiceBox() {
        class PictureChangedListener implements ChangeListener<Number> {

            /**
             * @param observable The {@code ObservableValue} which value changed
             * @param oldValue   The old value
             * @param newValue  The new value
             */
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                reset(canvas);
                picture = pictureService.findByID(newValue.intValue() + 1);
                Set<TrailEntity> trailEntities = pictureService.getTrails(newValue.intValue() + 1);
                Set<RectEntity> rectEntities = pictureService.getRects(newValue.intValue() + 1);
                for (TrailEntity trailEntity : trailEntities) {
                    JSONArray array = new JSONArray(trailEntity.getPoints());
                    for (Object o : array) {
                        JSONObject jsonObject = (JSONObject) o;
                        double x = jsonObject.getDouble(DefaultContext.X);
                        double y = jsonObject.getDouble(DefaultContext.Y);
                        graphicsContext.clearRect(x - 2, y - 2, DefaultContext.PENSIZE, DefaultContext.PENSIZE);
                    }
                }
                for (RectEntity rectEntity : rectEntities) {
                    graphicsContext.strokeRect(rectEntity.getX0(), rectEntity.getY0(), rectEntity.getX1() - rectEntity.getX0(), rectEntity.getY1() - rectEntity.getY0());
                }
            }
        }

        PictureChangedListener pictureChangedListener = new PictureChangedListener();

        List<String> pictures = new ArrayList<>();
        List<PictureEntity> pictureEntities = pictureService.findAll();
        for (PictureEntity pictureEntity : pictureEntities) {
            pictures.add(pictureEntity.getName());
        }
        ChoiceBox<String> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(pictures));
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(pictureChangedListener);
        choiceBox.setTooltip(new Tooltip(DefaultContext.PICTURES));
        return choiceBox;
    }

    /**
     * @author:pis
     * @description: 第一排HBob的初始化
     * @date: 19:45 2018/9/10
     */
    private HBox initTopBox() {
        HBox top = new HBox();
        top.setPadding(new Insets(5, 5, 0, 5));
        top.setSpacing(10);

        class DrawMousePress implements EventHandler<MouseEvent> {
            /**
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                trail = new TrailEntity();
                trail.setId(trailService.findLastID(DefaultContext.TRAILPATH) + 1);
                trail.setPictureTrail(picture.getId());
                jsonArray = new JSONArray();
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(DefaultContext.X, event.getX());
                jsonObject.put(DefaultContext.Y, event.getY());
                jsonArray.put(jsonObject);
            }
        }

        class DrawMouseReleased implements EventHandler<MouseEvent> {

            /**
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(DefaultContext.X, event.getX());
                jsonObject.put(DefaultContext.Y, event.getY());
                trail.setPoints(jsonArray.toString());
                String trailStr = trail.getId() + " " + trail.getPictureTrail() + " " + trail.getPoints() + DefaultContext.NEW_LINE;
                if (!trailService.add(DefaultContext.TRAILPATH, trailStr)) {
                    log.error(DefaultContext.SAVE_FAILED);
                }
            }
        }

        class DrawMouseDragged implements EventHandler<MouseEvent> {

            /**
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                graphicsContext.clearRect(event.getX() - 2, event.getY() - 2, DefaultContext.PENSIZE, DefaultContext.PENSIZE);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(DefaultContext.X, event.getX());
                jsonObject.put(DefaultContext.Y, event.getY());
                jsonArray.put(jsonObject);
            }
        }

        class SelectMousePress implements EventHandler<MouseEvent> {
            /**
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                x0 = event.getX();
                y0 = event.getY();

                Set<RectEntity> rectEntities = pictureService.getRects(picture.getId());
                rectState = rectangleService.judge(x0, y0, rectEntities);
            }
        }

        class SelectMouseReleased implements EventHandler<MouseEvent> {
            /**
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                if (rectState == null) {
                    String type;
                    x1 = event.getX();
                    y1 = event.getY();
                    rect = new RectEntity();
                    rect.setId(rectangleService.findLastID(DefaultContext.RECTPATH) + 1);
                    rect.setRectPicture(picture.getId());
                    rect.setX0(Math.min(x0, x1));
                    rect.setX1(Math.max(x0, x1));
                    rect.setY0(Math.min(y0, y1));
                    rect.setY1(Math.max(y0, y1));
                    saveMark.setDisable(false);
                    //确定类型
                    type = rectangleService.defineType(rect, pictureService.getTrails(picture.getId()));
                    typeString.setText(type);
                    markString.setText(DefaultContext.MARK);
                    rect.setType(type);
                    graphicsContext.strokeRect(Math.min(x0, x1), Math.min(y0, y1), Math.abs(x0 - x1), Math.abs(y0 - y1));
                } else {
                    saveMark.setDisable(true);
                    typeString.setText(rectState.getType());
                    markString.setText(rectState.getMark());
                }
            }
        }

        class SelectMouseDragged implements EventHandler<MouseEvent> {
            /**
             * @param event the event which occurred
             */
            @Override
            public void handle(MouseEvent event) {
                //do nothing
            }
        }

        SelectMouseDragged selectMouseDragged = new SelectMouseDragged();
        SelectMouseReleased selectMouseReleased = new SelectMouseReleased();
        SelectMousePress selectMousePress = new SelectMousePress();
        DrawMousePress drawMousePress = new DrawMousePress();
        DrawMouseDragged drawMouseDragged = new DrawMouseDragged();
        DrawMouseReleased drawMouseReleased = new DrawMouseReleased();

        //图片选择组件
        ChoiceBox<String> choiceBox = initChoiceBox();

        //绘制
        Button drawButton = new Button(DefaultContext.DRAW);
        drawButton.setOnAction(e -> {
            canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, selectMousePress);
            canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, selectMouseReleased);
            canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, selectMouseDragged);
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, drawMousePress);
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, drawMouseReleased);
            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, drawMouseDragged);
        });


        //选框
        Button selectButton = new Button(DefaultContext.SELECT);
        selectButton.setOnAction(e -> {
            canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, selectMousePress);
            canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, selectMouseReleased);
            canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, selectMouseDragged);
            canvas.removeEventHandler(MouseEvent.MOUSE_PRESSED, drawMousePress);
            canvas.removeEventHandler(MouseEvent.MOUSE_RELEASED, drawMouseReleased);
            canvas.removeEventHandler(MouseEvent.MOUSE_DRAGGED, drawMouseDragged);
        });


        top.getChildren().addAll(choiceBox, drawButton, selectButton);
        return top;
    }

    /**
     * @author:pis
     * @description: 第二排的HBox的初始化
     * @date: 19:43 2018/9/10
     */
    private HBox initMiddleBox() {
        //字符串
        HBox strings = new HBox();
        strings.setPadding(new Insets(5, 5, 0, 5));
        strings.setSpacing(10);
        typeString = new Label();
        markString = new TextField();
        saveMark = new Button();
        saveMark.setDisable(true);
        saveMark.setText(DefaultContext.SAVEMARK);
        saveMark.setOnAction(e -> {
            rect.setMark(markString.getText());
            String str = rect.getId() + " " + rect.getRectPicture() + " " + rect.getX0()
                    + " " + rect.getX1() + " " + rect.getY0() + " " + rect.getY1() + " " + rect.getType() + " " + rect.getMark() + DefaultContext.NEW_LINE;
            if (!rectangleService.add(DefaultContext.RECTPATH, str)) {
                log.error(DefaultContext.SAVE_FAILED);
            }
            saveMark.setDisable(true);
        });

        Label strLabel = new Label(DefaultContext.TYPEANDMARK);

        strings.getChildren().addAll(strLabel, typeString, markString, saveMark);

        return strings;
    }

    /**
     * @author:pis
     * @description: 第三排HBox的初始化
     * @date: 19:48 2018/9/10
     */
    private HBox initBottomBox() {
        HBox bottom = new HBox();
        bottom.setPadding(new Insets(5, 5, 0, 5));
        bottom.setSpacing(10);
        Label saveLabel = new Label(DefaultContext.NEWPICTURE);
        TextField textField = new TextField();
        textField.setText(DefaultContext.NAME);
        Button newPicture = new Button(DefaultContext.NEWPICTURE);
        newPicture.setOnAction(e -> {
            reset(canvas);
            picture.setName(textField.getText());
            picture.setId(pictureService.findLastID(DefaultContext.PICTUREPATH) + 1);
            String str = picture.getId() + " " + picture.getName() + DefaultContext.NEW_LINE;
            if (!pictureService.add(DefaultContext.PICTUREPATH, str)) {
                log.error(DefaultContext.SAVE_FAILED);
            }
        });
        bottom.getChildren().addAll(saveLabel, textField, newPicture);
        return bottom;
    }


    /**
     * @param primaryStage stage to show
     * @author:pis
     * @description: 重载application的start方法
     * @date: 11:59 2018/9/6
     */
    @Override
    public void start(Stage primaryStage) {

        //初始化时新建一个图片对象
        picture = new PictureEntity();
        picture.setId(pictureService.findLastID(DefaultContext.PICTUREPATH) + 1);


        Group root = new Group();

        //底板
        Rectangle rectangle = new Rectangle(DefaultContext.RECTSIZE, DefaultContext.RECTSIZE);
        drawBackground(rectangle);
        root.getChildren().add(rectangle);

        //第一排Box
        HBox top = initTopBox();
        //第二排Box
        HBox middle = initMiddleBox();
        //第三排Box
        HBox bottom = initBottomBox();


        canvas = new Canvas(DefaultContext.CANVASSIZE, DefaultContext.CANVASSIZE);
        canvas.setTranslateX((DefaultContext.RECTSIZE - DefaultContext.CANVASSIZE) / 2);
        canvas.setTranslateY((DefaultContext.RECTSIZE - DefaultContext.CANVASSIZE) / 2 + 50);
        reset(canvas);

        //canvas的上下文
        graphicsContext = canvas.getGraphicsContext2D();


        //整合
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(5, 5, 0, 5));
        vBox.setSpacing(10);
        vBox.getChildren().addAll(top, middle, bottom);
        root.getChildren().addAll(vBox, canvas);

        //scene,css,stage
        Scene scene = new Scene(root, DefaultContext.RECTSIZE, DefaultContext.RECTSIZE);
        scene.getStylesheets().add(this.getClass().getClassLoader().getResource("MistSilverSkin.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle(DefaultContext.SKETCHPAD);
        primaryStage.show();

        log.info(DefaultContext.SUCCESS);

    }


    public static void main(String[] args) {
        launch(args);
    }

}