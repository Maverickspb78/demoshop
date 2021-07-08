package com.andreev.demoshop.service;

import com.andreev.demoshop.domain.Bucket;
import com.andreev.demoshop.domain.User;
import com.andreev.demoshop.dto.BucketDTO;

import java.util.List;

public interface BucketService {
    Bucket createBucket(User user, List<Long> productIds);

    void addProducts(Bucket bucket, List<Long> productIds);

    BucketDTO getBucketByUser(String name);

    void commitBucketToOrder(String username);

    void removeFromBucket(Bucket bucket, Long id);

    Bucket getBucket(String name);

    void removeFromBucketS(Long id, String userName);

    void removeAllIdFromBucket(Bucket bucket, Long id);
}