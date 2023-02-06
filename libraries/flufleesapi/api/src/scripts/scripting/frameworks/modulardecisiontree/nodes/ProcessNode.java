package scripts.scripting.frameworks.modulardecisiontree.nodes;

public abstract class ProcessNode implements INode {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public INode getValidNode() {
        return this;
    }

}
