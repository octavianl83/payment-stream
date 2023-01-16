package com.logicore.kafka.serviceflowprocessor.ruleengine;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import model.payment.PaymentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RuleenginePaymentMessageService {

	@Autowired
	ObjectMapper objectMapper;

	public PaymentMessage processPaymentMessage(PaymentMessage paymentMessage) throws Exception {
		log.debug("payment message: {}", paymentMessage);
		//get the stateful session

		if (paymentMessage.getMessageProcessStatus().getStatus().equals("Initiated")) {
			paymentMessage.getMessageProcessStatus().setStatus("InstructionReceived");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("InstructionReceived")) {
			paymentMessage.getMessageProcessStatus().setStatus("InstructionParsed");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("InstructionParsed")) {
			paymentMessage.getMessageProcessStatus().setStatus("InstructionValidated");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("InstructionValidated")) {
			paymentMessage.getMessageProcessStatus().setStatus("InstructionDupChecked");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("InstructionDupChecked")) {
			paymentMessage.getMessageProcessStatus().setStatus("TransactionIDGenerated");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("TransactionIDGenerated")) {
			paymentMessage.getMessageProcessStatus().setStatus("Pacs.008Identified");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("Pacs.008Identified")) {
			paymentMessage.getMessageProcessStatus().setStatus("Pacs.008Parsed");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("Pacs.008Parsed")) {
			paymentMessage.getMessageProcessStatus().setStatus("TransactionValidated");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("TransactionValidated")) {
			paymentMessage.getMessageProcessStatus().setStatus("TransactionDupChecked");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("TransactionDupChecked")) {
			paymentMessage.getMessageProcessStatus().setStatus("SanctionsTransform");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("SanctionsTransform")) {
			paymentMessage.getMessageProcessStatus().setStatus("SanctionsTransformed");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("SanctionsTransformed")) {
			paymentMessage.getMessageProcessStatus().setStatus("SanctionsRequestInitiated");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("SanctionsRequestInitiated")) {
			paymentMessage.getMessageProcessStatus().setStatus("AccountLookupRequestInitiated");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("AccountLookupRequestInitiated")) {
			paymentMessage.getMessageProcessStatus().setStatus("AccountLookupReceived");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("AccountLookupReceived")) {
			paymentMessage.getMessageProcessStatus().setStatus("FundsControlTransform");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("FundsControlTransform")) {
			paymentMessage.getMessageProcessStatus().setStatus("FundsControlTransformed");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("FundsControlTransformed")) {
			paymentMessage.getMessageProcessStatus().setStatus("FundsControlRequestInitiated");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("FundsControlRequestInitiated")) {
			paymentMessage.getMessageProcessStatus().setStatus("FundsControlReceived");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("FundsControlReceived")) {
			paymentMessage.getMessageProcessStatus().setStatus("RtpMopIdentified");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("RtpMopIdentified")) {
			paymentMessage.getMessageProcessStatus().setStatus("RTPAccountPostingRequestInitiated");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("RTPAccountPostingRequestInitiated")) {
			paymentMessage.getMessageProcessStatus().setStatus("RtpAccountPostingReceived");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("RtpAccountPostingReceived")) {
			paymentMessage.getMessageProcessStatus().setStatus("RTPTransmitTransform");
		} else if (paymentMessage.getMessageProcessStatus().getStatus().equals("RTPTransmitTransform")) {
			paymentMessage.getMessageProcessStatus().setStatus("RTPTransmitTransformed");
		}  else if (paymentMessage.getMessageProcessStatus().getStatus().equals("RTPTransmitTransformed")) {
			paymentMessage.getMessageProcessStatus().setStatus("RTPTransmitInitiated");
		} else {
			paymentMessage.getMessageProcessStatus().setRuleName("NOT AVIALABLE");
		}
		return paymentMessage;
	}


}
	
