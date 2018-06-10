package ninja.tilman.chef.manager.markdown;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class MarkdownUtils {

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

}
