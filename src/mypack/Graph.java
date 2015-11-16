/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mypack;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Nandu Vinodan
 */


class Node{
    Point p;
    String name;
    List<Integer> adj;
    int[] forward; 
}


public class Graph extends Applet implements MouseListener,MouseMotionListener,ItemListener,ActionListener{
    String path="";
    int[][] adj_dis;
    int[] nodes;
    int s_count ;
    int routing_toggle;
    int start,end;
    Node[] n;
    static int count ;
    static char letter ;
    boolean dragged;
    boolean send ;
    CheckboxGroup route = null;
    Button sendData;
    boolean transmission;
    boolean drawcube;
    boolean drawing;
    boolean f,set;
    int cube_count1 =5;
    int cube_count2 = 5;
     int[] X=  new int[1000];
        int[] Y = new int[1000];
        int a = 0,b=0;
        int st ;
    
    int pkt ;
  
     public void addEdge(int i ,int j){
         if(!(n[i].adj.contains(j)) )
             
             n[i].adj.add(j);
          if(!(n[j].adj.contains(i)) )
                n[j].adj.add(i);
           for(int k=0 ; k<count ; k++){
            for(int l=0 ; l<count ; l++){
                if(n[k].adj.contains(l))
                adj_dis[k][l] = distance(n[k].p,n[l].p);
                else
                   adj_dis[k][l]=0;
            }
        }
        repaint();
    }
    public Graph(){
        nodes = new int[2];
        nodes[0] = nodes[1] = -1;
        Checkbox  routing = new Checkbox("Start Routing", route, false);
        sendData = new Button("SEND DATA");
        add(routing);
        add(sendData);
        
        adj_dis = new int[1000][1000];
        letter = 'A';
        n = new Node[1000];
        for(int i=0 ; i<1000 ; i++){
            adj_dis[i] = new int[1000];
            n[i] = new Node();
            n[i].p = new Point();
            n[i].name="";
            n[i].adj = new ArrayList<Integer>();
            n[i].forward = new int[1000];
          
        } 
        addMouseListener(this);
        addMouseMotionListener(this);
        routing.addItemListener(this);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("hi");
        if(routing_toggle == 0){
        n[count].p = e.getPoint();
        n[count].name += letter;
        letter = (char) (letter+1);
        count++;
        
       
        }
        else {
            System.out.println("routing enabled"); 
            drawcube = true;
            nodes[s_count] = findNode(e.getPoint());
            s_count = (s_count+1)%2;
            
           if(s_count==0)
                sendData.addActionListener(this);
       
            
        }
        
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //System.out.println("pressed");
        
        start = findNode(e.getPoint());
       // System.out.println("start" + start);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       // System.out.println("released");
        
        end = findNode(e.getPoint());
        if(dragged){
           // System.out.println("addinf edjf");
            if(start!=end)
          addEdge(start,end);
     //   dragged = false;
        }
        //System.out.println("end" + end);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
      //  System.out.println("entered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //System.out.println("exited");
    }
    @Override
    public void paint(Graphics g){
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 800,800);
        g.setColor(Color.DARK_GRAY);
        g.fillRect(800, 0, 800, 800);
      //  System.o
        //ut.println("i paoint");
        g.setColor(Color.BLUE);
         g.setFont(new Font("Arial",Font.BOLD,40));
         g.drawString("LINK STATE ROUTING PROTOCOL",50,80); 
         //start=-1,end=-1;
         
         if(drawcube ){
             g.setColor(Color.PINK);
           //  g.fill3DRect(end, end, end, start, dragged);
             g.fill3DRect(n[nodes[0]].p.x+50,n[nodes[0]].p.y-5,100,25,true);
             int x= n[nodes[0]].p.x + 50;
             if(!drawing){
             for(int i=0  ; i<cube_count1 ; i++)
             {
                     
                    g.setColor(Color.blue);
                    g.fillRect(x, n[nodes[0]].p.y+2, 10, 10);
                    x+=20;
             }
             } 
            
            }
         if(routing_toggle==1){
             
             int x = 820;
             int y = 70;
             g.setColor(Color.black);
             g.drawLine(800, 0, 800, 700);
             
            
             g.setColor(Color.ORANGE);
             g.setFont(new Font("Arial",Font.BOLD,20));
             g.drawString("FORWARDING TABLE", 1000, 30);
             for(int i=0 ;i < count ; i++){
                 g.setColor(Color.WHITE);
                 String t="";
                 t += (char)(i+65);
                 g.drawLine(x, y, x+60, y);
                 g.drawString(t,x,y-10);
                 for(int j=0 ; j<count ; j++){
                 g.setFont(new Font("Arial",Font.BOLD,20));
                 String s="";
                 s += (char)(j+65);
                 g.drawLine(x,y,x,y+30);
                 g.setColor(Color.ORANGE);
                 g.drawString(s, x+2, y+27);
                 g.setColor(Color.WHITE);
                 g.drawLine(x+30, y, x+30, y+30);
                 s =""; 
                 int temp = n[i].forward[j];
                 if(temp==-2)
                     s+="-";
                 else if(temp==-1)
                     s += (char)(i+65);
                 else
                     s += (char)(temp+65);
                 
                 g.drawString(s, x+32, y+27);
                 g.drawLine(x+60, y, x+60, y+30);
                 g.drawLine(x,y+30, x+60, y+30);
                 y += 30;
                 if(y > 600)
                 {
                     y=70;
                     x=x+100;
                 }
                 }
                 y += 30;
                 if(i!=nodes[0])
                 drawCube(i,g);
             }
         }
         
         if(transmission==true){
      
             if(pkt < path.length()-1){
                 
                    drawing = true;
                    if(!f){
                     transfer(path.charAt(pkt)-'0',path.charAt(pkt+1)-'0',g);
                            f=true;
                             if(pkt==0){
                                 g.setColor(Color.BLUE);
                                 g.setFont(new Font("Comic Sans",Font.ITALIC,15));
                                 g.drawString("Forwarding to " + (char)(65 + path.charAt(pkt+1)-'0'), n[path.charAt(pkt)-'0'].p.x, n[path.charAt(pkt)-'0'].p.y+60);
                                 set = true;
//     JOptionPane.showMessageDialog(this, "FORWARDING TO "+ (char) path.charAt(pkt+1));
                             }
                    }
                     st++;
                    System.out.println(st + " " + a);
                    if(st < 5)
                   { 
                       int x= n[path.charAt(pkt)-'0'].p.x + 50;
                       System.out.println("in src printin" + (5-st));
                       for(int i=0  ; i< 5-st ; i++)
                        {
                    
                    g.setColor(Color.blue);
                //     System.out.println(x + " " + n[path.charAt(pkt)-'0'].p.y+2 );
                    g.fillRect(x, n[path.charAt(pkt)-'0'].p.y+2, 10, 10);
                    x+=20;
                        }
                       move(g);
             
                   }
                    else if(st >= a+6){
                        pkt++;
                        if(pkt == path.length()-1)
                        {
                              g.setColor(Color.BLUE);
                                 g.setFont(new Font("Comic Sans",Font.ITALIC,15));
                                 g.drawString("Reached", n[path.charAt(pkt)-'0'].p.x, n[path.charAt(pkt)-'0'].p.y+60);
                       //          drawCube(nodes[1], g);
                                  int x= n[nodes[1]].p.x + 50;
                 for(int i=0  ; i<cube_count2 ; i++)
             {
                     
                    g.setColor(Color.blue);
                    g.fillRect(x, n[nodes[1]].p.y+2, 10, 10);
                    x+=20;
             }
             
                                 //       JOptionPane.showMessageDialog(this, "REACHED");
                        }
                        else{
                           // JOptionPane.showMessageDialog(this, "FORWARDING TO "+ (char) (65 + path.charAt(pkt+1)));
                            g.setColor(Color.BLUE);
                                 g.setFont(new Font("Comic Sans",Font.ITALIC,15));
                                 g.drawString("Forwariding to " + (char)(65  + path.charAt(pkt+1)-'0'), n[path.charAt(pkt)-'0'].p.x, n[path.charAt(pkt)-'0'].p.y+60);
                     //       
                        }
                        set = true;
                      System.out.println("stopping");
             //         pkt++;
                      f=false;
                 
                 a=0;
                 b=0;
                 st=0;
                 }
                  else if(st > a){
                      System.out.println("in des" + (st-a));
                        int x= n[path.charAt(pkt+1)-'0'].p.x + 50;
                     for(int i=0  ; i< st-a; i++)
                    {
                     
                    g.setColor(Color.blue);
                    //    System.out.println(x + " " + n[path.charAt(pkt+1)-'0'].p.y+2 );
                    g.fillRect(x, n[path.charAt(pkt+1)-'0'].p.y+2, 10, 10);
                    x+=20;
                     }
                    // move(g);
                    }
                  else{
                      set = false;
                      move(g);
                  }
               
               
                 
             }
   
           
         }
        for(int i=0 ; i<count ; i++)
        {
            //System.out.println("hello");
            g.setFont(new Font("Times",Font.BOLD,24));
            
            g.setColor(Color.RED);
            g.fillOval(n[i].p.x ,n[i].p.y,50, 50);
            g.setColor(Color.BLACK);
            g.drawString(n[i].name, n[i].p.x+17, n[i].p.y+32);
            g.setColor(Color.BLACK);
            int c=0;
            for(Integer j : n[i].adj){
                String nam = "";
                nam += (char)(65+j);
                g.setFont(new Font("Times",Font.BOLD, 15));
                g.drawString(nam +" "+ String.valueOf(adj_dis[i][j]), n[i].p.x-30, n[i].p.y+c);
                boolean flag1 = false,flag2=false;
              
                    g.drawLine(n[i].p.x+30, n[i].p.y+30, n[j].p.x+30, n[j].p.y+30);
            
                c+=12;
            }
    
            }
          if(transmission && pkt < path.length()-1){
       try {
                if(set)
                    Thread.sleep(1000);
                else
                     Thread.sleep(200);
                 } catch (InterruptedException ex) {
                     Logger.getLogger(Graph.class.getName()).log(Level.SEVERE, null, ex);
                 }
              repaint();
          }
    }
    
    
    @Override
    public void mouseDragged(MouseEvent e) {
        dragged= true;
       // System.out.println("dragged");
    //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("moved"); //To change body of generated methods, choose Tools | Templates.
    }

    private int findNode(Point pt) {
        for(int i=0 ; i<count ; i++){
           // System.out.println("checking: " + i  +pt.x+ " "+ pt.y+ " "+ n[i].p.x + " " +n[i].p.y);
           if(pt.x >= n[i].p.x && pt.y >= n[i].p.y && pt.x <= n[i].p.x+50 && pt.y <= n[i].p.y+50)
               return i;
        }
        return 0;
        
    }

    private int distance(Point p1, Point p2) {
     return (int) (Math.sqrt(Math.pow(p1.x-p2.x, 2) +  Math.pow(p1.y-p2.y, 2))); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        routing_toggle ^= 1;
        if(routing_toggle==1){
            for(int i=0 ; i<count ; i++){
                System.out.println(i+ " :");
              new Dijikstra().getShortestPath(adj_dis,n[i].forward,i);
              for(int j=0 ; j<count ; j++)
                    System.out.println(j + " " +n[i].forward[j]);
            }
            send = true;
           
        }
        else{
            transmission = false;
        }
        
            repaint();
            
        //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
       // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        if(e.getActionCommand()=="SEND DATA"){
            drawcube = true ;
            path = (String.valueOf(nodes[0]));
            pkt=0;
            transmit(nodes[0],nodes[1]);     
        }
    }

    private void transmit(int src, int des) {
        System.out.println("gonna transmit from "+ src + " " + des);
        if(n[src].forward[des]==-2){
            JOptionPane.showMessageDialog(this, " NO ROUTE ");
            return ;
        }
        if(n[src].forward[des]==-1){
            System.out.println(path);
            transmission = true;
            repaint();
            return ;
        }
        else{
            
             path += String.valueOf(n[src].forward[des]);
            transmit(n[src].forward[des],des);
        }
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void transfer(int i1, int i2,Graphics g) {
       // g.fillOval(100, 100, 100, 100);
        System.out.println("transfering " + i1 + " " + i2);
       g.setColor(Color.magenta);
       int x1 = n[i1].p.x+30 ;
       int y1 = n[i1].p.y+30;
       int x2 = n[i2].p.x +30;
       int y2 = n[i2].p.y +30;
         double dx,dy,steps,xc,yc,x,y;
        dx = x2-x1;
        dy = y2-y1;
        if(Math.abs(dx) > Math.abs(dy))
               steps = Math.abs(dx) ;
       else
               steps = Math.abs(dy);
        
        xc = dx/steps;
        yc = dy/steps ;
       
        x=x1;
        y=y1;
        for(int k=1 ; k<=steps ; k++){
            x = x+xc ;
            y = y+yc ;
           // System.out.println(x + " " + y);
            
                g.setColor(Color.red);
                if((k%15)==0){
         //   g.fillOval((int)x-10, (int) y-10, 5, 5);
                X[a++] = (int)x-10;
                Y[b++] = (int)y-10;
                }
        }
       
       // pkt++;
       
     //   repaint();
    //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void drawCube(int num,Graphics g) {
          g.setColor(Color.PINK);
          
             g.fill3DRect(n[num].p.x+50,n[num].p.y-5,100,25,true);
             int x= n[num].p.x + 50;
   
    }

    private void move(Graphics g) {
        g.setColor(Color.BLACK);
        if(st <= 5){
            for(int i=1 ; i<=st ; i++)
                    g.fillRect(X[i],Y[i], 10, 10);
        }
       
    //To change body of generated methods, choose Tools | Templates.
        else{
            for(int i=st-4 ; i < a && i<=st ; i++){
             
                   
                    g.fillRect(X[i],Y[i], 10, 10);
        }
                }
       
    }
    

    
}
