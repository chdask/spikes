package com.chdask.spikes.mustache;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
/**
 * Example class to bind a mustache template server side.
 * See also: https://github.com/spullara/mustache.java
 * Javadoc : http://mustachejava.s3-website-us-west-1.amazonaws.com/
 * 
 * @author Christina Daskalaki
 */
public class Example {

	public static void main(String[] args) throws IOException {

		String dataPath = "src/main/resources/data/data.json";
		String templatePath = "src/main/resources/templates/template.mustache";

		MustacheFactory mf = new DefaultMustacheFactory();
		Mustache mustache = mf.compile(templatePath);
		mustache.execute(new PrintWriter(System.out), getJsonObject(dataPath)).flush();
	}
	
	/**
	 * mock json data using json-simple
	 * @param dataPath
	 * @return JSONObject
	 */
	public static JSONObject getJsonObject(String dataPath){
		JSONObject json = null;
		try {
			json = (JSONObject) new JSONParser().parse(new FileReader(
					dataPath));
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
