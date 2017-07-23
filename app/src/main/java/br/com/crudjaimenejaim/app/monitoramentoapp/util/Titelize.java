package br.com.crudjaimenejaim.app.monitoramentoapp.util;

import java.util.Objects;

/**
 * Created by jaimenejaim on 17/07/17.
 */

public class Titelize {

    public static String titleize(final String input) {
        Objects.requireNonNull(input, "The input parameter may not be null.");

        final StringBuilder output = new StringBuilder(input.length());
        boolean lastCharacterWasWhitespace = true;

        for (final char currentCharacter : input.toCharArray()) {
            if (lastCharacterWasWhitespace) {
                output.append(Character.toTitleCase(currentCharacter));
            } else {
                output.append(currentCharacter);
            }
            lastCharacterWasWhitespace = Character.isWhitespace(currentCharacter);
        }
        return output.toString();
    }
}
