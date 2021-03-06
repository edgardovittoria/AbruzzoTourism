package it.univaq.sose.main;

import javax.servlet.ServletConfig;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;

import org.apache.cxf.transport.servlet.CXFNonSpringServlet;

import it.univaq.sose.service.soap.HomeAndSearchServiceImpl;
import it.univaq.sose.service.soap.PrenotazioneServiceImpl;

public class SimpleCXFNonSpringServlet extends CXFNonSpringServlet {

	private static final long serialVersionUID = 7377087079023159361L;

	@Override
	public void loadBus(ServletConfig servletConfig) {
		super.loadBus(servletConfig);
		Bus bus = getBus();
		BusFactory.setDefaultBus(bus);
		Endpoint.publish("/HomeAndSearchService", new HomeAndSearchServiceImpl());
		Endpoint.publish("/PrenotazioneService", new PrenotazioneServiceImpl());

	}

}
