import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;


public class chromosome {
    int degree=0;
    ArrayList<Double>coefficient =new ArrayList<Double>();
    ArrayList<points>p =new ArrayList<points>();
    double fitness=0;
    chromosome(){};
    chromosome(int deg){
        degree=deg;
        int c=0;
        Random r=new Random();
        int min =-10;
        int max=10;
        while(c<=degree) {
            double x = r.nextInt(max-min)+min;
                coefficient.add(x);
                c++;
            }
        }


    void calc_fitness(){
        double m=0;
        for(int i=0;i<p.size();i++){

            double y_predicted=coefficient.get(0);
            for(int j=1;j<coefficient.size();j++){
                y_predicted+= coefficient.get(j)*pow((p.get(i).x),j);
            }
            m+=pow((y_predicted-p.get(i).y),2);
        }
        m=m/p.size();
        fitness=m;

    }



    public static void main(String[] args) {

    }
}
