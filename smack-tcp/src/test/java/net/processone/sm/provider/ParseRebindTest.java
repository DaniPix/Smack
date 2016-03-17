package net.processone.sm.provider;

import com.jamesmurty.utils.XMLBuilder;
import net.processone.sm.packet.Rebind;
import org.jivesoftware.smack.util.PacketParserUtils;
import java.util.Properties;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by jpcarlino on 17/03/16.
 */
public class ParseRebindTest {
    private static final Properties outputProperties = initOutputProperties();

    @Test
    public void testParseFailed() throws Exception {
        String failedStanza = XMLBuilder.create("failure")
                .a("xmlns", "p1:rebind")
                .asString(outputProperties);

        Rebind.Failure failurePacket = ParseRebind.failure(
                PacketParserUtils.getParserFor(failedStanza));

        assertThat(failurePacket, is(notNullValue()));
        assertTrue(failurePacket.getMessage().equals(""));
    }

    @Test
    public void testParseFailedMessage() throws Exception {
        String message = "An error message";
        String failedStanza = XMLBuilder.create("failure")
                .a("xmlns", "p1:rebind")
                .text(message)
                .asString(outputProperties);

        Rebind.Failure failurePacket = ParseRebind.failure(
                PacketParserUtils.getParserFor(failedStanza));

        assertThat(failurePacket, is(notNullValue()));
        assertTrue(failurePacket.getMessage().equals(message));
    }

    @Test
    public void testParseRebind() throws Exception {
        String resumedStanza = XMLBuilder.create("rebind")
                .a("xmlns", "p1:rebind")
                .asString(outputProperties);

        Rebind.SuccessRebind reboundPacket = ParseRebind.success(
                PacketParserUtils.getParserFor(resumedStanza));

        assertThat(reboundPacket, is(notNullValue()));
    }

    private static Properties initOutputProperties() {
        Properties properties = new Properties();
        properties.put(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "yes");
        return properties;
    }

}
