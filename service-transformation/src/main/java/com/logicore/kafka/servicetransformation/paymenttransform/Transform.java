package com.logicore.kafka.servicetransformation.paymenttransform;

import model.payment.PaymentMessage;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Transform {

    public PaymentMessage transformPaymentMessage(String jarName, PaymentMessage paymentMessage) throws IOException, InterruptedException, URISyntaxException;
}
