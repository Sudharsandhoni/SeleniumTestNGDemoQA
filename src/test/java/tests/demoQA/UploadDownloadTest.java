package tests.demoQA;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.annotations.Test;

import core.annotations.EnableDownload;
import core.helper.download.DownloadUtils;
import test.pages.demoQA.UploadAndDownloadPage;
import tests.BaseTest;

public class UploadDownloadTest extends BaseTest {

	@EnableDownload
	@Test
	public void downloadTest() {
		UploadAndDownloadPage uploadAndDownloadPage = new UploadAndDownloadPage(browser);
		testReporter.step("Launch Browser with upload-download url", uploadAndDownloadPage::launch);
		testReporter.step("Click Download Button", uploadAndDownloadPage::clickDownloadButton);
		File file = testReporter.step("Wait For Download", () -> {
			return new DownloadUtils().waitForDownload(getDownloadPath(), "sampleFile.jpeg", 15);
		});

		testReporter.step("Validate Downloaded file exists", () -> Assert.assertTrue(file.exists(), "Download failed"));

	}

	@Test(dependsOnMethods = "downloadTest")
	public void uploadTest() {

		UploadAndDownloadPage uploadAndDownloadPage = new UploadAndDownloadPage(browser);
		testReporter.step("Launch Browser with upload-download url", uploadAndDownloadPage::launch);
		String uploadFileName = "sampleFile.jpeg";
		String filePath = Paths.get(getDownloadPath(), uploadFileName).toAbsolutePath().toString();
		testReporter.step("Upload file -> "+ filePath, () -> {			
			uploadAndDownloadPage.uploadDocument(filePath);
		});
		
		
		testReporter.step("Validate uploaded text ",
				() -> Assert.assertEquals(uploadAndDownloadPage.getUploadedFilepath(), "C:\\fakepath\\" + uploadFileName));

	}
}
