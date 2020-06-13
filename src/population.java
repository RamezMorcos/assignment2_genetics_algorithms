import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import static java.lang.Math.pow;

public class population {
    ArrayList<chromosome>pop=new ArrayList<>();
chromosome firstchiled=null;
double mse=99999999;
chromosome secondchiled=null;
    population(int popsize,ArrayList<points>p,int deg){
        for (int i=0;i<popsize;i++){
            chromosome c=new chromosome(deg);
            c.p=p;
            c.calc_fitness();
            pop.add(c);
        }
    }
    public void swapNumbers(int i, int j) {

        chromosome temp=new chromosome();
        temp.fitness=pop.get(i).fitness;
        temp.p=pop.get(i).p;
        temp.coefficient=pop.get(i).coefficient;
        temp.degree=pop.get(i).degree;

        pop.get(i).fitness = pop.get(j).fitness;
        pop.get(i).degree = pop.get(j).degree;
        pop.get(i).coefficient = pop.get(j).coefficient;
        pop.get(i).p = pop.get(j).p;

        pop.get(j).fitness = temp.fitness;
        pop.get(j).degree = temp.degree;
        pop.get(j).coefficient = temp.coefficient;
        pop.get(j).p = temp.p;


    }
    public  void bubble_srt() {
        int x = pop.size();

        for (int m = 0; m<x-1; m++) {
            for (int i = 0; i < x -m- 1; i++) {

                if (pop.get(i).fitness > pop.get(i+1).fitness) {
                    swapNumbers(i, i+1);
                }
            }
        }
    }
    public void selection (){
        bubble_srt();
        firstchiled=pop.get(0);
        if(mse>firstchiled.fitness) {
            mse = firstchiled.fitness;
        }

        secondchiled=pop.get(1);

    }
    public void crossover(){
        Random r=new Random();
        int len_of_crossover=r.nextInt(pop.get(0).coefficient.size());
        double cr=0.5;
        double y=r.nextDouble();
        if(y>cr){
            return ;
        }
        else{
            for(int i=len_of_crossover;i<firstchiled.coefficient.size();i++){
                double x= firstchiled.coefficient.get(i);
                firstchiled.coefficient.set(i,secondchiled.coefficient.get(i));
                secondchiled.coefficient.set(i,x);
            }
        }

    }
    public double delta_upper(double x){
        return 10-x;

    }
    public double delta_lower(double x){
        return x+10;

    }
    public double calculate(int generation_num ,int max_generation){
        Random rond=new Random();
        double r=rond.nextDouble();
        double p1=pow((1-generation_num)/max_generation,2);
        double p2=pow(r,p1);
        return p2;
    }
    public void mutation(int generation_num,int max_generation){
    double y1=0;
        double y2=0;
    Random rond=new Random();
        double r=0;
        for(int i=0;i<firstchiled.coefficient.size();i++){
            r=rond.nextDouble();
            if(r>.5){
               y1=delta_upper(firstchiled.coefficient.get(i));
                y2=delta_upper(secondchiled.coefficient.get(i));
            }
            else{
                y1=delta_lower(firstchiled.coefficient.get(i));
                y2=delta_lower(secondchiled.coefficient.get(i));

            }
            double deltai1=y1*calculate(generation_num,max_generation);
            double deltai2=y1*calculate(generation_num,max_generation);
            if (r>.5){
                firstchiled.coefficient.set(i,firstchiled.coefficient.get(i)+deltai1);
                secondchiled.coefficient.set(i,secondchiled.coefficient.get(i)+deltai1);
            }
            else{
                firstchiled.coefficient.set(i,firstchiled.coefficient.get(i)-deltai1);
                secondchiled.coefficient.set(i,secondchiled.coefficient.get(i)*deltai1);
            }
        }
    }
    public void replacement(){
        firstchiled.calc_fitness();
        secondchiled.calc_fitness();
        if(firstchiled.fitness<pop.get(0).fitness){
            pop.set(0,firstchiled);
            if(firstchiled.fitness<secondchiled.fitness){
            mse=pop.get(0).fitness;}
        }
        if(secondchiled.fitness<pop.get(0).fitness){
            pop.set(1,secondchiled);
            if(firstchiled.fitness>secondchiled.fitness){
                mse=pop.get(1).fitness;}
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int cases;
        Scanner s=new Scanner(System.in);
        cases=s.nextInt();
        for(int i=0;i<cases;i++){
            int num_points=s.nextInt();
            int degree=s.nextInt();
            ArrayList<points>p=new ArrayList<>();
            double count=0;

            double [] d=new double[64];
            File f=new File("input.txt");
           Scanner in = new Scanner(f);
           int coun=0;
           while(in.hasNextLine()){
               String str="";
               str+=in.nextLine();
               double y=Double.parseDouble(str);
               d[coun]=y;
               coun++;

           }


            for (int j=0;j<num_points;j++){
                points p1=new points();
                p1.x=count;
                count+=0.1;
                p1.y=d[j];
                p.add(p1);
            }
            int generation =0;
            population po=new population(1000,p,degree);
            while (generation<1000){
                po.selection();
                po.crossover();
                po.mutation(generation,100);
                po.replacement();


               generation++;

            }
            System.out.println("case: "+(i+1));
            System.out.println(po.mse);
            for(int k=0;k<po.pop.get(0).coefficient.size();k++){

                System.out.println(po.pop.get(0).coefficient.get(i));

            }
        }

    }


}
