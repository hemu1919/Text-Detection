import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.io.*;

public class Result {

  public static double log10(double x) {
    return Math.log(x)/Math.log(10);
  }

  public static void main(String[] args) {
	  BufferedImage img1=null,img2=null;
	  File f;
	  int[][] pixels1=null,pixels2=null;
	  int nrows=0,ncols=0;
	  try {
			f=new File(args[0]);
			img1=ImageIO.read(f);
			f=new File(args[1]);
			img2=ImageIO.read(f);
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		nrows=img1.getWidth();
		ncols=img1.getHeight();
		pixels1=new int[nrows][ncols];
		for(int y=0;y<ncols;y++)
			for(int x=0;x<nrows;x++) {
				pixels1[x][y]=MainControl.grayLevel(x,y,img1);
			}
		nrows=img2.getWidth();
		ncols=img2.getHeight();
		pixels2=new int[nrows][ncols];
		for(int y=0;y<ncols;y++)
			for(int x=0;x<nrows;x++) {
				pixels2[x][y]=MainControl.grayLevel(x,y,img2);
			}
			nrows=Math.min(nrows,img1.getWidth());
			ncols=Math.min(ncols,img1.getHeight());
		compute(pixels1,pixels2,nrows,ncols);
  }
  
  public static void compute (int[][] img1,int [][]img2, int nrows,int ncols) {
    /*int     nrows, ncols;
    int     img1[][], img2[][];*/
    double  peak, signal, noise, mse;

    /*if (args.length != 4) {
      System.out.println("Usage: Psnr <nrows> <ncols> <img1> <img2>");
      return;
    }
    nrows = Integer.parseInt(args[0]);
    ncols = Integer.parseInt(args[1]);
    img1 = new int[nrows][ncols];
    img2 = new int[nrows][ncols];
    ArrayIO.readByteArray(args[2], img1, nrows, ncols);
    ArrayIO.readByteArray(args[3], img2, nrows, ncols);*/

    signal = noise = peak = 0;
    for (int i=0; i<nrows; i++) {
      for (int j=0; j<ncols; j++) {
        signal += img1[i][j] * img1[i][j];
        noise += (img1[i][j] - img2[i][j]) * (img1[i][j] - img2[i][j]);
        if (peak < img1[i][j])
          peak = img1[i][j];
      }
    }

    mse = noise/(nrows*ncols); // Mean square error
    System.out.println("MSE: " + mse);
    System.out.println("SNR: " + 10*log10(signal/noise));
    System.out.println("PSNR(max=255): " + (10*log10(255*255/mse)));
    System.out.println("PSNR(max=" + peak + "): " + 10*log10((peak*peak)/mse));
  }
}
