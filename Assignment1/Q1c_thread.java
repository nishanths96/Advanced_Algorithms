import java.util.Scanner;

public class Q1c_thread{
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
		Q0 t1 = new Q0(m1,m2,0,0,0,0,m1.length,true);
		try {
			t1.t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end_time = System.currentTimeMillis();
		//uncomment to DISPLAY the RESULT_MATRIX if needed
		/*for(int i=0;i<n;i++){						
			for(int j=0;j<n;j++){
				System.out.print(t1.res[i][j]+"   ");
			}
			System.out.println("");
		}*/
		float time = end_time-start_time;
		time = time/1000;	
		//System.out.print("Input Size: "+ n+"	    " +"Execution time(sec): ");
		System.out.format("%.6f",time);
		System.out.println("");
	
	}

}
class Q0 implements Runnable{
	float[][] m1; float[][] m2;
	Thread t;
	int row_m1,col_m1,row_m2,col_m2,size;
	float[][] res;
	boolean s;
	public Q0(float[][] m1,float[][] m2,int row_m1,int col_m1,int row_m2,int col_m2, int size, boolean s){
		this.m1 = m1; this.m2 = m2;
		this.row_m1 = row_m1;
		this.row_m2 = row_m2;
		this.col_m1 = col_m1;
		this.col_m2 = col_m2;
		this.size = size;
		this.s = s;
		res = new float[size][size];
		t = new Thread(this);
		t.start();
	}
	public static float[][] strassen(float[][] m1,float[][] m2,int row_m1,int col_m1,int row_m2,int col_m2, int size){
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
			add(res,strassen(m1,m2,row_m1,col_m1,row_m2,col_m2,n),
					strassen(m1,m2,row_m1,col_m1+n,row_m2+n,col_m2,n),
				0,0);		//11
			add(res,strassen(m1,m2,row_m1,col_m1,row_m2,col_m2+n,n),
					strassen(m1,m2,row_m1,col_m1+n,row_m2+n,col_m2+n,n),
					0,n);		//12
			add(res,strassen(m1,m2,row_m1+n,col_m1,row_m2,col_m2,n),
					strassen(m1,m2,row_m1+n,col_m1+n,row_m2+n,col_m2,n),
					n,0);		//21
			add(res,strassen(m1,m2,row_m1+n,col_m1,row_m2,col_m2+n,n),
					strassen(m1,m2,row_m1+n,col_m1+n,row_m2+n,col_m2+n,n),
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
	@Override
	public void run(){
		if(s){
			int n = size/2;
			Q0 t2,t3,t4,t5,t6,t7,t8,t9;
			t2 = new Q0(m1,m2,row_m1,col_m1,row_m2,col_m2,n,false);
			t3 = new Q0(m1,m2,row_m1,col_m1+n,row_m2+n,col_m2,n,false);
			t4 = new Q0(m1,m2,row_m1,col_m1,row_m2,col_m2+n,n,false);
			t5 = new Q0(m1,m2,row_m1,col_m1+n,row_m2+n,col_m2+n,n,false);
			t6 = new Q0(m1,m2,row_m1+n,col_m1,row_m2,col_m2,n,false);
			t7 = new Q0(m1,m2,row_m1+n,col_m1+n,row_m2+n,col_m2,n,false);
			t8 = new Q0(m1,m2,row_m1+n,col_m1,row_m2,col_m2+n,n,false);
			t9 = new Q0(m1,m2,row_m1+n,col_m1+n,row_m2+n,col_m2+n,n,false);
			try {
				t2.t.join();
				t3.t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			add(res,t2.res,t3.res,0,0);
			try {
				t4.t.join();
				t5.t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			add(res,t4.res,t5.res,0,n);
			try {
				t6.t.join();
				t7.t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			add(res,t6.res,t7.res,n,0);
			try {
				t8.t.join();
				t9.t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			add(res,t8.res,t9.res,n,n);
		}
		else
			res = strassen(m1,m2,row_m1,col_m1,row_m2,col_m2,size);
	}

}