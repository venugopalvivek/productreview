package com.intuit.vivek.persistence.converters;

import com.intuit.vivek.persistence.model.ReviewState;

import javax.persistence.AttributeConverter;

/**
 * Created by vvenugopal on 10/21/17.
 */
public class ReviewStateConverter implements AttributeConverter<ReviewState, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ReviewState attribute) {
        return attribute.getValue();
    }

    @Override
    public ReviewState convertToEntityAttribute(Integer dbData) {
        return ReviewState.parse(dbData);
    }
}
