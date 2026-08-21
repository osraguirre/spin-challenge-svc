package com.spin.client;

import com.spin.dto.ProviderTransactionRequest;
import com.spin.dto.ProviderTransactionResponse;

public interface ProviderClient {
    ProviderTransactionResponse execute(ProviderTransactionRequest request);
}