package com.spin.controller;

import com.spin.constants.TransactionConstants;
import com.spin.model.TransactionsModel;
import com.spin.service.TransactionService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(TransactionConstants.API_BASE_PATH)
@Tag(name = TransactionConstants.API_TAG, description = TransactionConstants.API_DESCRIPTION)
public class TransactionsController {
    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transactions")
    @Operation(
            summary = TransactionConstants.GET_TRANSACTIONS_SUMMARY,
            description = TransactionConstants.GET_TRANSACTIONS_DESCRIPTION,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransactionsModel.class),
                    examples = @ExampleObject(value = TransactionConstants.TRANSACTION_REQUEST_EXAMPLE))))
        @ApiResponse(responseCode = "200", description = TransactionConstants.SUCCESS_RESPONSE,
            content = @Content(schema = @Schema(implementation = String.class)))
        @ApiResponse(responseCode = "400", description = TransactionConstants.BAD_REQUEST_RESPONSE)
        @ApiResponse(responseCode = "500", description = TransactionConstants.INTERNAL_SERVER_ERROR_RESPONSE)
    public ResponseEntity<String> requestTransactions(@Valid @RequestBody TransactionsModel transactionsModel) {
        return ResponseEntity.ok(transactionService.getTransactions());
    }
}
