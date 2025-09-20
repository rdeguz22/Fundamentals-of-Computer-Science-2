package student;

import java.io.File;

/**
 * Creates LinkedListDatasets and ArrayListDatasets, and times their addToFront(), addToBack(),
 * getRandomMeow(), and sortDataset() methods.
 */
public final class DatasetTimer {
    private static final String PATH = "src/main/resources/dataset";

    private static final int TIMES = 100;
    private static final int RANDOM_MEOWS = 100;
    private final File[] files;

    private Dataset llDataset;
    private Dataset alDataset;

    private DatasetTimer(String path) {
        this.files = new File(path).listFiles();
    }


    public static void main(String[] args) {
        DatasetTimer timer = new DatasetTimer(PATH);
        timer.timeAddEachToFront();
        timer.timeAddEachToBack();
        timer.timeAccessRandomElements();
        timer.timeSort();
    }

    /**
     * Initializes both Datasets to be the appropriate type.
     */
    private void initializeDatasets() {
        llDataset = new LinkedListDataset();
        alDataset = new ArrayListDataset();
    }

    /**
     * If either Dataset is null, initializes both to contain cat files.
     */
    private void loadDatasetsIfNull() {
        if (llDataset == null || alDataset == null) {
            initializeDatasets();
            llDataset.addEachToBack(files);
            alDataset.addEachToBack(files);
        }
    }

    /**
     * Runs two functions and prints a message about their timing.
     *
     * @param initialize whether to initialize the Datasets
     * @param title      the title of the function being run
     * @param llRunnable the LinkedListDataset version of the function
     * @param alRunnable the ArrayListDataset version of the function
     */
    private void timeMethod(boolean initialize, String title, Runnable llRunnable, Runnable alRunnable) {
        if (initialize) {
            initializeDatasets();
        }
        System.out.println(title);
        printAverageTime(llRunnable, alRunnable);
        System.out.println();
    }

    /**
     * Times the addEachToFront method for LinkedListDataset and ArrayListDataset.
     * Prints the nanoseconds for each.
     */
    private void timeAddEachToFront() {
        timeMethod(
                true,
                "Add each to front",
                () -> llDataset.addEachToFront(files),
                () -> alDataset.addEachToFront(files)
        );
    }

    /**
     * Times the addEachToBack method for LinkedListDataset and ArrayListDataset.
     * Prints the nanoseconds for each.
     */
    private void timeAddEachToBack() {
        timeMethod(
                true,
                "Add each to back",
                () -> llDataset.addEachToBack(files),
                () -> alDataset.addEachToBack(files)
        );
    }

    /**
     * Times the getRandomMeow method for LinkedListDataset and ArrayListDataset.
     * Prints the nanoseconds for each.
     */
    private void timeAccessRandomElements() {
        loadDatasetsIfNull();
        timeMethod(
                false,
                "Access random elements",
                () -> getRandomMeows(RANDOM_MEOWS, llDataset, false),
                () -> getRandomMeows(RANDOM_MEOWS, alDataset, false)
        );
    }

    /**
     * Times the sort method for LinkedListDataset and ArrayListDataset.
     * Prints the nanoseconds for each.
     */
    private void timeSort() {
        loadDatasetsIfNull();
        timeMethod(
                false,
                "Sort",
                llDataset::sortDataset,
                alDataset::sortDataset
        );
    }

    /**
     * Randomly selects meows from given dataset, and optionally plays them.
     *
     * @param num      the number of random meows to select
     * @param dataset  the dataset from which to select them
     * @param playThem whether to play the selected audio clip meows
     */
    private void getRandomMeows(int num, Dataset dataset, boolean playThem) {
        for (int i = 0; i < num; i++) {
            Dataset.Meow meow = dataset.getRandomMeow();
            if (playThem) {
                meow.play();
            }
        }
    }

    /**
     * Prints the average number of nanoseconds used to run each passed function.
     *
     * @param linkedListFunc a function that uses a LinkedListDataset
     * @param arrayListFunc  a function that uses an ArrayListDataset
     */
    private void printAverageTime(Runnable linkedListFunc, Runnable arrayListFunc) {
        long linkedListTime = averageTime(TIMES, linkedListFunc);
        System.out.printf("Nanoseconds for LinkedListDataset: %d\n", linkedListTime);
        long arrayListTime = averageTime(TIMES, arrayListFunc);
        System.out.printf("Nanoseconds for ArrayListDataset:  %d", arrayListTime);
        System.out.printf(" (%d nanoseconds more)\n", arrayListTime - linkedListTime);
    }

    /**
     * @param times the number of times to run the function
     * @param func  a function with no parameters and a void return
     * @return the average number of nanoseconds it took to run the passed function,
     * after running it the passed number of times
     */
    private long averageTime(int times, Runnable func) {
        long totalTime = 0;
        for (int i = 0; i < times; i++) {
            totalTime += timeMethod(func);
        }
        return totalTime / times;
    }

    /**
     * @param func a function with no parameters and a void return
     * @return the number of nanoseconds it took to run the passed function
     */
    private long timeMethod(Runnable func) {
        long before = System.nanoTime();
        func.run();
        long after = System.nanoTime();
        return after - before;
    }
}