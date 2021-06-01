package com.andreev.demoshop.service;

import com.andreev.demoshop.domain.Bucket;
import com.andreev.demoshop.domain.User;
import com.andreev.demoshop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);

    void addProducts(Bucket bucket, List<Long> productIds);

    BucketDTO getBucketByUser(String name);
}