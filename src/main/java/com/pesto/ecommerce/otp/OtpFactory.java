package com.pesto.ecommerce.otp;

import com.pesto.ecommerce.enums.ContactType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
public class OtpFactory {

    private final Map<ContactType, OtpProcessor> processorMap = new EnumMap<>(ContactType.class);

    @Autowired
    public OtpFactory(List<OtpProcessor> processors) {
        for (OtpProcessor processor : processors) {
            processorMap.put(processor.getContactType(), processor);
        }
    }

    public OtpProcessor get(ContactType contactType) {
        return processorMap.get(contactType);
    }

}
