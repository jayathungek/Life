Conway's game of Life is a cellular automaton devised by mathematician John Conway in 1970. It consists of a grid of cells, where each
cell can have one of two states: alive or dead.

Once the grid has been populated by an initial number of live cells, the following rules come into effect:
  1. Any live cell with less than 2 neigbors* dies
  2. Any live cell with 2 or 3 neighbors survives
  3. Any live cell with more than 3 neigbors dies
  4. Any dead cell with exactly 3 neigbors comes back to life

These four simple rules can result in surprisingly complex patterns evolving from an initial starting point.
The initial configuration of the grid is called the **seed** and the same starting seed always produces the same patterns and results.

** neighbors: In this context, _neighbors_ refers to cells in the Moore neighborhood of the target cell (shown in black below)
![moore](http://s15.postimg.org/br6z1po6z/moore_n.png "Moore neigborhood of a cell")

Instructions:
-------------
 1. Clone the Life repo
 2. Compile src/Life.java
 3. Two options for running from command line:

    * Using preset start seed: \>java Life [grid size] [start seed] [refresh speed]  
       Where [start seed] is one of:  
         i)   blinker  
         ii)  glider  
         iii) pento  
         iv)  acorn  
         v)   lwss  
      
    * Using a custom GRID file:  \>java Life [filename]  
       These can be found in the grids/ directory
       
Examples:
---------
 ![start](http://s9.postimg.org/7bqvqm38v/acorn_start.png "Initial start seed 'acorn'") 
 ![later](http://s9.postimg.org/ubxj2y12n/acorn_later.png "A few iterations later...")   
  Left: Start seed 'acorn'. Right: The result of running the program for a few generations  
  
![ark](http://s22.postimg.org/7zzg66zup/ark.png "ark.grid")  
 A few generations into ark.grid  
