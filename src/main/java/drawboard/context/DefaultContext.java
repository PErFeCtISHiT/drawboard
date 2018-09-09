package drawboard.context;

/**
 * @author: pis
 * @description: 默认上下文环境
 * @date: create in 19:19 2018/9/5
 */
public class DefaultContext {
    public static final String SKETCHPAD = "Sketchpad";
    public static final String DRAW = "DRAW";
    public static final String CLEAR = "CLEAR";
    public static final String MARK = "MARK";
    public static final String SELECT = "SELECT";
    public static final Double RECTSIZE = 500.0;
    public static final Double CANVASSIZE = 300.0;
    public static final Double PENSIZE = 2.0;
    public static final String X = "x";
    public static final String Y = "y";
    public static final String USER_DIR = "user.dir";
    public static final String RESOURCES = "/resources/";
    public static final String FILE_NOT_FOUND = "file not found";
    public static final String RECTPATH = System.getProperty(USER_DIR) + RESOURCES + "rect.txt";
    public static final String PICTUREPATH = System.getProperty(USER_DIR) + RESOURCES + "picture.txt";
    public static final String NAME = "name";
    public static final String CONFIRM = "confirm";
    public static final String SAVE_FAILED = "save failed";
    public static final String PICTURES = "pictures";
    public static final String SAVE = "save";
    public static final String NEWPICTURE = "new picture";
    public static final String TYPEANDMARK = "type and mark";
    public static final String NEW = "new";
    public static final String SUCCESS = "Bootup successful";
    public static final String NEW_LINE = System.getProperty("line.separator");
    public static final String TRAILPATH = System.getProperty(USER_DIR) + RESOURCES + "trail.txt";

    private DefaultContext() {
        throw new IllegalStateException("context class");
    }


}
