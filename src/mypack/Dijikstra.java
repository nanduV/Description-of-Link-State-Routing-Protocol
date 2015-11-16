/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mypack;

import java.util.List;
import static mypack.Graph.count;

/**
 *
 * @author Nandu Vinodan
 */
public class Dijikstra {
    String path="";
    int V = count;
    int minDistance(int dist[], boolean sptSet[])
{

   int min = Integer.MAX_VALUE, min_index=-1;
 
   for (int v = 0; v < V; v++){
     if (sptSet[v] == false && dist[v] <= min){
         min = dist[v];
         min_index = v;
     }
   }
 
   return min_index;
}
void printPath(int[] prev,int src,int des){
    if(prev[des]==-1)
        return ;
    printPath(prev,src,prev[des]);
    path += (String.valueOf(prev[des]));
}

    /**
     *
     * @param adj
     * @param forward
     * @param src
     * @param adj
     */
    public void getShortestPath(int[][] adj,int[] forward,int src){
       // System.out.println("src : " + src);
     int[] dist = new int[V];    
     int[] prev = new int[V];
     boolean[] sptSet = new boolean[V]; 
   
     for (int i = 0; i < V; i++){
        dist[i] = Integer.MAX_VALUE;
        sptSet[i] = false;
        prev[i] = -1;
     }

     dist[src] = 0;
     
 
     for (int count = 0; count < V-1; count++)
     {
       int u = minDistance(dist, sptSet);
 
       sptSet[u] = true;
 
       for (int v = 0; v < V; v++)
		{
         if (!sptSet[v] && adj[u][v]!=0 && dist[u] != Integer.MAX_VALUE && dist[u]+adj[u][v] < dist[v]){
              
             
            dist[v] = dist[u] + adj[u][v];
            prev[v] = u;
            }
	}	
     }
     
     for(int i=0 ; i<count ; i++){
        
         path = "";
         printPath(prev,src,i);
        // System.out.println(i + " " + dist[i] + " " + path);
         if(src==i)
             forward[i]=-1;
        else if(path.length()==0)
             forward[i] = -2;
         else if(path.length()==1)
             forward[i] = i;
         else {
             forward[i] = (path.charAt(1)-'0') ;
         }
         
         
     }
    
    }

}


