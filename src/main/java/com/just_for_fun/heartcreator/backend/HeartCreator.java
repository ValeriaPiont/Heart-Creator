package com.just_for_fun.heartcreator.backend;

import com.just_for_fun.heartcreator.backend.exception.ParseException;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class HeartCreator {

    private static final int LENGTH = 6;
    private static final int HEIGHT = 7;

    private static final String SPLIT_SIGN = "-";

    public String getHeart(String input) throws ParseException {
        String[] codes = input.trim().split(SPLIT_SIGN);
        if (codes.length < 2) {
            throw new ParseException("Can't parse input string.");
        } else {
            return createHeart(codes[0], codes[1]);
        }
    }

    private String createHeart(String code1, String code2) {
        StringBuilder heart = new StringBuilder();
        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if ((i == 0 && j % 3 != 0) || (i == 1 && j % 3 == 0) || (i - j == 2) || (i + j == 8)) {
                    heart.append(code1);
                } else {
                    heart.append(code2);
                }
            }
            heart.append("\n");
        }
        return heart.toString();
    }

}
