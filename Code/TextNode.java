import java.util.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class TextNode {
	private ArrayList<TreeNode> clus;
        HashMap m;
	public TextNode(ArrayList<TreeNode> clus) {
		this.clus=clus;
                /*try{
                    Class.forName("com.mysql.jdbc.Driver");  // MySQL database connection
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/images?" + "user=root&password=bujji");
                    Statement stmnt=con.createStatement();
                    stmnt.execute("create table features(id int auto_increment primary key,lid int,minx real,miny real,maxx real,maxy real,csum int,st real)");
                    stmnt.close();
                    con.close();
                }
                catch(Exception e) {
                    System.out.println(e.getMessage());
                }*/
                this.m=new HashMap();
//                this.c=new HashMap();
                //this.st=new HashMap();*/
                for(int i=0;i<clus.size();i++)
                    clus.get(i).clear();
	}

        public TextNode(TextNode t) {
            //this.c=t.c;
            this.m=t.m;
            this.clus=t.clus;
            //this.st=t.st;
        }
        public TreeNode get(int i) {
            return clus.get(i);
        }
        public int size() {
            return this.clus.size();
        }
	
	public LinkedList features_1(LinkedList x) {
            /*    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/images?" + "user=root&password=bujji");
                Statement stmnt=con.createStatement();
                ResultSet rs=stmnt.executeQuery("select * from features where lid="+x.hashCode());
                rs.last();int i=rs.getRow();
                LinkedList s=new LinkedList();
                if(i==0) {
                    ArrayList t1=maxmin(x);
                    int sum=sum(x);
                    double st=stroke(x);
                    s.add(t1);s.add(sum);s.add(st);
                    stmnt.execute("insert into features(lid,minx,miny,maxx,maxy,csum,st) values("+x.hashCode()+","+Integer.parseInt(t1.get(0).toString())+","+Integer.parseInt(t1.get(1).toString())+","+Integer.parseInt(t1.get(2).toString())+","+Integer.parseInt(t1.get(3).toString())+","+sum+","+st+")");
                }
                else if(i==1) {
                    rs.first();
                    ArrayList t1=new ArrayList();
                    t1.add(rs.getDouble(3));t1.add(rs.getDouble(4));t1.add(rs.getDouble(5));t1.add(rs.getDouble(6));
                    s.add(t1);s.add(rs.getInt(7));s.add(rs.getDouble(8));
                }
                rs.close();
                stmnt.close();con.close();
                return s;
		*/if(m.containsKey(x.hashCode()))
                    return (LinkedList)m.get(x.hashCode());
                /*else if(c.containsKey(x.hashCode()))
                    return (LinkedList)c.get(x.hashCode());*/
		LinkedList s=new LinkedList();
		s.add(maxmin(x));s.add(sum(x));s.add(stroke(x));
                /*if(m.size()>=500)
                    c.put(x.hashCode(),s);
                else*/
                    m.put(x.hashCode(),s);
		return s;
	}
	
	public double[] features(LinkedList u,LinkedList v) {
		double[] t=new double[7];
            ArrayList au,av;LinkedList lu,lv;
            //System.out.println("Dealing With : \n"+u.size()+"\t"+v.size());
                /*if(m.containsKey(u))
                    au=(ArrayList) m.get(u);
                else
                    au=maxmin(u);
                if(m.containsKey(v))
                    av=(ArrayList) m.get(v);
                else
                    av=maxmin(v);*/
                
//                    System.out.println("Entered");
                    lu=features_1(u);
                    lv=features_1(v);
					/*System.out.println(lu.get(1)/*+"\t"+lu.get(1)+"\t"+lu.get(2));
					System.out.println(lv.get(0)+"\t"+lv.get(1)+"\t"+lv.get(2));*/
//                    System.out.println("Ok after features");
                    au=(ArrayList)lu.get(0);av=(ArrayList)lv.get(0);
                    double xu=Double.parseDouble(au.get(0).toString());
                    double xv=Double.parseDouble(av.get(0).toString());
                    double yu=Double.parseDouble(au.get(1).toString());
                    double yv=Double.parseDouble(av.get(1).toString());
                    double wu=Double.parseDouble(au.get(2).toString())-xu;
                    double wv=Double.parseDouble(av.get(2).toString())-xv;
                    double hu=Double.parseDouble(au.get(3).toString())-yu;
                    double hv=Double.parseDouble(av.get(3).toString())-yv;
                    double sv,su;/*
                    if(st.containsKey(u))
                        su=(Double)st.get(u);
                    else
                        su=stroke(u);
                    if(st.containsKey(v))
                        sv=(Double)st.get(v);
                    else
                        sv=stroke(v);*/
                    su=(Double)lu.get(2);sv=(Double)lv.get(2);
                    double interval,width,height,top,bottom,stroked,color;
                    if(xu<xv)
                    	interval=Math.max(wu, wv)==0 ? 0 :Math.abs(xv-xu-wu)/Math.max(wu,wv);
                    else
                    	interval=Math.max(wu, wv)==0 ? 0 : Math.abs(xu-xv-wv)/Math.max(wu,wv);
                    width=Math.max(wu, wv)==0 ? 0 : Math.abs(wu-wv)/Math.max(wu,wv);
                    height=Math.max(hu,hv)==0 ? 0 : Math.abs(hu-hv)/Math.max(hu,hv);
                    top=Math.atan2(Math.abs(yu-yv) ,Math.abs(xu+(wu/2)-xv-(wv/2)) );
                    bottom=Math.atan2(Math.abs(yu+hu-yv-hv) , Math.abs(xu+(wu/2)-xv-(wv/2)) );
                    color=colord(Integer.parseInt(lu.get(1).toString()),Integer.parseInt(lv.get(1).toString()),u.size(),v.size());
                    //color=colord(u,v);
                    stroked=Math.max(su, sv)==0 ? 0 : Math.abs(su-sv)/Math.max(su,sv);
					t[0]=interval;
					t[1]=width;
					t[2]=height;
					t[3]=top;
					t[4]=bottom;
					t[5]=color;
					t[6]=stroked;
					
					
                    //System.out.println(interval+"\t"+width+"\t"+height+"\t"+top+"\t"+bottom+"\t"+stroked+"\t"+color);
                    //System.gc();
				return t;
	}
        
        public ArrayList maxmin(LinkedList v) {
            ArrayList c;
            if(v.size()>=30) {
                Thread1 t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,t28,t29,t30;
                t1=new Thread1(this,v,0,v.size()/30);
                t2=new Thread1(this,v,v.size()/30,2*(v.size()/30));
                t3=new Thread1(this,v,2*(v.size()/30),3*(v.size()/30));
                t4=new Thread1(this,v,3*(v.size()/30),4*(v.size()/30));
                t5=new Thread1(this,v,4*(v.size()/30),5*(v.size()/30));
                t6=new Thread1(this,v,5*(v.size()/30),6*(v.size()/30));
                t7=new Thread1(this,v,6*(v.size()/30),7*(v.size()/30));
                t8=new Thread1(this,v,7*(v.size()/30),8*(v.size()/30));
                t9=new Thread1(this,v,8*(v.size()/30),9*(v.size()/30));
                t10=new Thread1(this,v,9*(v.size()/30),10*(v.size()/30));
                t11=new Thread1(this,v,10*(v.size()/30),11*(v.size()/30));
                t12=new Thread1(this,v,11*(v.size()/30),12*(v.size()/30));
                t13=new Thread1(this,v,12*(v.size()/30),13*(v.size()/30));
                t14=new Thread1(this,v,13*(v.size()/30),14*(v.size()/30));
                t15=new Thread1(this,v,14*(v.size()/30),15*(v.size()/30));
                t16=new Thread1(this,v,15*(v.size()/30),16*(v.size()/30));
                t17=new Thread1(this,v,16*(v.size()/30),17*(v.size()/30));
                t18=new Thread1(this,v,17*(v.size()/30),18*(v.size()/30));
                t19=new Thread1(this,v,18*(v.size()/30),19*(v.size()/30));
                t20=new Thread1(this,v,19*(v.size()/30),20*(v.size()/30));
                t21=new Thread1(this,v,20*(v.size()/30),21*(v.size()/30));
                t22=new Thread1(this,v,21*(v.size()/30),22*(v.size()/30));
                t23=new Thread1(this,v,22*(v.size()/30),23*(v.size()/30));
                t24=new Thread1(this,v,23*(v.size()/30),24*(v.size()/30));
                t25=new Thread1(this,v,24*(v.size()/30),25*(v.size()/30));
                t26=new Thread1(this,v,25*(v.size()/30),26*(v.size()/30));
                t27=new Thread1(this,v,26*(v.size()/30),27*(v.size()/30));
                t28=new Thread1(this,v,27*(v.size()/30),28*(v.size()/30));
                t29=new Thread1(this,v,28*(v.size()/30),29*(v.size()/30));
                t30=new Thread1(this,v,29*(v.size()/30),v.size());
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
                try {
                    t1.join();t2.join();t3.join();t4.join();t5.join();
                    t6.join();t7.join();t8.join();t9.join();t10.join();
                    t11.join();t12.join();t13.join();t14.join();t15.join();
                    t16.join();t17.join();t18.join();t19.join();t20.join();
                    t21.join();t22.join();t23.join();t24.join();t25.join();
                    t26.join();t27.join();t28.join();t29.join();t30.join();
                }
                catch(Exception e) {}
                ArrayList c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16,c17,c18,c19,c20,c21,c22,c23,c24,c25,c26,c27,c28,c29,c30;
                c1=t1.getA();c2=t2.getA();c3=t3.getA();c4=t4.getA();c5=t5.getA();c6=t6.getA();c7=t7.getA();c8=t8.getA();c9=t9.getA();c10=t10.getA();
                c11=t11.getA();c12=t12.getA();c13=t13.getA();c14=t14.getA();c15=t15.getA();c16=t16.getA();c17=t17.getA();c18=t18.getA();c19=t19.getA();c20=t20.getA();
                c21=t21.getA();c22=t22.getA();c23=t23.getA();c24=t24.getA();c25=t25.getA();c26=t26.getA();c27=t27.getA();c28=t28.getA();c29=t29.getA();c30=t30.getA();
                int minx,miny,maxx,maxy;
                minx=Math.min(Integer.parseInt(c1.get(0).toString()),Integer.parseInt(c2.get(0).toString()));
                miny=Math.min(Integer.parseInt(c1.get(1).toString()),Integer.parseInt(c2.get(1).toString()));
                maxx=Math.max(Integer.parseInt(c1.get(2).toString()),Integer.parseInt(c2.get(2).toString()));
                maxy=Math.max(Integer.parseInt(c1.get(3).toString()),Integer.parseInt(c2.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c3.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c3.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c3.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c3.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c4.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c4.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c4.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c4.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c5.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c5.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c5.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c5.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c6.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c6.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c6.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c6.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c7.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c7.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c7.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c7.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c8.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c8.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c8.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c8.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c9.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c9.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c9.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c9.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c10.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c10.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c10.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c10.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c11.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c11.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c11.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c11.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c12.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c12.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c12.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c12.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c13.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c13.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c13.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c13.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c14.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c14.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c14.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c14.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c15.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c15.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c15.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c15.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c16.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c16.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c16.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c16.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c17.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c17.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c17.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c17.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c18.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c18.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c18.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c18.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c19.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c19.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c19.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c19.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c20.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c20.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c20.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c20.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c21.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c21.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c21.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c21.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c22.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c22.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c22.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c22.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c23.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c23.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c23.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c23.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c24.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c24.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c24.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c24.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c25.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c25.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c25.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c25.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c26.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c26.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c26.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c26.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c27.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c27.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c27.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c27.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c28.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c28.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c28.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c28.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c29.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c29.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c29.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c29.get(3).toString()));
                minx=Math.min(minx,Integer.parseInt(c30.get(0).toString()));
                miny=Math.min(miny,Integer.parseInt(c30.get(1).toString()));
                maxx=Math.max(maxx,Integer.parseInt(c30.get(2).toString()));
                maxy=Math.max(maxy,Integer.parseInt(c30.get(3).toString()));
                c=new ArrayList();
                c.add(minx);c.add(miny);c.add(maxx);c.add(maxy);
            }
            else
                c=maxmin_1(v,0,v.size());
            //m.put(v, c);
            return c;
        }
        
        public static synchronized ArrayList maxmin_1(LinkedList x,int start,int end) {
	int minx,miny,maxx,maxy;
        minx=miny=Integer.MAX_VALUE;
        maxx=maxy=Integer.MIN_VALUE;
	int a=end-start-1;

		while(a>3000) {
			minx=Math.min(minx,minx(x,a,a-3000));
                        miny=Math.min(minx,miny(x,a,a-3000));
                        maxx=Math.max(minx,maxx(x,a,a-3000));
                        maxy=Math.max(minx,maxy(x,a,a-3000));
			a-=3000;
		}
		minx=Math.min(minx,minx(x,a,0));
                miny=Math.min(minx,miny(x,a,0));
                maxx=Math.max(minx,maxx(x,a,0));
                maxy=Math.max(minx,maxy(x,a,0));
		ArrayList c=new ArrayList();
                c.add(minx);c.add(miny);c.add(maxx);c.add(maxy);
                return c;
}
        public static int minx(LinkedList s,int i,int j) {
	if(i==j-1)
		return Integer.MAX_VALUE;
	return Math.min(Integer.parseInt(((LinkedList)s.get(i)).get(0).toString()),minx(s,i-1,j));
}
        public static int miny(LinkedList s,int i,int j) {
	if(i==j-1)
		return Integer.MAX_VALUE;
	return Math.min(Integer.parseInt(((LinkedList)s.get(i)).get(1).toString()),miny(s,i-1,j));
}
public static int maxx(LinkedList s,int i,int j) {
	if(i==j-1)
		return Integer.MIN_VALUE;
	return Math.max(Integer.parseInt(((LinkedList)s.get(i)).get(0).toString()),maxx(s,i-1,j));
}
public static int maxy(LinkedList s,int i,int j) {
	if(i==j-1)
		return Integer.MIN_VALUE;
	return Math.max(Integer.parseInt(((LinkedList)s.get(i)).get(1).toString()),maxy(s,i-1,j));
}
        
	public double colord(/*LinkedList x,LinkedList y*/int x,int y,int s1,int s2) {
		Color c1,c2;/*
                if(c.containsKey(x))
                    c1 = new Color(Integer.parseInt(c.get(x).toString())/x.size());
                else
                    c1 = new Color(sum(x));
                if(c.containsKey(y))
                    c2 = new Color(Integer.parseInt(c.get(y).toString())/y.size());
                else
                    c2 = new Color(sum(y));*/
		c1=new Color(x/s1);c2=new Color(y/s2);
		double a,b,c;
		a=Math.pow((int)c1.getRed()-(int)c2.getRed(),2);
		b=Math.pow((int)c1.getGreen()-(int)c2.getGreen(),2);
		c=Math.pow((int)c1.getBlue()-(int)c2.getBlue(),2);
		return Math.sqrt(a+b+c)/255;
	}
	public int sum(LinkedList v) {
            int s=0;
            if(v.size()>=30) {
                Thread1 t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,t28,t29,t30;
                t1=new Thread1(this,v,0,v.size()/30,-7);
                t2=new Thread1(this,v,v.size()/30,2*(v.size()/30),-7);
                t3=new Thread1(this,v,2*(v.size()/30),3*(v.size()/30),-7);
                t4=new Thread1(this,v,3*(v.size()/30),4*(v.size()/30),-7);
                t5=new Thread1(this,v,4*(v.size()/30),5*(v.size()/30),-7);
                t6=new Thread1(this,v,5*(v.size()/30),6*(v.size()/30),-7);
                t7=new Thread1(this,v,6*(v.size()/30),7*(v.size()/30),-7);
                t8=new Thread1(this,v,7*(v.size()/30),8*(v.size()/30),-7);
                t9=new Thread1(this,v,8*(v.size()/30),9*(v.size()/30),-7);
                t10=new Thread1(this,v,9*(v.size()/30),10*(v.size()/30),-7);
                t11=new Thread1(this,v,10*(v.size()/30),11*(v.size()/30),-7);
                t12=new Thread1(this,v,11*(v.size()/30),12*(v.size()/30),-7);
                t13=new Thread1(this,v,12*(v.size()/30),13*(v.size()/30),-7);
                t14=new Thread1(this,v,13*(v.size()/30),14*(v.size()/30),-7);
                t15=new Thread1(this,v,14*(v.size()/30),15*(v.size()/30),-7);
                t16=new Thread1(this,v,15*(v.size()/30),16*(v.size()/30),-7);
                t17=new Thread1(this,v,16*(v.size()/30),17*(v.size()/30),-7);
                t18=new Thread1(this,v,17*(v.size()/30),18*(v.size()/30),-7);
                t19=new Thread1(this,v,18*(v.size()/30),19*(v.size()/30),-7);
                t20=new Thread1(this,v,19*(v.size()/30),20*(v.size()/30),-7);
                t21=new Thread1(this,v,20*(v.size()/30),21*(v.size()/30),-7);
                t22=new Thread1(this,v,21*(v.size()/30),22*(v.size()/30),-7);
                t23=new Thread1(this,v,22*(v.size()/30),23*(v.size()/30),-7);
                t24=new Thread1(this,v,23*(v.size()/30),24*(v.size()/30),-7);
                t25=new Thread1(this,v,24*(v.size()/30),25*(v.size()/30),-7);
                t26=new Thread1(this,v,25*(v.size()/30),26*(v.size()/30),-7);
                t27=new Thread1(this,v,26*(v.size()/30),27*(v.size()/30),-7);
                t28=new Thread1(this,v,27*(v.size()/30),28*(v.size()/30),-7);
                t29=new Thread1(this,v,28*(v.size()/30),29*(v.size()/30),-7);
                t30=new Thread1(this,v,29*(v.size()/30),v.size(),-7);
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
                try {
                    t1.join();t2.join();t3.join();t4.join();t5.join();
                    t6.join();t7.join();t8.join();t9.join();t10.join();
                    t11.join();t12.join();t13.join();t14.join();t15.join();
                    t16.join();t17.join();t18.join();t19.join();t20.join();
                    t21.join();t22.join();t23.join();t24.join();t25.join();
                    t26.join();t27.join();t28.join();t29.join();t30.join();
                }
                catch(Exception e) {}
                s+=t1.getS()+t2.getS()+t3.getS()+t4.getS()+t5.getS()+t6.getS()+t7.getS()+t8.getS()+t9.getS()+t10.getS();
                s+=t11.getS()+t12.getS()+t13.getS()+t14.getS()+t15.getS()+t16.getS()+t17.getS()+t18.getS()+t19.getS()+t20.getS();
                s+=t21.getS()+t22.getS()+t23.getS()+t24.getS()+t25.getS()+t26.getS()+t27.getS()+t28.getS()+t29.getS()+t30.getS();
            }
            else
                s=sum_1(v,0,v.size());
           // this.c.put(v, s);
            return s;
	}
	/*public int sumP(LinkedList x,int i,int j) {
		int sum=0;
		if(i==j-1)
			return sum;
		sum+=Integer.parseInt(((LinkedList)x.get(i)).get(2).toString()) + sumP(x,++i,j);
		return sum;
	}*/
       
        public synchronized int sum_1(LinkedList l,int start,int end) {
            int s=0;
            int a=end-start-1;
		while(a>3000) {
                        s+=sum1_1(l,a,a-3000);
			a-=3000;
		}
		s+=sum1_1(l,a,0);
                return s;
        }
        
        public int sum1_1(LinkedList l,int start,int end) {
            int s=0;
            if(start==end-1)
                return s;
            s+=Integer.parseInt(((LinkedList)l.get(start)).get(2).toString())+sum1_1(l,start-1,end);
            return s;
        }
        
	public double stroke(LinkedList s) {
		double val=Double.MAX_VALUE;
		double val1=0;
		int j=10,a=s.size()-1,i=0;
                to : while(j>0 && i<a) {
                    val1=0;
                    int x=Integer.parseInt(((LinkedList)s.get(i)).get(0).toString());
                    int y=Integer.parseInt(((LinkedList)s.get(i)).get(1).toString());
                    for(;y==Integer.parseInt(((LinkedList)s.get(i)).get(1).toString()) && i<a && Math.abs(x-Integer.parseInt(((LinkedList)s.get(i)).get(0).toString()))<=1 ;i++) {
                        //System.out.println(y+"\t"+Integer.parseInt(((LinkedList)s.get(i)).get(1).toString()));
                        val1++;
                        x=Integer.parseInt(((LinkedList)s.get(i)).get(0).toString());
                    }/*
                    ArrayList c1=stroke_1(s,x,y,i);
                    val1=Integer.parseInt(c1.get(0).toString());
                    i=Integer.parseInt(c1.get(1).toString());*/
                    //System.out.println(val1+"\t"+val);
                    if(Math.abs(val-val1) >=0 && Math.abs(val-val1) <=2)
                    	val=Math.max(val,val1);
                    else
			val=Math.min(val,val1);
                    
                    j--;
                    /*if(i<a)
                        i=stroke_2(s,Integer.parseInt(((LinkedList)s.get(i)).get(1).toString()),i,0);
                    */for(int k=0;k<3&&i<a;k++) {
                        y=Integer.parseInt(((LinkedList)s.get(i)).get(1).toString());
                        for(int l=0;y==Integer.parseInt(((LinkedList)s.get(i)).get(1).toString()) && i<a;i++,l++)
                            if(l>=5000)
                                break to;
                    }
                }
                if(val==Double.MAX_VALUE)
                    val=0;
             //   st.put(s,val);
		return val;
	}
        
	/*public synchronized ArrayList maxmin_1(LinkedList c,int start,int end)
	{
		ArrayList a=new ArrayList();
		double minx=Double.MAX_VALUE;
		double miny=Double.MAX_VALUE;
		double maxx=Double.MIN_VALUE;
		double maxy=Double.MIN_VALUE;
                int r=end-start;int k=0;
                while(r>1000) {
		for(int i=k*1000+start;i<(k+1)*1000+start;i++)
		{
			minx=Math.min(Integer.parseInt(((LinkedList)c.get(i)).get(0).toString()),minx);
			miny=Math.min(Integer.parseInt(((LinkedList)c.get(i)).get(1).toString()),miny);
			maxx=Math.max(Integer.parseInt(((LinkedList)c.get(i)).get(0).toString()),maxx);
			maxy=Math.max(Integer.parseInt(((LinkedList)c.get(i)).get(1).toString()),maxy);
		}
                r-=1000;k++;
                }
                for(int i=k*1000+start;i<end;i++)
		{
			minx=Math.min(Integer.parseInt(((LinkedList)c.get(i)).get(0).toString()),minx);
			miny=Math.min(Integer.parseInt(((LinkedList)c.get(i)).get(1).toString()),miny);
			maxx=Math.max(Integer.parseInt(((LinkedList)c.get(i)).get(0).toString()),maxx);
			maxy=Math.max(Integer.parseInt(((LinkedList)c.get(i)).get(1).toString()),maxy);
		}
		a.add(minx);
		a.add(miny);
		a.add(maxx);
		a.add(maxy);
		return a;
	}
	public ArrayList maxmin(LinkedList l)
	{
		ArrayList a=new ArrayList();
		if(l.size()>=20) {
			Thread1 t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20;
			t1=new Thread1(this,l,0,l.size()/20);
			t2=new Thread1(this,l,l.size()/20,2*(l.size()/20));
			t3=new Thread1(this,l,2*(l.size()/20),3*(l.size()/20));
			t4=new Thread1(this,l,3*(l.size()/20),4*(l.size()/20));
			t5=new Thread1(this,l,4*(l.size()/20),5*(l.size()/20));
			t6=new Thread1(this,l,5*(l.size()/20),6*(l.size()/20));
                        t7=new Thread1(this,l,6*(l.size()/20),7*(l.size()/20));
                        t8=new Thread1(this,l,7*(l.size()/20),8*(l.size()/20));
                        t9=new Thread1(this,l,8*(l.size()/20),9*(l.size()/20));
                        t10=new Thread1(this,l,9*(l.size()/20),10*(l.size()/20));
                        t11=new Thread1(this,l,10*(l.size()/20),11*(l.size()/20));
                        t12=new Thread1(this,l,11*(l.size()/20),12*(l.size()/20));
                        t13=new Thread1(this,l,12*(l.size()/20),13*(l.size()/20));
                        t14=new Thread1(this,l,13*(l.size()/20),14*(l.size()/20));
                        t15=new Thread1(this,l,14*(l.size()/20),15*(l.size()/20));
                        t16=new Thread1(this,l,15*(l.size()/20),16*(l.size()/20));
                        t17=new Thread1(this,l,16*(l.size()/20),17*(l.size()/20));
                        t18=new Thread1(this,l,17*(l.size()/20),18*(l.size()/20));
                        t19=new Thread1(this,l,18*(l.size()/20),19*(l.size()/20));
                        t20=new Thread1(this,l,19*(l.size()/20),l.size());
			t1.start();
			t2.start();
			t3.start();
			t4.start();
			t5.start();
			t6.start();
			t7.start();t8.start();t9.start();t10.start();
                        t11.start();
			t12.start();
			t13.start();
			t14.start();
			t15.start();
			t16.start();
			t17.start();t18.start();t19.start();t20.start();
			try
			{
				t1.join();t8.join();t9.join();t10.join();
                                //System.out.println("10 threads are completed");
				t2.join();t11.join();t12.join();t13.join();
                                t3.join();t14.join();t15.join();t16.join();
				t4.join();t17.join();t18.join();t19.join();
				t5.join();t20.join();//System.out.println("20 threads are completed");
				t6.join();
				t7.join();
			}
			catch( Exception e)
			{
				System.out.println(e.getMessage());
			}
                        System.out.println("Completed");
			Double minx,miny,maxx,maxy;
			minx=Double.parseDouble(t1.getA().get(0).toString());
			miny=Double.parseDouble(t1.getA().get(1).toString());
			maxx=Double.parseDouble(t1.getA().get(2).toString());
			maxy=Double.parseDouble(t1.getA().get(3).toString());
			minx=Math.min(Double.parseDouble(t2.getA().get(0).toString()),minx);
			minx=Math.min(Double.parseDouble(t3.getA().get(0).toString()),minx);
			minx=Math.min(Double.parseDouble(t4.getA().get(0).toString()),minx);
			minx=Math.min(Double.parseDouble(t5.getA().get(0).toString()),minx);
			minx=Math.min(Double.parseDouble(t6.getA().get(0).toString()),minx);
			minx=Math.min(Double.parseDouble(t7.getA().get(0).toString()),minx);
			miny=Math.min(Double.parseDouble(t2.getA().get(1).toString()),miny);
			miny=Math.min(Double.parseDouble(t3.getA().get(1).toString()),miny);
			miny=Math.min(Double.parseDouble(t4.getA().get(1).toString()),miny);
			miny=Math.min(Double.parseDouble(t5.getA().get(1).toString()),miny);
			miny=Math.min(Double.parseDouble(t6.getA().get(1).toString()),miny);
			miny=Math.min(Double.parseDouble(t7.getA().get(1).toString()),miny);
			maxx=Math.max(Double.parseDouble(t2.getA().get(2).toString()),maxx);
			maxx=Math.max(Double.parseDouble(t3.getA().get(2).toString()),maxx);
			maxx=Math.max(Double.parseDouble(t4.getA().get(2).toString()),maxx);
			maxx=Math.max(Double.parseDouble(t5.getA().get(2).toString()),maxx);
			maxx=Math.max(Double.parseDouble(t6.getA().get(2).toString()),maxx);
			maxx=Math.max(Double.parseDouble(t7.getA().get(2).toString()),maxx);
			maxy=Math.max(Double.parseDouble(t2.getA().get(3).toString()),maxy);
			maxy=Math.max(Double.parseDouble(t3.getA().get(3).toString()),maxy);
			maxy=Math.max(Double.parseDouble(t4.getA().get(3).toString()),maxy);
			maxy=Math.max(Double.parseDouble(t5.getA().get(3).toString()),maxy);
			maxy=Math.max(Double.parseDouble(t6.getA().get(3).toString()),maxy);
			maxy=Math.max(Double.parseDouble(t7.getA().get(3).toString()),maxy);
			a.add(minx);
			a.add(miny);
			a.add(maxx);
			a.add(maxy);
		}
		else
			a=maxmin_1(l,0,l.size());
		return a;
	}*/
        
        public void features1(LinkedList u,LinkedList v,LinkedList t) {
            ArrayList c1,c2,C=new ArrayList();LinkedList s=new LinkedList(),su=null,sv=null;
            if(m.containsKey(u.hashCode()))
                su=(LinkedList)m.get(u.hashCode());
            /*else
                su=(LinkedList)c.get(u.hashCode());*/
            if(m.containsKey(v.hashCode()))
                sv=(LinkedList)m.get(v.hashCode());
            /*else
                sv=(LinkedList)c.get(v.hashCode());*/
            c1=(ArrayList) su.get(0);
            c2=(ArrayList) sv.get(0);
            C.add(Math.min(Integer.parseInt(c1.get(0).toString()),Integer.parseInt(c2.get(0).toString())));
            C.add(Math.min(Integer.parseInt(c1.get(1).toString()),Integer.parseInt(c2.get(1).toString())));
            C.add(Math.max(Integer.parseInt(c1.get(2).toString()),Integer.parseInt(c2.get(2).toString())));
            C.add(Math.max(Integer.parseInt(c1.get(3).toString()),Integer.parseInt(c2.get(3).toString())));
            //m.put(t,C);
            s.add(C);
            int a=Integer.parseInt(su.get(1).toString())+Integer.parseInt(sv.get(1).toString());
            s.add(a);s.add(stroke(t));
	    //System.gc();
            /*if(m.size()>=500)
                c.put(t.hashCode(),s);
            else*/
                m.put(t.hashCode(),s);
            /*
            try {
                ResultSet rs1,rs;
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/images?" + "user=root&password=bujji");
                Statement stmnt=con.createStatement();
                rs=stmnt.executeQuery("select * from features where lid="+u.hashCode());rs.first();
                stmnt.close();
                Statement stmnt1=con.createStatement();
                rs1=stmnt1.executeQuery("select * from features where lid="+v.hashCode());rs1.first();
                stmnt1.close();
                Statement stmnt2=con.createStatement();
                stmnt2.execute("insert into features(lid,minx,miny,maxx,maxy,csum,st) values("+t.hashCode()+","+Math.min(rs.getDouble(3),rs1.getDouble(3))+","+Math.min(rs.getDouble(4),rs1.getDouble(4))+","+Math.max(rs.getDouble(5),rs1.getDouble(5))+","+Math.max(rs.getDouble(6),rs1.getDouble(6))+","+(rs.getInt(7)+rs1.getInt(7))+","+stroke(t)+")");
                stmnt2.close();
                rs.close();
                rs1.close();con.close();
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }*/
        }
        
	public ArrayList<TreeNode> formClus(double[] w,double t) {
		ArrayList<TreeNode> clus1=new ArrayList<TreeNode>(this.clus);
		TreeNode temp;
                //System.out.println(t);
		for(int i=0;i<clus1.size();i++) {
			for(int j=i+1;j<clus1.size();j++) {
                                temp=new TreeNode(null,-1,-1);
				LinkedList u=((TreeNode)clus1.get(i)).getData();
                                LinkedList v=((TreeNode)clus1.get(j)).getData();
								double val=dist(u,v,w);
           //                    System.out.println("Dealing With : "+u.size()+"\t"+v.size()+"\t"+val);
//								System.out.println(features(u,v).length);
				if(Double.compare(val, t) <= 0) {
                                        //System.out.println("Dealing With : \n"+u.size()+"\t"+v.size()+"\t"+dist(u,v,w));
                                        temp.addChild(((TreeNode)clus1.get(i)));
					temp.addChild(((TreeNode)clus1.get(j)));
					clus1.remove(((TreeNode)clus1.get(j)));
                    features1(u,v,temp.getData());
					clus1.set(i,temp);
					j--;
                                        //System.out.println(clus1.get(i).numChild());
                                        //System.out.println("Done");
				}
				//System.gc();
             //                   System.out.println("Done");
                                //try { Thread.sleep(2500); } catch(Exception e) { }
			}
                        //Thread1.gc();
		}
		return clus1;
	}
	public double dist(LinkedList u,LinkedList v,double[] w) {
		double[] xuv=features(u,v);
				/*for(int i=0;i<w.length;i++)
					System.out.print(xuv[i]+"\t");
				System.out.println();*/
		double x=mul(xuv,w);
		/*System.out.println(x);*/
		return x;
	}
	public double mul(double[] x,double[] w) {
		double pro=0;
		for(int i=0;i<x.length;i++)
			pro+=w[i]*x[i];
		return pro;
	}
        public static double round(double v) {
            return Math.round(v*100.0)/100.0;
        }
	public double[] grad(LinkedList C,LinkedList M,double[] theta) {
                double[] x=new double[8];
                x[0]=1;
		int c1=C.size();
		int c2=M.size();
		double p1=0,p2=0;
		double[] p3=new double[8],p4=new double[8];
                for(int i=0;i<p3.length;i++) {
                    p3[i]=p4[i]=0;
                }
		for(int i=0;i<c1;i++) {
			LinkedList u=((TreeNode)((LinkedList)C.get(i)).get(0)).getData();
			LinkedList v=((TreeNode)((LinkedList)C.get(i)).get(1)).getData();
			double[] X=features(u,v);
                        for(int j=1;j<x.length;j++)
                            x[j]=X[j-1];
                        /*System.out.println("Features And Theta");
                        for(int j=0;j<x.length;j++)
                            System.out.println(x[j]+"\t"+theta[j]);
                        System.out.println();*/
			//System.arraycopy(X, 0, x, 1, X.length);
                        //System.out.println(p1+"\t"+Math.log(h(x,theta)));
			p1+=Math.log(h(x,theta));
			p3=add(p3,mul(x,Math.log(1-h(x,theta))));
		}
		for(int i=0;i<c2;i++) {
			LinkedList u=((TreeNode)((LinkedList)M.get(i)).get(0)).getData();
                        LinkedList v=((TreeNode)((LinkedList)M.get(i)).get(1)).getData();
			double[] X=features(u,v);
                        for(int j=1;j<x.length;j++)
                            x[j]=X[j-1];
			p2+=Math.log(1-h(x,theta));
			p4=add(p4,mul(x,Math.log(h(x,theta))));
		}
		double num=p1+p2;
		double[] den=add(p3,p4);
		den=mul(den,(1/mul(den,den)));
		return mul(den,num);
	}
	public double[] optimize(LinkedList C,LinkedList M,double[] theta) {
		double step=0.1;
		double[] t=mul(grad(C,M,theta),step);
		//System.gc();
		return sub(theta,t);
	}
	public double[] add(double[] x,double[] y) {
                double[] t=new double[x.length];
		for(int i=0;i<t.length;i++)
			t[i]=x[i]+y[i];
		return t;
	}
	public double[] sub(double[] x,double[] y) {
                double[] t=new double[x.length];
		for(int i=0;i<t.length;i++)
			t[i]=x[i]-y[i];
		return t;
	}
	public double[] mul(double[] x,double c) {
                double[] t=new double[x.length];
		for(int i=0;i<t.length;i++)
			t[i]=x[i]*c;
		return t;
	}
	public double h(double[] x,double[] theta) {
		return (1/(Math.exp(-mul(x,theta))+1));
	}
	public ArrayList<TreeNode> detectText() {
            int conv=0;double e;
            double[] theta1,w=new double[7];
            ArrayList<TreeNode> tr;
            double[] theta={-0.257,0.11,0.21,0.12,0.13,0.13,0.12,0.1 };theta1=theta;
            LinkedList m,M,C;
            do {
                conv++;
                System.out.println("\nStart of Iteration : "+conv);
                theta=theta1;
                e=-theta[0];
                for(int i=0;i<w.length;i++)
                    w[i]=theta[i+1];
                System.out.println("Calling Cluster Formation");
				tr=formClus(w,e);
                //Thread1.paint(tr);
                System.out.println("Finished Forming Clusters : "+tr.size());
				if(conv==3)
					return tr;
                System.out.println("Called C Set Computation");
                m=findMinC(tr,w,e);
                if(m!=null)
                    C=m;
                else
                    C=new LinkedList();
                System.out.println("Completed C Set Computation. C : "+C.size());
                System.out.println("Called M Set Computation");
                m=findMinM(tr,w,e);
                if(m!=null)
                    M=m;
                else
                    M=new LinkedList();
                System.out.println("Completed M Set Computation. M : "+M.size());
                System.out.println("Calling Optimize Function");
                theta1=optimize(C,M,theta);
                HashMap temp=new HashMap();
                for(int i=0;i<25;i++) {
                    LinkedList x=clus.get(i).getData();
                    temp.put(x.hashCode(),(LinkedList)this.m.get(x.hashCode()));
                }
                this.m=temp;
                /*this.c.clear();
                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/images?" + "user=root&password=bujji");
                    Statement stmnt=con.createStatement();
                    stmnt.execute("delete from features where id > "+clus.size());
                    stmnt.close();con.close();
                }
                catch(Exception ex) {
                    System.out.println(ex.getMessage());
                }*/
                Thread1.gc();
                for(int i=0;i<theta.length;i++)
                    System.out.println(theta[i]+"\t"+theta1[i]+"\t"+Math.abs(theta[i]-theta1[i]));
                System.out.println("End of Iteration : "+conv);
                Thread1.gc();
		//System.gc();
            }while(conv<10 );//}&& !equals(theta,theta1));
            this.clus=tr;/*
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/images?" + "user=root&password=bujji");
                Statement stmnt=con.createStatement();
                stmnt.execute("drop table features");
                stmnt.close();con.close();
            }catch(Exception ex) { }*/
            System.out.println("Final Text Candidates Size : "+clus.size());
			return tr;
	}
        
        public boolean equals(double[] x,double[] x1) {
            for(int i=1;i<x.length;i++) {
                if(Double.compare(Math.abs(x[i]-x1[i]),0.025)<=0)
                    continue;
                else
                    return false;
			}
            return true;
        }
        /*
        public int fsize(int i,ArrayList<TreeNode> tr,int j) {
            if(j==tr.size()-1 && j!=i)
                return (tr.get(j).getSet()==null) ? 0 : tr.get(j).getSet().size();
            int s=0;
            if(j!=i)
                s+=(tr.get(j).getSet()==null) ? 0 : tr.get(j).getSet().size();
            s+=fsize(i,tr,j+1);
            return s;
        }
        
        public ArrayList<TreeNode> fill(ArrayList<TreeNode> tr,TreeNode i,int j) {
            ArrayList<TreeNode> temp=null;
            if(j==tr.size()-1 && tr.get(j)!=i)
                return tr.get(j).getSet();
            if(tr.get(j)!=i)
                temp=tr.get(j).getSet();
            if(temp!=null)
                temp.addAll(fill(tr,i,j+1));
            else
                temp=fill(tr,i,j+1);
            return temp;
        }*/
        
	public LinkedList findMinC(ArrayList<TreeNode> tr,double[] w,double e) {
                TreeNode[] c1,c2;
            LinkedList si=new LinkedList();
            for(int i=0;i<tr.size();i++) {
                    TreeNode c=tr.get(i);
                    c1=new TreeNode[c.size()+1];
                    //System.out.print(c1.length+"\t");
					int s=0;s+=tr.size()-1;
                    for(int j=0;j<tr.size();j++) {
						//System.out.println(s);
                        if(j!=i)
                            s+=tr.get(j).size();
					}
					c2=new TreeNode[s];int in=0;
                    //System.out.println(c2.length);
                    c1[0]=c;ArrayList<TreeNode> tem=c.getSet();
                    for(int j=1;j<c1.length;j++)
                        c1[j]=tem.get(j-1);
                    
					for(int j=0;j<tr.size();j++)
                        if(j!=i) {
                            c2[in++]=tr.get(j);
                        }
                    for(int k=0;k<tr.size();k++)
                        if(k!=i) {
                            ArrayList<TreeNode> t=tr.get(k).getSet();
                            for(int l=0;t!=null&&l<t.size();l++,in++)
                                c2[in]=t.get(l);
                        }
                    int p,q,r;
                    if(c1.length<3)
                        p=q=r=c1.length;
                    else {
                        p=c1.length/3;
                        q=2*(c1.length/3);
                        r=c1.length;
                    }
                    //System.out.println(c1.length+"\t"+c2.length);
                    if(c2.length>=10) {
                        Thread1 t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,t28,t29,t30;
                        t1=new Thread1(this,c1,0,p,c2,0,c2.length/10,w,e,null,0);
                        t2=new Thread1(this,c1,0,p,c2,c2.length/10,2*(c2.length/10),w,e,null,c2.length/10);
                        t3=new Thread1(this,c1,0,p,c2,2*(c2.length/10),3*(c2.length/10),w,e,null,2*(c2.length/10));
                        t4=new Thread1(this,c1,0,p,c2,3*(c2.length/10),4*(c2.length/10),w,e,null,3*(c2.length/10));
                        t5=new Thread1(this,c1,0,p,c2,4*(c2.length/10),5*(c2.length/10),w,e,null,4*(c2.length/10));
                        t6=new Thread1(this,c1,0,p,c2,5*(c2.length/10),6*(c2.length/10),w,e,null,5*(c2.length/10));
                        t7=new Thread1(this,c1,0,p,c2,6*(c2.length/10),7*(c2.length/10),w,e,null,6*(c2.length/10));
                        t8=new Thread1(this,c1,0,p,c2,7*(c2.length/10),8*(c2.length/10),w,e,null,7*(c2.length/10));
                        t9=new Thread1(this,c1,0,p,c2,8*(c2.length/10),9*(c2.length/10),w,e,null,8*(c2.length/10));
                        t10=new Thread1(this,c1,0,p,c2,9*(c2.length/10),c2.length,w,e,null,9*(c2.length/10));
                        t11=new Thread1(this,c1,p,q,c2,0,c2.length/10,w,e,null,0);
                        t12=new Thread1(this,c1,p,q,c2,c2.length/10,2*(c2.length/10),w,e,null,c2.length/10);
                        t13=new Thread1(this,c1,p,q,c2,2*(c2.length/10),3*(c2.length/10),w,e,null,2*(c2.length/10));
                        t14=new Thread1(this,c1,p,q,c2,3*(c2.length/10),4*(c2.length/10),w,e,null,3*(c2.length/10));
                        t15=new Thread1(this,c1,p,q,c2,4*(c2.length/10),5*(c2.length/10),w,e,null,4*(c2.length/10));
                        t16=new Thread1(this,c1,p,q,c2,5*(c2.length/10),6*(c2.length/10),w,e,null,5*(c2.length/10));
                        t17=new Thread1(this,c1,p,q,c2,6*(c2.length/10),7*(c2.length/10),w,e,null,6*(c2.length/10));
                        t18=new Thread1(this,c1,p,q,c2,7*(c2.length/10),8*(c2.length/10),w,e,null,7*(c2.length/10));
                        t19=new Thread1(this,c1,p,q,c2,8*(c2.length/10),9*(c2.length/10),w,e,null,8*(c2.length/10));
                        t20=new Thread1(this,c1,p,q,c2,9*(c2.length/10),c2.length,w,e,null,9*(c2.length/10));
                        t21=new Thread1(this,c1,q,r,c2,0,c2.length/10,w,e,null,0);
                        t22=new Thread1(this,c1,q,r,c2,c2.length/10,2*(c2.length/10),w,e,null,c2.length/10);
                        t23=new Thread1(this,c1,q,r,c2,2*(c2.length/10),3*(c2.length/10),w,e,null,2*(c2.length/10));
                        t24=new Thread1(this,c1,q,r,c2,3*(c2.length/10),4*(c2.length/10),w,e,null,3*(c2.length/10));
                        t25=new Thread1(this,c1,q,r,c2,4*(c2.length/10),5*(c2.length/10),w,e,null,4*(c2.length/10));
                        t26=new Thread1(this,c1,q,r,c2,5*(c2.length/10),6*(c2.length/10),w,e,null,5*(c2.length/10));
                        t27=new Thread1(this,c1,q,r,c2,6*(c2.length/10),7*(c2.length/10),w,e,null,6*(c2.length/10));
                        t28=new Thread1(this,c1,q,r,c2,7*(c2.length/10),8*(c2.length/10),w,e,null,7*(c2.length/10));
                        t29=new Thread1(this,c1,q,r,c2,8*(c2.length/10),9*(c2.length/10),w,e,null,8*(c2.length/10));
                        t30=new Thread1(this,c1,q,r,c2,9*(c2.length/10),c2.length,w,e,null,9*(c2.length/10));
                        t1.start();
                        t2.start();
                        t3.start();
                        t4.start();
                        t5.start();
                        t6.start();
                        t7.start();
                        t8.start();
                        t9.start();t10.start();
                        t11.start();t16.start();t17.start();t18.start();t19.start();t20.start();
                        t12.start();t21.start();t22.start();t23.start();t24.start();t25.start();
                        t13.start();t26.start();t27.start();t28.start();t29.start();t30.start();
                        t14.start();
                        t15.start();
                        try {
                            t1.join();t2.join();t3.join();t4.join();t5.join();
                            t6.join();t7.join();t8.join();t9.join();t10.join();
                            t11.join();t12.join();t13.join();t14.join();t15.join();
                            t16.join();t17.join();t18.join();t19.join();t20.join();
                            t21.join();t22.join();t23.join();t24.join();t25.join();
                            t26.join();t27.join();t28.join();t29.join();t30.join();
                        }
                        catch(Exception ex) {}
                        LinkedList temp=(t1.getC()!=null) ? t1.getC() : null;
                        temp= (temp==null||t2.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t2.getC().get(2).toString())) ? t2.getC(): temp;
                        temp= (temp==null||t3.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t3.getC().get(2).toString())) ? t3.getC(): temp;
                        temp= (temp==null||t4.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t4.getC().get(2).toString())) ? t4.getC(): temp;
                        temp= (temp==null||t5.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t5.getC().get(2).toString())) ? t5.getC(): temp;
                        temp= (temp==null||t6.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t6.getC().get(2).toString())) ? t6.getC(): temp;
                        temp= (temp==null||t7.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t7.getC().get(2).toString())) ? t7.getC(): temp;
                        temp= (temp==null||t8.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t8.getC().get(2).toString())) ? t8.getC(): temp;
                        temp= (temp==null||t9.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t9.getC().get(2).toString())) ? t9.getC(): temp;
                        temp= (temp==null||t10.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t10.getC().get(2).toString())) ? t10.getC(): temp;
                        temp= (temp==null||t11.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t11.getC().get(2).toString())) ? t11.getC(): temp;
                        temp= (temp==null||t12.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t12.getC().get(2).toString())) ? t12.getC(): temp;
                        temp= (temp==null||t13.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t13.getC().get(2).toString())) ? t13.getC(): temp;
                        temp= (temp==null||t14.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t14.getC().get(2).toString())) ? t14.getC(): temp;
                        temp= (temp==null||t15.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t15.getC().get(2).toString())) ? t15.getC(): temp;
                        temp= (temp==null||t16.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t16.getC().get(2).toString())) ? t16.getC(): temp;
                        temp= (temp==null||t17.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t17.getC().get(2).toString())) ? t17.getC(): temp;
                        temp= (temp==null||t18.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t18.getC().get(2).toString())) ? t18.getC(): temp;
                        temp= (temp==null||t19.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t19.getC().get(2).toString())) ? t19.getC(): temp;
                        temp= (temp==null||t20.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t20.getC().get(2).toString())) ? t20.getC(): temp;
                        temp= (temp==null||t21.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t21.getC().get(2).toString())) ? t21.getC(): temp;
                        temp= (temp==null||t22.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t22.getC().get(2).toString())) ? t22.getC(): temp;
                        temp= (temp==null||t23.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t23.getC().get(2).toString())) ? t23.getC(): temp;
                        temp= (temp==null||t24.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t24.getC().get(2).toString())) ? t24.getC(): temp;
                        temp= (temp==null||t25.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t25.getC().get(2).toString())) ? t25.getC(): temp;
                        temp= (temp==null||t26.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t26.getC().get(2).toString())) ? t26.getC(): temp;
                        temp= (temp==null||t27.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t27.getC().get(2).toString())) ? t27.getC(): temp;
                        temp= (temp==null||t28.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t28.getC().get(2).toString())) ? t28.getC(): temp;
                        temp= (temp==null||t29.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t29.getC().get(2).toString())) ? t29.getC(): temp;
                        temp= (temp==null||t30.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t30.getC().get(2).toString())) ? t30.getC(): temp;
                        if(temp!=null)
                            si.add(temp);
                    }
                    else if(c2.length<10)
                        if(c1.length>=3) {
                            Thread1 t1,t2,t3;
                            t1=new Thread1(this,c1,0,p,c2,0,c2.length,w,e,null,0);
                            t2=new Thread1(this,c1,p,q,c2,0,c2.length,w,e,null,0);
                            t3=new Thread1(this,c1,q,r,c2,0,c2.length,w,e,null,0);
                            t1.start();t2.start();t3.start();
                            try {
                                t1.join();t2.join();t3.join();
                            }
                            catch(Exception ex) { }
                            LinkedList temp=(t1.getC()!=null) ? t1.getC() : null;
                            temp= (temp==null||t2.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t2.getC().get(2).toString())) ? t2.getC(): temp;
                            temp=(temp==null||t3.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t3.getC().get(2).toString())) ? t3.getC(): temp;
                            if(temp!=null)
                                si.add(temp);
                        }
                        else {
                            LinkedList temp=min(c1,0,p,c2,0,c2.length,w,e,null,0);
                            if(temp!=null)
                                si.add(temp);
                        }
                    //System.out.println(c1.length+"\t"+c2.length+"\t"+si.get(si.size()-1));
            }
            //System.out.println(si+"\t"+si.get(si.size()-1));
            return si;
        }
        
	public LinkedList findMinM(ArrayList<TreeNode> tr,double[] w,double e) {
                TreeNode[] c1,c2;
            LinkedList s=new LinkedList();
            for(int i=0;i<tr.size();i++) {
                    TreeNode c=tr.get(i);
                    if(c.numChild()!=0) {
                        c1=new TreeNode[c.getChilds().get(0).size()+1];
                        c2=new TreeNode[c.getChilds().get(1).size()+1];
                        c1[0]=c.getChilds().get(0);
                        ArrayList<TreeNode> p=c.getChilds().get(0).getSet();
                        for(int j=1;j<c1.length;j++)
                            c1[j]=p.get(j-1);
                        c2[0]=c.getChilds().get(1);
                        p=c.getChilds().get(1).getSet();
                        for(int j=1;j<c2.length;j++)
                            c2[j]=p.get(j-1);
                    int t,u,v;
                    if(c1.length<3)
                        t=u=v=c1.length;
                    else {
                        t=c1.length/3;
                        u=2*(c1.length/3);
                        v=c1.length;
                    }
                    if(c2.length>=10) {
                        Thread1 t1,t2,t3,t4,t5,t6,t7,t8,t9,t10,t11,t12,t13,t14,t15,t16,t17,t18,t19,t20,t21,t22,t23,t24,t25,t26,t27,t28,t29,t30;
                        t1=new Thread1(this,c1,0,t,c2,0,c2.length/10,w,e,null,0);
                        t2=new Thread1(this,c1,0,t,c2,c2.length/10,2*(c2.length/10),w,e,null,c2.length/10);
                        t3=new Thread1(this,c1,0,t,c2,2*(c2.length/10),3*(c2.length/10),w,e,null,2*(c2.length/10));
                        t4=new Thread1(this,c1,0,t,c2,3*(c2.length/10),4*(c2.length/10),w,e,null,3*(c2.length/10));
                        t5=new Thread1(this,c1,0,t,c2,4*(c2.length/10),5*(c2.length/10),w,e,null,4*(c2.length/10));
                        t6=new Thread1(this,c1,0,t,c2,5*(c2.length/10),6*(c2.length/10),w,e,null,5*(c2.length/10));
                        t7=new Thread1(this,c1,0,t,c2,6*(c2.length/10),7*(c2.length/10),w,e,null,6*(c2.length/10));
                        t8=new Thread1(this,c1,0,t,c2,7*(c2.length/10),8*(c2.length/10),w,e,null,7*(c2.length/10));
                        t9=new Thread1(this,c1,0,t,c2,8*(c2.length/10),9*(c2.length/10),w,e,null,8*(c2.length/10));
                        t10=new Thread1(this,c1,0,t,c2,9*(c2.length/10),c2.length,w,e,null,9*(c2.length/10));
                        t11=new Thread1(this,c1,t,u,c2,0,c2.length/10,w,e,null,0);
                        t12=new Thread1(this,c1,t,u,c2,c2.length/10,2*(c2.length/10),w,e,null,c2.length/10);
                        t13=new Thread1(this,c1,t,u,c2,2*(c2.length/10),3*(c2.length/10),w,e,null,2*(c2.length/10));
                        t14=new Thread1(this,c1,t,u,c2,3*(c2.length/10),4*(c2.length/10),w,e,null,3*(c2.length/10));
                        t15=new Thread1(this,c1,t,u,c2,4*(c2.length/10),5*(c2.length/10),w,e,null,4*(c2.length/10));
                        t16=new Thread1(this,c1,t,u,c2,5*(c2.length/10),6*(c2.length/10),w,e,null,5*(c2.length/10));
                        t17=new Thread1(this,c1,t,u,c2,6*(c2.length/10),7*(c2.length/10),w,e,null,6*(c2.length/10));
                        t18=new Thread1(this,c1,t,u,c2,7*(c2.length/10),8*(c2.length/10),w,e,null,7*(c2.length/10));
                        t19=new Thread1(this,c1,t,u,c2,8*(c2.length/10),9*(c2.length/10),w,e,null,8*(c2.length/10));
                        t20=new Thread1(this,c1,t,u,c2,9*(c2.length/10),c2.length,w,e,null,9*(c2.length/10));
                        t21=new Thread1(this,c1,u,v,c2,0,c2.length/10,w,e,null,0);
                        t22=new Thread1(this,c1,u,v,c2,c2.length/10,2*(c2.length/10),w,e,null,c2.length/10);
                        t23=new Thread1(this,c1,u,v,c2,2*(c2.length/10),3*(c2.length/10),w,e,null,2*(c2.length/10));
                        t24=new Thread1(this,c1,u,v,c2,3*(c2.length/10),4*(c2.length/10),w,e,null,3*(c2.length/10));
                        t25=new Thread1(this,c1,u,v,c2,4*(c2.length/10),5*(c2.length/10),w,e,null,4*(c2.length/10));
                        t26=new Thread1(this,c1,u,v,c2,5*(c2.length/10),6*(c2.length/10),w,e,null,5*(c2.length/10));
                        t27=new Thread1(this,c1,u,v,c2,6*(c2.length/10),7*(c2.length/10),w,e,null,6*(c2.length/10));
                        t28=new Thread1(this,c1,u,v,c2,7*(c2.length/10),8*(c2.length/10),w,e,null,7*(c2.length/10));
                        t29=new Thread1(this,c1,u,v,c2,8*(c2.length/10),9*(c2.length/10),w,e,null,8*(c2.length/10));
                        t30=new Thread1(this,c1,u,v,c2,9*(c2.length/10),c2.length,w,e,null,9*(c2.length/10));
                        t1.start();
                        t2.start();
                        t3.start();
                        t4.start();
                        t5.start();
                        t6.start();
                        t7.start();
                        t8.start();
                        t9.start();t10.start();
                        t11.start();t16.start();t17.start();t18.start();t19.start();t20.start();
                        t12.start();t21.start();t22.start();t23.start();t24.start();t25.start();
                        t13.start();t26.start();t27.start();t28.start();t29.start();t30.start();
                        t14.start();
                        t15.start();
                        try {
                            t1.join();t2.join();t3.join();t4.join();t5.join();
                            t6.join();t7.join();t8.join();t9.join();t10.join();
                            t11.join();t12.join();t13.join();t14.join();t15.join();
                            t16.join();t17.join();t18.join();t19.join();t20.join();
                            t21.join();t22.join();t23.join();t24.join();t25.join();
                            t26.join();t27.join();t28.join();t29.join();t30.join();
                        }
                        catch(Exception ex) {}
                        LinkedList temp=(t1.getC()!=null) ? t1.getC() : null;
                        temp= (temp==null||t2.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t2.getC().get(2).toString())) ? t2.getC(): temp;
                        temp= (temp==null||t3.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t3.getC().get(2).toString())) ? t3.getC(): temp;
                        temp= (temp==null||t4.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t4.getC().get(2).toString())) ? t4.getC(): temp;
                        temp= (temp==null||t5.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t5.getC().get(2).toString())) ? t5.getC(): temp;
                        temp= (temp==null||t6.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t6.getC().get(2).toString())) ? t6.getC(): temp;
                        temp= (temp==null||t7.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t7.getC().get(2).toString())) ? t7.getC(): temp;
                        temp= (temp==null||t8.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t8.getC().get(2).toString())) ? t8.getC(): temp;
                        temp= (temp==null||t9.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t9.getC().get(2).toString())) ? t9.getC(): temp;
                        temp= (temp==null||t10.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t10.getC().get(2).toString())) ? t10.getC(): temp;
                        temp= (temp==null||t11.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t11.getC().get(2).toString())) ? t11.getC(): temp;
                        temp= (temp==null||t12.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t12.getC().get(2).toString())) ? t12.getC(): temp;
                        temp= (temp==null||t13.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t13.getC().get(2).toString())) ? t13.getC(): temp;
                        temp= (temp==null||t14.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t14.getC().get(2).toString())) ? t14.getC(): temp;
                        temp= (temp==null||t15.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t15.getC().get(2).toString())) ? t15.getC(): temp;
                        temp= (temp==null||t16.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t16.getC().get(2).toString())) ? t16.getC(): temp;
                        temp= (temp==null||t17.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t17.getC().get(2).toString())) ? t17.getC(): temp;
                        temp= (temp==null||t18.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t18.getC().get(2).toString())) ? t18.getC(): temp;
                        temp= (temp==null||t19.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t19.getC().get(2).toString())) ? t19.getC(): temp;
                        temp= (temp==null||t20.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t20.getC().get(2).toString())) ? t20.getC(): temp;
                        temp= (temp==null||t21.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t21.getC().get(2).toString())) ? t21.getC(): temp;
                        temp= (temp==null||t22.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t22.getC().get(2).toString())) ? t22.getC(): temp;
                        temp= (temp==null||t23.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t23.getC().get(2).toString())) ? t23.getC(): temp;
                        temp= (temp==null||t24.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t24.getC().get(2).toString())) ? t24.getC(): temp;
                        temp= (temp==null||t25.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t25.getC().get(2).toString())) ? t25.getC(): temp;
                        temp= (temp==null||t26.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t26.getC().get(2).toString())) ? t26.getC(): temp;
                        temp= (temp==null||t27.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t27.getC().get(2).toString())) ? t27.getC(): temp;
                        temp= (temp==null||t28.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t28.getC().get(2).toString())) ? t28.getC(): temp;
                        temp= (temp==null||t29.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t29.getC().get(2).toString())) ? t29.getC(): temp;
                        temp= (temp==null||t30.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t30.getC().get(2).toString())) ? t30.getC(): temp;
                        if(temp!=null)
                            s.add(temp);
                    }
                    else if(c2.length<10)
                        if(c1.length>=3) {
                            Thread1 t1,t2,t3;
                            t1=new Thread1(this,c1,0,t,c2,0,c2.length,w,e,null,0);
                            t2=new Thread1(this,c1,t,u,c2,0,c2.length,w,e,null,0);
                            t3=new Thread1(this,c1,u,v,c2,0,c2.length,w,e,null,0);
                            t1.start();t2.start();t3.start();
                            try {
                                t1.join();t2.join();t3.join();
                            }
                            catch(Exception ex) { }
                            LinkedList temp=t1.getC();
                            temp= (temp==null||t2.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t2.getC().get(2).toString())) ? t2.getC(): temp;
                            temp=(temp==null||t3.getC()!=null&&Double.parseDouble(temp.get(2).toString())>Double.parseDouble(t3.getC().get(2).toString())) ? t3.getC(): temp;
                            if(temp!=null)
                                s.add(temp);
                        }
                        else {
                            LinkedList temp=min(c1,0,t,c2,0,c2.length,w,e,null,0);
                            if(temp!=null)
                                s.add(temp);
                        }
                      //  System.out.println(c1.length+"\t"+c2.length+"\t"+s.get(s.size()-1));
                    }
                }
                if(s.size()!=0)
                    return s;
                else
                    return null;
	}
        
        public synchronized LinkedList min(TreeNode[] t1,int start,int end,TreeNode[] t2,int start1,int end1,double[] w,double e,LinkedList s,int j) {
            if(start==end || start1==end1)
                return null;
            TreeNode v=t2[start1];
            TreeNode u=t1[start];
            double m=dist(u.getData(),v.getData(),w);
            if(s==null) {
                s=new LinkedList();
                s.add(u);s.add(v);s.add(m);
            }
            else if(m<Double.parseDouble(s.get(2).toString())) {
                s.set(0,u);s.set(1,v);s.set(2,m);
            }
            if(start1==end1-1) {
                if(start==end-1)
                    return s;
                return min(t1,start+1,end,t2,j,end1,w,e,s,j);
            }
            return min(t1,start,end,t2,start1+1,end1,w,e,s,j);
            /*double min=Double.MAX_VALUE;
            TreeNode u=null,v=null;
            //System.out.print((end-start)+"\t"+(end1-start1)+"\t");
            for(int i=start;i<end;i++) {
                for(int j=start1;j<end1;j++) {
                    double m=dist(t1[i].getData(),t2[j].getData(),w);
                    if(min>m) {
                        min=m;
                        u=t1[i];
                        v=t2[j];
                    }
                }
            }
            //System.out.println(t1.length+"\t"+t2.length+"\t"+(end-start)+"\t"+(end1-start1));
            LinkedList s=new LinkedList();
            s.add(u);s.add(v);s.add(min);
            //System.out.println(s);
            if(u==null)
                return null;
            return s;*/
       }
}