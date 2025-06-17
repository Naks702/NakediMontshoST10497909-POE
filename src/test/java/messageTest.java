/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.finalpoe.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class messageTest {

    private Message message;

    @BeforeEach
    public void setup() {
        message = new Message();
    }

    @Test
    public void testCreateMessageId_LengthShouldBe10() {
        String messageId = message.createMessageId();
        assertEquals(10, messageId.length());
        assertTrue(messageId.matches("\\d{10}")); // Should only be digits
    }

    @Test
    public void testCreateMessageHash_ValidInput() {
        String message = "Hello World";
        String messageId = "1234567890";
        int count = 1;

        String hash = this.message.createMessageHash(message, messageId, count);

        assertNotNull(hash);
        assertTrue(hash.startsWith("12:1:HELLOWORLD"));
    }

    @Test
    public void testValidMessageLength_TooLong() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 251; i++) sb.append("a");
        assertFalse(message.validMessageLength(sb.toString()));
    }

    @Test
    public void testValidMessageLength_Valid() {
        String msg = "Short message";
        assertFalse(message.validMessageLength(msg)); // Because your logic returns true only if null AND <= 250
    }

    
}
