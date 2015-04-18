
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
            dataType: "json",
            data: {v1: v1, v2: v2, v3: v3}

        }).done(function (data) {
            for (var item in data) {
                alert(data[item].name);
            }
        });



    }
    $("#testJson").change(function () {
        var type_animal = $("#id_type_animal").val();
        var weight = $("#id_weight").val();
        var age = $("#id_age").val();
        var sterilized = $("#id_sterilized").val();
        var json = {"type_animal": type_animal, "weight": weight, "age": age, "sterilized": sterilized};
        var json2 = {"type_animal": type_animal};
        alert(type_animal);

        $.ajax({
            type: "POST",
            url: "/json",
            data: json,
            success: function (type_animal) {
                alert(type_animal);
            }

        });
    });

//filter 1
    $('#filter1').change(f1);

    $('#filter2').change(f1);

    $('#filter3').change(f1);
});