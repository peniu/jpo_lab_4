import java.awt.*;
import java.util.*;
import javax.swing.JApplet;
import java.awt.geom.*;

public class SpringApplet extends JApplet {
    private SimEngine engine;
    private SimTask task;
    private Timer timer;

    //metody do rysowania do zad na bdb
    //rysowanie siatki wspolrzednych
    public void siatka(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.lightGray);
        for (int i = 0; i < 800; i = i + 50) {
            g2.draw(new Line2D.Double(i, 0, i, 600));
        }
        for (int i = 0; i < 600; i = i + 50) {
            g2.draw(new Line2D.Double(0, i, 800, i));
        }
    }
    //rysowanie oznaczenia utwierdzenia
    public void utwierdzenie(Graphics g, Vector2D zaw) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);
        g2.draw(new Line2D.Double(zaw.getWspX() - 40, zaw.getWspY(), zaw.getWspX() + 40, zaw.getWspY()));
        for (int i = (int) zaw.getWspX() - 35; i < zaw.getWspX() + 40; i = i + 10) {
            g2.draw(new Line2D.Double(i, zaw.getWspY(), i + 10, zaw.getWspY() - 10));
        }
    }
    //rysowanie sprezyny
    public void sprezyna(Graphics2D g, Vector2D zaw, Vector2D x) {
        Graphics2D g2 = (Graphics2D) g;
        Vector2D sprez = x.roznica(zaw);
        Vector2D kier_sprez = sprez.norm();
        Vector2D obrot = new Vector2D(-kier_sprez.getWspY(), kier_sprez.getWspX());
        g2.draw(new Line2D.Double(zaw.getWspX(), zaw.getWspY(), zaw.getWspX() + 25 * kier_sprez.getWspX(), zaw.getWspY() + 25 * kier_sprez.getWspY()));
        g2.draw(new Line2D.Double(x.getWspX(), x.getWspY(), x.getWspX() - 25 * kier_sprez.getWspX(), x.getWspY() - 25 * kier_sprez.getWspY()));
        int il_troj = 40;
        int obw_troj = 30;
        double h = (sprez.dlugosc() - 50) / il_troj;
        double a = (obw_troj - h) / 2;
        double y = Math.sqrt(a * a - h * h / 4);
        Vector2D H = kier_sprez.iloczyn(h / 2);
        double x0 = zaw.getWspX() + 25 * kier_sprez.getWspX();
        double y0 = zaw.getWspY() + 25 * kier_sprez.getWspY();
        Vector2D A1 = new Vector2D();
        Vector2D A2 = new Vector2D();
        Vector2D Y1 = obrot.iloczyn(y);
        Vector2D Y2 = obrot.iloczyn(-y);
        for (int i = 0; i < il_troj; i++) {
            if (i % 2 == 0) {
                A1 = Y1.suma(H);
                A2 = Y2.suma(H);
            } else {
                A1 = Y2.suma(H);
                A2 = Y1.suma(H);
            }
            g2.draw(new Line2D.Double(x0, y0, x0 + A1.getWspX(), y0 + A1.getWspY()));
            g2.draw(new Line2D.Double(x0 + A1.getWspX(), y0 + A1.getWspY(), x0 + A1.getWspX() + A2.getWspX(), y0 + A1.getWspY() + A2.getWspY()));
            x0 = x0 + A1.getWspX() + A2.getWspX();
            y0 = y0 + A1.getWspY() + A2.getWspY();
        }
    }

    public void init() {
        this.engine = new SimEngine(20, 3, 0.3, 200, 9.81, new Vector2D(600, 300), new Vector2D(0, 0), new Vector2D(400, 50));
        this.task = new SimTask(engine, this, 0.1);
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(task, 100, 10);
    }
    public void paint(Graphics g) {
        setSize(800, 600);
        setBackground(Color.white);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.clearRect(0, 0, getWidth(), getHeight());
        siatka(g2);
        g2.setPaint(Color.black);
        Vector2D x = engine.getxM();
        Vector2D zaw = engine.getZaw();
        utwierdzenie(g2, zaw);
        sprezyna(g2, zaw, x);
        g2.fillOval((int) x.getWspX() - 10, (int) x.getWspY() - 10, 20, 20);
        double x0=x.getWspX();
        double y0=x.getWspY();
        //rysowanie wektorów sił i prędkości masy
        g2.setColor(Color.green);
        engine.getvM().rysowanieWektora(x0,y0,g2);
        g2.setColor(Color.red);
        engine.getFgraw().rysowanieWektora(x0,y0,g2);
        g2.setColor(Color.cyan);
        engine.getFwisk().rysowanieWektora(x0,y0,g2);
        g2.setColor(Color.magenta);
        engine.getFsprez().rysowanieWektora(x0,y0,g2);
        g2.setColor(Color.blue);
        engine.getFwyp().rysowanieWektora(x0,y0,g2);
    }
}