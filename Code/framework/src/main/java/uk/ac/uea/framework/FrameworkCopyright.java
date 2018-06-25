package uk.ac.uea.framework;

/**
 * An abstract class designed to provide a generic copyright class that displays the message of the University. When extended, this class allows the user to add their own name to the copyright message to create a full copyright message including both the creator(s) and the UEA.
 * Created by Jack L. Clements
 */
public abstract class FrameworkCopyright {
    /** {@link String} field containing string detailing University information. Should not change.*/
    private final String text = "This application is based on the Simple Android Application Framework. (c) University\n" +
            "of East Anglia 2015.";

    /**
     * Method to return the full copyright string of the UEA plus the specific user who wrote the subclass
     * @return the text variable set as final within the framework plus the implemented method of whomever wrote the specific subclass
     */
    public final String getCopyright(){
        return text + "\n\n" + getAppCopyright();
    }

    /**
     * Abstract method, intent of which is to return the specific user who wrote the implementation of the subclass.
     * @return should return a string displaying the user who wrote the implementation
     */
    protected abstract String getAppCopyright();
}
