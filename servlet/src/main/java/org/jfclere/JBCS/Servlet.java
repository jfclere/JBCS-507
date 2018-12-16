package org.jfclere.JBCS;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(description = "Test servlet for mod_cache", urlPatterns = { "/" })
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String payload = null;
	public void init() throws ServletException {
		StringBuilder load = new StringBuilder(1024);
		for (int i=0; i<128;i++) {
			load.append("ABCDEFG");
		}
		payload = load.toString();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = "mod_cache test servlet";
		response.setHeader("ETag","W/\"5581-1509871979000\"");
		/* set an Expires according to the parameters */
                String sdate = request.getParameter("date");
		if (sdate != null) {
                        Calendar cal = Calendar.getInstance();
                        String expires = "Invalid";
                        if (sdate.equals("+1")) {
				cal.add(Calendar.DATE, 1);
				SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz");
                                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                expires = sdf.format(cal.getTime());
			} else if (sdate.equals("-1")) {
				cal.add(Calendar.DATE, -1); 
				SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss zzz");
                                sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
                                expires = sdf.format(cal.getTime());
			}
			response.setHeader("TraceDateParm", sdate);

			response.setHeader("Expires", expires);
		} else
			response.setHeader("Expires", "Wed, 21 Oct 2018 07:28:00 GMT");
		Date date = new Date();
		response.setHeader("TraceDate", date.toString());
		Random rand = new Random();
		int n = rand.nextInt(32) + 1;
		response.setHeader("TraceSize", ""+n);

		PrintWriter out = response.getWriter();
		out.println("<title>" + title + "</title>");
		out.println("</head>");
		out.println("<body>");

		out.println("<h3>" + title + "</h3>");
		for (int j=0; j<n; j++) {
			out.print(payload);
		}

       		out.println("</body>");
        	out.println("</html>");
	}

	/**
  	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
         */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}

