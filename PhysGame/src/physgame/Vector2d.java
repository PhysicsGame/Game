/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physicsgame;

/**
 *
 * @author Games
 */
public class Vector2d {
    
    private double x, y;
    Vector2d(){
        
        x = y =0;
        
    }
    
    Vector2d(double a, double b){
        x =a;
        y=b;
    }
 public double getX(){
     return x;
 }   
 public double getY(){
     return y;
 }
 public double[] getVector(){
     double[] a= {x, y};   
     return  a;
    
 } 
 public void setX(double a){
     x = a;
 }
 public void setY(double b){
     y = b;
 }
}
