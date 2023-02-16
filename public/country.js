function resolveCountry() {
    var phoneNumber = $('#phoneNumber').val();

    if (!isValidPhoneNumberFormat(phoneNumber)) {
        $('#show-results').html('Wrong format');
        return;
    }

    if (phoneNumber.startsWith("00")) {
        phoneNumber = "+" + phoneNumber.substring(2, phoneNumber.length)
    }

    $.ajax({
        url: "/country",
        data:  JSON.stringify(phoneNumber),
        type: "POST",
        contentType: "application/json",
        dataType: "text",
        success: function (data) {
            $('#show-results').html(data);
        },
        error: function (e) {
            $('#show-results').html(e.responseText);
        }
    });
}

function isValidPhoneNumberFormat(phoneNumber) {
  var regex = /^[\+]?[0-9]*$/;
  return regex.test(phoneNumber);
}