package com.andreev.demoshop.controllers;

import com.andreev.demoshop.dto.BucketDTO;
import com.andreev.demoshop.dto.ProductDTO;
import com.andreev.demoshop.service.BucketService;
import com.andreev.demoshop.service.ProductService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.stream.IntStream;

@Controller
public class BucketController {

    private final BucketService bucketService;
    private final ProductService productService;

    public BucketController(BucketService bucketService, ProductService productService) {
        this.bucketService = bucketService;
        this.productService = productService;
    }

    @GetMapping("/bucket")
    public String aboutBucket(Model model, Principal principal){
        if (principal == null){
            model.addAttribute("bucket", new BucketDTO());
        } else {
            BucketDTO bucketDto = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDto);
        }

        return "bucket";
    }

    @PostMapping("/bucket")
    public String commitBucket(Principal principal, @RequestParam("address") String address){
        if(principal != null){
            bucketService.commitBucketToOrder(principal.getName(), address);
        }
        return "redirect:/bucket";
        }

    @GetMapping("/bucket/delete/{id}") // remove from bucket 1 count product from id
    public String removeFromBucket(@PathVariable(value = "id") Long id, Principal principal) {
        for (int i = 0; i < bucketService.getBucketByUser(principal.getName()).getBucketDetails().size(); i++) {
            if (bucketService.getBucketByUser(principal.getName()).getBucketDetails().get(i).getProductId().equals(id)) {
                bucketService.removeFromBucket(bucketService.getBucket(principal.getName()), id);
                break;
            }
        }
        return "redirect:/bucket";
    }

//    @MessageMapping("/removeProduct")
//    @Transactional
//    public void massageRemoveProduct(ProductDTO dto, Principal principal) {
//        Long id = dto.getId();
//        for (int i = 0; i < bucketService.getBucketByUser(principal.getName()).getBucketDetails().size(); i++) {
//            if (bucketService.getBucketByUser(principal.getName()).getBucketDetails().get(i).getProductId().equals(id)) {
//                bucketService.removeFromBucket(bucketService.getBucket(principal.getName()), id);
//                break;
//            }
//        }
//    }

    @GetMapping("/bucket/add/{id}")
    public String addFromBucket(@PathVariable(value = "id") Long id, Principal principal) {
        productService.addToUserBucket(id, principal.getName());
        return "redirect:/bucket";

    }

    @GetMapping("/bucket/deleteAll/{id}") // remove from bucket all count product from id
    public String removeFromBucketAllId(@PathVariable(value = "id") Long id, Principal principal) {
        for (int i = 0; i < bucketService.getBucketByUser(principal.getName()).getBucketDetails().size(); i++) {
            if (bucketService.getBucketByUser(principal.getName()).getBucketDetails().get(i).getProductId().equals(id)) {
                bucketService.removeAllIdFromBucket(bucketService.getBucket(principal.getName()), id);
            }
        }
        return "redirect:/bucket";
    }

    @GetMapping("/bucket/order")
    public String aboutOrder(Model model, Principal principal){
        if (principal == null){
            model.addAttribute("bucket", new BucketDTO());
        } else {
            BucketDTO bucketDto = bucketService.getBucketByUser(principal.getName());
            model.addAttribute("bucket", bucketDto);
        }

        return "order";
    }
}

