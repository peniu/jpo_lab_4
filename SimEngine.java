public class SimEngine {
    //parametry symulacji
    private double masa,k,c,L0,g;
    //wektory polozenia i predkosci masy i polozenia pkt zawieszenia
    private Vector2D xM,vM,zaw;
    //akcesory
    public double getC() {
        return c;
    }
    public double getK() {
        return k;
    }
    public double getG() {
        return g;
    }
    public double getL0() {
        return L0;
    }
    public double getMasa() {
        return masa;
    }
    public Vector2D getxM(){return xM;}
    public Vector2D getvM(){return vM;}
    public Vector2D getZaw(){return zaw;}
    public void setC(double c) {
        if(c>0){
            this.c = c;
        }
        else{
            this.c=1;
        }
    }
    public void setG(double g) {
        if(g>0){
            this.g = g;
        }
        else{
            this.g=9.81;
        }
    }
    public void setK(double k) {
        if(k>0){
            this.k = k;
        }
        else{
            this.k=1;
        }
    }
    public void setL0(double l0) {
        if(l0>0){
            this.L0 = l0;
        }
        else{
            this.L0=100;
        }
    }
    public void setMasa(double masa) {
        if(masa>0){
            this.masa = masa;
        }
        else{
            this.masa=1;
        }
    }
    public void setxM(Vector2D x){
        this.xM=x;
    }
    public void setvM(Vector2D v){
        this.vM=v;
    }
    public void setZaw(Vector2D z){
        this.zaw=z;
    }
    //konstruktor z parametrami
    public SimEngine(double masa,double k,double c,double L0,double g,Vector2D x,Vector2D v,Vector2D z){
        setMasa(masa);
        setK(k);
        setC(c);
        setL0(L0);
        setG(g);
        setxM(x);
        setvM(v);
        setZaw(z);
    }
    //metoda symulujaca ruch masy
    public void sim(double time){
        Vector2D oldX=xM;
        //wektory sil grawitacji i tlumienia
        Vector2D Fgraw = new Vector2D(0,getMasa()*getG());
        Vector2D Fwisk = vM.iloczyn(-getC());
        Vector2D sprezyna = new Vector2D(xM.getWspX()-zaw.getWspX(),xM.getWspY()-zaw.getWspY());
        double L=sprezyna.dlugosc();
        Vector2D kier_sprez=sprezyna.norm();
        //wektor sily sprezystosci (kierunek wzdluz sprezyny)
        Vector2D Fsprez = kier_sprez.iloczyn(getK()*(getL0()-L));
        Vector2D wyp1 = Fsprez.suma(Fwisk);
        //sila wypadkowa
        Vector2D Fwyp = wyp1.suma(Fgraw);
        //przyspieszenie wypadkowe
        Vector2D a = Fwyp.iloczyn(1/getMasa());
        //this.vM = vM.suma(a.iloczyn(time));
        this.xM = xM.suma(a.iloczyn(time*time/2));
        this.vM = oldX.roznica(xM).iloczyn(1/time);
        System.out.println("pyk");
        //Fwisk.info();
        Fsprez.info();
        Fwyp.info();
        vM.info();
    }
    public void reset(){
        //zerowanie predkosci masy
        this.vM=new Vector2D(0,0);
    }
    public static void main(String[] args){
    }
}