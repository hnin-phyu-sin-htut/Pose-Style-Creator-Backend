package com.example.demo.builder;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import org.springframework.web.multipart.MultipartFile;

public class StabilityMultipartBuilder {
	
	public static byte[] build(
            String boundary,
            MultipartFile image,
            String prompt
    ) throws Exception {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        // prompt
        bos.write(("--" + boundary + "\r\n").getBytes());
        bos.write("Content-Disposition: form-data; name=\"prompt\"\r\n\r\n".getBytes());
        bos.write(prompt.getBytes(StandardCharsets.UTF_8));
        bos.write("\r\n".getBytes());

        // image
        bos.write(("--" + boundary + "\r\n").getBytes());
        bos.write(("Content-Disposition: form-data; name=\"image\"; filename=\"" +
                image.getOriginalFilename() + "\"\r\n").getBytes());
        bos.write(("Content-Type: " + image.getContentType() + "\r\n\r\n").getBytes());
        bos.write(image.getBytes());
        bos.write("\r\n".getBytes());

        // finish
        bos.write(("--" + boundary + "--\r\n").getBytes());

        return bos.toByteArray();
    }

}
