package tests.demoQA;

import org.testng.annotations.Test;

import test.pages.demoQA.BrokenLinkAndImagesPage;
import test.pages.demoQA.HomePage;
import test.pages.demoQA.LinksPage;
import tests.BaseTest;

public class LinksTest extends BaseTest {

    @Test
    public void validateLinks() {
    		HomePage homePage = new HomePage(browser);
		testReporter.step("Launch HomePage", homePage::launch);
		testReporter.step("Click Elements Card", homePage::clickElementsCard);
		testReporter.step("Click Links Text/Link", homePage::clickLinksText);
        LinksPage linksPage = new LinksPage(browser);
        linksPage.waitForPageToLoad();
		


        //testReporter.step("Launch DemoQA Links page", linksPage::launch);

        // ---------- HOME LINKS ----------
        testReporter.step(
                "Home link should open DemoQA Home page in a new tab",
                linksPage::validateHomeLinkNavigation
        );

        testReporter.step(
                "Second Home link should open DemoQA Home page in a new tab",
                linksPage::validateHomeLink2Navigation
        );

        // ---------- API LINKS ----------
        linksPage.getApiLinks().forEach(link ->
                testReporter.step(
                        String.format(
                                "API link '%s' should return status %d with message '%s'",
                                link.name(), link.status(), link.statusText()
                        ),
                        () -> linksPage.validateApiLink(link)
                )
        );
    }
}