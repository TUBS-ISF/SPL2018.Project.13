package ninja.tilman.chef.manager.markdown;



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

// #endif
import ninja.tilman.chef.data.Recipe;
import ninja.tilman.chef.data.ingredients.Ingredients;
import ninja.tilman.chef.data.ingredients.IngredientsClassProvider;
import ninja.tilman.chef.data.ingredients.StructuredIngredients;
import ninja.tilman.chef.data.ingredients.StructuredIngredients.Ingredient;
import ninja.tilman.chef.data.ingredients.TextIngredients;
import ninja.tilman.chef.manager.RecipeManager;


public class MarkdownRecipeManager implements RecipeManager {
	
	
	private final Parser parser;
	
	public MarkdownRecipeManager() {
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
        Ingredients ingredients = null;

        
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
			
			Class<? extends Ingredients> ingredientsClass = IngredientsClassProvider.getInstance().getIngredientsClass();
			if (ingredientsClass == StructuredIngredients.class) {
				ingredients = createIngredientsFromNodes(ingredientsSection.getNodes());
			} else if (ingredientsClass == TextIngredients.class) {
				ingredients = new TextIngredients(ingredientsSection.getText());
			}
			
			currentNode = ingredientsSection.getNextNode();
		}
        
		if (currentNode != null) {
			instructionsSection = nextSection(currentNode);
			instructions = instructionsSection.getText();
		}
  
		return new Recipe(name, description, instructions, ingredients);
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
	
	private Ingredients createIngredientsFromNodes(List<Node> ingredientsNodes) {
		List<Ingredient> ingredientsList = new ArrayList<>();
		for (Node node : ingredientsNodes) {
			if (node instanceof ListBlock) {
				for (Node child : node.getChildren()) {
					ingredientsList.add(createIngredientFromNode(child));
				}
			} else {
				ingredientsList.add(createIngredientFromNode(node));
			}
		}
		return new StructuredIngredients(ingredientsList);
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
}
