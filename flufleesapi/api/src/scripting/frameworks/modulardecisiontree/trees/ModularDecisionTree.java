package scripting.frameworks.modulardecisiontree.trees;

import scripting.frameworks.modulardecisiontree.nodes.ModularDecisionNode;

/**
 * @author Fluffee
 */
public class ModularDecisionTree extends DecisionTree {
    ModularDecisionNode root;

    public ModularDecisionTree(ModularDecisionNode root) {
        this.root = root;
    }

    public ModularDecisionNode getRoot() {
        return root;
    }

    public void setRoot(ModularDecisionNode root) {
        this.root = root;
    }

    public boolean shouldRemoveRoot() {
        return root.shouldRemove();
    }
}
