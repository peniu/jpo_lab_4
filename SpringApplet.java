import java.awt.*;
import java.util.*;
import javax.swing.JApplet;

public class SpringApplet extends JApplet{
    private SimEngine engine;
    private SimTask task;
    private Timer timer;
    public void init() {
        this.engine = new SimEngine(2, 10, 0.3, 100, 10, 300, 150, 0, 0, 300, 50);
        this.task = new SimTask(engine, this, 0.1);
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(task,1000,100);
    }
    public void paint(Graphics g) {
        setSize(800,600);
        setBackground(Color.white);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.clearRect(0,0,getWidth(),getHeight());
        g2.setPaint(Color.black);
        //ustawienie poczatku rysowania w pkt zawieszenia
        double x0=engine.getZawX();
        double y0=engine.getZawY();
        //ustalenie wektora liny
        Vector2D lina = new Vector2D(engine.getWspXM()+engine.getZawX(),engine.getWspYM()+engine.getZawY());
        lina.rysowanieWektora(x0,y0,g2);
        //rysowanie owalu symbolizujacego mase
        g2.fillOval((int)engine.getWspXM(),(int)engine.getWspYM(),10,10);
    }
}