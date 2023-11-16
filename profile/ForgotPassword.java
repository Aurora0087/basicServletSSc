package com.webApp.profile;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Session;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Authenticator;
import javax.mail.Message;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/forgotPassword")
public class ForgotPassword extends HttpServlet {

	private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "aurora";
    private static final String DB_PASSWORD = "aurora";
	
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String email = request.getParameter("email");
		
		RequestDispatcher dispatcher = null;
		
		//
		if (!isUserExist(email)) {
			dispatcher=request.getRequestDispatcher("login.jsp");
			
			request.setAttribute("status", "faild");
			request.setAttribute("message", "User do'not exist.");
			
			dispatcher.forward(request, response);
		}
		else {
		
		int otpvalue = 0;
		
		HttpSession mySession = request.getSession();
		
		
		if(email!=null || !email.equals("")) {
			
			Random rand = new Random();
			otpvalue = rand.nextInt(1255650);

			String to = email;
			
			// Getting session object
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				
				
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("skillswap.k2.i@gmail.com", "xzhbhphqilzpbpad");
				}
			});
			// compose email message
			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(email));
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
				message.setSubject("OTP for Forgot Password");
				message.setText("your OTP is: " + otpvalue);
				System.out.println(otpvalue);
				// send message
				Transport.send(message);
			}

			catch (Exception e) {
				throw new RuntimeException(e);
			}
			
			dispatcher = request.getRequestDispatcher("EnterOtp.jsp");
			
			mySession.setAttribute("otp",otpvalue); 
			mySession.setAttribute("email",email); 
			request.setAttribute("message","OTP is sent to your email id");
			request.setAttribute("status", "success");
			
			dispatcher.forward(request, response);
			
		}
		else {
			dispatcher = request.getRequestDispatcher("forgotPassword.jsp");
			request.setAttribute("status", "success");
			request.setAttribute("message","Email is not given");
			
			dispatcher.forward(request, response);
		}
    }
		
	}
    
	private boolean isUserExist(String email) {
	    	
	    	try {
				Class.forName("oracle.jdbc.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	    	
	        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
	        	
	        	
	            String sql = "SELECT COUNT(*) FROM app_user WHERE email = ?";
	            
	            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
	                preparedStatement.setString(1, email);
	                
	                try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                	
	                    if (resultSet.next()) {
	                        int count = resultSet.getInt(1);
	                        return count != 0;
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false; 
	    }

}
