package boot;

import view.CLI;
import view.View;

public class Run {

    public static void main(String[] args) {
        View view = new CLI();
        view.launch();
    }

}
