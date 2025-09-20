package student;

import edu.willamette.cs1.spellingbee.SpellingBeeGraphics;

import java.awt.Color;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpellingBee {
    private static final String ENGLISH_DICTIONARY = "res/EnglishWords.txt";

    private SpellingBeeGraphics sbg;
    private List<String> wordList;
    private String puzzle;
    private Set<Character> puzzleLetters;
    private char centerLetter;
    private Set<String> foundWords = new HashSet<>();
    private int total;
    private static final int PUZZLE_LENGTH = 7;
    private static final int MIN_WORD_LENGTH = 4;
    private static final int PANGRAM_BONUS = 7;

    public void run() {
        sbg = new SpellingBeeGraphics();
        sbg.addField("Puzzle", (s) -> puzzleAction(s));
        sbg.addField("Word", (s) -> wordAction(s));
        sbg.addButton("Solve", (s) -> solveAction());
        try {
            wordList = initializeWordList();
        } catch (IOException e) {
            sbg.showMessage("Error loading dictionary.", Color.RED);
        }
    }

    private List<String> initializeWordList() throws IOException {
        List<String> wordList = Files.readAllLines(Paths.get(ENGLISH_DICTIONARY));
        sbg.showMessage(String.format("Loaded %d words", wordList.size()), Color.BLACK);
        return wordList;
    }

    private void puzzleAction(String s) {
        if (isValidPuzzle(s)) {
            this.puzzle = s.toUpperCase();
            this.puzzleLetters = new HashSet<>();
            for (int i = 0; i < puzzle.length(); i++) {
                char c = puzzle.charAt(i);
                puzzleLetters.add(c);
            }
            this.centerLetter = puzzle.charAt(0);
            sbg.setBeehiveLetters(puzzle);
            foundWords = new HashSet<>();
            total = 0;
        }
    }

    private boolean isValidPuzzle(String s) {
        s = s.toUpperCase();
        if (s.length() != PUZZLE_LENGTH) {
            sbg.showMessage("Puzzle must contain exactly seven letters.", Color.RED);
            return false;
        }
        Set<Character> letters = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!Character.isLetter(c)) {
                sbg.showMessage("Puzzle can only contain letters.", Color.RED);
                return false;
            }
            if (!letters.add(c)) {
                sbg.showMessage("Puzzle cannot have duplicate letters.", Color.RED);
                return false;
            }
        }
        if (!letters.contains(s.charAt(0))) {
            sbg.showMessage("First letter must be the center letter.", Color.RED);
            return false;
        }
        return true;
    }

    private void solveAction() {
        if (wordList == null || puzzle == null) {
            sbg.showMessage("Enter a valid puzzle.", Color.RED);
            return;
        }
        List<String> validWords = findValidWords();
        int total = 0;
        for (int i = 0; i < validWords.size(); i++) {
            String word = validWords.get(i);
            int score = calculateScore(word);
            total += score;
            Color color = findColor(word);
            sbg.addWord(word.toLowerCase() + " (" + score + ")", color);
        }
        sbg.showMessage(String.format("Found %d words. Total score: %d", validWords.size(), total), Color.BLACK);
    }

    private boolean isValidWord(String word) {
        word = word.toUpperCase();
        if (word.length() < MIN_WORD_LENGTH) {
            return false;
        }
        if (!word.contains(String.valueOf(centerLetter))) {
            return false;
        }
        for (int i = 0; i < word.length(); i++) {
            if (!puzzleLetters.contains(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private int calculateScore(String word) {
        if (word.length() == MIN_WORD_LENGTH) {
            return 1;
        }
        int score = word.length();
        if (isPangram(word)) {
            score += PANGRAM_BONUS;
        }
        return score;
    }

    private boolean isPangram(String word) {
        Set<Character> wordLetters = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {
            wordLetters.add(word.charAt(i));
        }
        return wordLetters.size() == PUZZLE_LENGTH;
    }

    private void wordAction(String s) {
        s = s.toUpperCase();
        if (!wordList.contains(s.toLowerCase())) {
            sbg.showMessage("Word not in dictionary.", Color.RED);
            return;
        }
        for (int i = 0; i < s.length(); i++) {
            if (!puzzleLetters.contains(s.charAt(i))) {
                sbg.showMessage("Word contains invalid letters.", Color.RED);
                return;
            }
        }
        if (s.length() < MIN_WORD_LENGTH) {
            sbg.showMessage("Word must be at least four letters long.", Color.RED);
            return;
        }
        if (!s.contains(String.valueOf(centerLetter))) {
            sbg.showMessage("Word must contain the center letter.", Color.RED);
            return;
        }
        if (foundWords.contains(s)) {
            sbg.showMessage("Word already found.", Color.RED);
            return;
        }
        addWord(s);
        updateScoreDisplay();
    }

    private List<String> findValidWords() {
        List<String> validWords = new ArrayList<>();
        for (int i = 0; i < wordList.size(); i++) {
            String word = wordList.get(i);
            if (isValidWord(word) && !foundWords.contains(word.toUpperCase())) {
                validWords.add(word);
            }
        }
        return validWords;
    }

    private void addWord(String word) {
        word = word.toLowerCase();
        int score = calculateScore(word);
        total += score;
        foundWords.add(word.toUpperCase());
        Color color = findColor(word);
        sbg.addWord(word + " (" + score + ")", color);
    }

    private Color findColor(String word) {
        Color color;
        if (isPangram(word)) {
            color = Color.BLUE;
        } else {
            color = Color.BLACK;
        }
        return color;
    }

    private void updateScoreDisplay() {
        sbg.showMessage(String.format("Found %d words. Total score: %d", foundWords.size(), total), Color.BLACK);
    }

    public static void main(String[] args) {
        new SpellingBee().run();
    }
}
