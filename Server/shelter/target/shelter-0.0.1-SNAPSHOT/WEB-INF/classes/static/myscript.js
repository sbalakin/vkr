
$(document).ready(function () {

    function f1() {

        v1 = $("#filter1").val();
        v2 = $("#filter2").val();
        v3 = $("#filter3").val();


        alert(v1);
        alert(v2);
        alert(v3);

        $.ajax({
            type: "GET",
            url: "/filter",
            data: {v1: v1, v2: v2, v3: v3}
        }).done(function (data) {
            
        });



    }
//    $("#add_button").click(function () {
//        type_animal = $("#id_type_animal").val();
//        weight = $("#id_weight").val();
//        age = $("#id_age").val();
//        $.ajax({
//            type: "POST",
//            url: "/animals/add",
//            data: {type_animal: type_animal, weight: weight, age: age}
//        }).done(function (data) {
//            $("#age").html('<center id="age" ><h6>' + 'Возраст: ' + '<span>' + age + '</span></h6></center>');
//
//        });
//
//    });

//filter 1
    $('#filter1').change(f1);

    $('#filter2').change(f1);

    $('#filter3').change(f1);
});