package grafo;


import grafo.bancoMYSQL.buscarPop;
import grafo.bancoMYSQL.Pop;
import static grafo.Dijkstra.Pop1;
import static grafo.Dijkstra.Pop2;
import java.io.*;
import java.util.*;
 
public class Dijkstra {
    public static ArrayList<Pop> vertices = new ArrayList();
    public static ArrayList<Metrica> aresta = new ArrayList();
    public static buscarPop p =new buscarPop();
    
    private static int i= 0;
    private static int j= -1;
    public static String caminho="";
    public static String removeVertice="";
    public static String removeArestaDe="";
    public static String removeArestaPara="";
    public static int metrica;
    public static int valor;

    
 public static Graph.Edge[] criaCaminho(){
       // System.out.print(vertices.size());
        Graph.Edge[] t =new Graph.Edge[vertices.size()];

     while(i < vertices.size()){
        t[i]= new Graph.Edge(Pop1(),Pop2(),Metrica());
        i++;
     }
     return t;
 }

  public static int Metrica(){
          System.out.print("aq "+metrica+"\n");
        switch (metrica) {
            case 1:
                j++;
                System.out.print(aresta.get(j).getA()+"\n");
                return aresta.get(j).getA();
                
            case 2:
                j++;
                System.out.print(aresta.get(j).getB()+"\n");
                return aresta.get(j).getB();
            default:
                j++;
                System.out.print(aresta.get(j).getC()+"\n");
                return aresta.get(j).getC();
        }
          
}
      
     
   public static String Pop1(){
     
      if(!vertices.get(i).getPop1().equals(removeVertice) && !vertices.get(i).getPop2().equals(removeVertice)){
           if((vertices.get(i).getPop1().equals(removeArestaDe) && vertices.get(i).getPop2().equals(removeArestaPara))
                   || (vertices.get(i).getPop1().equals(removeArestaPara) && vertices.get(i).getPop2().equals(removeArestaDe))){
              
                    return null;
                 //   System.out.print(vertices.get(i).getPop1()+"\n");
               
             
           }else{  
               System.out.println(vertices.get(i).getPop1()+" "+vertices.get(i).getPop2());
                  return vertices.get(i).getPop1();
                         }
           
       }
       return null;
   }
   public static String Pop2(){
       
      if(!vertices.get(i).getPop1().equals(removeVertice) && !vertices.get(i).getPop2().equals(removeVertice)){
          if((vertices.get(i).getPop1().equals(removeArestaDe) && vertices.get(i).getPop2().equals(removeArestaPara))
                  || (vertices.get(i).getPop1().equals(removeArestaPara) && vertices.get(i).getPop2().equals(removeArestaDe))){
                        
                        return null;
                 //    System.out.print(vertices.get(i).getPop2()+"\n");
              
          }else{     return vertices.get(i).getPop2();}
            
     }
     return null;
   }
   
   public static String resposta(){
         String resposta="";
         if(metrica==1){
            resposta="<a style='color:#DF0101'>Caminho Percorrido: </a><br><br> "+caminho+"<br><br><a style='color:#DF0101'>Hops: </a>"+valor+"</html>";
         }else if(metrica == 2){
             resposta="<a style='color:#DF0101'>Caminho Percorrido:</a><br><br> "+caminho+"<br><br><a style='color:#DF0101'>Distância Geográfica: </a>"+valor+"</html>";
         }else {
             resposta="<a style='color:#DF0101'>Caminho Percorrido:</a><br><br> "+caminho+"<br><br> <a style='color:#DF0101'>Custo: </a>"+valor+"</html>";
         }
         
         return resposta;
     }

  // private static final String START = "POA";
  // private static final String END = "NTL";
 
   public static String PROCURAR(String START,String END,int metricas) {
      
     vertices = p.buscarTodosUsuarios();
     aresta = p.Metrica();
     System.out.print("Outro: "+metricas);
     metrica=metricas;
     
     
     Graph g=new Graph(criaCaminho()) ;
    
      caminho="";
      g.dijkstra(START);
      g.printPath(END);
      
      i=0;
      j=-1;
      caminho=resposta(); 
     return caminho;
      
   }
   public static String removeVertice(String Remove,String START,String END,int metricas ) {
    vertices = p.buscarTodosUsuarios();
    aresta = p.Metrica();
    metrica=metricas;
    
     caminho="";
     removeVertice=Remove;
     Graph g=new Graph(criaCaminho()) ;
     g.dijkstra(START);
     g.printPath(END);
     i=0;
     j=-1;
     removeVertice="";
     return resposta();
   }
   public static String removeAresta(String removeDe,String removePara,String START,String END,int metricas) {
    vertices = p.buscarTodosUsuarios();
    aresta = p.Metrica();
    metrica=metricas;
    
    
     caminho="";
     
     removeArestaDe=removeDe;
     removeArestaPara=removePara;
     
     Graph g=new Graph(criaCaminho()) ;
     g.dijkstra(START);
     g.printPath(END);
     i=0;
     j=-1;
    // removeArestaDe="";
    // removeArestaPara="";
     
     removeArestaDe="";
     removeArestaPara="";
     
     return resposta();
   }
}
 
class Graph {
   private final Map<String, Vertex> graph; // mapping of vertex names to Vertex objects, built from a set of Edges
 
   /** One edge of the graph (only used by Graph constructor) */
   public static class Edge {
     
      
      public final String v1, v2;
      public final int dist;
      public Edge(String v1, String v2, int dist) {
         
         this.v1 = v1;
         this.v2 = v2;
         this.dist = dist;
      }
   }
 
   /** One vertex of the graph, complete with mappings to neighbouring vertices */
   public static class Vertex implements Comparable<Vertex> {
      public final String name;
      public int dist = Integer.MAX_VALUE; // MAX_VALUE assumed to be infinity
      public Vertex previous = null;
      public final Map<Vertex, Integer> neighbours = new HashMap<>();
      
    
      public Vertex(String name) {
         this.name = name;
      }
 
      private void printPath() {
         if (this == this.previous) {
            
            Dijkstra.caminho=Dijkstra.caminho+" "+this.name;
           System.out.printf("%s", this.name);
         } else if (this.previous == null) {
            Dijkstra.caminho=Dijkstra.caminho+"'%s(Vertice não tem Caminho: )'"+this.name;
            System.out.printf("%s(unreached)", this.name);
         } else {
            this.previous.printPath();
            Dijkstra.caminho=Dijkstra.caminho+" -> "+this.name;
            Dijkstra.valor=this.dist;
            System.out.printf(" -> %s(%d)", this.name, this.dist);
         }
      }
 
      public int compareTo(Vertex other) {
         return Integer.compare(dist, other.dist);
      }
   }
 
   /** Builds a graph from a set of edges */
   public Graph(Edge[] edges) {
      graph = new HashMap<>(edges.length);
 
      //one pass to find all vertices
      for (Edge e : edges) {
         if (!graph.containsKey(e.v1)) graph.put(e.v1, new Vertex(e.v1));
         if (!graph.containsKey(e.v2)) graph.put(e.v2, new Vertex(e.v2));
      }
 
      //another pass to set neighbouring vertices
      for (Edge e : edges) {
         graph.get(e.v1).neighbours.put(graph.get(e.v2), e.dist);
         //graph.get(e.v2).neighbours.put(graph.get(e.v1), e.dist); // also do this for an undirected graph
      }
   }
 
   /** Runs dijkstra using a specified source vertex */ 
   public void dijkstra(String startName) {
      if (!graph.containsKey(startName)) {
         System.err.printf("Graph doesn't contain start vertex \"%s\"\n", startName);
         return;
      }
      final Vertex source = graph.get(startName);
      NavigableSet<Vertex> q = new TreeSet<>();
 
      // set-up vertices
      for (Vertex v : graph.values()) {
     
         v.previous = v == source ? source : null;
         v.dist = v == source ? 0 : Integer.MAX_VALUE;
         q.add(v);
        
      }
 
      dijkstra(q);
   }
 
   /** Implementation of dijkstra's algorithm using a binary heap. */
   private void dijkstra(final NavigableSet<Vertex> q) {      
      Vertex u, v;
      while (!q.isEmpty()) {
 
         u = q.pollFirst(); // vertex with shortest distance (first iteration will return source)
         if (u.dist == Integer.MAX_VALUE) break; // we can ignore u (and any other remaining vertices) since they are unreachable
 
         //look at distances to each neighbour
         for (Map.Entry<Vertex, Integer> a : u.neighbours.entrySet()) {
            v = a.getKey(); //the neighbour in this iteration
 
            final int alternateDist = u.dist + a.getValue();
            if (alternateDist < v.dist) { // shorter path to neighbour found
               q.remove(v);
               v.dist = alternateDist;
               v.previous = u;
               q.add(v);
            } 
         }
      }
   }
 
   /** Prints a path from the source to the specified vertex */
   public void printPath(String endName) {
      if (!graph.containsKey(endName)) {
         System.err.printf("não contem o vertice \"%s\"\n", endName);
         return;
      }
 
      graph.get(endName).printPath();
      System.out.println();
   }
 
}