import java.util.Scanner;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateMat{
	public static void main(String[] args){
		Scanner in = new Scanner(System.in);
		int size = in.nextInt();
		BufferedWriter bw = null;
		float[][] mat1 = new float[size][size];
		float[][] mat2 = new float[size][size];
		Random rand = new Random();
		try{
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					mat1[i][j] = rand.nextFloat();
				}
			}
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					mat2[i][j] = rand.nextFloat();
				}
			}
			File file = new File("C:/Users/Hemanth/Desktop/PESU/Adv.Algo/Assignment/1/InputFiles/"+size+".txt");
			if(!file.exists())
				file.createNewFile();
			FileWriter f = new FileWriter(file);
			bw = new BufferedWriter(f);
			bw.write(size+" ");
			bw.write("\r\n");
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					bw.write(mat1[i][j]+"   ");
				}
				bw.write("\r\n");
			}
			bw.write("\r\n");
			for(int i=0;i<size;i++){
				for(int j=0;j<size;j++){
					bw.write(mat2[i][j]+"   ");
				}
				bw.write("\r\n");
			}
		}
		catch (IOException ioe) {
	   		ioe.printStackTrace();
		}
		finally
		{ 
	   		try{
	      		if(bw!=null)
		 		bw.close();
	   		}catch(Exception ex){
	       		System.out.println("Error in closing the BufferedWriter"+ex);
	    	}
		}
	}
}