package com.webApp.videoContent;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import org.apache.jasper.tagplugins.jstl.core.Set;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;


@WebServlet("/uploadvideo")
@MultipartConfig
public class UploadVideo extends HttpServlet{
	
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
    
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
	//taking txt data
	String title = request.getParameter("title");
	String disc = request.getParameter("description");
	String skills = request.getParameter("skills").toUpperCase();
	boolean free = Boolean.parseBoolean(request.getParameter("free"));
	
	HttpSession session = request.getSession();
	
	//posting user id
	int uId =  Integer.parseInt((String) session.getAttribute("id"));
	
	
		
	RequestDispatcher dispatcher = request.getRequestDispatcher("uploadVideoContent.jsp");
		
	String videoPath = request.getServletContext().getRealPath(File.separator +"videos");
	System.out.println(videoPath);
	
	Collection<Part> Parts = request.getParts();
	
	//store files
	String videoFileName = uploadFiles(Parts, videoPath, "video/");
	String thumbnailFileName = uploadFiles(Parts, videoPath, "image/");
	
	if (videoFileName != null && !videoFileName.isEmpty() && thumbnailFileName != null && !thumbnailFileName.isEmpty()) {
		
		System.out.println(videoFileName);
		System.out.println(thumbnailFileName);
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Integer isUpdate = 0;	
		
		try (Connection connection = DriverManager.getConnection(DB_URL,DB_USER,DB_PASSWORD)){
			
			String sql = "INSERT INTO video_content (id, title, description, skill, post_date, video_url, thumbnail_url, likes, dislikes, views, blocked, free, user_id) "
                + "VALUES (video_content_sequence.NEXTVAL, ?, ?, ?, CURRENT_TIMESTAMP, ?, ?, 0, 0, 0, 0, ?, ?)";
			
					
			 try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                 preparedStatement.setString(1, title);
                 preparedStatement.setString(2, disc);
                 preparedStatement.setString(3, skills);
                // preparedStatement.setDate(4, new java.sql.Date(new Date().getTime()));
                 preparedStatement.setString(4, videoFileName);
                 preparedStatement.setString(5, thumbnailFileName);
                 preparedStatement.setBoolean(6, free);
                 preparedStatement.setInt(7, uId);
                isUpdate = preparedStatement.executeUpdate();
             }
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (isUpdate>0) {
			request.setAttribute("Status", "ok");
		dispatcher.forward(request, response);
		}
		else {
			request.setAttribute("Status", "fail");
			dispatcher.forward(request, response);
		}
		
	}
	else {
		request.setAttribute("Status", "fail");
		dispatcher.forward(request, response);
	}
	
	
	}
	
	
	
	
	private String uploadFiles(Collection<Part> parts, String realPath, String fileType) {
		
		for (Part part : parts) {
			
	        if (part.getContentType() != null && part.getContentType().startsWith(fileType)) {
	            String fileName = getFileName(part);
	            if (fileName != null && !fileName.isEmpty()) {
	                String uniqueFileName = generateUniqueFileName(fileName);
	                String uploadUrl = realPath + File.separator + uniqueFileName;

	                File uploadDirectory = new File(uploadUrl);
	                if (!uploadDirectory.exists() && uploadDirectory.mkdirs()) {
	                   
	                } else {
	                    
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

    private String generateUniqueFileName(String originalFileName) {
    	
    	String uniqueId = UUID.randomUUID() + originalFileName;
        return uniqueId;
    }

}
