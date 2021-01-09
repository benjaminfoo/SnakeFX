package de.ostfalia.snakecore.util;

/**
 * @author Benjamin Wulfert
 * Prints a similiar banner to the console like spring boot in order to notify of an application start.
 * Useful when multiple clients are started within the same environment with lots of logging noise.
 */
public class BannerPrinter {

    public void printBanner(){

        System.out.println("   _____             __        _______  __");
        System.out.println("  / ___/____  ____ _/ /_____  / ____/ |/ /");
        System.out.println("  \\__ \\/ __ \\/ __ `/ //_/ _ \\/ /_   |   / ");
        System.out.println(" ___/ / / / / /_/ / ,< /  __/ __/  /   |  ");
        System.out.println("/____/_/ /_/\\__,_/_/|_|\\___/_/    /_/|_|  ");
        System.out.println(" - by Benjamin Wulfert -> Client has been started!");
    }

}
