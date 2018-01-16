# ant-colony-optimization
This repository implements the max-min ant optimization model proposed in
[this](http://www.sciencedirect.com/science/article/pii/S1568494612001901#sec0010) paper. The program is written in Java and can be used
to solve the traveling salesman problem for a general graph. The example program in this repository uses the graph
[Berlin52](https://github.com/pdrozdowski/TSPLib.Net/blob/master/TSPLIB95/tsp/berlin52.tsp) but it could easily be replaced with any graph
of EUC_2D type.  
As with all nature inspired optimization-algorithms, this is not guaranteed to find the optimal solution every time but rather to converge
to a "good enough" solution in a shorter time and to be robust to changes of the graph structure.
