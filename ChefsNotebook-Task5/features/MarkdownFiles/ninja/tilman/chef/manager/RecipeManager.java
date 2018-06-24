package ninja.tilman.chef.manager;



import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.ListBlock;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.ThematicBreak;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.options.MutableDataSet;

import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.data.RecipeBuilder;
import ninja.tilman.chef.manager.RecipeManager;


public class RecipeManager {
	
	
	private final Parser parser;
	
	public RecipeManager() {
		MutableDataSet markdownOptions = new MutableDataSet();
		// markdownOptions.set(Parser.EXTENSIONS, Arrays.asList(YamlFrontMatterExtension.create()));
		
		parser = Parser.builder(markdownOptions).build();
	}

	public List<Recipe> getRecipes() {
		ImmutableList.Builder<Recipe> builder = ImmutableList.builder();
		// this should be try-with-resource, but that is not supported :(
		try {
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("recipes"));
	        try {
				for (Path path : directoryStream) {
					builder.add(createRecipe(path));
		        }
	        } finally {
	        	directoryStream.close();
	        }
		} catch (IOException e) {
        	throw new RuntimeException(e);
        }
		return builder.build();
	}

	private Recipe createRecipe(Path path) throws IOException {
		String input = new String(Files.readAllBytes(path));
		RecipeBuilder builder = new RecipeBuilder();
		
		Node document = parser.parse(input);

        Node currentNode = document.getFirstChild();
        
        Heading nameNode = null;
        Section descriptionSection = null;
        Section ingredientsSection = null;
        Section instructionsSection = null;        
        
		if (currentNode instanceof Heading && ((Heading) currentNode).getLevel() == 1) {
	        nameNode = (Heading) currentNode;
	        builder.setName(nameNode.getChildChars().toString());
        	currentNode = currentNode.getNext();
        }
		
		if (currentNode != null && !(currentNode instanceof ListBlock)) {
	        descriptionSection = nextSection(currentNode);
	        builder.setDescription(descriptionSection.getText());
	        currentNode = descriptionSection.getNextNode();
		}
		
		
		
		if (currentNode != null) {
			ingredientsSection = nextSection(currentNode);
			setIngredients(builder, ingredientsSection);
			currentNode = ingredientsSection.getNextNode();
		}
        
		if (currentNode != null) {
			instructionsSection = nextSection(currentNode);
			builder.setInstructions(instructionsSection.getText());
		}
  
		return builder.build();
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
	
	
}
