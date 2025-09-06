package com.emrednmz.controllers.impl;

import com.emrednmz.controllers.IRestPaymentController;
import com.emrednmz.controllers.RestBaseController;
import com.emrednmz.controllers.RootEntity;
import com.emrednmz.dto.request.PaymentRequest;
import com.emrednmz.dto.response.DtoPayment;
import com.emrednmz.model.Payment;
import com.emrednmz.services.IPaymentService;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("payment")
public class RestPaymentControllerImpl extends RestBaseController implements IRestPaymentController {

    private final IPaymentService paymentService;

    public RestPaymentControllerImpl(IPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    @GetMapping("list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RootEntity<RestPageableEntity<DtoPayment>> getAllPayments(RestPageableRequest request) {
        Page<Payment> page = paymentService.findAllPayments(getPageable(request));
        List<DtoPayment> dtoPayments = paymentService.getDtoPayments(page.getContent());
        return ok(toRestPageableResponse(page, dtoPayments));
    }

    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RootEntity<DtoPayment> getPaymentById(@PathVariable Long id) {
        return ok(paymentService.getDtoById(id));
    }

    @Override
    @PostMapping("save")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    public RootEntity<DtoPayment> createPayment(@RequestBody @Valid PaymentRequest paymentRequest) {
        return ok(paymentService.createPayment(paymentRequest));
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RootEntity<DtoPayment> updatePayment(@RequestBody @Valid PaymentRequest paymentRequest, @PathVariable Long id) {
        return ok(paymentService.updatePayment(paymentRequest, id));
    }

    @Override
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    public void deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
    }
}
