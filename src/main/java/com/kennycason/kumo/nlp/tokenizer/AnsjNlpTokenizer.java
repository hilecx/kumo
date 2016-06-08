package com.kennycason.kumo.nlp.tokenizer;

import org.ansj.dic.LearnTool;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.apache.commons.lang3.StringUtils;
import org.languagetool.language.Chinese;
import org.languagetool.tokenizers.Tokenizer;

import java.util.ArrayList;
import java.util.List;

public class AnsjNlpTokenizer implements WordTokenizer {


    public AnsjNlpTokenizer() {
    }

    @Override
    public List<String> tokenize(final String sentence) {
        LearnTool learn = new LearnTool();

        // 关闭人名识别
        learn.isAsianName = true;
        // 关闭外国人名识别
        learn.isForeignName = true;

        Result parse = new NlpAnalysis().setLearnTool(learn).parseStr(sentence);
        System.out.println(parse);
        final List<String> tokens = new ArrayList<>();
        for (final Term rawToken : parse.getTerms()) {
            String termString = rawToken.toString();
            if (StringUtils.isNotEmpty(termString)) {
                if(!(termString.contains("/a") || termString.contains("/n")|| termString.contains("/en")|| termString.contains("/i")))
                    continue;
                if(termString.indexOf('/') > -1)
                    tokens.add(termString.substring(0, termString.indexOf('/')));
                else
                    tokens.add(termString);
            }
        }
        return tokens;
    }

}
