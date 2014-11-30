

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *削除できる節のペアを覚えるための構造体
 * @author HONDA Etsuro
 */
public class CPair {
    public final static int FIRSTSET = 0;
    public final static int SCONDSET = 1;
    public final static int WSETS = 2;

    public int c1;
    public int c2;
    public int u1;
    public  int u2;
    public int pattern; //いくつの変数の組が該当するか；二組2、一組1
    public int try_num; //何回目の試行で得たデータであるか




     CPair(){
        this.c1=0;
        c2=0;
        u1=0;
        u2=0;
        pattern=-1;
        try_num=0;
    }



     //結果の出力
     public void outputValue(){
    	 System.out.printf ( " C1: %6.5s",c1);
    	 System.out.printf ( " C2: %6.5s",c2);
    	 System.out.printf ( " U1: %6.5s",u1);
    	 System.out.printf ( " U2: %6.5s",u2);
    	 System.out.printf ( " pat: %-4d",pattern);
    	 System.out.printf ( " try num: %6.5s%n",try_num);
    }
    public void outputWValue(){
         outputValue();
    }
     //

}
