import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;


public class SpellChecker {
    public static void main(String[] args) {

        // Step 1: Demonstrate tree capabilities
        testTree();

        // Step 2: Read the dictionary and report the tree statistics
        BinarySearchTree<String> dictionary = readDictionary();
        System.out.println();
        reportTreeStats(dictionary);

        // Step 3: Perform spell checking
        spellCheck(dictionary);
    }

    public static void testTree() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();

        // Add a bunch of values to the tree
        tree.insert("Olga");
        tree.insert("Tomeka");
        tree.insert("Benjamin");
        tree.insert("Ulysses");
        tree.insert("Tanesha");
        tree.insert("Judie");
        tree.insert("Tisa");
        tree.insert("Santiago");
        tree.insert("Chia");
        tree.insert("Arden");

        // Make sure it displays in sorted order
        tree.display("--- Initial Tree State ---");
        reportTreeStats(tree);

        // Try to add a duplicate
        if (tree.insert("Tomeka")) {
            System.out.println("oops, shouldn't have returned true from the insert");
        }
        tree.display("--- After Adding Duplicate ---");
        reportTreeStats(tree);

        // Remove some existing values from the tree
        tree.remove("Olga");    // Root node
        tree.remove("Arden");   // a leaf node
        tree.display("--- Removing Existing Values ---");
        reportTreeStats(tree);

        // Remove a value that was never in the tree, hope it doesn't crash!
        tree.remove("Karl");
        tree.display("--- Removing A Non-Existent Value ---");
        reportTreeStats(tree);
    }

    public static void reportTreeStats(BinarySearchTree<String> tree) {
        System.out.println("-- Tree Stats --");
        System.out.printf("Total Nodes : %d\n", tree.numberNodes());
        System.out.printf("Leaf Nodes  : %d\n", tree.numberLeafNodes());
        System.out.printf("Tree Height : %d\n", tree.height());
    }

    // Construct a BST of words from Dictionary.txt
    public static BinarySearchTree<String> readDictionary() {
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        File file = new File("Dictionary.txt");
        ArrayList<String> words = new ArrayList<>();

        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                words.add(word);
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the file: " + ex);
        }

        // shuffle all words from file and add them to the dictionary
        java.util.Collections.shuffle(words, new java.util.Random(System.currentTimeMillis()));
        for(String word : words){
            tree.insert(word);
        }

        return tree;
    }

    // Check spelling of every word in Letter.txt
    public static void spellCheck(BinarySearchTree<String> dictionary){
        System.out.println();
        System.out.println("--- Misspelled Words in Letter.txt ---");
        File file = new File("Letter.txt");

        try (Scanner input = new Scanner(file)) {
            while (input.hasNextLine()) {
                String line = input.nextLine().toLowerCase();
                printWrongWords(line, dictionary);
            }
        }
        catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the file: " + ex);
        }
    }

    // Reformat line into stripped words and check their spelling
    private static void printWrongWords(String line, BinarySearchTree<String> dictionary){
        line = line.replaceAll("[\",:.!?\n]","");
        String[] words = line.split(" ");

        for(String word : words){
            if(!dictionary.search(word) && word != ""){
                System.out.println(word);
            }
        }
    }


}
