package com.andreev.demoshop.endpoint;

import com.andreev.demoshop.domain.Order;
import com.andreev.demoshop.service.OrderServiceImpl;
import com.andreev.demoshop.service.UserService;
import com.andreev.demoshop.ws.order.GetOrderRequest;
import com.andreev.demoshop.ws.order.GetOrderResponse;
import com.andreev.demoshop.ws.order.OrderWS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

public class OrderEndpoint {
    public static final String NAMESPACE_URL = "http://andreev.com/demoshop/ws/order";

    private UserService userService;
    private OrderServiceImpl orderService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderEndpoint(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @PayloadRoot(namespace = NAMESPACE_URL, localPart = "getOrderRequest")
    @ResponsePayload // полезная нагрузка
    public GetOrderResponse getOrderWs(@RequestPayload GetOrderRequest request) {
        GetOrderResponse response = new GetOrderResponse();
        orderService.getAll().forEach(order -> response.getOrder().add(createOrdersWs(order)));
        return response;
    }

    private OrderWS createOrdersWs(Order order){
        OrderWS orderWS = new OrderWS();
        orderWS.setId(order.getId());
        orderWS.setUsername(order.getUser().getName());
        orderWS.setSumma(order.getSum().doubleValue());
        orderWS.setProductAmount(order.getDetails().get(0).getAmount().toBigInteger());

        try {
            orderWS.setCreatedDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(order.getCreated().toString()));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return orderWS;
    }
}
