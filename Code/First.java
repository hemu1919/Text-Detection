import java.applet.*;  
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;
import java.util.*;
public class First extends Applet{
	static BufferedImage img;
	MainControl mc;
	File f;
	ArrayList<TreeNode> tr;
        @Override
	public void init() {
		try {
			f=new File("src/Photos/00_02.jpg");
			img=ImageIO.read(f);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		mc=new MainControl(img);
	}
        public void paint_u(ArrayList<TreeNode> tr) {
            for(int i=0;i<tr.size();i++) {
                LinkedList p=tr.get(i).getData();
                for(int j=0;j<p.size();j++)
                    img.setRGB(Integer.parseInt(((LinkedList)p.get(j)).get(0).toString()), Integer.parseInt(((LinkedList)p.get(j)).get(1).toString()), new Color(0).getRGB());
                repaint();
                try { Thread.sleep(1800); } catch(Exception e) { }
            }
        }
        @Override
	public void start() {
		tr=mc.findRegions();
		repaint();
	} 
        @Override
	public void paint(Graphics g) {
		int max=img.getWidth()*img.getHeight()/2;
		//System.out.println(max);
		for(int i=0;i<tr.size();i++) {
			LinkedList s=tr.get(i).getData();
			//System.out.print(s.size()+"\t");
			for(int j=0;j<s.size() && s.size() < max;j++)
				img.setRGB(Integer.parseInt(((LinkedList)s.get(j)).get(0).toString()),Integer.parseInt(((LinkedList)s.get(j)).get(1).toString()),new Color(0).getRGB());
			g.drawImage(img,0,0,null);try { Thread.sleep(2500); } catch(Exception e) { }
		}
		String name=f.getName();
		g.drawImage(img,0,0,null);
		/*try {
			File outputfile = new File("Output/"+name+"_"+tr.size());
			ImageIO.write(img, "jpg", outputfile);
		} catch (IOException e) { }*/
	}
}

    /*
    <applet code="First.class" width="600" height="500"> 
    </applet> 
    */  