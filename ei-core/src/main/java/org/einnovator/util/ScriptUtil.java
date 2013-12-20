package org.einnovator.util;

import javax.script.*;


import java.util.*;
import java.io.*;


public class ScriptUtil {

	public static Object eval(String engName, String script) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine eng = mgr.getEngineByName(engName);
		return eval(eng, script);
	}

	public static Object eval_js(String s) {
		return eval("JavaScript", s);
	}

	public static Object eval_groovy(String s) {
		return eval("Groovy", s);
	}
	

	public static Object eval(ScriptEngine eng, String script) {
		if (eng==null) return null;
		try {
		    return eng.eval(script);
		} catch (ScriptException ex) { 
			throw new RuntimeException(ex);
		}
	}

	public static Object invoke(ScriptEngine eng, String f, Object... args) {		
		if (eng==null) return null;
		Invocable ieng = (Invocable) eng;
		try {
			return ieng.invokeFunction(f, args);
		} catch (ScriptException ex) { 
			throw new RuntimeException(ex);
		} catch (NoSuchMethodException ex) { 
			throw new RuntimeException(ex);
		}
	}
	
	public static Object evalResource(String engName, String fn) {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine eng = mgr.getEngineByName(engName);
		return evalResource(eng, fn);
	}	

	public static Object evalResource(ScriptEngine eng, String fn) {
		if (eng==null) return null;
		try {
			InputStream is = ScriptUtil.class.getResourceAsStream(fn);
			Reader reader = new InputStreamReader(is);
			return eng.eval(reader);
		} catch (ScriptException ex) {
			throw new RuntimeException(ex);	
		}
	}	
	

	public static ScriptEngine findScriptEngine(String cue) {	
		ScriptEngine eng = findScriptEngineByLanguage(cue);
		if (eng!=null) {
			return eng;
		}
		eng = findScriptEngineByName(cue);
		if (eng!=null) {
			return eng;
		}
		return findScriptEngineByExtension(cue);
	}
	
	public static ScriptEngine findScriptEngineByLanguage(String langName) {	
		return findScriptEngineByLanguage(langName, null);
	}
	
	public static ScriptEngine findScriptEngineByLanguage(String langName, String langVersion) {	
		ScriptEngineManager mgr = new ScriptEngineManager();
		List<ScriptEngineFactory> lft =  mgr.getEngineFactories();
		for (ScriptEngineFactory ft: lft) {
			String langName_ = ft.getLanguageName();
		    String langVersion_ = ft.getLanguageVersion();
		    if ((langName==null || langName.equals(langName_)) && 
		    	(langVersion==null || langVersion.equals(langVersion_))) {
		      return ft.getScriptEngine();
		    }
		}
		return null;
	} 

	public static ScriptEngine findScriptEngineByName(String name) {	
		return findScriptEngineByName(name, null);	
	}
	
	public static boolean isTrue(Object value) {
		if (value==null) {
			return false;
		}
		if (value instanceof Boolean) {
			return ((Boolean)value).booleanValue();
		}
		return false;
	}
	
	public static ScriptEngine findScriptEngineByName(String name, String version) {	
		ScriptEngineManager mgr = new ScriptEngineManager();
		List<ScriptEngineFactory> lft =  mgr.getEngineFactories();
		for (ScriptEngineFactory ft: lft) {
			String engName = ft.getEngineName();		
			String engVersion = ft.getEngineVersion();
			if (version!=null && !version.equals(engVersion)) return null;
			if (engName.equals(name)) return ft.getScriptEngine();		
			List<String> engNames = ft.getNames();
			for(String name_: engNames) {
				if (name_.equals(name)) return ft.getScriptEngine();
			}
		}
		return null;
	} 

	public static ScriptEngine findScriptEngineByExtension(String ext) {	
		ScriptEngineManager mgr = new ScriptEngineManager();		
		return mgr.getEngineByExtension(ext);
	}

	
	public static void listScriptEngines() {	
		ScriptEngineManager mgr = new ScriptEngineManager();
		List<ScriptEngineFactory> lft =  mgr.getEngineFactories();
		for (ScriptEngineFactory ft: lft) {
			System.out.println("ScriptEngineFactory Info");
			String engName = ft.getEngineName();
			String engVersion = ft.getEngineVersion();
			String langName = ft.getLanguageName();
			String langVersion = ft.getLanguageVersion();
			System.out.printf("\tScript Engine: %s (%s)\n",  engName, engVersion);
			List<String> engNames = ft.getNames();
			for(String name: engNames) {
				System.out.printf("\tEngine Alias: %s\n", name);
			}
			System.out.printf("\tLanguage: %s (%s)\n", langName, langVersion);
		}
	  }    
	
	
}
