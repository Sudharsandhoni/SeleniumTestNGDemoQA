package tests.data.demoQA;

import java.util.ArrayList;
import java.util.List;

public class CheckBoxNode {

	public enum State {
		UNCHECKED, CHECKED, INDETERMINATE, UNKNOWN
	}

	private String name;
	private State state;
	private List<CheckBoxNode> children;

	public CheckBoxNode(String name, State state, List<CheckBoxNode> children) {
		this.name = name;
		this.state = state;
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public State getState() {
		return state;
	}

	public List<CheckBoxNode> getChildren() {
		return children;
	}

	public CheckBoxNode toggle(String targetName) {
		//System.out.println("This Name On Init -->> "+ this.name);
	    return toggleInternal(targetName).node;
	}

	private ToggleResult toggleInternal(String targetName) {
		//System.out.println("This Name -->> "+ this.name);
	    if (this.name.equals(targetName)) {
	        return new ToggleResult(toggleSelf(), true);
	    }

	    boolean anyChildChanged = false;
	    List<CheckBoxNode> newChildren = new ArrayList<>();

	    for (CheckBoxNode child : children) {
	        ToggleResult result = child.toggleInternal(targetName);
	        newChildren.add(result.node);
	        anyChildChanged |= result.found;
	    }

	    // If target not in this subtree, return this node unchanged
	    if (!anyChildChanged) {
    		//System.out.println(" -->> Nothing changed");
	        return new ToggleResult(this, false);
	    }

	    State derived = deriveStateFromChildren(newChildren);
	    return new ToggleResult(new CheckBoxNode(name, derived, newChildren), true);
	}

	private CheckBoxNode toggleSelf() {
		//System.out.println("Toggle Self -->> "+ this.name);
		State newState = (this.state == State.CHECKED) ? State.UNCHECKED : State.CHECKED;

		List<CheckBoxNode> updatedChildren = new ArrayList<>();
		for (CheckBoxNode child : children) {
			updatedChildren.add(child.withStateRecursive(newState));
		}

		return new CheckBoxNode(name, newState, updatedChildren);
	}

	private CheckBoxNode withStateRecursive(State state) {
		//System.out.println("withStateRecursive -->> "+ this.name);
		List<CheckBoxNode> updated = new ArrayList<>();
		for (CheckBoxNode child : children) {
			updated.add(child.withStateRecursive(state));
		}
		return new CheckBoxNode(name, state, updated);
	}

	private State deriveStateFromChildren(List<CheckBoxNode> kids) {
		//System.out.println("deriveStateFromChildren -->> "+ this.name);
		boolean allChecked = kids.stream().allMatch(c -> c.state == State.CHECKED);
		boolean allUnchecked = kids.stream().allMatch(c -> c.state == State.UNCHECKED);

		if (allChecked)
			return State.CHECKED;
		if (allUnchecked)
			return State.UNCHECKED;
		return State.INDETERMINATE;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		buildString(sb, 0);
		return sb.toString();
	}

	private void buildString(StringBuilder sb, int level) {
		sb.append("  ".repeat(level)).append("- ").append(name).append(" [").append(state).append("]").append("\n");

		for (CheckBoxNode child : children) {
			child.buildString(sb, level + 1);
		}
	}
	
	private static class ToggleResult {
	    final CheckBoxNode node;
	    final boolean found;

	    ToggleResult(CheckBoxNode node, boolean found) {
	        this.node = node;
	        this.found = found;
	    }
	}
	
	public List<String> pathTo(String target) {
	    List<String> path = new ArrayList<>();
	    if (findPath(this, target, path)) {
	        return path;
	    }
	    throw new IllegalArgumentException("Node not found: " + target);
	}

	private boolean findPath(CheckBoxNode node, String target, List<String> path) {
	    path.add(node.name);

	    if (node.name.equals(target)) {
	        return true;
	    }

	    for (CheckBoxNode child : node.children) {
	        if (findPath(child, target, path)) {
	            return true;
	        }
	    }

	    path.remove(path.size() - 1);
	    return false;
	}

	
	public int size() {
	    return 1 + children.stream()
	                        .mapToInt(CheckBoxNode::size)
				.sum();
	}
	
	public List<String> getAllCheckBoxNames() {
	    List<String> result = new ArrayList<>();
	    result.add(this.name);
	    for (CheckBoxNode child : children) {
	        result.addAll(child.getAllCheckBoxNames());   // deep children names
	    }

	    return result;
	}


}


