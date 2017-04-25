import java.util.Scanner;
public class Q1a{
	public static float[][] inner_product(float mat1[][], float mat2[][]){
		float[][] m3 = new float[mat1.length][mat1.length];
		for(int i=0;i<mat1.length;i++){
			for(int j=0;j<mat1.length;j++){
				m3[i][j] = 0.0f;
				for(int k=0;k<mat1.length;k++){
					m3[i][j] = m3[i][j] + mat1[i][k]*mat2[k][j];
				}
			}
		}
		return m3;
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
		float[][] m3 = inner_product(m1,m2);
		long end_time = System.currentTimeMillis();
		/*for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				System.out.print(m3[i][j]+"   ");
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