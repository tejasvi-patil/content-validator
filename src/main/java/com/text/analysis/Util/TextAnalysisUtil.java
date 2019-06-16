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

    private HashMap<String,String> getPatternMatchWOrdMap() {
        objectionableWordMap.put("Adult", "(.*harass.*|.*sex.*|.*drug.*|.*molest.*)");
        objectionableWordMap.put("Violent", "(.*murder.*|.*bomb.*|.*suicide.*)");
        return objectionableWordMap;
    }

    private HashMap<String,List<String>> getStrictMatchWordMap() {
        List<String> adult = new ArrayList<>(Arrays.asList("harass","sex","drug","molest"));
        List<String> violent = new ArrayList<>(Arrays.asList("murder","bomb","suicide"));
        strictObjectionableWordMap.put("Adult", adult);
        strictObjectionableWordMap.put("Violent", violent);
        return strictObjectionableWordMap;
    }

    public Set<String> filterOffensiveContentCategory(String userComment, boolean strictMatchMode) {
        String[] comments = userComment.toLowerCase().split(" ");
        Set<String> wordCategory = new HashSet<>();
        if(strictMatchMode) {
            /**
             * Match exact offensive words
             */
            Map<String, List<String>> objectionableStrictWordMap = getStrictMatchWordMap();
            Arrays.stream(comments).forEach(inputWord -> {
                for (Map.Entry<String, List<String>> entrySet : objectionableStrictWordMap.entrySet()) {
                    if (entrySet.getValue().contains(inputWord)) {
                        wordCategory.add(entrySet.getKey());
                    }
                }
            });
        }
        else {
            Map<String, String> objectionableWordMap = getPatternMatchWOrdMap();
            for (Map.Entry<String, String> entry : objectionableWordMap.entrySet()) {

                if (Pattern.matches(entry.getValue(), userComment)) {
                    wordCategory.add(entry.getKey());
                }

            }
        }
        return wordCategory;
    }

}
