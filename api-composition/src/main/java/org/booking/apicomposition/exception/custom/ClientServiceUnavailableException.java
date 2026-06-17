package org.booking.apicomposition.exception.custom;

import org.booking.apicomposition.exception.dto.ErrorCode;

public class ClientServiceUnavailableException extends BaseException {
    public ClientServiceUnavailableException(String serviceName, String paymentId, Throwable cause) {
        super("Failed to fetch " + serviceName + ":" + paymentId, ErrorCode.CLIENT_SERVICE_UNAVAILABLE, cause);
    }
}
