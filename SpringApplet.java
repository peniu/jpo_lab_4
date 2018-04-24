import java.awt.*;
import java.util.*;
import javax.swing.JApplet;

public class SpringApplet extends JApplet{
    private SimEngine engine;
    private SimTask task;
    private Timer timer;
    public void init() {
        this.engine = new SimEngine(1, 4, 0.1, 200, 9.81,new Vector2D(600,300),new Vector2D(0,0),new Vector2D(400,50));
        this.task = new SimTask(engine, this, 1);
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(task,1000,10);
    }
    public void paint(Graphics g) {
        setSize(800,600);
        setBackground(Color.white);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.clearRect(0,0,getWidth(),getHeight());
        g2.setPaint(Color.black);
        Vector2D x = engine.getxM();
        Vector2D zaw = engine.getZaw();
        g2.drawLine((int)zaw.getWspX(),(int)zaw.getWspY(),(int)x.getWspX(),(int)x.getWspY());
        g2.fillOval((int)x.getWspX(),(int)x.getWspY(),10,10);
    }
}