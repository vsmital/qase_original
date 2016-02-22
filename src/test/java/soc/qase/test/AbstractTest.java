package soc.qase.test;

import com.sun.istack.internal.NotNull;
import junit.framework.TestCase;
import org.apache.commons.lang.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soc.qase.test.server.QaseServer;

/**
 * Created by Vojtech.Smital on 22.2.2016.
 */
public abstract class AbstractTest extends TestCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTest.class);

    private static Thread qaseServerThread = null;
    private static QaseServer qaseServer = null;

    //@BeforeClass
    public static void initQaseServer() {
        qaseServer = new QaseServer();
        qaseServerThread = new Thread(qaseServer);
        qaseServerThread.start();
    }

    /**
     * Sets value of field (descripted by fieldName) on target instance/object.
     * @param target instance/object on which should be field value set.
     * @param fieldName name of field for which we want to set a value.
     * @param fieldValue value to be set.
     */
    public static void setPrivateFieldValue(@NotNull Object target, @NotNull String fieldName, @NotNull Object fieldValue) {
        try {
            FieldUtils.writeField(target, fieldName, fieldValue, true);
        } catch (IllegalAccessException iae) {
            LOGGER.error("Unable to set " + fieldName + " private field value on object of instance " + target.getClass().getSimpleName(), iae);
            fail();
        }
    }

    /**
     * Converts input hexMessage to byte[] and sends via DatagramSocket
     * @param hexMessage message encoded in hexadicimal characters.
     */
    public void sendHexMessage(final @NotNull String hexMessage) {
        final byte[] data = hexStringToByteArray(hexMessage);
        qaseServer.sendData(data);
    }

    private byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
