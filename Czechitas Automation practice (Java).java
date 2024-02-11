package cz.czechitas.automation;

import org.junit.jupiter.api.Test;

public class HomeworkTest extends TestRunner {

    @Test
    void homeworkTest() {
        //Parent login
        browser.loginSection.clickLoginMenuLink();
        browser.loginSection.insertEmail("pernikovachaloupka@blablaland.com");
        browser.loginSection.insertPassword("Mnaumnau3");
        browser.loginSection.clickLoginButton();

        //Random name generation, verification that it's unique in the list of applications
        browser.headerMenu.goToApplicationsSection();
        String randomName = StringUtils.capitalize(browser.generateRandomName(10));
        browser.applicationSection.search(randomName);
        asserter.applicationSection.checkApplicationsTableIsEmpty();

        //New application creation
        browser.applicationSection.clickCreateNewApplicationButton();
        browser.applicationSection.selectProgrammingSection();
        browser.applicationSection.clickCreateApplicationButton();
        browser.applicationDetailsSection.insertStudentFirstName("Bubla");
        browser.applicationDetailsSection.insertStudentLastName(randomName);
        browser.applicationDetailsSection.selectTerm("20.12. - 30.12.2023");
        browser.applicationDetailsSection.insertBirthdate("12.12.2012");
        browser.applicationDetailsSection.selectCashPaymentMethod();
        browser.applicationDetailsSection.insertNote("Alergický na papír.");
        browser.applicationDetailsSection.clickAcceptTermsCheckbox();
        browser.applicationDetailsSection.clickCreateApplicationButton();

        //Check there's one application under the random name in the list
        browser.headerMenu.goToApplicationsSection();
        browser.applicationSection.search(randomName);
        asserter.applicationSection.checkNumberOfApplications(1);

        //Edit payment method
        browser.applicationSection.clickEditFirstApplicationButton();
        browser.applicationDetailsSection.selectBankTransferPaymentMethod();
        browser.applicationDetailsSection.clickEditApplicationButton();

        //Verify the change of the payment method
        browser.headerMenu.goToApplicationsSection();
        browser.applicationSection.search(randomName);
        browser.applicationSection.openFirstApplicationDetailsPage();
        asserter.applicationDetailAction.checkPaymentMethod("Bankovní převod");

        //Logout
        browser.loginSection.logout();
    }
}
