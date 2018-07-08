import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.ThematicBreak;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.manager.RecipeManager;

public aspect MarkdownFiles {
	List<Recipe> around(RecipeManager it): call(List<Recipe> RecipeManager.getRecipes()) && target(it) {
		ImmutableList.Builder<Recipe> builder = ImmutableList.builder();
		// this should be try-with-resource, but that is not supported :(
		try {
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("recipes"));
	        try {
				for (Path path : directoryStream) {
					builder.add(it.createRecipe(path));
		        }
	        } finally {
	        	directoryStream.close();
	        }
		} catch (IOException e) {
        	throw new RuntimeException(e);
        }
		return builder.build();
	}

	Recipe RecipeManager.createRecipe(Path path) throws IOException {
		MutableDataSet markdownOptions = new MutableDataSet();		
		Parser parser = Parser.builder(markdownOptions).build();
		
		String input = new String(Files.readAllBytes(path));
		Recipe recipe = new Recipe();
		
		Node document = parser.parse(input);

        Node currentNode = document.getFirstChild();
        
        Heading nameNode = null;
        Section descriptionSection = null;
        Section ingredientsSection = null;
        Section instructionsSection = null;
        
		if (currentNode instanceof Heading && ((Heading) currentNode).getLevel() == 1) {
	        nameNode = (Heading) currentNode;
	        recipe.setName(nameNode.getChildChars().toString());
        	currentNode = currentNode.getNext();
        }
		
		if (currentNode != null && !(currentNode instanceof ListBlock)) {
	        descriptionSection = nextSection(currentNode);
	        recipe.setDescription(descriptionSection.getText());
	        currentNode = descriptionSection.getNextNode();
		}
		
		if (currentNode != null) {
			ingredientsSection = nextSection(currentNode);
			// TODO
			setIngredients(recipe, ingredientsSection);
			currentNode = ingredientsSection.getNextNode();
		}
        
		if (currentNode != null) {
			instructionsSection = nextSection(currentNode);
			recipe.setInstructions(instructionsSection.getText());
		}
		
		return recipe;
	}

	public Section RecipeManager.nextSection(Node currentNode) {
		ThematicBreak endNode;
		if (currentNode instanceof ThematicBreak) {
			endNode = (ThematicBreak) currentNode;
		} else {
			endNode = (ThematicBreak) currentNode.getNextAny(ThematicBreak.class);
		}
		List<Node> nodes = getNodesUntil(currentNode, endNode);
		
		return new Section(endNode, nodes);
	}
	
	public List<Node> RecipeManager.getNodesUntil(Node currentNode, Node endNode) {
		ImmutableList.Builder<Node> listBuilder = ImmutableList.builder();
		while (currentNode != endNode) {
			listBuilder.add(currentNode);
			currentNode = currentNode.getNext();
		}
		return listBuilder.build();
	}
	

	/**
	 * @see https://github.com/vsch/flexmark-java/issues/222#issuecomment-378103108
	 */
	static BasedSequence includeToTrailingEOL(BasedSequence chars) {
	    int endOffset = chars.getEndOffset();
	    while (endOffset < chars.getBaseSequence().length() && chars.getBaseSequence().charAt(endOffset) != '\n') {
	        endOffset++;
	    }
	
	    if (endOffset < chars.getBaseSequence().length()) endOffset++;
	
	    if (endOffset != chars.getEndOffset()) {
	        chars = chars.baseSubSequence(chars.getStartOffset(), endOffset);
	    }
	    return chars;
	}

	public static class Section {
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
				stringBuilder.append(includeToTrailingEOL(node.getChars()));
			}
			return stringBuilder.toString();
		}
	}
}
