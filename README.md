# A*Pathfinding
## Author: Jonas Hoffmann

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
              
                        
              
              
> Checked nodes and nodes beside barriers are put in the Closed-Set list.<br/>

```javascript
Pathfinding.initializer.getData().getClosedSet().add(x);

for(Node sBCheckNode : getSurroundingNodes(sNode)) {
    if(sBCheckNode.isBarrier()) {
        Pathfinding.initializer.getData().getClosedSet().add(sNode);
    }
}
```

> Nodes in the Open-Set are around the current X node.

```javascript
public ArrayList<Node> getSurroundingNodes(Node xNode) {
ArrayList<Node> nodes = new ArrayList<>();
for(int x = -1; x <= 1; x++) {
for(int y = -1; y <= 1; y++) {
    Node c_Node = getNodeFromXY(xNode.getPosX()+x, xNode.getPosY()+y);
    if(c_Node != null) {
        if (!xNode.equals(c_Node)) {
            if (!Pathfinding.initializer.getData().getClosedSet().contains(c_Node)) {
                if (!c_Node.isBarrier()) {
                    nodes.add(c_Node);
                    c_Node.getPanel().setBackground(Color.BLUE);
                }
            }
        }
    }
}
}
return nodes;
}
```



Most [important part](https://github.com/JonasHffm/Pathfinding/blob/master/src/de/jonas/pathfinding/func/Function.java) of the project.





