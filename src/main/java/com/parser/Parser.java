package com.parser;

import com.model.Match;

import java.util.List;

public interface Parser {

    List<Match> parse();

    String getHtml();

}
