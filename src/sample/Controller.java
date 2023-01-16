package sample;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//adding a period when translating from text to morse causes it to appear at the start of the sentence.
public class Controller {

    @FXML
    private TextArea inputField;
    @FXML
    public Label outputLabel;
    public boolean checker = false;



    private void deselect(TextArea textField) {
        Platform.runLater(() -> {
            if (textField.getText().length() > 0 && textField.selectionProperty().get().getEnd() == 0) {
                deselect(textField);
            } else {
                textField.selectEnd();
                textField.deselect();
            }
        });
    }

    @FXML
    public void initialize() {
        deselect(inputField);
        inputField.textProperty().addListener((observableValue, s, t1) -> {
            StringBuilder input = new StringBuilder(inputField.getText());

            if (!checker) {
                input.delete(1, 116);
//                char c = input.charAt(input.length()-1);
                //problem.

                checker = true;
                Platform.runLater(() -> {

                    inputField.setText(String.valueOf(input));
                    inputField.positionCaret(inputField.getText().length());
                });

            } else {
                Platform.runLater(() -> {
                    inputField.setText(String.valueOf(input));
                    inputField.positionCaret(inputField.getText().length());
                });
            }
            //if statement doesn't contain . - space or /.
            Pattern pattern = Pattern.compile("[^.\s/-]");
            Matcher match = pattern.matcher(input);
            boolean val = match.find();
            if (val) {
            outputLabel.setText(lettersToMorse(String.valueOf(input)));
            } else {
                outputLabel.setText(morseToLetters(String.valueOf(input)));
            }

        });
    }


    private String morseToLetters(String statement) {
        StringBuilder newStatement = new StringBuilder(statement);
        String[] words = statement.split("/");
        for (String word : words) {
            String[] letters = word.split(" ");
            for (String letter : letters) {
                String output = switch (letter) {
                    case ".-" -> "a";
                    case ".-.-" -> "ä";
                    case ".--.-" -> "á";
                    case ".--.-." -> "@";
                    case "-..." -> "b";
                    case "-.-." -> "c";
                    case "-.." -> "d";
                    case "." -> "e";
                    case "..-.." -> "é";
                    case ".-..-" -> "è";
                    case "..-." -> "f";
                    case "--." -> "g";
                    case "...." -> "h";
                    case ".." -> "i";
                    case ".---" -> "j";
                    case "-.-" -> "k";
                    case ".-.." -> "l";
                    case "--" -> "m";
                    case "-." -> "n";
                    case "---" -> "o";
                    case "---." -> "ö";
                    case ".--." -> "p";
                    case "--.-" -> "q";
                    case ".-." -> "r";
                    case "...--.." -> "ß";
                    case "..." -> "s";
                    case "-" -> "t";
                    case "..-" -> "u";
                    case "..--" -> "ü";
                    case "...-" -> "v";
                    case ".--" -> "w";
                    case "-..-" -> "x";
                    case "-.--" -> "y";
                    case "--.." -> "z";
                    case "-----" -> "0";
                    case ".----" -> "1";
                    case "..---" -> "2";
                    case "...--" -> "3";
                    case "....-" -> "4";
                    case "....." -> "5";
                    case "-...." -> "6";
                    case "--..." -> "7";
                    case "---.." -> "8";
                    case "----." -> "9";
                    case "..--.." -> "?";
                    case "-.-.--" -> "!";
                    case ".-.-.-" -> ".";
                    case "--..--" -> ",";
                    case "---..." -> ":";
                    case "-.-.-." -> ";";
                    case "-....-" -> "-";
                    case "..--.-" -> "_";
                    case "-.--." -> "(";
                    case "-.--.-" -> ")";
                    case ".-..-." -> "\"";
                    case ".----." -> "'";
                    case "-...-" -> "=";
                    case ".-.-." -> "+";
                    case "/" -> " ";
                    case "" -> "";

                    default -> "#";
                };
                int letterStartIndex = newStatement.indexOf(letter);
                int letterEndIndex = letterStartIndex + letter.length();
                newStatement.replace(letterStartIndex, letterEndIndex, output);
            }
        }
        statement = String.valueOf(newStatement);
        statement = statement.replace("\s", "");
        statement = statement.replace("/", " ");
        return statement;

    }

    private String lettersToMorse(String statement) {
        StringBuilder newStatement = new StringBuilder(statement);
        int index =0;
        String[] words = statement.split(" ");
        for (String word : words) {

            char[] letters = word.toCharArray();
            if(index!=0) {
                newStatement.insert(newStatement.indexOf(word), '/');
            }
            for (char letter : letters) {
                String output = switch (letter) {
                    case 'a' -> ".-";
                    case 'ä' -> ".-.-";
                    case 'á' -> ".--.-";
                    case '@' -> ".--.-.";
                    case 'b' -> "-...";
                    case 'c' -> "-.-.";
                    case 'd' -> "-..";
                    case 'e' -> ".";
                    case 'é' -> "..-..";
                    case 'è' -> ".-..-";
                    case 'f' -> "..-.";
                    case 'g' -> "--.";
                    case 'h' -> "....";
                    case 'i' -> "..";
                    case 'j' -> ".---";
                    case 'k' -> "-.-";
                    case 'l' -> ".-..";
                    case 'm' -> "--";
                    case 'n' -> "-.";
                    case 'o' -> "---";
                    case 'ö' -> "---.";
                    case 'p' -> ".--.";
                    case 'q' -> "--.-";
                    case 'r' -> ".-.";
                    case 's' -> "...";
                    case 'ß' -> "...--..";
                    case 't' -> "-";
                    case 'u' -> "..-";
                    case 'ü' -> "..--";
                    case 'v' -> "...-";
                    case 'w' -> ".--";
                    case 'x' -> "-..-";
                    case 'y' -> "-.--";
                    case 'z' -> "--..";
                    case '0' -> "-----";
                    case '1' -> ".----";
                    case '2' -> "..---";
                    case '3' -> "...--";
                    case '4' -> "....-";
                    case '5' -> ".....";
                    case '6' -> "-....";
                    case '7' -> "--...";
                    case '8' -> "---..";
                    case '9' -> "----.";
                    case '?' -> "..--..";
                    case '!' -> "-.-.--";
                    case '.' -> ".-.-.-";
                    case ',' -> "--..--";
                    case ':' -> "---...";
                    case ';' -> "-.-.-.";
                    case '-' -> "-....-";
                    case '_' -> "..--.-";
                    case '(' -> "-.--.";
                    case ')' -> "-.--.-";
                    case '"' -> ".-..-.";
                    case '\'' -> ".----.";
                    case '=' -> "-...-";
                    case '+' -> ".-.-.";
                    case '/' -> "-..-.";
                    case ' ' -> "/";
                    case '\u0009' -> "[Tab]";
                    case '\n' -> "¶";

                    default -> "#";
                };
                newStatement.insert(newStatement.indexOf(String.valueOf(letter)),' ');
                newStatement.replace(newStatement.indexOf(String.valueOf(letter)),newStatement.indexOf(String.valueOf(letter))+1,output);

            }
            index++;
        }
        statement = String.valueOf(newStatement);
        statement = statement.replace(""," ");

        return statement;
    }
}