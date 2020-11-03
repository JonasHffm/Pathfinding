# Pathfinding
# Author: Jonas Hoffmann

____________________________________________________________

Setting up grid:

Setting start node    -->  Shift + Leftclick on node
Setting end node      -->  Alt   + Leftclick on node
Setting barrier nide  -->  Ctrl  + Leftclick on node

Press start afterwards!

____________________________________________________________


            ALGORITHM

              f(x) = g(x) + h(x)

              g(x) -> Distance between current (x) node and start node
              h(x) -> Distance between current (x) node and end node

              Check which fCost is the smallest value and take it as new X node.
              
              
Checked nodes and nodes beside barriers are put in the Closed-Set list.
Nodes in the Open-Set are around the current X node.
