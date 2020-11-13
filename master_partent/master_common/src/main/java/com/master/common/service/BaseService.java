package com.master.common.service;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author wuxx
 * @version 1.0
 * @date 2020/11/13 17:18
 * @className BaseService
 * @description TODO
 */
public class BaseService<T> {
    protected Specification<T> getSpec(String companyId) {
        Specification<T> spec = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery query, CriteriaBuilder cb) {
                return cb.equal(root.get("companyId").as(String.class), companyId);
            }
        };
        return spec;
    }
}
