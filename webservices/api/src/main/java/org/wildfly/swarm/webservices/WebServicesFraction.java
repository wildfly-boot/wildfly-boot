/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */

package org.wildfly.swarm.webservices;

import org.wildfly.swarm.config.Webservices;
import org.wildfly.swarm.config.webservices.ClientConfig;
import org.wildfly.swarm.config.webservices.EndpointConfig;
import org.wildfly.swarm.config.webservices.Handler;
import org.wildfly.swarm.config.webservices.PreHandlerChain;
import org.wildfly.swarm.container.Fraction;

public class WebServicesFraction extends Webservices<WebServicesFraction> implements Fraction {

    private static final String STANDARD_ENDPOINT_CONFIG = "Standard-Endpoint-Config";

    private static final String RECORDING = "Recording-Endpoint-Config";
    private static final String RECORDING_HANDLERS = "recording-handlers";
    private static final String SOAP_PROTOCOLS = "##SOAP11_HTTP ##SOAP11_HTTP_MTOM ##SOAP12_HTTP ##SOAP12_HTTP_MTOM";
    private static final String RECORDING_HANDLER = "RecordingHandler";
    private static final String RECORDING_HANDLER_CLASS = "org.jboss.ws.common.invocation.RecordingServerHandler";
    private static final String SOAP_HOST = "127.0.0.1";

    private static final String STANDARD_CLIENT_CONFIG = "Standard-Client-Config";

    private WebServicesFraction() {

    }

    public static WebServicesFraction createDefaultFraction() {
        return new WebServicesFraction().wsdlHost(SOAP_HOST)
                .endpointConfig(new EndpointConfig(STANDARD_ENDPOINT_CONFIG))
                .endpointConfig(createRemoteEndpoint())
                .clientConfig(new ClientConfig(STANDARD_CLIENT_CONFIG));
    }

    private static final EndpointConfig createRemoteEndpoint() {
        return new EndpointConfig(RECORDING)
                .preHandlerChain(new PreHandlerChain(RECORDING_HANDLERS)
                        .protocolBindings(SOAP_PROTOCOLS)
                .handler(new Handler(RECORDING_HANDLER).attributeClass(RECORDING_HANDLER_CLASS)));
    }
}
