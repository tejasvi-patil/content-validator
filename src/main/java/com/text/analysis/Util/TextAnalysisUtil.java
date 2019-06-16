/**
 * @Author patiltejasviv@gmail.com
 * License: MIT
 */
package com.text.analysis.Util;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;

@Component
public class TextAnalysisUtil {

    private HashMap<String,String> objectionableWordMap = new HashMap();

    private HashMap<String, List<String>> strictObjectionableWordMap = new HashMap<>();

    /**
     * Create categories of objectionable words. Just for demo.
     * NOTE: Here it is hardcoded but it can be loaded by others means like file and api.
     * @return
     */
    private HashMap<String,String> getPatternMatchWOrdMap() {
        objectionableWordMap.put("Adult", "(.*harass.*|.*sex.*|.*drug.*|.*molest.*)");
        objectionableWordMap.put("Violent", "(.*murder.*|.*bomb.*|.*suicide.*)");
        return objectionableWordMap;
    }

    /**
     * Create categories of objectionable words. Just for demo.
     * NOTE: Here it is hardcoded but it can be loaded by others means like file and api.
     * @return
     */
    private HashMap<String,List<String>> getStrictMatchWordMap() {
        List<String> adult = new ArrayList<>(Arrays.asList("harass","sex","drug","molest"));
        List<String> violent = new ArrayList<>(Arrays.asList("murder","bomb","suicide"));
        strictObjectionableWordMap.put("Adult", adult);
        strictObjectionableWordMap.put("Violent", violent);
        return strictObjectionableWordMap;
    }

    /**
     * Method that analyse the input user comment and returns the set of objectionable words if present based on the mode passed
     * @param userComment user comment text
     * @param strictMatchMode true/false based on the preference
     * @return Set of words that were found objectionable
     */
    public Set<String> filterOffensiveContentCategory(String userComment, boolean strictMatchMode) {
        String[] comments = userComment.toLowerCase().split(" ");
        Set<String> wordsCollection = new HashSet<>();
        if(strictMatchMode) { // If strict mode is enabled

            //Match exact offensive words
            Map<String, List<String>> objectionableStrictWordMap = getStrictMatchWordMap(); // Get the words collection
            Arrays.stream(comments).forEach(inputWord -> {
                for (Map.Entry<String, List<String>> entrySet : objectionableStrictWordMap.entrySet()) {
                    if (entrySet.getValue().contains(inputWord)) {
                        wordsCollection.add(entrySet.getKey());
                    }
                }
            });
        }
        else { // if strict mode is disabled
            Map<String, String> objectionableWordMap = getPatternMatchWOrdMap(); // Get the words collection
            for (Map.Entry<String, String> entry : objectionableWordMap.entrySet()) {

                if (Pattern.matches(entry.getValue(), userComment)) {
                    wordsCollection.add(entry.getKey()); // Add word to collection
                }

            }
        }
        return wordsCollection;
    }

}
