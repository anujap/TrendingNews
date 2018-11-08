package com.example.anuja.trendingnews.app.common;

import com.example.anuja.trendingnews.model.Articles;

public class Utils {

    public static String getStringByArticle(Articles article) {
        StringBuilder builder = new StringBuilder();
        builder.append(article.getTitle())
                .append("-")
                .append(article.getSource());
        return builder.toString();
    }
}
