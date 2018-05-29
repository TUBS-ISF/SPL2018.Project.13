package ninja.tilman.chef.manager;

//#if MarkdownFiles

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//#if Structured
import java.util.ArrayList;
// #endif
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.ThematicBreak;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;

// #if Structured
import ninja.tilman.chef.data.Ingredient;
// #endif
import ninja.tilman.chef.data.Recipe;


public class MarkdownRecipeManager implements RecipeManager {
	
	
	private final Parser parser;
	
	MarkdownRecipeManager() {
		MutableDataSet markdownOptions = new MutableDataSet();
		// markdownOptions.set(Parser.EXTENSIONS, Arrays.asList(YamlFrontMatterExtension.create()));
		
		parser = Parser.builder(markdownOptions).build();
	}

	@Override
	public List<Recipe> getRecipes() {
		ImmutableList.Builder<Recipe> builder = ImmutableList.builder();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("recipes"))) {
			for (Path path : directoryStream) {
				builder.add(createRecipe(path));
	        }
        } catch (IOException e) {
        	throw new RuntimeException(e);
        }
		return builder.build();
	}

	private Recipe createRecipe(Path path) throws IOException {
		String input = new String(Files.readAllBytes(path));
		Node document = parser.parse(input);

        Node currentNode = document.getFirstChild();
		
        
        String name = null;
        String description = null;
        String instructions = null;
        // #if Text
//@        String ingredients = null;
        // #endif
        // #if Structured
        List<Ingredient> ingredients = null;
        // #endif
        
        
        Heading nameNode = null;
        Section descriptionSection = null;
        Section ingredientsSection = null;
        Section instructionsSection = null;        
        
		if (currentNode instanceof Heading && ((Heading) currentNode).getLevel() == 1) {
	        nameNode = (Heading) currentNode;
	        name = nameNode.getChildChars().toString();
        	currentNode = currentNode.getNext();
        }
		
		if (currentNode != null && !(currentNode instanceof ListBlock)) {
	        descriptionSection = nextSection(currentNode);
	        description = descriptionSection.getText();
	        currentNode = descriptionSection.getNextNode();
		}
		
		
		
		if (currentNode != null) {
			ingredientsSection = nextSection(currentNode);
			
			// #if Text
//@			ingredients = ingredientsSection.getText();
			// #endif
			// #if Structured
			ingredients = createIngredientsFromNodes(ingredientsSection.getNodes());
			// #endif
			
			currentNode = ingredientsSection.getNextNode();
		}
        
		if (currentNode != null) {
			instructionsSection = nextSection(currentNode);
			instructions = instructionsSection.getText();
		}
  
		return new Recipe(input, name, description, instructions, ingredients);
	}

	private Section nextSection(Node currentNode) {
		ThematicBreak endNode;
		if (currentNode instanceof ThematicBreak) {
			endNode = (ThematicBreak) currentNode;
		} else {
			endNode = (ThematicBreak) currentNode.getNextAny(ThematicBreak.class);
		}
		List<Node> nodes = getNodesUntil(currentNode, endNode);
		
		return new Section(endNode, nodes);
	}
	
	private List<Node> getNodesUntil(Node currentNode, Node endNode) {
		ImmutableList.Builder<Node> listBuilder = ImmutableList.builder();
		while (currentNode != endNode) {
			listBuilder.add(currentNode);
			currentNode = currentNode.getNext();
		}
		return listBuilder.build();
	}
	
	// #if Structured
	private List<Ingredient> createIngredientsFromNodes(List<Node> ingredientsNodes) {
		ImmutableList.Builder<Ingredient> ingredientsBuilder = ImmutableList.builder();
		for (Node node : ingredientsNodes) {
			if (node instanceof ListBlock) {
				for (Node child : node.getChildren()) {
					ingredientsBuilder.add(createIngredientFromNode(child));
				}
			} else {
				ingredientsBuilder.add(createIngredientFromNode(node));
			}
		}
		return ingredientsBuilder.build();
	}
	
	private Ingredient createIngredientFromNode(Node node) {
		StringBuilder textBuilder = new StringBuilder();
		List<Ingredient> children = new ArrayList<>();
		for (Node child : node.getChildren()) {
			if (child instanceof ListBlock) {
				for (Node listItem : child.getChildren()) {
					children.add(createIngredientFromNode(listItem));
				}
			} else {
				textBuilder.append(child.getChars());
			}
		}
		String text = textBuilder.toString().trim();
		return createIngredient(children, text, node);
	}

	private Ingredient createIngredient(List<Ingredient> children, String text, Node node) {
		// TODO node in ingredients
		return new Ingredient(text, children);
	}
	// #endif
	
	/**
	 * @see https://github.com/vsch/flexmark-java/issues/222#issuecomment-378103108
	 */
    private BasedSequence includeToTrailingEOL(BasedSequence chars) {
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
    
    
    private class Section {
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
// #endif
