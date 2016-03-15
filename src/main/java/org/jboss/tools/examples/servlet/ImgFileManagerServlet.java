package org.jboss.tools.examples.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

@WebServlet(name = "ImgFileManagerServlet", urlPatterns = {"/upload"})
@MultipartConfig(
		location="/tmp", 
		fileSizeThreshold=1024*1024,
		maxFileSize=1024*1024*5, 
		maxRequestSize=1024*1024*5*5
)
public class ImgFileManagerServlet extends HttpServlet {
	
	private final static Logger LOGGER = 
			Logger.getLogger(ImgFileManagerServlet.class.getCanonicalName());
	
	private final String serverHome = System.getProperty("jboss.home.dir");
	private final static String IMG_UPLOAD_DIR = "/uploads/cposimg";
	private final static String CONTEXT_PATH = "/images";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request,
	        HttpServletResponse response)
	        throws ServletException, IOException {
		
	    response.setContentType("text/html;charset=UTF-8");
  
	    final String imgPath = serverHome + IMG_UPLOAD_DIR;
	    final Part filePart = request.getPart("file");
	    final String fileName = getFileName(filePart);

	    OutputStream out = null;
	    InputStream filecontent = null;
	    final PrintWriter writer = response.getWriter();

	    try {
	        out = new FileOutputStream(new File(imgPath + File.separator + fileName));
	        filecontent = filePart.getInputStream();

	        int read = 0;
	        final byte[] bytes = new byte[32*1024];

	        while ((read = filecontent.read(bytes)) != -1) {
	            out.write(bytes, 0, read);
	        }
	        writer.println(CONTEXT_PATH + File.separator + fileName);
	        
	        LOGGER.log(Level.INFO, "File {0} being uploaded to {1}", 
	                new Object[]{fileName, imgPath});
	        
	    } catch (FileNotFoundException fne) {
	        writer.println("You either did not specify a file to upload or are "
	                + "trying to upload a file to a protected or nonexistent "
	                + "location.");
	        writer.println("<br/> ERROR: " + fne.getMessage());

	        LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {1}", fne.getMessage());
	    } finally {
	        if (out != null) {
	            out.close();
	        }
	        if (filecontent != null) {
	            filecontent.close();
	        }
	        if (writer != null) {
	            writer.close();
	        }
	    }
	}

//	@Override
//	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
//	      ServletContext cntx= getServletContext();
//	      // Get the absolute path of the image
//	      String filename = cntx.getRealPath("Images/button.png");
//	      // retrieve mimeType dynamically
//	      String mime = cntx.getMimeType(filename);
//	      if (mime == null) {
//	        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
//	        return;
//	      }
//
//	      resp.setContentType(mime);
//	      File file = new File(filename);
//	      resp.setContentLength((int)file.length());
//
//	      FileInputStream in = new FileInputStream(file);
//	      OutputStream out = resp.getOutputStream();
//
//	      // Copy the contents of the file to the output stream
//	       byte[] buf = new byte[1024];
//	       int count = 0;
//	       while ((count = in.read(buf)) >= 0) {
//	         out.write(buf, 0, count);
//	      }
//	    out.close();
//	    in.close();
//	}
	
	private String getFileName(final Part part) {
	    final String partHeader = part.getHeader("content-disposition");
	    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
}
