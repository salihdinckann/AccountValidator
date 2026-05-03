package com.hku;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class AccountValidatorTest {
    private AccountValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AccountValidator();
        System.out.println("Test is starting...");
    }

    @AfterEach
    void tearDown() {
        System.out.println("Test is over.");
    }

    // TC01 Valid first name (EP)
    @Test
    void testFirstNameValid() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Salih", "Dinçkan", "salih.dinckan@std.hku.edu.tr",
                "30/10/2002", "Salih123!", "Salih123!");
        List<String> errors = validator.validate(req);
        assertFalse(errors.stream().anyMatch(e -> e.contains("First name")),
                "There should be no error in the valid name.");
    }

    // TC02 Empty first name (EP)
    @Test
    void testFirstNameEmpty() {
        CreateAccountRequest req = new CreateAccountRequest(
                "", "Dinçkan", "salih.dinckan@std.hku.edu.tr",
                "30/10/2002", "Salih123!", "Salih123!");
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("First name is required."));
    }

    // TC03 First name too long (BVA: 51 chars)
    @Test
    void testFirstNameTooLong() {
        String longName = "A".repeat(51);
        CreateAccountRequest req = new CreateAccountRequest(
                longName, "Dinçkan", "salih.dinckan@std.hku.edu.tr",
                "30/10/2002", "Salih123!", "Salih123!");
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("First name max 50 characters."));
    }

    // TC04 First name with digit (EP)
    @Test
    void testFirstNameWithDigit() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Salih3", "Dinçkan", "salih.dinckan@std.hku.edu.tr",
                "30/10/2002", "Salih123!", "Salih123!");
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("First name only letters and hyphens."));
    }

    // TC05 Valid last name (EP)
    @Test
    void testLastNameValid() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Faruk", "Esendemir", "faruk.esendemir@std.hku.edu.tr",
                "21/12/2003", "Faruk123!", "Faruk123!");
        List<String> errors = validator.validate(req);
        assertFalse(errors.stream().anyMatch(e -> e.contains("Last name")));
    }

    // TC06 Invalid email (EP)
    @Test
    void testEmailInvalidFormat() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Faruk", "Esendemir", "notanemail",
                "21/12/2003", "Faruk123!", "Faruk123!");
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("Invalid email format."));
    }

    // TC07 Email empty (EP)
    @Test
    void testEmailEmpty() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Faruk", "Esendemir", "",
                "21/12/2003", "Faruk123!", "Faruk123!");
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("Email is required."));
    }

    // TC08 DoB future date (EP)
    @Test
    void testDoBFuture() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Salih", "Dinçkan", "salih.dinckan@std.hku.edu.tr",
                "01/01/2030", "Salih123!", "Salih123!");
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("Date cannot be in the future."));
    }

    // TC09 DoB under 18 (BVA)
    @Test
    void testDoBUnder18() {
        // Bugünden 18 yıl öncesinin 1 gün sonrası → 18 yaşından 1 gün küçük
        LocalDate justUnder18 = LocalDate.now().minusYears(18).plusDays(1);
        String dob = justUnder18.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        CreateAccountRequest req = new CreateAccountRequest(
                "Salih", "Dinçkan", "salih.dinckan@std.hku.edu.tr",
                dob, "Salih123!", "Salih123!");
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("Must be at least 18 years old."));
    }

    // TC10 DoB exactly 18 (BVA)
    @Test
    void testDoBExactly18() {
        LocalDate exactly18 = LocalDate.now().minusYears(18);
        String dob = exactly18.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        CreateAccountRequest req = new CreateAccountRequest(
                "Salih", "Dinçkan", "salih.dinckan@std.hku.edu.tr",
                dob, "Salih123!", "Salih123!");
        List<String> errors = validator.validate(req);
        assertFalse(errors.stream().anyMatch(e -> e.contains("18")),
                "You must be at least 18 years old.");
    }

    // TC11 Password too short (BVA)
    @Test
    void testPasswordTooShort() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Faruk", "Esendemir", "faruk.esendemir@std.hku.edu.tr",
                "21/12/2003", "Faruk1!", "Faruk1!");
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("Password min 8 characters."));
    }

    // TC12 Password too long (BVA)
    @Test
    void testPasswordTooLong() {
        String pwd = "A1!" + "a".repeat(18); // 21 chars
        CreateAccountRequest req = new CreateAccountRequest(
                "Faruk", "Esendemir", "faruk.esendemir@std.hku.edu.tr",
                "21/12/2003", pwd, pwd);
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("Password max 20 characters."));
    }

    // TC13 Password missing uppercase (EP)
    @Test
    void testPasswordNoUppercase() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Faruk", "Esendemir", "faruk.esendemir@std.hku.edu.tr",
                "21/12/2003", "faruk123!", "faruk123!");
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("Password must include uppercase."));
    }

    // TC14 Valid password (EP)
    @Test
    void testPasswordValid() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Salih", "Dinçkan", "salih.dinckan@std.hku.edu.tr",
                "30/10/2002", "Salih123!", "Salih123!");
        List<String> errors = validator.validate(req);
        assertFalse(errors.stream().anyMatch(e -> e.contains("Password")));
    }

    // TC15 Confirm password mismatch (EP)
    @Test
    void testConfirmMismatch() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Salih", "Dinçkan", "salih.dinckan@std.hku.edu.tr",
                "30/10/2002", "Salih123!", "Salih123?");
        List<String> errors = validator.validate(req);
        assertTrue(errors.contains("Passwords do not match."));
    }

    // TC16 All fields valid (combination)
    @Test
    void testAllValid() {
        CreateAccountRequest req = new CreateAccountRequest(
                "Faruk", "Esendemir", "faruk.esendemir@std.hku.edu.tr",
                "21/12/2003", "Faruk123!", "Faruk123!");
        List<String> errors = validator.validate(req);
        assertTrue(errors.isEmpty(), "The record must be completely valid and error-free."  );
    }
}