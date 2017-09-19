
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

public class Thread1 extends Thread {
    MainControl m;
    TreeNode t;
    TextNode tn;
    TreeNode[] t1,t2;
    int x,y,w,h;
    int[][][] c0,c1;
    BufferedImage img;
    LinkedList[] s;
    int th,start,end,j;
    int start1,end1;
    double d;
    ArrayList<TreeNode> c;
    static ArrayList<TreeNode> tr;
    //ArrayList<LinkedList> ch;
    LinkedList s1,s2;
    ArrayList a;
    double[] we;
    int del;
    private boolean b;
    public Thread1(TextNode tn,LinkedList s1,int start,int end) {
        this.tn=tn;
        this.start=start;
        this.end=end;
        this.s1=s1;
        this.th=-6;
    }
    public Thread1(TextNode tn,LinkedList s1,int start,int end,int th) {
        this.tn=tn;
        this.start=start;
        this.end=end;
        this.s1=s1;
        this.th=th;
    }
    public Thread1(MainControl m,int start,int end,int start1,int end1,int j) {
        this.m=m;
        this.start=start;
        this.end=end;
        this.start1=start1;
        this.end1=end1;
        this.th=-2;
        this.j=j;
    }
    public Thread1(MainControl m,int start,int end,int j) {
        this.m=m;
        this.start=start;
        this.end=end;
        this.j=j;
        this.th=-3;
    }
    public Thread1(MainControl m,int j,int th) {
        this.j=j;
        this.th=th;
        this.m=m;
    }
    public Thread1(MainControl m,LinkedList[] s,int start,int end,int j,int th) {
        this.m=m;
        this.s=s;
        this.start=start;
        this.end=end;
        this.th=th;
        this.j=j;
    }
    public Thread1(MainControl m,int start,int end,int j,int th) {
        this.m=m;
        this.start=start;
        this.end=end;
        this.th=th;
        this.j=j;
    }
    public Thread1(TreeNode t,ArrayList<TreeNode> c,int start,int end,int[][][] c0,int[][][] c1,BufferedImage img,int x,int y,int w,int h) {
        this.t=t;
        this.c=c;
        this.start=start;
        this.end=end;
        this.c0=c0;
        this.c1=c1;
        this.w=w;
        this.h=h;
        this.x=x;
        this.y=y;
        this.img=img;
        this.th=-5;
    }
    public Thread1(TextNode tn,TreeNode[] t1,int start,int end,TreeNode[] t2,int start1,int end1,double[] we,double e,LinkedList s,int j) {
        this.tn=tn;
        this.t1=t1;
        this.start=start;
        this.end=end;
        this.t2=t2;
        this.start1=start1;
        this.end1=end1;
        this.we=we;
        this.d=e;
        this.s1=s;
        this.j=j;
        this.th=-8;
    }
    public Thread1(int th) {
        this.th=th;
    }
    public Thread1(LinkedList out,LinkedList in,int start,int end) {
        this.s1=out;
        this.s2=in;
        this.start=start;
        this.end=end;
        this.th=-16;
    }
    public static void gc() {
        Thread1 t1=new Thread1(-9);
        t1.start();
        return;
    }
    public static void paint(ArrayList<TreeNode> tr) {
        Thread1 t1=new Thread1(-10);
        Thread1.tr=tr;
        t1.start();
        return;
    }
    @Override
    public void run() {
        if(this.th==-1)
            this.s=this.m.run1(s,start,end,j);
        else if(this.th==-15)
            this.m.run(s, j,start,end);
        else if(this.th==-2)
            this.m.findOuterRegions_1(start,end,start1,end1,j);
        else if(this.th==-3)
           this.m.extremal(start, end,j);
        else if(this.th==-4)
            this.m.cr(start,end,j);
        else if(this.th==-5)
            this.t.extend_1(c,start,end,c0,c1,img,x,y,w,h);
        else if(this.th==-6)
            a=this.tn.maxmin_1(s1, start, end);
        else if(this.th==-7)
            x=this.tn.sum_1(s1, start, end);
        else if(this.th==-8)
            s1=this.tn.min(t1,start,end,t2,start1,end1,we,d,s1,j);
        else if(this.th==-9)
            System.gc();/*
        else if(this.th==-10) {
            First f=new First();
            f.paint_u(tr);
        }
        */else if(this.th==-11)
            this.m.findRegions_1(j);
        else if(this.th==-12)
            this.m.findOuterRegions(j);
        else if(this.th==-13)
            this.m.extremal_1(j);
        else if(this.th==-14)
            m.add(j);
        else if(this.th==-16)
            b=TreeNode.contains(s1,s2,start,end);
    }
    public ArrayList<TreeNode> get() { return this.c; }
    //public ArrayList<LinkedList> getC() { return this.ch; }
    public LinkedList[] getP() { return this.s; }
    public LinkedList getC() { return this.s1; }
    public ArrayList getA() { return this.a; }
    public int getS() { return this.x; }
    public double getD() { return this.d; }
    public boolean getB() { return this.b; }
}