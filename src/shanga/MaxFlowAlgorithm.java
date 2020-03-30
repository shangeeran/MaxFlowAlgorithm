package shanga;

import java.util.concurrent.TimeUnit;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MaxFlowAlgorithm {
    public static void main(String[] args) throws InterruptedException{

        Scanner sc = new Scanner(System.in);
        int option;

        do {
            System.out.println("===============================================");
            System.out.println("Welcome to Max Flow Algorithms");
            System.out.println("Please select the option");
            System.out.println("1 : Vertices Six Format");
            System.out.println("2 : Vertices Twelve Format");
            System.out.println("3 : Vertices Twenty Four Format");
            System.out.println("4 : Vertices Forty Eight Format");
            System.out.println("5 : Exit");


            System.out.print("\t"+"Your Option is : ");
            // Stop strings when we enter Option
            while (!sc.hasNextInt()) {
                System.out.print("Invalid Entry!!, Please Select the Option : ");
                sc.next();
            }
            // Get the option from user assign to option variable
            option = sc.nextInt();

            switch (option) {
                case 1:
                    six();
                    break;
                case 2:
                    twelve();
                    break;
                case 3:
                    twentyFour();
                    break;
                case 4:
                    fortyEight();
                    break;
                case 5:
                    exitProgramme();
                    break;
                default:
                    System.out.println("Invalid Option!!! Reenter...");
                    break;
            }

        } while(option!=5); // The option until the not equal 5 programme is run
    }

    static class Graph {
        int vertices;
        int graph[][];

        public Graph(int vertex, int[][] graph) {
            this.vertices = vertex;
            this.graph = graph;
        }

        public int findMaxFlow(int source, int sink) {
            //residual graph
            int[][] residualGraph = new int[vertices][vertices];

            //initialize residual graph same as original graph
            for (int i = 0; i <vertices ; i++) {
                for (int j = 0; j <vertices ; j++) {
                    residualGraph[i][j] = graph[i][j];
                }
            }

            //initialize parent [] to store the path Source to destination
            int [] parent = new int[vertices];

            int max_flow = 0; //initialize the max flow

            while(isPathExist_BFS(residualGraph, source, sink, parent)){
                //if here means still path exist from source to destination

                //parent [] will have the path from source to destination
                //find the capacity which can be passed though the path (in parent[])

                int flow_capacity = Integer.MAX_VALUE;

                int t = sink;
                while(t!=source){
                    int s = parent[t];
                    flow_capacity = Math.min(flow_capacity, residualGraph[s][t]);
                    t = s;
                }

                //update the residual graph
                //reduce the capacity on fwd edge by flow_capacity
                //add the capacity on back edge by flow_capacity
                t = sink;
                while(t!=source){
                    int s = parent[t];
                    residualGraph[s][t]-=flow_capacity;
                    residualGraph[t][s]+=flow_capacity;
                    t = s;
                }

                //add flow_capacity to max value
                max_flow+=flow_capacity;
            }
            return max_flow;
        }

        public boolean isPathExist_BFS(int [][] residualGraph, int src, int dest, int [] parent){
            boolean pathFound = false;

            //create visited array [] to
            //keep track of visited vertices
            boolean [] visited = new boolean[vertices];

            //Create a queue for BFS
            Queue<Integer> queue = new LinkedList<>();

            //insert the source vertex, mark it visited
            queue.add(src);
            parent[src] = -1;
            visited[src] = true;

            while(queue.isEmpty()==false){
                int u = queue.poll();

                //visit all the adjacent vertices
                for (int v = 0; v <vertices ; v++) {
                    //if vertex is not already visited and u-v edge weight >0
                    if(visited[v]==false && residualGraph[u][v]>0) {
                        queue.add(v);
                        parent[v] = u;
                        visited[v] = true;
                    }
                }
            }
            //check if dest is reached during BFS
            pathFound = visited[dest];
            return pathFound;
        }
    }


    private static void six() throws InterruptedException {
        long startTime = System.nanoTime();   // to measure the start time
        Scanner scInt = new Scanner(System.in);
        int vertices = 6;
        int[][] graph = {
                {0, 16, 13, 0, 0, 0},
                {0, 0, 10, 10, 0, 0},
                {0, 4, 0, 0, 14, 0},
                {0, 0, 9, 0, 0, 20},
                {0, 0, 0, 7, 0, 4},
                {0, 0, 0, 0, 0, 0}
        };
        System.out.println("Please select the mode");
        System.out.println("1 : Find the Max Flow");
        System.out.println("2 : Update the value");
        System.out.println("3 : Remove the value");
        System.out.println("Your Selection is : ");
        int select = scInt.nextInt();

        if (select==1){


            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 5;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();


        } else if (select==2){
            System.out.println("Please enter the start vertices ");
            int changeValueStart = scInt.nextInt();
            System.out.println("Please enter the end vertices ");
            int changeValueEnd = scInt.nextInt();
            System.out.println("Please enter the capacity of changing ");
            int changeValue = scInt.nextInt();

            graph[changeValueStart][changeValueEnd] = changeValue;
            System.out.println("Value has been changed");

            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 5;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();

        } else {
            System.out.println("Please enter the start vertices ");
            int deleteValueStart = scInt.nextInt();
            System.out.println("Please enter the end vertices ");
            int deleteValueEnd = scInt.nextInt();

            graph[deleteValueStart][deleteValueEnd] = 0;
            System.out.println("Value has been removed");

            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 5;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();
        }


    }








    private static void twelve() throws InterruptedException {
        long startTime = System.nanoTime();   // to measure the start time
        Scanner scInt = new Scanner(System.in);
        int vertices = 12;
        int[][] graph = {
                {0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0},
                {0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 15},
                {0, 5, 4 , 10, 6, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 5, 8, 1, 0, 12, 0, 0, 0, 0},
                {0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 12, 11},
                {0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        System.out.println("Please select the mode");
        System.out.println("1 : Find the Max Flow");
        System.out.println("2 : update the value");
        System.out.println("3 : Remove the value");
        System.out.println("Your Selection is : ");
        int select = scInt.nextInt();

        if (select==1){
            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 11;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();


        } else if (select==2){
            System.out.println("Please enter the start vertices ");
            int changeValueStart = scInt.nextInt();
            System.out.println("Please enter the end vertices ");
            int changeValueEnd = scInt.nextInt();
            System.out.println("Please enter the value of changing ");
            int changeValue = scInt.nextInt();

            graph[changeValueStart][changeValueEnd] = changeValue;
            System.out.println("Value has been changed");

            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 11;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();

        } else {
            System.out.println("Please enter the start vertices ");
            int deleteValueStart = scInt.nextInt();
            System.out.println("Please enter the end vertices ");
            int deleteValueEnd = scInt.nextInt();

            graph[deleteValueStart][deleteValueEnd] = 0;
            System.out.println("Value has been removed");

            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 11;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();
        }
    }







    private static void twentyFour() throws InterruptedException {

        long startTime = System.nanoTime();   // to measure the start time
        Scanner scInt = new Scanner(System.in);
        int vertices = 24;
        int[][] graph = {
                {0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0},
                {0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 15, 0, 0, 9, 0, 0, 18, 0, 0, 12, 10, 0, 0},
                {0, 5, 4, 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 4, 0, 0, 11, 0, 10, 15},
                {0, 0, 0, 5, 8, 1, 0, 12, 0, 0, 0, 0, 0, 5, 4, 10, 6, 0, 0, 0, 0, 0, 0, 0},
                {0, 5, 0, 7, 0, 8, 12, 0, 0, 0, 0, 0, 0, 0, 0, 5, 8, 1, 9, 12, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 12, 15, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 12, 11},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0},
                {0, 16, 13, 0, 0, 0, 0, 11, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 9, 0, 14, 0},
                {0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 14, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 5, 4 , 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 2, 10, 15},
                {0, 0, 0, 5, 8, 1, 9, 12, 0, 0, 0, 0, 0, 5, 4 , 10, 6, 0, 0, 0, 0, 0, 0, 0},
                {0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0, 0, 0, 0, 5, 8, 1, 9, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 12, 11, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 12, 11},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0}
        };
        System.out.println("Please select the mode");
        System.out.println("1 : Find the Max Flow");
        System.out.println("2 : update the value");
        System.out.println("3 : Remove the value");
        System.out.println("Your Selection is : ");
        int select = scInt.nextInt();

        if (select==1){
            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 23;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();


        } else if (select==2){
            System.out.println("Please enter the start vertices ");
            int changeValueStart = scInt.nextInt();
            System.out.println("Please enter the end vertices ");
            int changeValueEnd = scInt.nextInt();
            System.out.println("Please enter the value of changing ");
            int changeValue = scInt.nextInt();

            graph[changeValueStart][changeValueEnd] = changeValue;
            System.out.println("Value has been changed");

            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 23;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();

        } else {
            System.out.println("Please enter the start vertices ");
            int deleteValueStart = scInt.nextInt();
            System.out.println("Please enter the end vertices ");
            int deleteValueEnd = scInt.nextInt();

            graph[deleteValueStart][deleteValueEnd] = 0;
            System.out.println("Value has been removed");

            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 23;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();
        }

    }








    private static void fortyEight() throws InterruptedException {
        long startTime = System.nanoTime();   // to measure the start time
        Scanner scInt = new Scanner(System.in);
        int vertices = 48;
        int[][] graph = {
                {0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 8, 0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 15, 0, 0, 9, 0, 0, 18, 0, 0, 12, 10, 0, 0, 0, 5, 4, 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 4, 0, 0, 11, 0, 10, 15},
                {0, 0, 0, 5, 8, 1, 0, 12, 0, 0, 0, 0, 0, 5, 4, 10, 6, 0, 0, 0, 0, 2, 0, 0, 0, 5, 0, 7, 0, 8, 12, 5, 0, 0, 0, 0, 0, 0, 0, 5, 8, 1, 9, 12, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 12, 15, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 3, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 12, 11},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0},
                {0, 16, 13, 0, 0, 0, 0, 11, 0, 0, 15, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 8, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 5, 14, 0, 0, 0, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 14, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 5, 4 , 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 2, 0, 0, 0, 2, 10, 15},
                {0, 0, 0, 5, 8, 1, 9, 12, 0, 0, 0, 0, 0, 5, 4 , 10, 6, 0, 0, 0, 0, 0, 5, 0, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0, 0, 0, 0, 5, 8, 1, 9, 0, 5, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 12, 11, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 8, 0, 0, 0, 5, 0, 12, 11},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0},

                {0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 0, 9, 0, 0, 20, 5, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 15, 0, 0, 0, 0, 0, 18, 0, 0, 12, 10, 0, 0, 0, 5, 4, 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 4, 0, 0, 11, 0, 10, 15},
                {0, 0, 0, 5, 8, 1, 0, 12, 0, 0, 0, 0, 0, 5, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 7, 0, 8, 12, 0, 0, 0, 0, 0, 0, 0, 0, 5, 8, 1, 9, 12, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 12, 15, 0, 5, 0, 0, 0, 15, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 8, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 12, 11},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0, 0, 0, 0, 9, 7, 0, 4, 0, 0, 0, 0, 0, 9, 0, 0, 0, 9, 0, 5, 0, 0, 0, 0, 0, 0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0},
                {0, 16, 13, 0, 0, 0, 0, 11, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 9, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 14, 0, 0, 9, 0, 0, 20, 0, 0, 0, 10, 0, 0, 0, 5, 4 , 10, 6, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 2, 10, 15},
                {0, 0, 0, 5, 8, 1, 9, 12, 0, 0, 0, 0, 0, 5, 4 , 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0, 4, 0, 0, 5, 8, 1, 9, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 12, 11, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0, 0, 5, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 5, 0, 12, 11},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0},

                {0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0, 0, 0, 0, 0, 9, 0, 6, 0, 0, 9, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 15, 0, 0, 9, 0, 0, 18, 0, 0, 12, 10, 0, 0, 0, 5, 0, 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 4, 4, 0, 11, 0, 10, 15},
                {0, 0, 0, 5, 8, 1, 0, 12, 0, 0, 0, 0, 0, 5, 4, 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 8, 12, 0, 0, 0, 0, 5, 0, 0, 0, 5, 8, 1, 9, 12, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 12, 15, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 12, 11},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 5, 0, 0, 0, 0, 0, 9, 0, 0, 0, 4, 0, 0, 0, 0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0},
                {0, 16, 13, 0, 0, 0, 0, 11, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 14, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 5, 4 , 10, 6, 0, 0, 0, 0, 4, 0, 0, 0, 4, 0, 7, 0, 0, 0, 0, 0, 2, 10, 15},
                {0, 0, 0, 5, 8, 1, 9, 12, 0, 0, 0, 0, 0, 5, 4 , 10, 6, 0, 0, 0, 0, 6, 0, 0, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0, 0, 0, 0, 5, 8, 1, 9, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 12, 11, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0, 5, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 12, 11},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 5, 0, 0, 0, 0, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0},

                {0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 16, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 15, 0, 0, 9, 0, 0, 18, 0, 0, 12, 10, 0, 0, 0, 5, 4, 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 4, 0, 0, 11, 0, 10, 15},
                {0, 0, 0, 5, 8, 1, 0, 12, 0, 0, 0, 0, 0, 5, 4, 10, 6, 0, 0, 7, 0, 0, 0, 0, 0, 5, 0, 7, 0, 8, 12, 0, 0, 5, 0, 0, 0, 4, 0, 0, 8, 1, 9, 12, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 12, 15, 0, 5, 0, 7, 0, 15, 12, 0, 0, 5, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 12, 11},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 0, 0, 0, 0, 7, 0, 7, 0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0},
                {0, 16, 13, 0, 0, 0, 0, 11, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 0, 10, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 13, 0, 0, 0, 0, 3, 0, 0, 15, 0},
                {0, 4, 0, 0, 14, 0, 0, 8, 4, 0, 0, 0, 0, 0, 0, 12, 0, 0, 0, 0, 9, 0, 14, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0},
                {0, 0, 0, 7, 0, 4, 0, 0, 0, 0, 10, 14, 0, 0, 9, 0, 0, 20, 0, 0, 12, 10, 0, 0, 0, 5, 4 , 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 7, 0, 0, 0, 0, 0, 2, 10, 15},
                {0, 0, 0, 5, 8, 1, 9, 12, 0, 0, 0, 0, 0, 5, 4 , 10, 6, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0, 0, 0, 0, 5, 8, 1, 9, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 5, 14, 12, 11, 0, 5, 0, 7, 0, 15, 12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 11},
                {0, 5, 0, 7, 0, 0, 10, 15, 11, 0, 0, 0, 0, 0, 0, 9, 7, 2, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 6, 0, 0, 0, 5, 0, 7, 7, 0, 10, 15, 11, 0, 0, 0}
        };
        System.out.println("Please select the mode");
        System.out.println("1 : Find the Max Flow");
        System.out.println("2 : update the value");
        System.out.println("3 : Remove the value");
        System.out.println("Your Selection is : ");
        int select = scInt.nextInt();

        if (select==1){
            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 47;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();


        } else if (select==2){
            System.out.println("Please enter the start vertices ");
            int changeValueStart = scInt.nextInt();
            System.out.println("Please enter the end vertices ");
            int changeValueEnd = scInt.nextInt();
            System.out.println("Please enter the value of changing ");
            int changeValue = scInt.nextInt();

            graph[changeValueStart][changeValueEnd] = changeValue;
            System.out.println("Value has been changed");

            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 47;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();

        } else {
            System.out.println("Please enter the start vertices ");
            int deleteValueStart = scInt.nextInt();
            System.out.println("Please enter the end vertices ");
            int deleteValueEnd = scInt.nextInt();

            graph[deleteValueStart][deleteValueEnd] = 0;
            System.out.println("Value has been removed");

            Graph g = new Graph(vertices, graph);
            int source = 0;
            int destination = 47;
            int max_flow = g.findMaxFlow(source,destination);
            System.out.println("Maximum flow from source: " + source + " to destination: " + destination);
            System.out.println("The maximum possible flow is " + max_flow);

            // sleep for 5 sec
            TimeUnit.SECONDS.sleep(5);
            long endTime = System.nanoTime();   // to measure the end time
            long timeElapsed = endTime - startTime;
            System.out.println("Execution time in nano seconds : " + timeElapsed );
            System.out.println("Execution time in milliseconds : " + timeElapsed/1000000 );
            System.out.println();
        }
    }

    private static void exitProgramme() {
        System.out.println("Thank you!");
        System.exit(0); //Programme exit
    }
}
