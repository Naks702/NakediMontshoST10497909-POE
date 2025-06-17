/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

import com.mycompany.finalpoe.LoginsnReg;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author sugar
 */
public class NewEmptyJUnitTest {
    
    public NewEmptyJUnitTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    
    @AfterEach
    public void tearDown() {
    }

    private LoginsnReg loginSystem;
    
    @BeforeEach
    void setUp() {
        loginSystem = new LoginsnReg();
        // Clear any existing user data before each test
        LoginsnReg.userCredentials.clear();
    }
    
    @Test
    @DisplayName("Test valid username with underscore and 5 characters or less")
    void testValidUsername() {
        assertTrue(loginSystem.checkUserName("user_"));
        assertTrue(loginSystem.checkUserName("ab_cd"));
        assertTrue(loginSystem.checkUserName("a_"));
        assertTrue(loginSystem.checkUserName("test_"));
    }
    
    @Test
    @DisplayName("Test invalid username without underscore")
    void testInvalidUsernameNoUnderscore() {
        assertFalse(loginSystem.checkUserName("user"));
        assertFalse(loginSystem.checkUserName("test"));
        assertFalse(loginSystem.checkUserName("admin"));
    }
    
    @Test
    @DisplayName("Test invalid username with more than 5 characters")
    void testInvalidUsernameTooLong() {
        assertFalse(loginSystem.checkUserName("user_name"));
        assertFalse(loginSystem.checkUserName("test_user"));
        assertFalse(loginSystem.checkUserName("admin_123"));
    }
    
    @Test
    @DisplayName("Test valid password with all requirements")
    void testValidPassword() {
        assertTrue(loginSystem.checkPassword("Password123!"));
        assertTrue(loginSystem.checkPassword("MyPass@456"));
        assertTrue(loginSystem.checkPassword("Test#789Abc"));
        assertTrue(loginSystem.checkPassword("Valid$Pass1"));
    }
    
    @Test
    @DisplayName("Test invalid password missing uppercase")
    void testInvalidPasswordNoUppercase() {
        assertFalse(loginSystem.checkPassword("password123!"));
        assertFalse(loginSystem.checkPassword("mypass@456"));
    }
    
    @Test
    @DisplayName("Test invalid password missing lowercase")
    void testInvalidPasswordNoLowercase() {
        assertFalse(loginSystem.checkPassword("PASSWORD123!"));
        assertFalse(loginSystem.checkPassword("MYPASS@456"));
    }
    
    @Test
    @DisplayName("Test invalid password missing digit")
    void testInvalidPasswordNoDigit() {
        assertFalse(loginSystem.checkPassword("Password!"));
        assertFalse(loginSystem.checkPassword("MyPass@Test"));
    }
    
    @Test
    @DisplayName("Test invalid password missing special character")
    void testInvalidPasswordNoSpecialChar() {
        assertFalse(loginSystem.checkPassword("Password123"));
        assertFalse(loginSystem.checkPassword("MyPass456"));
    }
    
    @Test
    @DisplayName("Test invalid password too short")
    void testInvalidPasswordTooShort() {
        assertFalse(loginSystem.checkPassword("Pass1!"));
        assertFalse(loginSystem.checkPassword("Ab1@"));
    }
    
    @Test
    @DisplayName("Test invalid password too long")
    void testInvalidPasswordTooLong() {
        assertFalse(loginSystem.checkPassword("ThisPasswordIsTooLongAndShouldFail123!@#"));
    }
    
    @Test
    @DisplayName("Test valid South African cellphone number")
    void testValidCellphoneNumber() {
        assertTrue(loginSystem.checkCellPhoneNumber("+27123456789"));
        assertTrue(loginSystem.checkCellPhoneNumber("+27987654321"));
        assertTrue(loginSystem.checkCellPhoneNumber("+27111222333"));
    }
    
    @Test
    @DisplayName("Test invalid cellphone number without +27")
    void testInvalidCellphoneNoCountryCode() {
        assertFalse(loginSystem.checkCellPhoneNumber("123456789"));
        assertFalse(loginSystem.checkCellPhoneNumber("0123456789"));
    }
    
    @Test
    @DisplayName("Test invalid cellphone number wrong length")
    void testInvalidCellphoneWrongLength() {
        assertFalse(loginSystem.checkCellPhoneNumber("+2712345678")); // Too short
        assertFalse(loginSystem.checkCellPhoneNumber("+271234567890")); // Too long
    }
    
    @Test
    @DisplayName("Test invalid cellphone number with letters")
    void testInvalidCellphoneWithLetters() {
        assertFalse(loginSystem.checkCellPhoneNumber("+27abc456789"));
        assertFalse(loginSystem.checkCellPhoneNumber("+2712345678a"));
    }
    
  
    
 
    @Test
    @DisplayName("Test failed login with non-existent user")
    void testFailedLoginNonExistentUser() {
        assertFalse(loginSystem.loginUser("nonexistent", "Password123!"));
    }
    
   
 
    
    @Test
    @DisplayName("Test edge cases for username validation")
    void testUsernameEdgeCases() {
        // Empty string
        assertFalse(loginSystem.checkUserName(""));
        
        // Only underscore
        assertTrue(loginSystem.checkUserName("_"));
        
        // Exactly 5 characters with underscore
        assertTrue(loginSystem.checkUserName("test_"));
        
        // 6 characters with underscore (should fail)
        assertFalse(loginSystem.checkUserName("test_1"));
    }
    
    @Test
    @DisplayName("Test edge cases for password validation")
    void testPasswordEdgeCases() {
        // Exactly 8 characters (minimum)
        assertTrue(loginSystem.checkPassword("Pass123!"));
        
        // Exactly 20 characters (maximum)
        assertTrue(loginSystem.checkPassword("Pass123!Pass123!Pass"));
        
        // 21 characters (should fail)
        assertFalse(loginSystem.checkPassword("Pass123!Pass123!Pass1"));
    }
    
    
}
