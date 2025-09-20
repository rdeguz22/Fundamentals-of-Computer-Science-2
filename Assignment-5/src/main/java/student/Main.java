package student;

import java.util.List;
import java.util.Scanner;

/**
 * A program that launches a graphical user interface selected by the user.
 */
public final class Main {
    private static final List<GuiLauncher<?>> LAUNCHERS = List.of(new MeowGuiLauncher());

    // prevent this class from being instantiated
    private Main() {
    }

    private static GuiLauncher<?> getUserChoice() {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        while (true) {
            System.out.println("Options:");
            for (int i = 0; i < LAUNCHERS.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, LAUNCHERS.get(i).getName());
            }
            System.out.println("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= LAUNCHERS.size()) {
                    return LAUNCHERS.get(choice - 1);
                } else {
                    System.out.println("Your choice was out of range.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    /**
     * Launches the GUI of the user's choice.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        GuiLauncher<?> launcher = getUserChoice();
        launcher.launchGui();
    }
}
