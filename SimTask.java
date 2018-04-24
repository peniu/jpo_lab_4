import java.util.TimerTask;

public class SimTask extends TimerTask {
    private SimEngine engine;
    private SpringApplet applet;
    private double time;
    public SimTask(SimEngine eng,SpringApplet app,double t){
        this.engine=eng;
        this.applet=app;
        this.time=t;
    }
    public void run(){
        //uruchomienie obliczenia kolejnego kroku symulacji
        //odswiezenie powierzchni appletu
        engine.sim(time);
        applet.repaint();
    }
    public static void main(String[] args){
    }
}