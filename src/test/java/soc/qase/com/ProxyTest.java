package soc.qase.com;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import soc.qase.info.User;
import soc.qase.test.AbstractTest;
import soc.qase.test.server.QaseServer;

/**
 * Created by Vojtech.Smital on 18.2.2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProxyTest extends AbstractTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProxyTest.class);

    @Mock
    private CommunicationHandler communicationHandler;

    @InjectMocks
    private static Proxy proxy;

    public static void init() {
        final Thread thread = new Thread() {
            public void run() {
                proxy = new Proxy(createUser(), true);
                proxy.connect("127.0.0.1", QaseServer.getPort());
            }
        };
        thread.start();
    }

    @Ignore
    @Test
    public void testProcessIncomingDataPacket_challangeProcessing() {
        setPrivateFieldValue(proxy, "user", createUser());
        setPrivateFieldValue(proxy, "sentChallenge", true);

        final String hexString = "ffffffff6368616c6c656e67652036313933";
        proxy.processIncomingDataPacket(hexStringToByteArray(hexString));
    }

    @Ignore
    @Test
    public void testProcessIncomingDataPacket_clientConnectProcessing() {
        setPrivateFieldValue(proxy, "user", createUser());
        setPrivateFieldValue(proxy, "sentConnect", true);

        final String hexString = "ffffffff636c69656e745f636f6e6e656374";
        proxy.processIncomingDataPacket(hexStringToByteArray(hexString));
    }

    @Ignore
    @Test
    public void testProcessIncomingDataPacket_cmdConfigString() {
        final String hexString = "01000080010000800c220000002a000000000000005468652045646765000b636d6420636f6e666967737472696e677320343220300a00";
        proxy.processIncomingDataPacket(hexStringToByteArray(hexString));
    }

    @Ignore
    @Test
    public void testProcessIncomingDataPacket_serverSpawnBaseline() {
        final String hexString = "0e0000800e0000000e83ca84017638010002800700f4580e800700f4580e0e808a80097702b0ed00000000b0ed1f000e808a8009790330f80000000030f81f000e828280057c0006e01700000006e0172c0e838280057da0ff0012e017a0ff0012e0172c0e838280057e6017a002e01f6017a002e01f2c0e838280057fe02ea01e200de02ea01e200d2b0b70726563616368652034320a00";
        proxy.processIncomingDataPacket(hexStringToByteArray(hexString));
    }

    @Ignore
    @Test
    public void testProcessIncomingDataPacket_serverConfigString() {
        final String hexString = "10000080100000800d2005566f6a745c6d616c652f6772756e74000a02566f6a7420656e7465726564207468652067616d650a0014f1020000ffffffff00010211c2398030c012500b00000060000000005800000060000004010000000000005a43100000020064000500070012978a900b01ffff4000018030c012500b608030c012500b622080808001172028a013200d80808001288030c012000b80808001320028c012b80a8080800133c026c012b80a808080013400280014b80a80808001790000000030f8808080017fe02ea01e200d000001010009";
        proxy.processIncomingDataPacket(hexStringToByteArray(hexString));
        }

    @Ignore
    @Test
    public void testProcessIncomingDataPacket_precacheString() {
        final String hexString
                = "170000801700000016838b82793d016b00602ee0d36002602ee0d3600202000000803f0000803f0000803f0f0f200b0f040000803f05200002000116838b82793e016a00602e00da4001602e00da400102000000803f0000803f0000803f151f20141e010000803f05200002000116878b82793f016c00002840e1400600008743002840e1400602000000803f0000803f0000803f03341f03112a0000803f25200041003b0116878b827940016c000028c0d240060000b4420028c0d2400602000000803f0000803f0000803f03111f03342a0000803f25200041003b0116878b827941016c00402f00da400600003443402f00da400602000000803f0000803f0000803f34031f11032a0000803f25200041003b0116838b827942017100000900dd800e000900dd800e02000000803f0000803f0000803f19181019192d0000803f05200002000116839b885143017500002028080900dde010080900dde0100000803f0000803f0000803f0000803f16879b885144017500002028080900dde0100000b442080900dde0100000803f0000803f0000803f0000803f16838b827945017100000900d7800e000900d7800e02000000803f0000803f0000803f19181019192d0000803f05200002000116839b885146017500002028080900d7e010080900d7e0100000803f0000803f0000803f0000803f16879b885147017500002028080900d7e0100000b442080900d7e0100000803f0000803f0000803f0000803f16838b82794801710000090006800e00090006800e02000000803f0000803f0000803f19181019192d0000803f05200002000116839b88514901750000202808090006e01008090006e0100000803f0000803f0000803f0000803f16879b88514a01750000202808090006e0100000b44208090006e0100000803f0000803f0000803f0000803f16838b82794b0171000009000c800e0009000c800e02000000803f0000803f0000803f19181019192d0000803f05200002000116839b88514c0175000020280809000ce0100809000ce0100000803f0000803f0000803f0000803f16879b88514d0175000020280809000ce0100000b4420809000ce0100000803f0000803f0000803f0000803f137072656361636865203b20636d6420626567696e2031383436380a00";
        proxy.processIncomingDataPacket(hexStringToByteArray(hexString));
    }

    @Test
    public void testProcessIncomingDataPacket_serverFrame() {
        final String hexString = "500000009c010080141703000016030000000102118612c822011b010ba2ff000000000000580000000400000000129b8080010129c822011b00c822011b010b0000";
        proxy.processIncomingDataPacket(hexStringToByteArray(hexString));
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

    private static User createUser() {
        return new User("QASE_Vojt", "female/athena", 65536, 0, 90, User.HAND_RIGHT, null);
    }
}
