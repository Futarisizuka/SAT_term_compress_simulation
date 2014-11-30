/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Random;

/**
 *
 * @author HONDA Etsuro
 */
public class SAT_termDel_sim {

    /**
     * @param args the command line arguments
     */
        //シミュレーションデータ
        static DataSet data;
        //シミュレーションデータを変えながら何回試行するか
        final static long TOTAL_TRL_NUM=10;

        //同じデータで何回セット圧縮を試みるか
        final static int TR_1SET_DATA_NUM=1000;
        //同じデータで何度乱数を発生させて圧縮を試みるか（一セット）
        final static int TR_LOOP = 81;

        //このクラスでのグローバルデータ
        final static int D_MAX_NUM=DataSet.C_MAX*DataSet.C_MAX; //削除する節の記憶用構造体の大きさ
        static CPair[] cpairs= new CPair[TR_LOOP*TR_1SET_DATA_NUM];//削除する節の記憶用構造体
        static CPair cpair; //ワーク用構造体
        static int wpair_num ; //削除する節の個数
        static int wpair_num_sum=0; //数セット圧縮を試みたとき削除する節がいくつあるか
        static int wpair_num_total=0; //数セット圧縮をするときの平均を求めるため
        static int try_num=0; //試行の番号用
        static Random rnd=new Random();




    public static void main(String[] args) {
        //ループカウンタ
        long i;
        int j;

        //通算試行回数の初期化
        try_num=0;
        //試行用データを変えて試行とデータ圧縮を何回かやってみる
        for(i=1;i<=TOTAL_TRL_NUM;i++){
        	//試行用データの初期化
            data=new DataSet();

            //まずSAT疑似データを表示
            System.out.println("------------------------------------");
            data.outputDataSet();            
            
        	//試行1データにおける結果データの初期化
        	wpair_num = 0;

        	
        	//SATデータはそのままで試行圧縮を何セットかやってみる
            for(j=0;j<TR_1SET_DATA_NUM;j++){
            	rnd.setSeed(j);  //乱数の元を変更してみる
	            //試行
	            tryal();

	            //データの圧縮
	            compressData();
	            //統計用データの更新
	            wpair_num_sum=(DataSet.C_MAX-data.sein_num);
	            

	            /*　以下、ループの終わりまで表示用ルーチン　*/

	            //
	            //試行結果を表示
	            System.out.print("i="+i+" : ");
	            output_CSets();

	            //一つのデータの何セット目の試行圧縮か
	            System.out.println("compress no."+(j+1));

	            System.out.println("------------------------------------");
	            System.out.println("wpair_set:"+wpair_num);            //これまでの試行で何組存在したか
	            System.out.println("------------------------------------");
	            data.countout_seinflag();	//注：pdata.count_seinflag();とどちらかを使うこと
	            System.out.println("************************************");
	            //最終結果の表示
//	            data.count_seinflag();  //注：data.countout_seinflag();とどちらかを使うこと

            }
            wpair_num_total+=wpair_num_sum;
            
            /*　以下　ループの終わりまで表示用ルーチン　*/            
            //同じデータで試行を何セットか行ったときに一回あたりどれだけ該当する節の組が見つかったかの平均
            System.out.println("===================================");
            System.out.println("TRY " + i+ " :" + "wpair number  "+(wpair_num_sum)+"/"+DataSet.C_MAX);
            System.out.println("===================================");

        }
        //元のSATデータを変えて試行を何セットか行ったときに一回あたりどれだけ該当する節の組が見つかったかの平均
        System.out.println("+++++++++++++++++++++++++++++++++++");
        System.out.println("Do " + TOTAL_TRL_NUM + " :" + "wpair ave.  "+(wpair_num_total/TOTAL_TRL_NUM)+"/"+DataSet.C_MAX);
        System.out.println("+++++++++++++++++++++++++++++++++++");
    }

    //SATデータの圧縮
    private static void compressData(){
    	int i;
    	for(i=0;i<wpair_num;i++){
    		if (cpairs[i].pattern==CPair.WSETS){
    			data.sein_flag[cpairs[i].c1]=false;
    			data.sein_flag[cpairs[i].c2]=false;
    		}
    	}
    }
    //圧縮できる節を見つけるための試行
   private static void tryal() {
       boolean pair1;
       boolean pair2;

       for (try_num=0;try_num<TR_LOOP;try_num++){
    	   //試行結果を記憶するワーク構造体の初期化
    	   cpair = cpairs[wpair_num];
    	   cpair=new CPair();  //コンストラクタによる初期化
    	   //乱数を発生させ試行する節を選ぶ
    	   setCandU(cpair);
    	   //選ばれた節の判定
    	   pair1=checkPair(cpair.u1);
    	   pair2=checkPair(cpair.u2);
    	   //該当する節の組を記憶する
    	   if(pair1 && pair2){
    		   setU_2Set();
    	   }
		}
   }

//節の組の表示ルーチン
    private static void output_CSets() {
    	int i;
        System.out.println("wpair_num: "+wpair_num );
        for(i=0;i<wpair_num;i++){
        	System.out.printf("no.%5s : ",i);
            cpairs[i].outputWValue();        	
        }

    }


//該当する節を記憶するルーチン
    private static void setU_2Set() {
        cpairs[wpair_num]=cpair;
        cpairs[wpair_num].pattern=CPair.WSETS;
        cpairs[wpair_num].try_num=try_num;

        wpair_num++;
    }

//


    //該当する節であるかをチェックする
    private static boolean checkPair(int u_no){
        int C1_u;
            C1_u = data.C[cpair.c1][u_no];
        int C2_u;
            C2_u = data.C[cpair.c2][u_no];
        int work;
        work=C1_u+C2_u;
        if(C1_u!=0 && C2_u!=0 && work==0){
        	return true;
        }
        return false;
    }

     //乱数を発生させ、適当に節と変数を選ぶ
    public static  void setCandU(CPair cpair){
        cpair.c1=rnd.nextInt(DataSet.C_MAX);
        cpair.c2=rnd.nextInt(DataSet.C_MAX);
        cpair.u1=rnd.nextInt(DataSet.U_MAX);
        cpair.u2=rnd.nextInt(DataSet.U_MAX);
     }

}


