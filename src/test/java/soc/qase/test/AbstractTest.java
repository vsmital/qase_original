package soc.qase.test;

import com.sun.istack.internal.NotNull;
import junit.framework.TestCase;
import org.apache.commons.lang.reflect.FieldUtils;

/**
 * Created by Vojtech.Smital on 22.2.2016.
 */
public class AbstractTest extends TestCase {

    /**
     * Sets value of field (descripted by fieldName) on target instance/object.
     * @param target instance/object on which should be field value set.
     * @param fieldName name of field for which we want to set a value.
     * @param fieldValue value to be set.
     */
    public void setPrivateFieldValue(@NotNull Object target, @NotNull String fieldName, @NotNull Object fieldValue) {
        try {
            FieldUtils.writeField(target, fieldName, fieldValue, true);
        } catch (IllegalAccessException iae) {
            fail();
        }
    }
}
