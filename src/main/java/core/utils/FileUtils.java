package core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Base64;

import core.exceptions.CustomException;

public class FileUtils {
						
	public static void saveBase64ImgToPngFile(String base64Img, String directoryPath, String logicalName) throws CustomException {
		
		try {
			File dir = new File(directoryPath);
			if(!dir.exists()) {
				dir.mkdirs();
			}
	        String safeName = logicalName
	                .replaceAll("[\\\\/:*?\"<>|]", "_")   // Windows forbidden chars
	                .replaceAll("\\s+", "_")
	                .replaceAll("_+", "_");
	        
	        int maxLen = 150;
	        if (safeName.length() > maxLen) {
	            safeName = safeName.substring(0, maxLen);
	        }
			
			File file = new File(dir, safeName+ ".png");
			
			byte[] imageBytes = Base64.getDecoder().decode(base64Img);
			
			try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(imageBytes);
            }
		}
		catch(Exception e) {
			LogUtils.logError(e.getMessage());
            throw new CustomException(String.format("Error while saving base64 to PNG file -> %s", e.getMessage()));
		}
		
		
	}

}
