package student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class DatasetTest {

    private Dataset dataset;

    @BeforeEach
    public void setup() {
        dataset = new ArrayListDataset();
    }

    @Test
    public void addsEachToFrontAddsToFront() {
        File file1 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/B_ANI01_MC_FN_SIM01_101.wav");
        File file2 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/I_BLE01_EU_FN_DEL01_219.wav");
        File file3 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/I_SPI01_EU_MN_NAI01_102.wav");
        File[] files = {file1, file2, file3};
        dataset.addEachToFront(files);
        assertEquals(file3.getName(), dataset.data.get(0).audioFile.getName());
        assertEquals(file2.getName(), dataset.data.get(1).audioFile.getName());
        assertEquals(file1.getName(), dataset.data.get(2).audioFile.getName());
    }

    @Test
    public void addEachToBackAddsToBack() {
        File file1 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/B_ANI01_MC_FN_SIM01_101.wav");
        File file2 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/I_BLE01_EU_FN_DEL01_219.wav");
        File file3 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/I_SPI01_EU_MN_NAI01_102.wav");
        File[] files = {file1, file2, file3};
        dataset.addEachToBack(files);
        assertEquals(file1.getName(), dataset.data.get(0).audioFile.getName());
        assertEquals(file2.getName(), dataset.data.get(1).audioFile.getName());
        assertEquals(file3.getName(), dataset.data.get(2).audioFile.getName());
    }

    @Test
    public void getRandomMeowGetsMeow() {
        File file1 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/B_ANI01_MC_FN_SIM01_101.wav");
        dataset.addEachToBack(new File[]{file1});
        Dataset.Meow meow1 = dataset.getRandomMeow();
        assertNotNull(meow1);
        assertEquals(file1.getName(), meow1.audioFile.getName());
    }

    @Test
    public void getRandomMeowReturnsException() {
        assertThrows(IllegalStateException.class, () -> dataset.getRandomMeow());
    }

    @Test
    public void compareToCompares() {
        File file1 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/B_ANI01_MC_FN_SIM01_101.wav");
        File file2 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/I_BLE01_EU_FN_DEL01_219.wav");
        File file3 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/I_NUL01_MC_MI_SIM01_101.wav");
        Dataset.Meow meow1 = new Dataset.Meow(file1);
        Dataset.Meow meow2 = new Dataset.Meow(file2);
        Dataset.Meow meow3 = new Dataset.Meow(file3);
        assertTrue(meow1.compareTo(meow2) < 0);
        assertTrue(meow3.compareTo(meow2) > 0);
        assertEquals(0, meow1.compareTo(meow1));
    }

    @Test
    public void sortDatasetSorts() {
        File file1 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/B_ANI01_MC_FN_SIM01_101.wav");
        File file2 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/B_ANI01_MC_FN_SIM01_102.wav");
        File file3 = new File("/Users/ricardo.deguzman/IdeaProjects/homework-4-hw4-rico-doug/src/main/resources/dataset/I_BLE01_EU_FN_DEL01_219.wav");
        dataset.addEachToBack(new File[]{file1, file2, file3});
        dataset.sortDataset();
        assertEquals("ANI01", dataset.data.get(0).catID);
        assertEquals(101, dataset.data.get(0).recordingSessionVocalCounter);
        assertEquals("ANI01", dataset.data.get(1).catID);
        assertEquals(102, dataset.data.get(1).recordingSessionVocalCounter);
        assertEquals("BLE01", dataset.data.get(2).catID);
    }
}