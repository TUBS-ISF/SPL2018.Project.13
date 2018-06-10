package ninja.tilman.chef.manager.markdown;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.ThematicBreak;

class Section {
	private final ThematicBreak endNode;
	private final ImmutableList<Node> nodes;
	
	public Section(ThematicBreak endNode, List<Node> nodes) {
		this.endNode = endNode;
		this.nodes = ImmutableList.copyOf(nodes);
	}

	public ThematicBreak getEndNode() {
		return endNode;
	}

	public ImmutableList<Node> getNodes() {
		return nodes;
	}
	
	public Node getNextNode() {
		if (endNode == null) {
			return null;
		}
		return endNode.getNext();
	}
	
	public String getText() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Node node : nodes) {
			stringBuilder.append(MarkdownUtils.includeToTrailingEOL(node.getChars()));
		}
		return stringBuilder.toString();
	}
}