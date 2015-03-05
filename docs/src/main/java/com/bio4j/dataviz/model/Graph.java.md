
JSON POJO _(Plain Old Java Object)_ representing a graph.


```java
package com.bio4j.dataviz.model;

import java.util.List;

public class Graph {

	public Graph(){

	}

	public Graph(List<Node> nodes, List<Edge> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}

	public List<Node> nodes;
	public List<Edge> edges;
}

```


------

### Index

+ src
  + main
    + java
      + com
        + bio4j
          + [bio4j-graphical-tools.java][main\java\com\bio4j\bio4j-graphical-tools.java]
          + dataviz
            + [GenerateDataViz.java][main\java\com\bio4j\dataviz\GenerateDataViz.java]
            + [GetJsonWithProteinsSharingGOandTaxonomy.java][main\java\com\bio4j\dataviz\GetJsonWithProteinsSharingGOandTaxonomy.java]
            + model
              + [Edge.java][main\java\com\bio4j\dataviz\model\Edge.java]
              + [GOTerm.java][main\java\com\bio4j\dataviz\model\GOTerm.java]
              + [Graph.java][main\java\com\bio4j\dataviz\model\Graph.java]
              + [NCBITaxon.java][main\java\com\bio4j\dataviz\model\NCBITaxon.java]
              + [Node.java][main\java\com\bio4j\dataviz\model\Node.java]
              + [Protein.java][main\java\com\bio4j\dataviz\model\Protein.java]
  + test
    + java
      + com
        + bio4j
          + [bio4j-graphical-tools.java][test\java\com\bio4j\bio4j-graphical-tools.java]

[main\java\com\bio4j\bio4j-graphical-tools.java]: ..\..\bio4j-graphical-tools.java.md
[main\java\com\bio4j\dataviz\GenerateDataViz.java]: ..\GenerateDataViz.java.md
[main\java\com\bio4j\dataviz\GetJsonWithProteinsSharingGOandTaxonomy.java]: ..\GetJsonWithProteinsSharingGOandTaxonomy.java.md
[main\java\com\bio4j\dataviz\model\Edge.java]: Edge.java.md
[main\java\com\bio4j\dataviz\model\GOTerm.java]: GOTerm.java.md
[main\java\com\bio4j\dataviz\model\Graph.java]: Graph.java.md
[main\java\com\bio4j\dataviz\model\NCBITaxon.java]: NCBITaxon.java.md
[main\java\com\bio4j\dataviz\model\Node.java]: Node.java.md
[main\java\com\bio4j\dataviz\model\Protein.java]: Protein.java.md
[test\java\com\bio4j\bio4j-graphical-tools.java]: ..\..\..\..\..\..\test\java\com\bio4j\bio4j-graphical-tools.java.md