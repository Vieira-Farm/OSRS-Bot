package scripting.frameworks.modulardecisiontree.trees;

import scripting.frameworks.modulardecisiontree.nodes.ConstructorDecisionNode;
import scripting.frameworks.modulardecisiontree.nodes.INode;

public class DecisionTree {
    ConstructorDecisionNode root;

    public DecisionTree(ConstructorDecisionNode root) {
        this.root = root;
    }

    protected DecisionTree() {
    }

    public ConstructorDecisionNode getRoot() {
        return root;
    }

    public void setRoot(ConstructorDecisionNode root) {
        this.root = root;
    }

    public INode getValidNode() {
        return root.getValidNode();
    }

}
