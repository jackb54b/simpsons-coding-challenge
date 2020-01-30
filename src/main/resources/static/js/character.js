$(function() {

    $("#searchCharacter button[name=search]").click(function( event ) {
        search(event);
    });

    function search(event) {
        var id = $("#searchCharacter input[name=id]").val();
        var firstName = $("#searchCharacter input[name=firstName]").val();
        var lastName = $("#searchCharacter input[name=lastName]").val();
        var age= $("#searchCharacter input[name=age]").val();
        var data = {id: id, firstName: firstName, lastName: lastName, age: age};
        $.ajax({
            type: "POST",
            url: "/character/search",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                var trsHTML = '';
                $.each( data, function( i, item ) {
                    trsHTML += createCharacterTr(item);
                });
                $("tbody[name=characterRows]").empty();
                $('tbody[name=characterRows]').append(trsHTML);
            },
            error: function(XMLHttpRequest) {
                showDivWithMessage(XMLHttpRequest.responseJSON.message);
            }
        });
    }

    function createCharacterTr(item) {
        var tr = '<tr><td>'+ item.id+ '</td><td>'+ item.firstName+ '</td><td>'+ item.lastName+ '</td><td>'+ item.age
        + '</td><td><a href="'+ item.picture + '" target="_blank">Picture</a>'
            + '<td><button data-id="' + item.id + '"class="phraseCharacter btn btn-primary btn-block">Phrase</button></td>'
            + '<td><button data-id="' + item.id + '"class="deleteCharacter btn btn-primary btn-block">Delete</button></td>'
            + '<td><button data-id="' + item.id + '"class="editCharacter btn btn-primary btn-block">Edit</button></td></tr>'
        return tr;
    }

    $("#charctersTable").on("click",".deleteCharacter", (function (e) {
        var id = $(this).data('id')
        $.ajax({
            url: '/character/'+id,
            type: 'DELETE',
            success: function(result) {
                showDivWithMessage('Operation sucess!');
                search(e);
            }
        });
    }));

    $("#charctersTable").on("click",".editCharacter", (function (e) {
        var id = $(this).data('id')
        $.ajax({
            type: "GET",
            url: "/character/"+id,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(data) {
                $("#addCharacter input[name=id]").val(data.id);
                $("#addCharacter input[name=firstName]").val(data.firstName);
                $("#addCharacter input[name=lastName]").val(data.lastName);
                $("#addCharacter input[name=age]").val(data.age);
                $("#addCharacter input[name=picture]").val(data.picture);
            },
            error: function(XMLHttpRequest) {
                showDivWithMessage(XMLHttpRequest.responseJSON.message);
            }
        });

    }));

    $("#addCharacter button[name=add]").click(function( event ) {
        var id = $("#addCharacter input[name=id]").val();
        var firstName = $("#addCharacter input[name=firstName]").val();
        var lastName = $("#addCharacter input[name=lastName]").val();
        var age = $("#addCharacter input[name=age]").val();
        var picture = $("#addCharacter input[name=picture]").val();
        var data = {id:id, firstName: firstName, lastName: lastName, age: age, picture: picture};
        $.ajax({
            type: "POST",
            url: "/character",
            data: JSON.stringify(data),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function(item) {
               showDivWithMessage('Operation sucess! Use search to find new element.');
               $("#addCharacter input[name=id]").val(null);
            },
            error: function(XMLHttpRequest) {
                showDivWithMessage(XMLHttpRequest.responseJSON.message);
                $("#addCharacter input[name=id]").val(null);
            }
        });

    });

    function showDivWithMessage(message) {
        $("#errorDiv").html(message);
        $("#errorDiv").addClass('show');
        $("#errorDiv").show().delay(5000).fadeOut();
    }

});
