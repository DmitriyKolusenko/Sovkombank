list_phones = [];
$("#logout_btn").click(function() {
    $.ajax({
        type: "GET",
        url: "http://localhost:8095/logout",
        success: function(result) {
            $("#logout_btn").addClass("disabled");
            $("#inputNumbersForm").addClass("disabled");
            $("#inputDialog").removeClass("disabled");
        },
        error: function(xhr, status, error) {
           alert(xhr + " " + status + " " + error);
        }
    });
});

var loginData = {
    setLoginForm: function() {
        $("#loginSubmit").click(function() {
            username = $("input#username").val();
            password = $("input#password").val();
            $.ajax({
                type: "POST",
                url: "http://localhost:8095/loginAction",
                data: {
                   'username': username,
                   'password': password
                },
                dataType: 'json',
                success: function(result) {
                    sessionStorage.setItem('user_token', result.token);
                    $("#logout_btn").removeClass("disabled");
                    $("#inputNumbersForm").removeClass("disabled");
                    $("#inputDialog").addClass("disabled");

                },
                error: function(xhr, status, error) {
                   alert(xhr + " " + status + " " + error);
                }
            });
        });
    },
    setInputInvitedNumbersForm: function() {
        createInputInvitedNumbersForm();
    }
}

loginData.setLoginForm();
loginData.setInputInvitedNumbersForm();

function createInputInvitedNumbersForm() {
    container = $("#container_for_phones");
    $("#add_phone").click(function() {
        data = {"phone": $("input#phone").val(), "message": $("input#message").val()};
        htmlStr = "<div class=\"container_phone\">" + data.phone + "</div>"
        list_phones.push(data);
        container.append(htmlStr)
    });
    $("#invite_numbers").click(function() {
        str = JSON.stringify(list_phones);
        $.ajax({
            type: "POST",
            url: "http://localhost:8095/sendInvite",
            contentType: 'application/json; charset=utf-8',
            data: str,
            dataType: 'json',
            success: function(result) {
               alert(result.message);
               list_phones = [];
               container.empty();
            },
            error: function(xhr, status, error) {
                response = JSON.parse(xhr.responseText)
                alert("status: " + response.status + "message: " + response.message);
                list_phones = [];
                container.empty();
            }
        });
    });
}