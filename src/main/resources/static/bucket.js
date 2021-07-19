var stomp = null;
var sum = 0;
var count =1;

// подключаемся к серверу по окончании загрузки страницы
window.onload = function() {
    connect();
};

function connectBucket() {
    var socket = new SockJS('/socket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stomp.subscribe('/topic/bucket', function (bucket) {
            renderItemBucket(bucket);
        });
    });
}

// хук на интерфейс
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendContent(); });
    $( "#send" ).click(function() { sendContentBucket(); });
});

// отправка сообщения на сервер
function sendContentBucket() {
    stomp.send("/app/bucket", {}, JSON.stringify({
        'id': $("#id").val()
        // ,
        // 'amount': $("#amount").val(),
        // 'sum': $("#sum").val()
    }));


}

// рендер сообщения, полученного от сервера
function renderItemBucket(bucketJson) {
    var bucket = JSON.parse(bucketJson.body);
    bucketInfo()
    $("#table").append("<tr>" +
        "<td>" + bucket.id + "</td>" +
        "<td>" + bucket.amount + "</td>" +
        "<td>" + bucket.sum + "</td>" +
    //     // "<td><a href='/products'" + product.id + "/bucket'>Add to bucket</a></td>" +
        "<td>"+
    //     "<td><a onclick=\"javascript:addToBucket(\'" + product.id + "\');\">Add to bucket</a>" +"</td>" +
    //     "<td><a onclick=\"javascript:removeProduct(\'" + product.id + "\');\">Remove</a>" +"</td>" +
        "</td>" +
        "</tr>");
}

function bucketInfo(id) {
    stomp.send("/app/bucket", {}, JSON.stringify({'id':id}));
    $("#bucket").text("Bucket " + "Count: " + $("#bucket.amount") + "Sum: "+ $("#bucket.sum"));
    count = count +1;
    sum = sum + $("#price").val();

}

function removeProduct(id) {
    stomp.send("/app/removeProduct", {}, JSON.stringify({'id':id}));
}