package org.einnovator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
	public static Object[] tokenize(String s) {
		Reader r = new BufferedReader(new StringReader(s));
		StreamTokenizer stk = new StreamTokenizer(r);
		stk.parseNumbers();
		//stk.eolIsSignificant(true);
		//stk.slashStarComments(false);
		//stk.slashSlashComments(false);
		//stk.quoteChar('\"');
		//stk.quoteChar('\'');
		//stk.whitespaceChars(0, ' ');
		//stk.wordChars('A', 'z');
		//stk.ordinaryChars(' ' + 1, 0xff);
		List<Object> l = new ArrayList<Object>();
		//System.out.println(s);
		try {
			int tk = stk.nextToken();
			while (tk!=StreamTokenizer.TT_EOF) {
				switch(tk) {
					case StreamTokenizer.TT_EOL: l.add(null); break;
					case StreamTokenizer.TT_NUMBER: l.add(stk.nval); break;
					case StreamTokenizer.TT_WORD: l.add(stk.sval); break;
					default: l.add((char)tk);
				}
				//System.out.println(tk + " : "  + l.get(l.size()-1));
				tk = stk.nextToken();
			}
		} catch (IOException e) { 
			throw new RuntimeException(e);
		}
		return l.toArray();
	}

}
