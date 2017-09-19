import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class MainControl {
	int xmin,ymin,w,h;
	TreeNode root;
        ArrayList<LinkedList> s;
	int[][][] temp;
	int[][][] regions,outer;
	int[][][] child0,child1;
        int[] thresh;
        int[][] pixels,minI,maxI;
	BufferedImage img;
        
        public MainControl(MainControl m) {
            this.pixels=m.pixels;this.outer=m.outer;this.child0=m.child0;this.child1=m.child1;
            this.thresh=m.thresh;this.minI=m.minI;this.maxI=m.maxI;this.regions=m.regions;this.img=m.img;
            this.temp=m.temp;this.w=m.w;this.h=m.h;this.xmin=m.xmin;this.ymin=m.ymin;this.root=m.root;
        }
        
	public MainControl(BufferedImage img) {
		this.img=img;
  		xmin=img.getMinX();
		ymin=img.getMinY();
		h=img.getHeight();
		w=img.getWidth();
                thresh=new int[2];
                s=new ArrayList<LinkedList>();
		pixels=new int[w][h];
                temp=new int[thresh.length][w][h];
		regions=new int[thresh.length][w][h];
		outer=new int[thresh.length][w][h];
                child0=new int[thresh.length][w][h];
                child1=new int[thresh.length][w][h];
                thresh[0]=160;
                thresh[1]=180;
                //thresh[2]=140;
		for(int y=ymin;y<h;y++)
			for(int x=xmin;x<w;x++)
                            pixels[x][y]=grayLevel(x,y,img);
                root=new TreeNode(null,-1,-1);
		//System.gc();
	}
	
	public static int grayLevel(int x,int y,BufferedImage img) {
				Color c = new Color(img.getRGB(x, y));
				int red = (int)c.getRed();
				int green = (int)c.getGreen();
				int blue = (int)c.getBlue();
				int sum = (red + green + blue)/3;
				return sum;
	}
	
        public void cr(int start,int end,int th) {
            for(int i=start;i<end;i++) {
                    minI[th][i]=Integer.MAX_VALUE;
                    maxI[th][i]=Integer.MIN_VALUE;
                    for(int y=ymin;y<h;y++)
                        for(int x=xmin;x<w;x++) {
                            if(regions[th][x][y]==i && minI[th][i]>pixels[x][y])
                                minI[th][i]=pixels[x][y];
                            else if(regions[th][x][y]==i && maxI[th][i]<pixels[x][y])
                                maxI[th][i]=pixels[x][y];
                        }
                    //System.out.println(th+"\t"+minI[th][i]+"\t"+maxI[th][i]);
                }
                for(int y=ymin;y<h;y++)
                        for(int x=xmin;x<w;x++) {
                                    if(pixels[x][y]<=Math.round((maxI[th][regions[th][x][y]]-minI[th][regions[th][x][y]])/2))
                                        child0[th][x][y]=regions[th][x][y];
                                    else
                                        child1[th][x][y]=regions[th][x][y];
                        }
        }
        
        public ArrayList<TreeNode> findRegions() {
            System.out.println("started");
            Thread1 t1,t2,t3;
            t1=new Thread1(this,0,-11);
            t2=new Thread1(this,1,-11);
            //t3=new Thread1(this,2,-11);
            t1.start();t2.start();//t3.start();
            try {
                t1.join();t2.join();//t3.join();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
            t1=new Thread1(this,0,-12);
            t2=new Thread1(this,1,-12);
            //t3=new Thread1(this,2,-12);
            t1.start();t2.start();//t3.start();
            try {
                t1.join();t2.join();//t3.join();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
            t1=new Thread1(this,0,-13);
            t2=new Thread1(this,1,-13);
            //t3=new Thread1(this,2,-13);
            t1.start();t2.start();//t3.start();
            try {
                t1.join();t2.join();//t3.join();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
            t1=new Thread1(this,0,-14);
            t2=new Thread1(this,1,-14);
            //t3=new Thread1(this,2,-14);
            t1.start();t2.start();//t3.start();
            try {
                t1.join();t2.join();//t3.join();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Final Tree Size : "+root.size());
            Thread1.gc();
            root.extend(child0,child1,img,xmin,ymin,w,h);
            System.out.println("After Extend Tree Size : "+root.size());
            root.computeVar();
            root.setChilds(root.detectMSER());
            System.out.println("MSER Tree Size : "+root.size());
            root.minreg();
            Thread1.gc();
            root=TreeNode.lr(root);
            System.out.println("After Linear Reduction MSER Tree Size : "+root.size());
            ArrayList<TreeNode> t=TreeNode.ta(root);
            TextNode clus=new TextNode(t);
            System.out.println("After Tree Accumulation MSER Tree Size : "+clus.size());
            t=clus.detectText();
            System.out.println("Finished");
			return t;
        }
        
        public synchronized void findOuterRegions(int th) {
            MainControl m=new MainControl(this);
            if(w>=7&&h>=7) {
                    Thread1 t1,t2,t3,t4,t5,t6,t7;
                    t1=new Thread1(m,0,h/7,0,w/7,th);
                    t2=new Thread1(m,h/7,2*(h/7),w/7,2*(w/7),th);
                    t3=new Thread1(m,2*(h/7),3*(h/7),2*(w/7),3*(w/7),th);
                    t4=new Thread1(m,3*(h/7),4*(h/7),3*(w/7),4*(w/7),th);
                    t5=new Thread1(m,4*(h/7),5*(h/7),4*(w/7),5*(w/7),th);
                    t6=new Thread1(m,5*(h/7),6*(h/7),5*(w/7),6*(w/7),th);
                    t7=new Thread1(m,6*(h/7),h,6*(w/7),w,th);
                    t1.start();
                    t2.start();
                    t3.start();
                    t4.start();
                    t5.start();
                    t6.start();
                    t7.start();
                    try {
                        t1.join();
                        t2.join();
                        t3.join();
                        t4.join();
                        t5.join();
                        t6.join();
                        t7.join();
                    }
                    catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                else
                    m.findOuterRegions_1(0,h,0,w,th);
                this.reverse(m);
        }
        
        public synchronized void extremal_1(int th) {
            System.out.print("For threshold : "+thresh[th]+"\t");
            MainControl m=new MainControl(this);
            int l=size(-1,th);
                if(l>=30) {
                    Thread1 t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
                    Thread1 t11,t12,t13,t14,t15,t16,t17,t18,t19,t20;
                    Thread1 t21,t22,t23,t24,t25,t26,t27,t28,t29,t30;
                    t1=new Thread1(m,0,l/30,th);
                    t2=new Thread1(m,l/30,2*(l/30),th);
                    t3=new Thread1(m,2*(l/30),3*(l/30),th);
                    t4=new Thread1(m,3*(l/30),4*(l/30),th);
                    t5=new Thread1(m,4*(l/30),5*(l/30),th);
                    t6=new Thread1(m,5*(l/30),6*(l/30),th);
                    t7=new Thread1(m,6*(l/30),7*(l/30),th);
                    t8=new Thread1(m,7*(l/30),8*(l/30),th);
                    t9=new Thread1(m,8*(l/30),9*(l/30),th);
                    t10=new Thread1(m,9*(l/30),10*(l/30),th);
                    t11=new Thread1(m,10*(l/30),11*(l/30),th);
                    t12=new Thread1(m,11*(l/30),12*(l/30),th);
                    t13=new Thread1(m,12*(l/30),13*(l/30),th);
                    t14=new Thread1(m,13*(l/30),14*(l/30),th);
                    t15=new Thread1(m,14*(l/30),15*(l/30),th);
                    t16=new Thread1(m,15*(l/30),16*(l/30),th);
                    t17=new Thread1(m,16*(l/30),17*(l/30),th);
                    t18=new Thread1(m,17*(l/30),18*(l/30),th);
                    t19=new Thread1(m,18*(l/30),19*(l/30),th);
                    t20=new Thread1(m,19*(l/30),20*(l/30),th);
                    t21=new Thread1(m,20*(l/30),21*(l/30),th);
                    t22=new Thread1(m,21*(l/30),22*(l/30),th);
                    t23=new Thread1(m,22*(l/30),23*(l/30),th);
                    t24=new Thread1(m,23*(l/30),24*(l/30),th);
                    t25=new Thread1(m,24*(l/30),25*(l/30),th);
                    t26=new Thread1(m,25*(l/30),26*(l/30),th);
                    t27=new Thread1(m,26*(l/30),27*(l/30),th);
                    t28=new Thread1(m,27*(l/30),28*(l/30),th);
                    t29=new Thread1(m,28*(l/30),29*(l/30),th);
                    t30=new Thread1(m,29*(l/30),l,th);
                    t1.start();
                    t2.start();
                    t3.start();
                    t4.start();
                    t5.start();
                    t6.start();
                    t7.start();
                    t8.start();
                    t9.start();
                    t10.start();
                    t11.start();
                    t12.start();
                    t13.start();
                    t14.start();
                    t15.start();
                    t16.start();
                    t17.start();
                    t18.start();
                    t19.start();
                    t20.start();
                    t21.start();
                    t22.start();
                    t23.start();
                    t24.start();
                    t25.start();
                    t26.start();
                    t27.start();
                    t28.start();
                    t29.start();
                    t30.start();
                    try {
                        t1.join();
                        t2.join();
                        t3.join();
                        t4.join();
                        t5.join();
                        t6.join();
                        t7.join();
                        t8.join();
                        t9.join();
                        t10.join();
                        t11.join();
                        t12.join();
                        t13.join();
                        t14.join();
                        t15.join();
                        t16.join();
                        t17.join();
                        t18.join();
                        t19.join();
                        t20.join();
                        t21.join();
                        t22.join();
                        t23.join();
                        t24.join();
                        t25.join();
                        t26.join();
                        t27.join();
                        t28.join();
                        t29.join();
                        t30.join();
                    }
                    catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                else
                    m.extremal(0,l,th);
                this.reverse(m);
		System.out.println("Detected Extremal Regions : "+size(-1,th));
        }
        
	public synchronized void findRegions_1(int th) {
                int f=-1,r=0;
                System.out.print("For threshold : "+thresh[th]+"\t");
		for(int y=ymin;y<h;y++)
			for(int x=xmin;x<w;x++) {
				outer[th][x][y]=regions[th][x][y]=-1;
				if(pixels[x][y]<=thresh[th])
					temp[th][x][y]=0;
				else
					temp[th][x][y]=1;
			}
		for(int y=ymin;y<h;y++)
			for(int x=xmin;x<w;x++) {
				int i;
                                child0[th][x][y]=child1[th][x][y]=-1;
				i=temp[th][x][y];
				if(check(x-1,y,i,th)) {
					regions[th][x][y]=regions[th][x-1][y];
					continue;
				}
                                if(check(x-1,y-1,i,th)) {
					regions[th][x][y]=regions[th][x-1][y-1];
					continue;
				}
                                if(check(x,y-1,i,th)) {
					regions[th][x][y]=regions[th][x][y-1];
					continue;
				}
                                if(check(x+1,y-1,i,th)) {
					regions[th][x][y]=regions[th][x+1][y-1];
				}
                                else {
					regions[th][x][y]=++f;
                                }
			}
                r+=size(-1,th);
                minI=new int[thresh.length][r];
                maxI=new int[thresh.length][r];
                MainControl m=new MainControl(this);
                if(r>=20) {
                    Thread t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20;
                    t1=new Thread1(m,0,r/20,th,-4);
                    t2=new Thread1(m,r/20,2*(r/20),th,-4);
                    t3=new Thread1(m,2*(r/20),3*(r/20),th,-4);
                    t4=new Thread1(m,3*(r/20),4*(r/20),th,-4);
                    t5=new Thread1(m,4*(r/20),5*(r/20),th,-4);
                    t6=new Thread1(m,5*(r/20),6*(r/20),th,-4);
                    t7=new Thread1(m,6*(r/20),7*(r/20),th,-4);
                    t8=new Thread1(m,7*(r/20),8*(r/20),th,-4);
                    t9=new Thread1(m,8*(r/20),9*(r/20),th,-4);
                    t10=new Thread1(m,9*(r/20),10*(r/20),th,-4);
                    t11=new Thread1(m,10*(r/20),11*(r/20),th,-4);
                    t12=new Thread1(m,11*(r/20),12*(r/20),th,-4);
                    t13=new Thread1(m,12*(r/20),13*(r/20),th,-4);
                    t14=new Thread1(m,13*(r/20),14*(r/20),th,-4);
                    t15=new Thread1(m,14*(r/20),15*(r/20),th,-4);
                    t16=new Thread1(m,15*(r/20),16*(r/20),th,-4);
                    t17=new Thread1(m,16*(r/20),17*(r/20),th,-4);
                    t18=new Thread1(m,17*(r/20),18*(r/20),th,-4);
                    t19=new Thread1(m,18*(r/20),19*(r/20),th,-4);
                    t20=new Thread1(m,19*(r/20),r,th,-4);
                    t1.start();t2.start();t3.start();t4.start();t5.start();t6.start();t7.start();
                    t8.start();t9.start();t10.start();t11.start();t12.start();t13.start();t14.start();
                    t15.start();t16.start();t17.start();t18.start();t19.start();t20.start();
                    try {
                      t1.join();t2.join();t3.join();t4.join();t5.join();t6.join();t7.join();
                      t8.join();t9.join();t10.join();t11.join();t12.join();t13.join();t14.join();
                      t15.join();t16.join();t17.join();t18.join();t19.join();t20.join();
                    }
                    catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                else
                    m.cr(0,r,th);
                this.reverse(m);
		//System.gc();
		System.out.println("Detected Regions : "+r);
		//System.gc();
                /*root.extend(child0,child1,img,xmin,ymin,w,h);
                System.out.println("After Extend Tree Size : "+root.size());
                //TreeNode.computeVar(root);
                //System.out.println("Variation of 100th child : "+root.getChilds().get(101).getVar());
                root.setChilds(root.detectMSER());
                System.out.println("MSER Tree Size : "+root.size());
		//System.gc();
                root=TreeNode.lr(root);
                System.out.println("After Linear Reduction MSER Tree Size : "+root.size());
                TextNode clus=new TextNode(TreeNode.ta(root));
                System.out.println("After Tree Accumulation MSER Tree Size : "+clus.size());
                double[] w=new double[7];
                for(int i=0;i<w.length;i++)
                    w[i]=0.1;
                //System.out.println("Top Level Clusters : "+clus.formClus(w, 0.2).size());
                clus.detectText();
                /*
                double[] x=clus.features(clus.get(16).getData(), clus.get(19).getData());
                for(int i=0;i<x.length;i++)
                    System.out.println(x[i]);
                System.out.println(clus.get(16).getData().size()+" : "+clus.get(16).getRid()+"\t"+clus.get(19).getData().size()+"  : "+clus.get(19).getRid()+"\t"+clus.dist(clus.get(16).getData(), clus.get(19).getData(),w));*/
	}
        
        public synchronized LinkedList[] run1(LinkedList[] s,int start,int end,int th) {
            for(int i=start;i<end;i++) {
			s[i]=new LinkedList();
			for(int y=ymin;y<h;y++)
				for(int x=xmin;x<w;x++)
					if(regions[th][x][y]==i) {
						LinkedList b=new LinkedList();
						b.add(x);b.add(y);b.add(img.getRGB(x,y));
						s[i].add(b);
					}
                }
            return s;
        }
        
	public synchronized void add(int th) {
                System.out.print("After Forming Tree with threshold : "+thresh[th]+"\t");
                LinkedList[] s=new LinkedList[size(-1,th)];
                Thread1 t1,t2,t3,t4,t5,t6,t7,t8,t9,t10;
                MainControl m=new MainControl(this);
                if(s.length>=10) {
                    t3=new Thread1(m,s,2*(s.length/10),3*(s.length/10),th,-1);
                    t1=new Thread1(m,s,0,s.length/10,th,-1);
                    t2=new Thread1(m,s,s.length/10,2*(s.length/10),th,-1);
                    t4=new Thread1(m,s,3*(s.length/10),4*(s.length)/10,th,-1);
                    t5=new Thread1(m,s,4*(s.length/10),5*(s.length)/10,th,-1);
                    t6=new Thread1(m,s,5*(s.length/10),6*(s.length)/10,th,-1);
                    t7=new Thread1(m,s,6*(s.length/10),7*(s.length)/10,th,-1);
                    t8=new Thread1(m,s,7*(s.length/10),8*(s.length)/10,th,-1);
                    t9=new Thread1(m,s,8*(s.length/10),9*(s.length)/10,th,-1);
                    t10=new Thread1(m,s,9*(s.length/10),s.length,th,-1);
                    t1.start();
                    t2.start();
                    t3.start();
                    t4.start();
                    t5.start();
                    t6.start();
                    t7.start();
                    t8.start();
                    t9.start();
                    t10.start();
                    try {
                        t1.join();
                        t2.join();
                        t3.join();
                        t4.join();
                        t5.join();
                        t6.join();
                        t7.join();
                        t8.join();
                        t9.join();
                        t10.join();
                    }
                    catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                    System.arraycopy(s,0,t1.getP(),0,s.length/10);
                    System.arraycopy(s,s.length/10,t2.getP(),s.length/10,s.length/10);
                    System.arraycopy(s,2*(s.length/10),t3.getP(),2*(s.length/10),s.length/10);
                    System.arraycopy(s,3*(s.length/10),t4.getP(),3*(s.length/10),s.length/10);
                    System.arraycopy(s,4*(s.length/10),t5.getP(),4*(s.length/10),s.length/10);
                    System.arraycopy(s,5*(s.length/10),t6.getP(),5*(s.length/10),s.length/10);
                    System.arraycopy(s,6*(s.length/10),t7.getP(),6*(s.length/10),s.length/10);
                    System.arraycopy(s,7*(s.length/10),t8.getP(),7*(s.length/10),s.length/10);
                    System.arraycopy(s,8*(s.length/10),t9.getP(),8*(s.length/10),s.length/10);
                    System.arraycopy(s,9*(s.length/10),t10.getP(),9*(s.length/10),s.length/10);
                    //System.out.println(t2.getP().length);
                }
                else
                    s=m.run1(s,0,s.length,th);
                this.reverse(m);
                if(s.length>=10) {
                    t1=new Thread1(m,s,0,s.length/10,th,-15);
                    t2=new Thread1(m,s,s.length/10,2*(s.length/10),th,-15);
                    t3=new Thread1(m,s,2*(s.length/10),3*(s.length/10),th,-15);
                    t4=new Thread1(m,s,3*(s.length/10),4*(s.length/10),th,-15);
                    t5=new Thread1(m,s,4*(s.length/10),5*(s.length/10),th,-15);
                    t6=new Thread1(m,s,5*(s.length/10),6*(s.length/10),th,-15);
                    t7=new Thread1(m,s,6*(s.length/10),7*(s.length/10),th,-15);
                    t8=new Thread1(m,s,7*(s.length/10),8*(s.length/10),th,-15);
                    t9=new Thread1(m,s,8*(s.length/10),9*(s.length/10),th,-15);
                    t10=new Thread1(m,s,9*(s.length/10),s.length,th,-15);
                    t1.start();
                    t2.start();
                    t3.start();
                    t4.start();
                    t5.start();
                    t6.start();
                    t7.start();
                    t8.start();
                    t9.start();
                    t10.start();
                    try {
                        t1.join();
                       // System.out.println("Thread 1");
                        t2.join();
                       // System.out.println("Thread 2");
                        t3.join();
                       // System.out.println("Thread 3");
                        t4.join();
                       // System.out.println("Thread 4");
                        t5.join();
                       // System.out.println("Thread 5");
                        t6.join();
                       // System.out.println("Thread 6");
                        t7.join();
                       // System.out.println("Thread 7");
                        t8.join();
                       // System.out.println("Thread 8");
                        t9.join();
                       // System.out.println("Thread 9");
                        t10.join();
                        //System.out.println("Thread 10");
                    }
                    catch(Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                else
                    m.run(s,th,0,s.length);
                this.reverse(m);m=null;
                System.out.println("Tree Size : "+root.numChild());
        }
        
        public void reverse(MainControl m) {
            this.pixels=m.pixels;this.outer=m.outer;this.child0=m.child0;this.child1=m.child1;
            this.thresh=m.thresh;this.minI=m.minI;this.maxI=m.maxI;this.regions=m.regions;
            this.temp=m.temp;this.w=m.w;this.h=h;this.xmin=m.xmin;this.ymin=m.ymin;this.root=m.root;
        }
        
        public synchronized void run(LinkedList[] s,int th,int start,int end) {
            TreeNode temp;
            for(int i=start;i<end;i++) {
                    temp = new TreeNode(s[i],th,i);
                    if(root.numChild()==0) {
                        root.addChild(temp);
                        continue;
                    }
                    ArrayList<TreeNode> t=root.getSet();boolean flag=false;
                    //run_1(t,t.size());
                    for(int j=t.size()-1;j>-1;j--) {
                        LinkedList a=new LinkedList(),b=new LinkedList();
                        //System.out.println("Adding Child (threshold,region_id,size) : ("+thresh[th]+","+i+","+temp.getData().size()+") to : "+t.get(j).getData().size());
                        boolean cond1=true,cond2=true;
                        LinkedList out,in;
                        if(temp.getTh()==t.get(j).getTh()) {
                            cond1=cond2=false;
                        }
                        else if(s[i].size()<=t.get(j).getData().size()) {
                            cond2=false;
                            out=t.get(j).getData();
                            in=s[i];
                            int mod=s[i].size();
                            if(mod>=40) {
                                Thread1 t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,t28,t29,t30,t31,t32,t33,t34,t35,t36,t37,t38,t39,t40;
                                t1=new Thread1(out,in,0,mod/40);
                                t2=new Thread1(out,in,mod/40,2*(mod/40));
                                t3=new Thread1(out,in,2*(mod/40),3*(mod/40));
                                t4=new Thread1(out,in,3*(mod/40),4*(mod/40));
                                t5=new Thread1(out,in,4*(mod/40),5*(mod/40));
                                t6=new Thread1(out,in,5*(mod/40),6*(mod/40));
                                t7=new Thread1(out,in,6*(mod/40),7*(mod/40));
                                t8=new Thread1(out,in,7*(mod/40),8*(mod/40));
                                t9=new Thread1(out,in,8*(mod/40),9*(mod/40));
                                t10=new Thread1(out,in,9*(mod/40),10*(mod/40));
                                t11=new Thread1(out,in,10*(mod/40),11*(mod/40));
                                t12=new Thread1(out,in,11*(mod/40),12*(mod/40));
                                t13=new Thread1(out,in,12*(mod/40),13*(mod/40));
                                t14=new Thread1(out,in,13*(mod/40),14*(mod/40));
                                t15=new Thread1(out,in,14*(mod/40),15*(mod/40));
                                t16=new Thread1(out,in,15*(mod/40),16*(mod/40));
                                t17=new Thread1(out,in,16*(mod/40),17*(mod/40));
                                t18=new Thread1(out,in,17*(mod/40),18*(mod/40));
                                t19=new Thread1(out,in,18*(mod/40),19*(mod/40));
                                t20=new Thread1(out,in,19*(mod/40),20*(mod/40));
                                t21=new Thread1(out,in,20*(mod/40),21*(mod/40));
                                t22=new Thread1(out,in,21*(mod/40),22*(mod/40));
                                t23=new Thread1(out,in,22*(mod/40),23*(mod/40));
                                t24=new Thread1(out,in,23*(mod/40),24*(mod/40));
                                t25=new Thread1(out,in,24*(mod/40),25*(mod/40));
                                t26=new Thread1(out,in,25*(mod/40),26*(mod/40));
                                t27=new Thread1(out,in,26*(mod/40),27*(mod/40));
                                t28=new Thread1(out,in,27*(mod/40),28*(mod/40));
                                t29=new Thread1(out,in,28*(mod/40),29*(mod/40));
                                t30=new Thread1(out,in,29*(mod/40),30*(mod/40));
                                t31=new Thread1(out,in,30*(mod/40),31*(mod/40));
                                t32=new Thread1(out,in,31*(mod/40),32*(mod/40));
                                t33=new Thread1(out,in,32*(mod/40),33*(mod/40));
                                t34=new Thread1(out,in,33*(mod/40),34*(mod/40));
                                t35=new Thread1(out,in,34*(mod/40),35*(mod/40));
                                t36=new Thread1(out,in,35*(mod/40),36*(mod/40));
                                t37=new Thread1(out,in,36*(mod/40),37*(mod/40));
                                t38=new Thread1(out,in,37*(mod/40),38*(mod/40));
                                t39=new Thread1(out,in,38*(mod/40),39*(mod/40));
                                t40=new Thread1(out,in,39*(mod/40),mod);
                                t1.start();
                                t2.start();
                                t3.start();
                                t4.start();
                                t5.start();
                                t6.start();
                                t7.start();
                                t8.start();
                                t9.start();t10.start();
                                t11.start();
                                t12.start();
                                t13.start();
                                t14.start();
                                t15.start();
                                t16.start();
                                t17.start();
                                t18.start();
                                t19.start();t20.start();
                                t21.start();
                                t22.start();
                                t23.start();
                                t24.start();
                                t25.start();
                                t26.start();
                                t27.start();
                                t28.start();
                                t29.start();t30.start();
                                t31.start();
                                t32.start();
                                t33.start();
                                t34.start();
                                t35.start();
                                t36.start();
                                t37.start();
                                t38.start();
                                t39.start();t40.start();
                                try {
                                    t1.join();t2.join();t3.join();t4.join();t5.join();
                                    t6.join();t7.join();t8.join();t9.join();t10.join();
                                    t11.join();t12.join();t13.join();t14.join();t15.join();
                                    t16.join();t17.join();t18.join();t19.join();t20.join();
                                    t21.join();t22.join();t23.join();t24.join();t25.join();
                                    t26.join();t27.join();t28.join();t29.join();t30.join();
                                    t31.join();t32.join();t33.join();t34.join();t35.join();
                                    t36.join();t37.join();t38.join();t39.join();t40.join();
                                }
                                catch(Exception e) {}
                                cond1=cond1 && t1.getB() && t2.getB() && t3.getB() && t4.getB() && t5.getB() && t6.getB() && t7.getB() && t8.getB() && t9.getB() && t10.getB();
                                cond1=cond1 && t11.getB() && t12.getB() && t13.getB() && t14.getB() && t15.getB() && t16.getB() && t17.getB() && t18.getB() && t19.getB() && t20.getB();
                                cond1=cond1 && t21.getB() && t22.getB() && t23.getB() && t24.getB() && t25.getB() && t26.getB() && t27.getB() && t28.getB() && t29.getB() && t30.getB();
                                cond1=cond1 && t31.getB() && t32.getB() && t33.getB() && t34.getB() && t35.getB() && t36.getB() && t37.getB() && t38.getB() && t39.getB() && t40.getB();
                            }
                            else
                                cond1=cond1 && TreeNode.contains(out,in,0,mod);
                        }
                        else {
                            cond1=false;
                            out=s[i];
                            in=t.get(j).getData();
                            int mod=t.get(j).getData().size();
                            if(mod>=40) {
                                Thread1 t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,t28,t29,t30,t31,t32,t33,t34,t35,t36,t37,t38,t39,t40;
                                t1=new Thread1(out,in,0,mod/30);
                                t2=new Thread1(out,in,mod/30,2*(mod/30));
                                t3=new Thread1(out,in,2*(mod/30),3*(mod/30));
                                t4=new Thread1(out,in,3*(mod/30),4*(mod/30));
                                t5=new Thread1(out,in,4*(mod/30),5*(mod/30));
                                t6=new Thread1(out,in,5*(mod/30),6*(mod/30));
                                t7=new Thread1(out,in,6*(mod/30),7*(mod/30));
                                t8=new Thread1(out,in,7*(mod/30),8*(mod/30));
                                t9=new Thread1(out,in,8*(mod/30),9*(mod/30));
                                t10=new Thread1(out,in,9*(mod/30),10*(mod/30));
                                t11=new Thread1(out,in,10*(mod/30),11*(mod/30));
                                t12=new Thread1(out,in,11*(mod/30),12*(mod/30));
                                t13=new Thread1(out,in,12*(mod/30),13*(mod/30));
                                t14=new Thread1(out,in,13*(mod/30),14*(mod/30));
                                t15=new Thread1(out,in,14*(mod/30),15*(mod/30));
                                t16=new Thread1(out,in,15*(mod/30),16*(mod/30));
                                t17=new Thread1(out,in,16*(mod/30),17*(mod/30));
                                t18=new Thread1(out,in,17*(mod/30),18*(mod/30));
                                t19=new Thread1(out,in,18*(mod/30),19*(mod/30));
                                t20=new Thread1(out,in,19*(mod/30),20*(mod/30));
                                t21=new Thread1(out,in,20*(mod/30),21*(mod/30));
                                t22=new Thread1(out,in,21*(mod/30),22*(mod/30));
                                t23=new Thread1(out,in,22*(mod/30),23*(mod/30));
                                t24=new Thread1(out,in,23*(mod/30),24*(mod/30));
                                t25=new Thread1(out,in,24*(mod/30),25*(mod/30));
                                t26=new Thread1(out,in,25*(mod/30),26*(mod/30));
                                t27=new Thread1(out,in,26*(mod/30),27*(mod/30));
                                t28=new Thread1(out,in,27*(mod/30),28*(mod/30));
                                t29=new Thread1(out,in,28*(mod/30),29*(mod/30));
                                t30=new Thread1(out,in,29*(mod/30),mod);
                                t30=new Thread1(out,in,29*(mod/40),30*(mod/40));
                                t31=new Thread1(out,in,30*(mod/40),31*(mod/40));
                                t32=new Thread1(out,in,31*(mod/40),32*(mod/40));
                                t33=new Thread1(out,in,32*(mod/40),33*(mod/40));
                                t34=new Thread1(out,in,33*(mod/40),34*(mod/40));
                                t35=new Thread1(out,in,34*(mod/40),35*(mod/40));
                                t36=new Thread1(out,in,35*(mod/40),36*(mod/40));
                                t37=new Thread1(out,in,36*(mod/40),37*(mod/40));
                                t38=new Thread1(out,in,37*(mod/40),38*(mod/40));
                                t39=new Thread1(out,in,38*(mod/40),39*(mod/40));
                                t40=new Thread1(out,in,39*(mod/40),mod);
                                t1.start();
                                t2.start();
                                t3.start();
                                t4.start();
                                t5.start();
                                t6.start();
                                t7.start();
                                t8.start();
                                t9.start();t10.start();
                                t11.start();
                                t12.start();
                                t13.start();
                                t14.start();
                                t15.start();
                                t16.start();
                                t17.start();
                                t18.start();
                                t19.start();t20.start();
                                t21.start();
                                t22.start();
                                t23.start();
                                t24.start();
                                t25.start();
                                t26.start();
                                t27.start();
                                t28.start();
                                t29.start();t30.start();
                                t31.start();
                                t32.start();
                                t33.start();
                                t34.start();
                                t35.start();
                                t36.start();
                                t37.start();
                                t38.start();
                                t39.start();t40.start();
                                try {
                                    t1.join();t2.join();t3.join();t4.join();t5.join();
                                    t6.join();t7.join();t8.join();t9.join();t10.join();
                                    t11.join();t12.join();t13.join();t14.join();t15.join();
                                    t16.join();t17.join();t18.join();t19.join();t20.join();
                                    t21.join();t22.join();t23.join();t24.join();t25.join();
                                    t26.join();t27.join();t28.join();t29.join();t30.join();
                                    t31.join();t32.join();t33.join();t34.join();t35.join();
                                    t36.join();t37.join();t38.join();t39.join();t40.join();
                                }
                                catch(Exception e) {}
                                cond2=cond2 && t1.getB() && t2.getB() && t3.getB() && t4.getB() && t5.getB() && t6.getB() && t7.getB() && t8.getB() && t9.getB() && t10.getB();
                                cond2=cond2 && t11.getB() && t12.getB() && t13.getB() && t14.getB() && t15.getB() && t16.getB() && t17.getB() && t18.getB() && t19.getB() && t20.getB();
                                cond2=cond2 && t21.getB() && t22.getB() && t23.getB() && t24.getB() && t25.getB() && t26.getB() && t27.getB() && t28.getB() && t29.getB() && t30.getB();
                                cond2=cond2 && t31.getB() && t32.getB() && t33.getB() && t34.getB() && t35.getB() && t36.getB() && t37.getB() && t38.getB() && t39.getB() && t40.getB();
                            }
                            else
                                cond2=cond2 && TreeNode.contains(out,in,0,mod);
                       /*     int mod=(t.get(j).getData().size())/500;int k=0;
                            for(;k<mod;k++)
                                cond2=cond2 && TreeNode.contains(s[i].toArray(), Arrays.copyOfRange(t.get(j).getData().toArray(), k*500, (k+1)*500));
                            cond2=cond2 && TreeNode.contains(s[i].toArray(),Arrays.copyOfRange(t.get(j).getData().toArray(), k*500, t.get(j).getData().size()));*/
                        }
                        //System.out.println(cond1+"\t"+cond2);
                        if( cond1 ) {
                          //  System.out.println("1");
                            t.get(j).addChild(temp);
                            flag=false;
                           // System.out.println("OK");
                            break;
                        }
                        else if( cond2 ) {
                            temp.setParent(t.get(j));
                            temp.setChild(t.get(j));
                            flag=false;
                            break;
                        }
                        else
                            flag=true;
                    }
                    if(flag)
                        root.addChild(temp);
            }
        }
        public MainControl() { }

        /*public synchronized boolean contains(Object[] outer,Object[] inner,int i,int j) {
            if(i==j)
                return true;
            boolean b=TreeNode.contains(outer, Arrays.copyOfRange(inner, i*100, (i+1)*100));
            if(!b)
                return false;
            return  b && contains(outer,inner,i+1,j);
        }*/
        
	public int size(int p,int th) {
		int c=-1;
                if(th==-1 && p==-1) {
                    int sum=0;
                    for(int i=0;i<thresh.length;i++)
                        sum+=size(-1,i);
                    return sum;
                }
		if(p==-1) {
			for(int i=ymin;i<h;i++)
				for(int j=xmin;j<w;j++) 
					if(c<regions[th][j][i] && regions[th][j][i]!=-1)
						c=regions[th][j][i];
                }
		else {
			for(int i=ymin;i<h;i++)
				for(int j=xmin;j<w;j++) 
					if(regions[th][j][i]==p)
						c++;
		}
		return (c+1);
	}
	
	public boolean check(int x,int y,int i,int th) {
		if(x>=w || y>=h || x<xmin || y<ymin)
			return false;
		if(i==temp[th][x][y]&&regions[th][x][y]!=-1)
			return true;
		else
			return false;
	}
	
	public synchronized void extremal(int start,int end,int th) {
		for(int i=start;i<end;i++)
			if(min_max(i,th)) {
			}
			else
				for(int y=ymin;y<h;y++)
					for(int x=xmin;x<w;x++)
						if(regions[th][x][y] > i)
							regions[th][x][y] -= 1;
						else if(regions[th][x][y] == i)
							regions[th][x][y] = -1;
	}

        public boolean min_max(int i,int th) {
		int c=-1, C=256,co=256,Co=-1;
		for(int y=ymin;y<h;y++)
			for(int x=xmin;x<w;x++) {
				if(regions[th][x][y]==i && c < pixels[x][y])
					c=pixels[x][y];
				if(outer[th][x][y]==i && C>pixels[x][y])
					C=pixels[x][y];
				if(regions[th][x][y]==i && co>pixels[x][y])
					co=pixels[x][y];
				if(outer[th][x][y]==i && Co<pixels[x][y])
					Co=pixels[x][y];				
			}
		if(c < C || co > Co)
			return true;
		else
			return false;
	}
	
	public synchronized void findOuterRegions_1(int starty,int endy,int startx,int endx,int th) {
		for(int y=starty;y<endy;y++) 
			for(int x=startx;x<endx;x++) {
				int i=temp[th][x][y];
				if(!(check(x-1,y-1,i,th)|| x-1<xmin || y-1<ymin)) {
					outer[th][x-1][y-1]=regions[th][x][y];
					outer[th][x][y]=regions[th][x-1][y-1];
				}
				if(!(check(x,y-1,i,th)|| y-1<ymin )) {
					outer[th][x][y-1]=regions[th][x][y];
					outer[th][x][y]=regions[th][x][y-1];
				}
				if(!(check(x+1,y-1,i,th)|| x+1>=w || y-1<ymin)) {
					outer[th][x+1][y-1]=regions[th][x][y];
					outer[th][x][y]=regions[th][x+1][y-1];
				}
				if(!(check(x-1,y,i,th)|| x-1<xmin)) {
					outer[th][x-1][y]=regions[th][x][y];
					outer[th][x][y]=regions[th][x-1][y];
				}
				if(!(check(x+1,y,i,th)|| x+1>=w )) {
					outer[th][x+1][y]=regions[th][x][y];
					outer[th][x][y]=regions[th][x+1][y];
				}
				if(!(check(x-1,y+1,i,th)|| x-1<xmin || y+1>=h)) {
					outer[th][x-1][y+1]=regions[th][x][y];
					outer[th][x][y]=regions[th][x-1][y+1];
				}
				if(!(check(x,y+1,i,th)|| y+1>=h)) {
					outer[th][x][y+1]=regions[th][x][y];
					outer[th][x][y]=regions[th][x][y+1];
				}
				if(!(check(x+1,y+1,i,th)|| x+1>=w || y+1>=h)) {
					outer[th][x+1][y+1]=regions[th][x][y];
					outer[th][x][y]=regions[th][x+1][y+1];
				}
				/*if(regions[x][y]==97)
					img.setRGB(x,y,new Color(255,0,0).getRGB());*/
			}
	}
}
