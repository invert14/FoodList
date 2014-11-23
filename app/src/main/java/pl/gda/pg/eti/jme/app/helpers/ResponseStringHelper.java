package pl.gda.pg.eti.jme.app.helpers;

public class ResponseStringHelper {

    public static int getIntFromResponseString(String responseString) {
        responseString = responseString.replace("\n", "").replace("\r", "");
        Integer id;
        try {
            id = new Integer(responseString);
        } catch (NumberFormatException e) {
            return 0;
        }
        return id;
    }
}
