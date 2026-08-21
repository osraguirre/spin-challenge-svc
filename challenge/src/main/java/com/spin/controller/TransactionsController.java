package com.spin.controller;

import com.spin.constants.Constants;
import com.spin.dto.TransactionResponse;
import com.spin.model.TransactionsModel;
import com.spin.service.TransactionService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("${transactions.api.path}")
@Tag(name = Constants.API_TAG, description = Constants.API_DESCRIPTION)
public class TransactionsController {
    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping()
    @Operation(
            summary = Constants.EXECUTE_TRANSACTION_SUMMARY,
            description = Constants.EXECUTE_TRANSACTION_DESCRIPTION,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                required = true,
                content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransactionsModel.class),
                    examples = @ExampleObject(value = Constants.TRANSACTION_REQUEST_EXAMPLE))))
        @ApiResponse(responseCode = "200", description = Constants.SUCCESS_RESPONSE,
            content = @Content(schema = @Schema(implementation = TransactionResponse.class)))
        @ApiResponse(responseCode = "400", description = Constants.BAD_REQUEST_RESPONSE)
        @ApiResponse(responseCode = "500", description = Constants.INTERNAL_SERVER_ERROR_RESPONSE)
    public ResponseEntity<TransactionResponse> executeTransaction(@Valid @RequestBody TransactionsModel transactionsModel) {
        return ResponseEntity.ok(transactionService.executeTransaction(transactionsModel));
    }

    @GetMapping()
    @Operation(
            summary = Constants.GET_TRANSACTIONS_SUMMARY,
            description = Constants.GET_TRANSACTIONS_DESCRIPTION)
    @ApiResponse(responseCode = "200", description = Constants.GET_TRANSACTIONS_SUCCESS_RESPONSE,
        content = @Content(array = @ArraySchema(schema = @Schema(implementation = TransactionResponse.class))))
    @ApiResponse(responseCode = "500", description = Constants.INTERNAL_SERVER_ERROR_RESPONSE)
    public ResponseEntity<List<TransactionResponse>> findTransactions(
            @Parameter(description = Constants.ACCOUNT_ID_FILTER_DESCRIPTION, example = Constants.ACCOUNT_ID_EXAMPLE)
            @RequestParam(required = false) String accountId,
            @Parameter(description = Constants.STATUS_FILTER_DESCRIPTION, example = Constants.STATUS_EXAMPLE)
            @RequestParam(required = false) String status,
            @Parameter(description = Constants.TYPE_FILTER_DESCRIPTION, example = Constants.TYPE_EXAMPLE_CREDIT)
            @RequestParam(required = false) String type,
            @Parameter(description = Constants.PAGE_DESCRIPTION)
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = Constants.LIMIT_DESCRIPTION)
            @RequestParam(defaultValue = "50") int limit) {
        int safeLimit = Math.clamp(limit, 1, 100);
        int safePage = Math.clamp(page, 0, Integer.MAX_VALUE / safeLimit);
        return ResponseEntity.ok(transactionService.findTransactions(
                accountId, status, type, safeLimit, safePage * safeLimit));
    }
}
