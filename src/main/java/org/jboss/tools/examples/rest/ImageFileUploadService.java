package org.jboss.tools.examples.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

@Path("/imgs")
public class ImageFileUploadService {

	private final String IMG_UPLOAD_PATH = System.getProperty("jboss.home.dir") + "/uploads/cposimg";
	private final static String CONTEXT_PATH = "rest/imgs";

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(MultipartFormDataInput input) {

		String fileName = "";
		String filePath = IMG_UPLOAD_PATH + File.separator;
		Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
		List<InputPart> inputParts = uploadForm.get("file");
		if (inputParts == null) {
			throw new RestServiceException(
					Response.status(Response.Status.BAD_REQUEST).entity("No file specified").build());
		}
		
		for (InputPart inputPart : inputParts) {
			try {
				MultivaluedMap<String, String> header = inputPart.getHeaders();
				fileName = getFileName(header);

				// convert the uploaded file to inputstream
				InputStream inputStream = inputPart.getBody(InputStream.class, null);

				// constructs upload file path
				filePath += fileName;

				writeFile(inputStream, filePath);

				System.out.printf("File %s successfully uploaded to %s\n", fileName, filePath);
				
			} catch (IOException e) {
				System.err.printf("Error uploading %s to %s. Error: %s\n", fileName, filePath, e.getMessage());
				throw new RestServiceException(
						Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
			}
		}
		return Response.status(Status.OK).entity(CONTEXT_PATH + File.separator + fileName).build();
	}

	@GET
	@Path("{imgName}")
	@Produces("image/*")
	public Response getFile(@PathParam(value="imgName") String imgName) {
		final String filePath = IMG_UPLOAD_PATH + File.separator + imgName;
		try {
			String mimeType = Files.probeContentType(Paths.get(filePath));
			if (mimeType == null) {
				return Response.status(Response.Status.BAD_REQUEST).entity("Invalid file specified").build();
			}
			
			File file = new File(filePath);
			ResponseBuilder response = Response.ok((Object) file);
			response.header("Content-Disposition", "attachment; filename=" + imgName);
			return response.build();
			
		} catch (IOException e) {
			System.err.printf("Error processing file download %s. Error: %s\n", imgName, e.getMessage());
			throw new RestServiceException(Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build());
		}
	}
	
	/**
	 * header sample { Content-Type=[image/png], Content-Disposition=[form-data;
	 * name="file"; filename="filename.extension"] }
	 **/
	// get uploaded filename, is there a easy way in RESTEasy?
	private String getFileName(MultivaluedMap<String, String> header) {

		String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

		for (String filename : contentDisposition) {
			if ((filename.trim().startsWith("filename"))) {

				String[] name = filename.split("=");

				String finalFileName = name[1].trim().replaceAll("\"", "");
				return finalFileName;
			}
		}
		return "unknown";
	}

	// save to somewhere
	private void writeFile(InputStream input, String filename) throws IOException {

		File file = new File(filename);	
		if (!file.exists()) {
			file.createNewFile();
		}
		
	    OutputStream outStream = new FileOutputStream(file);
		
	    int read = 0;
	    byte[] buffer = new byte[32*1024];
	    
	    while ((read = input.read(buffer)) != -1) {
	    	outStream.write(buffer, 0, read);
        }
	    outStream.write(buffer);
	    outStream.flush();
	    outStream.close();
	}
}