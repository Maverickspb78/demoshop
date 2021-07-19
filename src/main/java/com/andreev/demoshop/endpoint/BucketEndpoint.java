package com.andreev.demoshop.endpoint;

import com.andreev.demoshop.dto.BucketDTO;
import com.andreev.demoshop.dto.BucketDetailDTO;
import com.andreev.demoshop.service.BucketService;
import com.andreev.demoshop.ws.bucket.BucketWS;
import com.andreev.demoshop.ws.bucket.GetBucketRequest;
import com.andreev.demoshop.ws.bucket.GetBucketResponse;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.security.Principal;


public class BucketEndpoint {
    public static final String NAMESPACE_URL = "http://andreev.com/demoshop/ws/bucket";

    private final BucketService bucketService;

    public BucketEndpoint(BucketService bucketService) {
        this.bucketService = bucketService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getBucketRequest")
    @ResponsePayload
    public GetBucketResponse getBucketWS(@RequestPayload GetBucketRequest request, Principal principal) {
        GetBucketResponse response = new GetBucketResponse();
        bucketService.getBucketByUser(principal.getName()).getBucketDetails()
                .forEach(dto -> response.getBucket().add(createBucketWs(dto)));
        return response;
    }

    private BucketWS createBucketWs(BucketDetailDTO dto) {
        BucketWS bucketWS = new BucketWS();
        bucketWS.setAmount(dto.getAmount());
        bucketWS.setSum(dto.getSum());
        return bucketWS;
    }
}
