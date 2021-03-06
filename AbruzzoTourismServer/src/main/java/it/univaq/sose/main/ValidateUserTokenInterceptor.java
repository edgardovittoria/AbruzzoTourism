package it.univaq.sose.main;

import java.util.List;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.apache.wss4j.dom.handler.WSHandlerResult;
import org.apache.wss4j.common.principal.WSUsernameTokenPrincipalImpl;
import org.apache.wss4j.dom.WSSecurityEngineResult;

;

@SuppressWarnings("rawtypes")
public class ValidateUserTokenInterceptor extends AbstractPhaseInterceptor {

	public ValidateUserTokenInterceptor(String s) {
		super(s);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void handleMessage(Message message) throws Fault {
		boolean userTokenValidated = false;
		List<Object> result = (List<Object>) message.getContextualProperty(WSHandlerConstants.RECV_RESULTS);
		for (int i = 0; i < result.size(); i++) {
			WSHandlerResult res = (WSHandlerResult) result.get(i);
			for (int j = 0; j < res.getResults().size(); j++) {
				WSSecurityEngineResult secRes = (WSSecurityEngineResult) res.getResults().get(j);
				WSUsernameTokenPrincipalImpl principal = (WSUsernameTokenPrincipalImpl) secRes.get("principal");

				if (// !principal.isPasswordDigest() ||
				principal.getNonce() == null || principal.getPassword() == null || principal.getCreatedTime() == null) {
					throw new RuntimeException("Invalid Security Header");
				} else {
					userTokenValidated = true;
				}
			}
		}

		if (!userTokenValidated) {
			throw new RuntimeException("Security processing failed");
		}
	}

}
