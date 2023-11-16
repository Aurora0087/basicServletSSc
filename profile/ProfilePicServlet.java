package com.webApp.profile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.UUID;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;


@WebServlet("/uploadprofilepic")
@MultipartConfig
public class ProfilePicServlet extends HttpServlet {

	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
    
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		HttpSession session=request.getSession();
		
		String realPath = request.getServletContext().getRealPath(File.separator+"profileImage");
		System.out.println(realPath);
		
		Collection<Part> Parts = request.getParts();
		
		String profilePicName = uploadFiles(Parts, realPath);
		
		Integer uId = Integer.valueOf((String) session.getAttribute("id"));
		
		if (profilePicName == null || profilePicName.isEmpty()) {
			
		}
		else {
			try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			
			String sql = "UPDATE app_user SET profile_image = ? WHERE id = ?";
			
			try(Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
		       		 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
		       	            
							preparedStatement.setString(1, profilePicName);
		       	            preparedStatement.setInt(2, uId);
		       	           Integer isUpdateInteger = preparedStatement.executeUpdate();
		       	           
		       	           if (isUpdateInteger>0) {
		       	        	   
		       	        	   
		   				}
		       	           else {
		       	        	   
		   				}
		       	 } catch (SQLException e) {
		   			e.printStackTrace();
		   		}
		}
	}
	
	//for uploading files
	private String uploadFiles(Collection<Part> parts, String realPath) {
		
		for (Part part : parts) {
			
	        if (part.getContentType() != null && part.getContentType().startsWith("image/")) {
	            String fileName = getFileName(part);
	            if (fileName != null && !fileName.isEmpty()) {
	                String uniqueFileName = generateUniqueFileName(fileName);
	                String uploadUrl = realPath + File.separator + uniqueFileName;

	                File uploadDirectory = new File(uploadUrl);
	                if (!uploadDirectory.exists() && uploadDirectory.mkdirs()) {
	                    // Directory created successfully
	                } else {
	                    // Handle directory creation failure
	                }

	                try {
	                    part.write(uploadUrl);
	                    return uniqueFileName;
	                } catch (IOException e) {
	                    // Handle file write exception
	                    e.printStackTrace();
	                }
	            }
	        }
	    }
	    return null;
	}
	
	
	//for getting original file name
	private String getFileName(Part part) {
		
        String contentDisposition = part.getHeader("content-disposition");
        
        String[] tokens = contentDisposition.split(";");
        
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
            	
                return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

	//for unique name 
    private String generateUniqueFileName(String originalFileName) {
    	
    	String uniqueId = UUID.randomUUID() + originalFileName;
        return uniqueId;
    }
	
}
