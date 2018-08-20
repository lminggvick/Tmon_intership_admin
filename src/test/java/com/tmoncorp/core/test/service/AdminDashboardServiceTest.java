package com.tmoncorp.core.test.service;

import com.tmoncorp.admin.exception.InvalidParameterException;
import com.tmoncorp.admin.model.mock.MockApi;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.security.cert.CollectionCertStoreParameters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdminDashboardServiceTest {
    public static final Logger logger = LoggerFactory.getLogger(AdminDashboardServiceTest.class);

    @Test
    public void sub() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator =factory.getValidator();
        MockApi api = new MockApi();
        api.setApiId(333);
        api.setApiName("na");
        api.setAdditionalDescription("asdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdasdaasdadasdsdasdsad");
        api.setIsUsedApi('F');

        if (!(api.getIsUsedApi() == 'T' || api.getIsUsedApi() == 'F')) {
            throw new InvalidParameterException("isUsedApi 는 T 또는 F 여야 합니다.");
        }

        Set<ConstraintViolation<MockApi>> constraintViolations = validator.validate(api);
        if (constraintViolations.size() > 0) {
            String errorMessage = constraintViolations.iterator().next().getMessage();
            logger.debug("\n\n --> error : " + errorMessage);
        }
    }

    @Test
    public void testFilter() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(5);
        list.add(1);
        list.add(4);
        list.add(3);
        IntStream.range(2, 5).forEach(i -> logger.debug(String.valueOf(i)));
    }
}
