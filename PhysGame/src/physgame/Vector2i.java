/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package physgame;

/**
 *
 * @author Games
 */
public class Vector2i {
    
    private int x, y;
    Vector2i(){
        
        x = y =0;
        
    }
    
    Vector2i(int a, int b){
        x =a;
        y=b;
    }
 public int getX(){
     return x;
 }   
 public int getY(){
     return y;
 }
 public int[] getVector(){
     int[] a= {x, y};   
     return  a;
    
 } 
 public void setX(int a){
     x = a;
 }
 public void setY(int b){
     y = b;
 }
}
