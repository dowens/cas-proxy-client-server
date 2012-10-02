package com.camptocamp.security;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.cas.client.util.CommonUtils;
import org.springframework.security.cas.authentication.CasAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/*")
public class Proxy {
	protected static final Log logger = LogFactory.getLog(Proxy.class
			.getPackage().getName());
	protected static final Log statsLogger = LogFactory.getLog(Proxy.class
			.getPackage().getName() + ".statistics");

	@RequestMapping(value = "/p", method = { GET, POST })
	public void p(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		final CasAuthenticationToken token = (CasAuthenticationToken) request
				.getUserPrincipal();
		String targetUrl = "http://localhost:8080/geonetwork/srv/eng/admin";
		final String proxyTicket = token.getAssertion().getPrincipal()
				.getProxyTicketFor(targetUrl );
		final String serviceUrl = targetUrl + "?ticket="
				+ URLEncoder.encode(proxyTicket, "UTF-8");
		String proxyResponse = CommonUtils.getResponseFromServer(serviceUrl,
				"UTF-8");
		response.getWriter().write("Hi :) '"+proxyResponse+"'");
	}

	@RequestMapping(value = "/loggedout", method = { GET, POST })
	public void loggedout(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().write("bye :(");
	}
}
