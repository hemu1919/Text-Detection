import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

class TreeNode {
	private ArrayList<TreeNode> children;
	private LinkedList data;
	private TreeNode parent;
	private double var;
        private int thresh;
        private int rid;
        
	public TreeNode(LinkedList data,int thresh,int rid) {
		this.data=data;
		this.children=new ArrayList<TreeNode>(0);
		this.var=Double.MAX_VALUE;
                this.thresh=thresh;
		this.parent=null;
                this.rid=rid;
	}
        
        public TreeNode() {
                this.data=null;
		this.children=new ArrayList<TreeNode>(0);
		this.var=Double.MAX_VALUE;
                this.thresh=-1;
		this.parent=null;
                this.rid=-1;
        }
        
        public TreeNode(TreeNode t) {
                this.data=t.data;
		this.children=new ArrayList<TreeNode>(t.children);
		this.var=t.var;
                this.thresh=t.thresh;
		this.parent=t.parent;
                this.rid=t.rid;
        }
        
        public void clear() {
            this.children.clear();
        }
        
	public int size() {
		int sum=0;
		if(this.numChild()==0)
			return sum;
		for(int i=0;i<this.numChild();i++) {
                    TreeNode t=(TreeNode)this.children.get(i);
                    if(t!=null)
			sum+=t.size();
                }
                sum+=this.numChild();
		return sum;
	}
        
	public LinkedList getData() {
		if(this.data!=null)
			return this.data;
		LinkedList d=new LinkedList();
		for(int i=0;i<this.numChild();i++) {
                    LinkedList p=((TreeNode)this.children.get(i)).getData();
                    if(p!=null)
			d.addAll(p);
                }
		return d;
	}
        
	public ArrayList<TreeNode> getSet() {
                if(this.size()==0)
                    return null;
		ArrayList<TreeNode> clus=new ArrayList<TreeNode>(this.size());
		if(this.numChild()==0 || this==null)
			return clus;
		clus.addAll(this.getChilds());
		for(int i=0;i<this.numChild();i++) {
                    TreeNode c=(TreeNode)this.children.get(i);
                    if( c!=null && c.getSet()!=null)
			clus.addAll(c.getSet());
                }
		return clus;
	}
        
	public int numChild() {
		return this.children.size();
	}
        
        public void setChilds(ArrayList<TreeNode> children) {
            this.children=children;
        }
        
	public void addChild(TreeNode t) {
			t.parent=this;
			this.children.add(t);
	}
        
	public ArrayList<TreeNode> getChilds() {
		return this.children;
	}
        
        public ArrayList<TreeNode> detectMSER() {
            TreeNode t=null;
            //System.out.println(this.getVar());
            if(this.numChild()>=1) {
                ArrayList<TreeNode> c=this.getChilds(),c1=new ArrayList(),c2=new ArrayList();
                double varc=Double.MAX_VALUE;
		for(int i=0;i<this.numChild();i++) {
                        t=(TreeNode)c.get(i);
                        c1.addAll(t.detectMSER());
                }
                for(int j=0;j<c1.size();j++) {
                    varc=Math.min(varc, c1.get(j).var);
                    if(this.var>c1.get(j).var)
                        c2.add(c1.get(j));
                }
                if(this.var>varc) {
                    return c2;
                }
                else{
                    this.children=c2;
                    c1=new ArrayList();c1.add(this);
                    return c1;
                }
	    }
            ArrayList<TreeNode> c1=new ArrayList<TreeNode>();
            c1.add(this);
            return c1;
        }
        
        public void removeChild(TreeNode t) {
            this.children.remove(t);
        }
        
/*	public static TreeNode findNode(ArrayList<TreeNode> t1,TreeNode t2,int i) {
            if(i>0) {
                if(t1.get(i).thresh==t2.thresh)
                    return findNode(t1,t2,i-1);
                System.out.println(t1.get(i).data.size()+"\t"+t2.data.size());
                System.out.println(contains(t1.get(i).data.toArray(),t2.data.toArray())+"\t"+contains(t2.data.toArray(),t1.get(i).data.toArray()));
                if(contains(t1.get(i).data.toArray(),t2.data.toArray()))
                    return t1.get(i);
                else if(contains(t2.data.toArray(),t1.get(i).data.toArray())) {
                    t2.parent=t1.get(i).parent;
                    t1.get(i).parent.children.set(t1.get(i).parent.children.indexOf(t1.get(i)),t2);
                    t2.addChild(t1.get(i));
                    return t2;
                }
                return findNode(t1,t2,i-1);
            }
            return null;
        /*        //System.out.println(t1.thresh+"\t"+t2.thresh+"\t"+t1.data.size()+"\t"+t2.data.size());
                if(t1.thresh==t2.thresh)
                     return null;
                if(t1.data!=null)
                    System.out.println(contains(t1.data.toArray(),t2.data.toArray())+"\t"+contains(t2.data.toArray(),t1.data.toArray()));
		if(t1.data==null || contains(t1.data.toArray(),t2.data.toArray())){
			ArrayList<TreeNode> c=t1.getChilds();
			for(int i=0;i<c.size();i++) {
				TreeNode t=(TreeNode) c.get(i);
				TreeNode x=findNode(t,t2);
				if(x!=null)
					return x;
			}
			return t1;
		}
		else if(contains(t2.data.toArray(),t1.data.toArray())) {
			t2.parent=t1.parent;
			t1.parent.children.set(t1.parent.children.indexOf(t1),t2);
			t2.addChild(t1);
			return t2;
		}
		else
			return null;
	}*/
        
    public void minreg() {
        double theta1=0.01,theta2=0.35,amin=0.3,amax=1.2;
        ArrayList<TreeNode> c=this.getChilds();
        for(int i=0;c!=null && i<c.size();i++)
            c.get(i).minreg();
        if(this.parent!=null) {
            ArrayList a=TextNode.maxmin_1(this.getData(),0,this.getData().size());
            int w=(Integer.parseInt(a.get(3).toString()))-(Integer.parseInt(a.get(0).toString()));
            int h=(Integer.parseInt(a.get(2).toString()))-(Integer.parseInt(a.get(1).toString()));
            double as=(double)w/h;
            if(as>amax)
                this.var=this.var+theta1*(as-amax);
            else if(as<amin)
                this.var=this.var+theta2*(amin-as);
            else
                this.var=var;
        }
}
        
        public void setChild(TreeNode t) {
            TreeNode parent=t.parent;
            parent.addChild(this);
            this.addChild(t);
            parent.removeChild(t);
        }
        
        public void setParent(TreeNode t) {
            this.parent=t.parent;
        }
        
	public void computeVar() {
            if(this.numChild()>0) {
                TreeNode t=null;
                ArrayList<TreeNode> c=this.getChilds();
                for(int i=0;i<c.size();i++) {
                    if(c.get(i).thresh==this.thresh)
                        t=(TreeNode)c.get(i);
                    c.get(i).computeVar();
                }
                if(this.parent!=null)
                    this.var=(double)Math.abs(this.getData().size()-t.getData().size())/this.getData().size();
            }
            else
                this.var=1;
/*            if(t.var!=Double.MAX_VALUE)
                return;
            ArrayList<TreeNode> c=t.getChilds();
            if(t.numChild()==0) {
                t.var=0;
                return;
            }
            TreeNode t1=null;
            for(int i=0;i<c.size()&&c.get(i).thresh==t.thresh;i++)
                t1=c.get(i);
            if(t1!=null) {
                LinkedList u=new LinkedList(t.getData()),v=new LinkedList(t1.getData());
                //v.removeAll(u);
                t.var=(double)Math.abs(v.size()-u.size())/u.size();
            }
*/        }
        
	public static TreeNode lr(TreeNode t) {
		if(t.numChild()==0)
			return t;
		else if(t.numChild()==1) {
			TreeNode c=lr((TreeNode)t.children.get(0));
			if(t.var<=c.var) {
				for(int i=0;i<c.numChild();i++)
					t.addChild((TreeNode)c.children.get(i));
				t.children.remove(0);
				return t;
			}
			else
				return c;
		}
		else {
			ArrayList<TreeNode> c= t.getChilds();
			for(int i=0;i<c.size();i++) {
				TreeNode t1=(TreeNode)c.get(i);
				t.set(i,lr(t1));
			}
			return t;
		}
	}
        
        public void set(int i,TreeNode t) {
            this.children.set(i,t);
        }
        
	public static ArrayList<TreeNode> ta(TreeNode t) {
		if(t.numChild()==0) {
			ArrayList<TreeNode> temp=new ArrayList<TreeNode>(0);temp.add(t);
			return temp;
		}
		else {
			double C_min=1f;
			ArrayList<TreeNode> C=new ArrayList<TreeNode>(0);
			ArrayList<TreeNode> c=t.getChilds();
			for(int i=0;i<c.size();i++)
				C.addAll(ta((TreeNode)c.get(i)));
			for(int i=0;i<C.size();i++) {
				TreeNode t1=(TreeNode)C.get(i);
				if(C_min > t1.var)
					C_min=t1.var;
			}
			if(C_min >= t.var) {
				t.children.clear();
				ArrayList<TreeNode> temp=new ArrayList<TreeNode>(0);temp.add(t);
				return temp;
			}
			else
				return C;
		}
	}

        public synchronized void extend_1(ArrayList<TreeNode> c,int start,int end,int[][][] c0,int[][][] c1,BufferedImage img,int xmin,int ymin,int w,int h) {
            for(int i=start;i<end;i++) {
                TreeNode t=(TreeNode)c.get(i);
                LinkedList l=new LinkedList();
                LinkedList r=new LinkedList();
                for(int y=ymin;y<h;y++)
                    for(int x=xmin;x<w;x++) {
                        LinkedList s=new LinkedList();
                        s.add(x);s.add(y);s.add(img.getRGB(x, y));
                        if(c0[t.thresh][x][y]==t.rid)
                            l.add(s);
                        else if(c1[t.thresh][x][y]==t.rid)
                            r.add(s);
                    }
                TreeNode lc=new TreeNode(l,t.thresh,t.rid);
                TreeNode rc=new TreeNode(r,t.thresh,t.rid);
                t.extend(c0,c1,img,xmin,ymin,w,h);
                if(l.size()!=0)
                    t.addChild(lc);
                if(r.size()!=0)
                    t.addChild(rc);
                //computeVar(t);
            }
        }
        
        public void extend(int[][][] c0,int[][][] c1,BufferedImage img,int xmin,int ymin,int w,int h) {
            ArrayList<TreeNode> c=this.getChilds(),c2=new ArrayList<TreeNode>();
            TreeNode p=new TreeNode();
            if(c.size()>=40) {
                Thread1 t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,t28,t29,t30,t31,t32,t33,t34,t35,t36,t37,t38,t39,t40;
                t1=new Thread1(p,c,0,c.size()/15,c0,c1,img,xmin,ymin,w,h);
                t2=new Thread1(p,c,c.size()/15,2*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t3=new Thread1(p,c,2*(c.size()/40),3*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t4=new Thread1(p,c,3*(c.size()/40),4*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t5=new Thread1(p,c,4*(c.size()/40),5*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t6=new Thread1(p,c,5*(c.size()/40),6*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t7=new Thread1(p,c,6*(c.size()/40),7*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t8=new Thread1(p,c,7*(c.size()/40),8*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t9=new Thread1(p,c,8*(c.size()/40),9*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t10=new Thread1(p,c,9*(c.size()/40),10*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t11=new Thread1(p,c,10*(c.size()/40),11*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t12=new Thread1(p,c,11*(c.size()/40),12*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t13=new Thread1(p,c,12*(c.size()/40),13*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t14=new Thread1(p,c,13*(c.size()/40),14*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t15=new Thread1(p,c,14*(c.size()/40),15*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t16=new Thread1(p,c,15*(c.size()/40),16*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t17=new Thread1(p,c,16*(c.size()/40),17*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t18=new Thread1(p,c,17*(c.size()/40),18*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t19=new Thread1(p,c,18*(c.size()/40),19*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t20=new Thread1(p,c,19*(c.size()/40),20*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t21=new Thread1(p,c,20*(c.size()/40),21*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t22=new Thread1(p,c,21*(c.size()/40),22*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t23=new Thread1(p,c,22*(c.size()/40),23*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t24=new Thread1(p,c,23*(c.size()/40),24*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t25=new Thread1(p,c,24*(c.size()/40),25*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t26=new Thread1(p,c,25*(c.size()/40),26*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t27=new Thread1(p,c,26*(c.size()/40),27*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t28=new Thread1(p,c,27*(c.size()/40),28*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t29=new Thread1(p,c,28*(c.size()/40),29*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t30=new Thread1(p,c,29*(c.size()/40),30*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t31=new Thread1(p,c,30*(c.size()/40),31*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t32=new Thread1(p,c,31*(c.size()/40),32*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t33=new Thread1(p,c,32*(c.size()/40),33*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t34=new Thread1(p,c,33*(c.size()/40),34*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t35=new Thread1(p,c,34*(c.size()/40),35*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t36=new Thread1(p,c,35*(c.size()/40),36*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t37=new Thread1(p,c,36*(c.size()/40),37*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t38=new Thread1(p,c,37*(c.size()/40),38*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t39=new Thread1(p,c,38*(c.size()/40),39*(c.size()/40),c0,c1,img,xmin,ymin,w,h);
                t40=new Thread1(p,c,39*(c.size()/40),c.size(),c0,c1,img,xmin,ymin,w,h);
                t1.start();t2.start();t3.start();t4.start();t5.start();t6.start();t7.start();t8.start();t9.start();t10.start();
                t11.start();t12.start();t13.start();t14.start();t15.start();
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
            }
            else
                extend_1(c,0,c.size(),c0,c1,img,xmin,ymin,w,h);
        }
        
        public static int grayLevel(int a) {
            Color c = new Color(a);
            int red = (int)c.getRed();
            int green = (int)c.getGreen();
            int blue = (int)c.getBlue();
            int sum = (red + green + blue)/3;
            return sum;
        }
        public int getTh() { return this.thresh; }
        public double getVar() { return this.var; }
        public int getRid() { return this.rid; }
	public static boolean contains(LinkedList outer, LinkedList inner,int start,int end) {
            //System.out.println(outer.size()+"\t"+inner.size());
           for(int i=start;i<end;i++)
                if(!outer.contains(inner.get(i)))
                    return false;
            return true;
            /*if(start==end)
                return true;
            boolean b=outer.contains(inner.get(start));
            if(b)
                return b && contains(outer,inner,start+1,end);
            return b;*/
		//return Arrays.asList(outer).containsAll(Arrays.asList(inner));
	}
}
