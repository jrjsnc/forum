
var maxLength = 255;
$('#textareaChars').keyup(function() {
  var length2 = $(this).val().length;
  var length = maxLength-length2;
  $('#chars').text(length);
});