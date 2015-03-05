
JSON POJO _(Plain Old Java Object)_ for a node


```java
package com.bio4j.dataviz.model;

public class Node {



	public String id;
	public String name;
	public String group;

	public Node() {
	}

	public Node(String id, String name, String group) {
		this.id = id;
		this.name = name;
		this.group = group;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}


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