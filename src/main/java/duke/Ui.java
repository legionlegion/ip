package duke;

import java.util.Scanner;

/**
 * Manages the user interface for the Duke application. This class handles input
 * and output, displaying
 * greetings, errors, and other messages to the user.
 */
public class Ui {
    private static final String LINE = "\t____________________________________________________________\n";
    private String hello = Ui.LINE + "\tHello! I'm %s\n" + "\tWhat can I do for you?\n" + Ui.LINE;
    private String goodbye = "\tBye. Hope to see you again soon!\n" + Ui.LINE;

    private Storage storage = null;
    private TaskList taskList = null;

    /**
     * Manages the user interface for the Duke application. This class handles input
     * and output, displaying
     * greetings, errors, and other messages to the user.
     */
    Ui(String name, Storage storage, TaskList taskList) {
        this.storage = storage;
        this.taskList = taskList;
        this.hello = String.format(this.hello, name);
    }

    /**
     * Displays a message indicating an error loading tasks.
     */
    public void showLoadingError() {
        System.out.println("Error loading tasks!");
    }

    /**
     * Displays the welcome message to the user.
     */
    public void showWelcome() {
        System.out.println(this.hello);
    }

    /**
     * Displays the goodbye message to the user.
     */
    public void showGoodbye() {
        System.out.println(this.goodbye);
    }

    /**
     * Initiates the main application loop, handling user input and coordinating
     * responses.
     */
    public void start() {
        System.out.println(this.hello);
        this.storage.loadTasks();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String input = scanner.nextLine();
            System.out.print(Ui.LINE);

            Parser.ParsedCommand parsedCommand = Parser.parse(input);
            if (parsedCommand.getCommandType() == CommandType.INVALID) {
                Ui.printInvalid();
                continue;
            }
            int taskIndex = parsedCommand.getTaskNumber() - 1;
            switch (parsedCommand.getCommandType()) {
                case INVALID:
                    break;
                case BYE:
                    // handle BYE
                    System.out.println(this.goodbye);
                    scanner.close();
                    this.storage.saveTasks();
                    return;
                case LIST:
                    // handle LIST
                    this.taskList.list();
                    break;
                case MARK:
                    this.taskList.getTask(taskIndex).mark();
                    break;
                case UNMARK:
                    this.taskList.getTask(taskIndex).unmark();
                    break;
                case DELETE:
                    Task deletedTask = this.taskList.getTask(taskIndex);
                    this.taskList.deleteTask(taskIndex);
                    System.out.println("\tNoted. I've removed this task:\n\t" + deletedTask);
                    break;
                case EVENT:
                case TODO:
                case DEADLINE:
                    Task task = Parser.createTask(parsedCommand.getCommandType(), input);
                    if (task != null) {
                        this.taskList.addTask(task);
                        System.out.println("\tGot it. I've added this task:\n\t" + task);
                        System.out.println("\tNow you have " + TaskList.storageFill + " tasks in the list.");
                    }
                    break;
                default:
                    break;
            }

            System.out.println(Ui.LINE);
        }
    }

    /**
     * Prints a message indicating the user has entered an invalid command.
     */
    public static void printInvalid() {
        System.out.println("\tOOPS!!! That is not a valid command! "
                + "Try the following: \n"
                + "\ttodo xxx\n"
                + "\tdeadline xxx /by xxx\n"
                + "\tevent xxx /from xxx /to xxx");
    }
}
