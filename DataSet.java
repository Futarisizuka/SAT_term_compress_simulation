/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.Random;
/**
 *SAT疑似データ
 * @author HONDA Etsuro
 */
public class DataSet {
    public static final int C_MAX=1000;
    public static final int U_MAX=100;
    //SATのシミュレーションデータ
    public int[][] C=new int[C_MAX][U_MAX];
    public boolean[] sein_flag=new boolean[C_MAX];
    public int sein_num;


   DataSet(){
        int i,j;
        Random rnd = new Random();
        for(i=0;i<C_MAX;i++){
            for(j=0;j<U_MAX;j++){
                C[i][j]=rnd.nextInt(3)-1;
            }
            sein_flag[i]=true;
        }
        sein_num=C_MAX;
   }
 //いくつのデータが存在しているかの計算と表示
   public void countout_seinflag(){
	   int i;
	   int counter=0;
	   double rate,total;

	   for(i=0;i<C_MAX;i++){
		   if(this.sein_flag[i]){
			   counter++;
		   }
	   }

	   if(this.sein_num!=0){
		   rate=1.0-(double)counter/(double)this.sein_num;
	   }else{
		   rate=0;
	   }
	   total=1.0-(double)counter/(double)C_MAX;
	   System.out.println("sein/pre_sein/total="+counter+"/"+this.sein_num+"/"+C_MAX);
	   System.out.printf("1loop compress rate=%f%n",rate);
	   System.out.printf("total compress rate=%f%n",total);
	   this.sein_num=counter;

   }
 //いくつのデータが存在しているかの計算
   public void count_seinflag(){
	   int i;
	   int counter=0;
	   for(i=0;i<C_MAX;i++){
		   if(this.sein_flag[i]){
			   counter++;
		   }
	   }
	   this.sein_num=counter;
   }


//現在のデータセットの内容を表示
	public void outputDataSet(){
		int i,j;
		//番号を表示
		System.out.println("//////////////////////////////////////////////////////////////");
		System.out.print("  i        :");
		for(i=1;i<=U_MAX;i++){
			System.out.printf("%5d",i);
		}
		System.out.println(" | sein_flag");
				System.out.println("-------------------------------------");

		//データを表示

        for(i=0;i<C_MAX;i++){
    		System.out.printf("data %6d:",(i+1));
        	for(j=0;j<U_MAX;j++){
            	System.out.printf("%5d",this.C[i][j]);
            }
        	System.out.printf(" | %b%n",this.sein_flag[i]);
        }
        System.out.println(" ：sein num:"+this.sein_num);

		System.out.println("//////////////////////////////////////////////////////////////");
	}

}
