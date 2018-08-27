package boot;

import controller.SokobanHQ;
import model.SokobanDataSource;
import view.CLI;

public class Run {

    public static void main(String[] args) {
        CLI screen = new CLI();
        SokobanDataSource datasource = new SokobanDataSource();
        SokobanHQ hq = new SokobanHQ(datasource, screen);

        screen.addObserver(hq);
        datasource.addObserver(hq);

        screen.launch();
    }

}
