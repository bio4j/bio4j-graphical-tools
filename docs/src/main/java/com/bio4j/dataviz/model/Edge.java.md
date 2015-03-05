
JSON POJO _(Plain Old Java Object)_ for an edge


```java
package com.bio4j.dataviz.model;

public class Edge {

	public String source;
	public String target;
	public String value;
	public String group;

	public Edge(){

	}

	public Edge(String target, String source, String value, String group) {
		this.target = target;
		this.source = source;
		this.value = value;
		this.group = group;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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