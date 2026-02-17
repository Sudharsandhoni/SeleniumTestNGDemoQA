package core.helper.download;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import core.constants.TestGlobals;

public class DownloadUtils {

	public String createUniqueDownloadDir(String testName) {
//		String path = System.getProperty("user.dir") + "/downloads/" + testName + "_" + System.currentTimeMillis();
//		new File(path).mkdirs();
//		return path;
		Path rootDir = TestGlobals.getDownloadsRootDir();

		Path downloadDir = rootDir.resolve(
	    		testName + "_" + System.currentTimeMillis()
	    ).toAbsolutePath();
		try {
			Files.createDirectories(downloadDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return downloadDir.toString();
	}

	public File waitForDownload(String dir, String fileName, int timeoutSec) {
		Path filePath = Paths.get(dir, fileName);
		long endTime = System.currentTimeMillis() + timeoutSec * 1000;

		while (System.currentTimeMillis() < endTime) {
			if (Files.exists(filePath)) {
				try {
					long size1 = Files.size(filePath);
					Thread.sleep(500);
					long size2 = Files.size(filePath);

					if (size1 == size2 && size1 > 0) {
						return filePath.toFile();
					}
				} catch (Exception ignored) {
				}
			}
		}
		throw new RuntimeException("File not downloaded within timeout");
	}

}
