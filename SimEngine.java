public class SimEngine {
    //parametry symulacji
    private double masa,k,c,L0,g;
    //polozenie masy
    private double wspXM,wspYM;
    //predkosc masy
    private double vXM,vYM;
    //polozenie pkt zawieszenia
    private double zawX,zawY;
    private double C1,C2,C3,C4;
    private double n,W0;
    private double x0,y0;
    public void setX0(double x0){
        this.x0=x0;
    }
    public void setY0(double y0){
        this.y0=y0;
    }
    public double getX0(){
        return this.x0;
    }
    public double getY0(){
        return this.y0;
    }
    //akcesory
    //stale calkowania C1,C2 - dla y, C3,C4-dla x
    public static double czas;
    public void setN(double np){
        this.n=np;
    }
    public void setW0(double w0){
        this.W0=w0;
    }
    public double getN(){
        return n;
    }
    public double getW0(){
        return W0;
    }
    public void setC1(double c1){
        this.C1=c1;
    }
    public void setC2(double c2){
        this.C2=c2;
    }
    public void setC3(double c3) {
        this.C3=c3;
    }
    public void setC4(double c4){
        this.C4=c4;
    }
    public double getC1(){
        return C1;
    }
    public double getC2(){
        return C2;
    }
    public double getC3(){
        return C3;
    }
    public double getC4(){
        return C4;
    }
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
    public double getWspXM() {
        return wspXM;
    }
    public double getWspYM() {
        return wspYM;
    }
    public double getZawX() {
        return zawX;
    }
    public double getZawY() {
        return zawY;
    }
    public double getvXM() {
        return vXM;
    }
    public double getvYM() {
        return vYM;
    }
    public void setC(double c) {
        if(c>0){
            this.c = c;
        }
        else{
            this.c=0.5;
        }
    }
    public void setG(double g) {
        if(g>0){
            this.g = g;
        }
        else{
            this.g=10;
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
            this.L0=1;
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
    public void setvXM(double vXM) {
        this.vXM = vXM;
    }
    public void setvYM(double vYM) {
        this.vYM = vYM;
    }
    public void setWspXM(double wspXM) {
        this.wspXM = wspXM;
    }
    public void setWspYM(double wspYM) {
        this.wspYM = wspYM;
    }
    public void setZawX(double zawX) {
        this.zawX = zawX;
    }
    public void setZawY(double zawY) {
        this.zawY = zawY;
    }
    public SimEngine(double masa,double k,double c,double L0,double g,double wspXM,double wspYM,double vXM,double vYM,double zawX,double zawY){
        double y0=zawY+L0+(masa*g)/k;
        setY0(y0);
        double x0=zawX;
        setX0(x0);
        setN(c/(2*masa));
        setW0(Math.sqrt(k/masa));
        //ustawienie stalych calkowania w zaleznosci od delty rownania char r rozniczkowegos
        if(getN()==getW0()){
            setC1(y0);
            setC2(vYM+y0*getN());
            setC3(x0);
            setC4(vXM+x0*getN());
        }
        else if(getN()<getW0()){
            setC1(y0);
            double Wn=Math.sqrt(getW0()*getW0()-getN()*getN());
            setC2((vYM+getN()*getC1())/Wn);
            setC3(x0);
            setC4((vXM+getN()*getC3())/Wn);
        }
        else{
            setC2((vYM+y0*(getN()+Math.sqrt(getN()*getN()-getW0()*getW0())))/(2*getN()));
            setC1(y0-getC2());
            setC4((vXM+x0*(getN()+Math.sqrt(getN()*getN()-getW0()*getW0())))/(2*getN()));
            setC3(x0-getC4());
        }
        setMasa(masa);
        setK(k);
        setC(c);
        setL0(L0);
        setG(g);
        setWspXM(wspXM);
        setWspYM(wspYM);
        setvXM(vXM);
        setvYM(vYM);
        setZawX(zawX);
        setZawY(zawY);
        this.czas = 0;
    }
    public void sim(double time){
        //zapamietanie wartosci polozenia masy (do policzenia predkosci)
        double oldX=getWspXM();
        double oldY=getWspYM();
        czas=czas+time;
        //sprawdzenie typu tlumienia (delta r char r roz)
        if(getN()==getW0()){
            double Y=getC1()*Math.exp(-getN()*czas)+getC2()*czas*Math.exp(-getN()*czas);
            double X=(getC3()*Math.exp(-getN()*czas)+getC4()*czas*Math.exp(-getN()*czas));
            setWspYM(getY0()-Y);
            setWspXM(getX0()-X);
        }
        else if(getN()<getW0()){
            double Y=(Math.exp(-getN()*czas))*(getC1()*Math.cos(czas*Math.sqrt(getW0()*getW0()-getN()*getN()))+getC2()*Math.sin(czas*Math.sqrt(getW0()*getW0()-getN()*getN())));
            double X=(Math.exp(-getN()*czas))*(getC3()*Math.cos(czas*Math.sqrt(getW0()*getW0()-getN()*getN()))+getC4()*Math.sin(czas*Math.sqrt(getW0()*getW0()-getN()*getN())));
            setWspYM(getY0()-Y);
            setWspXM(getX0()-X);
        }
        else{
            double Y=getC1()*Math.exp(-czas*(getN()+Math.sqrt(getN()*getN()-getW0()*getW0())))+getC2()*Math.exp(-czas*(getN()+Math.sqrt(getN()*getN()-getW0()*getW0())));
            double X=getC3()*Math.exp(-czas*(getN()+Math.sqrt(getN()*getN()-getW0()*getW0())))+getC4()*Math.exp(-czas*(getN()+Math.sqrt(getN()*getN()-getW0()*getW0())));
            setWspYM(getY0()-Y);
            setWspXM(getX0()-X);
        }
        //obliczenie nowej predkosci masy
        setvYM((oldY-getWspYM())/time);
        setvXM((oldX-getWspXM())/time);

    }
    public void reset(){
        //zerowanie predkosci masy
        setvXM(0);
        setvYM(0);
    }
    public static void main(String[] args){
    }
}