var stomp = null;


// подключаемся к серверу по окончании загрузки страницы
window.onload = function() {
    connect();
};

function connect() {
    var socket = new SockJS('/socket');
    stomp = Stomp.over(socket);
    stomp.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stomp.subscribe('/topic/products', function (product) {
            renderItem(product);
        });
    });
}

// хук на интерфейс
$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#send" ).click(function() { sendContent(); });
});

// отправка сообщения на сервер
function sendContent() {
    stomp.send("/app/products", {}, JSON.stringify({
        'title': $("#title").val(),
        'price': $("#price").val()
    }));


}

// рендер сообщения, полученного от сервера
function renderItem(productJson) {
    var product = JSON.parse(productJson.body);
    $("#table").append("<tr>" +
        "<td>" + product.title + "</td>" +
        "<td>" + product.price + "</td>" +
        "<td>"+
        "<td><a onclick=\"javascript:addToBucket(\'" + product.id + "\' + \'\">Add to bucket</a>" +"</td>" +
        // "<td><a onclick=\"javascript:removeProduct(\'" + product.id + "\');\">Remove</a>" +"</td>" +
        "</td>" +
        "</tr>");
}

function addToBucket(id, productJson) {
    stomp.send("/app/addToBucket", {}, JSON.stringify({'id':id}));
    $("#bucket").text("Bucket " + "Count: "  + "Sum: ");

}

function removeProduct(id) {
    stomp.send("/app/removeProduct", {}, JSON.stringify({'id':id}));
}
