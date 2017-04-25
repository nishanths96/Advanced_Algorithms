import java.util.Scanner;

public class Q1c{
	public static float[][] strassens_method(float[][] m1, float[][] m2){
		float res[][] = strassens(m1,m2,0,0,0,0,m1.length);
		return res;
	}
	public static float[][] strassens(float[][] m1,float[][] m2,int row_m1,int col_m1,int row_m2,int col_m2, int size){
		float[][] res = new float[size][size];
		if(size==2){
			float x1 = (m1[row_m1][col_m1]+m1[row_m1+1][col_m1+1]) * (m2[row_m2][col_m2]+m2[row_m2+1][col_m2+1]);
      		float x2 = (m1[row_m1+1][col_m1]+m1[row_m1+1][col_m1+1]) * m2[row_m2][col_m2];
      		float x3 = m1[row_m1][col_m1] * (m2[row_m2][col_m2+1]-m2[row_m2+1][col_m2+1]);
      		float x4 = m1[row_m1+1][col_m1+1] * (m2[row_m2+1][col_m2]-m2[row_m2][col_m2]);
      		float x5 = (m1[row_m1][col_m1]+m1[row_m1][col_m1+1]) * m2[row_m2+1][col_m2+1];
      		float x6 = (m1[row_m1+1][col_m1]-m1[row_m1][col_m1]) * (m2[row_m2][col_m2]+m2[row_m2][col_m2+1]);
      		float x7 = (m1[row_m1][col_m1+1]-m1[row_m1+1][col_m1+1]) * (m2[row_m2+1][col_m2]+m2[row_m2+1][col_m2+1]);
      		res[0][0] = x1+x4-x5+x7;
      		res[0][1] = x3+x5;
      		res[1][0] = x2+x4;
      		res[1][1] = x1-x2+x3+x6;
		}
		else{
			int n = size/2;
			add(res,strassens(m1,m2,row_m1,col_m1,row_m2,col_m2,n),
					strassens(m1,m2,row_m1,col_m1+n,row_m2+n,col_m2,n),
				0,0);		//11
			add(res,strassens(m1,m2,row_m1,col_m1,row_m2,col_m2+n,n),
					strassens(m1,m2,row_m1,col_m1+n,row_m2+n,col_m2+n,n),
					0,n);		//12
			add(res,strassens(m1,m2,row_m1+n,col_m1,row_m2,col_m2,n),
					strassens(m1,m2,row_m1+n,col_m1+n,row_m2+n,col_m2,n),
					n,0);		//21
			add(res,strassens(m1,m2,row_m1+n,col_m1,row_m2,col_m2+n,n),
					strassens(m1,m2,row_m1+n,col_m1+n,row_m2+n,col_m2+n,n),
					n,n);		//22
		}
		return res;	
	}
	public static void add(float[][] res,float[][] m1,float[][] m2,int row, int col){
		for(int i=0;i<m1.length;i++){
			for(int j=0;j<m1.length;j++){
				res[i+row][j+col] = m1[i][j]+m2[i][j];
			}
		}
	}
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		float[][] m1 = new float[n][n];
		float[][] m2 = new float[n][n];
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				m1[i][j] = in.nextFloat();
		for(int i=0;i<n;i++)
			for(int j=0;j<n;j++)
				m2[i][j] = in.nextFloat();
		long start_time = System.currentTimeMillis();
		float[][] m3 = strassens_method(m1,m2);
		long end_time = System.currentTimeMillis();
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.print(m3[i][j]+"   ");
			}
			System.out.println("");
		}
		float time = end_time-start_time;
		time = time/1000;	
		//System.out.print("Input Size: "+ n+"	    " +"Execution time(sec): ");
		System.out.format("%.6f",time);
		System.out.println("");
	}

}