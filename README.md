# Pathfinding
# Author: Jonas Hoffmann

____________________________________________________________

Setting up grid:

Setting start node    -->  Shift + Leftclick on node<br/>
Setting end node      -->  Alt   + Leftclick on node<br/>
Setting barrier nide  -->  Ctrl  + Leftclick on node<br/>
<br/>
Press start afterwards!<br/>

____________________________________________________________


            ALGORITHM

              f(x) = g(x) + h(x)

              g(x) -> Distance between current (x) node and start node
              h(x) -> Distance between current (x) node and end node

              Check which fCost is the smallest value and take it as new X node.
              
              
Checked nodes and nodes beside barriers are put in the Closed-Set list.<br/>
Nodes in the Open-Set are around the current X node.
