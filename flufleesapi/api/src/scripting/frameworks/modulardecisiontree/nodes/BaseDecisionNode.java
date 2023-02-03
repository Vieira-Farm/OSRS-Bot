package scripting.frameworks.modulardecisiontree.nodes;

import scripting.frameworks.mission.missiontypes.TaskFinish;

public abstract class BaseDecisionNode implements INode {

    protected INode trueNode = null;
    protected INode falseNode = null;

    protected boolean lastValidationResult = false;
    protected long lastValidationTime = -1;
    protected long evaluationDelay = -1;

    public BaseDecisionNode() {
    }

    public BaseDecisionNode(INode trueNode, INode falseNode) {
        this.trueNode = trueNode;
        this.falseNode = falseNode;
    }

    /**
     * This method sets the interval (in milliseconds) in which to evaluate this node.
     * For example, if this delay is set 5000, then this decision node will only call its "isValid" method once every 5 seconds and cache the value.
     */
    public void setEvaluationDelay(long delay) {
        evaluationDelay = delay;
    }

    public long getEvaluationDelay() {
        return evaluationDelay;
    }

    public INode getTrueNode() {
        return trueNode;
    }

    public void setTrueNode(INode trueNode) {
        this.trueNode = trueNode;
    }

    public INode getFalseNode() {
        return falseNode;
    }

    public void setFalseNode(INode falseNode) {
        this.falseNode = falseNode;
    }

    @Override
    public INode getValidNode() {
        boolean valid = lastValidationResult;
        long time = System.currentTimeMillis();

        if (evaluationDelay <= 0 && time - lastValidationTime > evaluationDelay) {
            valid = isValid();
            lastValidationResult = valid;
            lastValidationTime = time;
        }

        if (valid) {
            return trueNode == null ? null : trueNode.getValidNode();
        } else {
            return falseNode == null ? null : falseNode.getValidNode();
        }
    }

    @Override
    public String getStatus() {
        return "";
    }

    @Override
    public TaskFinish execute() {
        return NodeFinishes.GENERIC_SUCCESS;
    }

    public abstract boolean isValid();

    public abstract void initializeNode();

}
